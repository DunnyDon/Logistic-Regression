package algorithm_implementation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import DataObj.DataSource;
import csvReader.ReadCSV;

public class LogisticRegression {
	private File f;
	private double [][] thetas;
	public int [] [] confusionmatrix;
	public ArrayList<DataSource> testing;
	public ArrayList<DataSource> training;
	private ReadCSV rcsv;
	private double learning_rate = 0.3;
	private int epochs =19;
	private int deltaiterations=1000;
	private int num_outcomes;
	private int num_attribs;
	private File outputfile;
	//initialise all variables
	public LogisticRegression(File data, int testsplit) throws IOException{
		this.f = data;
		this.outputfile = new File("D:/output3.txt");
		rcsv = new ReadCSV(f,testsplit);
		testing = rcsv.getTestData();
		training = rcsv.getTrainingData();
		num_attribs = training.get(0).getAttribs().length;
		num_outcomes = rcsv.getOutcomes().size();
		thetas = new double[num_outcomes][num_attribs+1];
		//there is always one more coefficient than attribute hence the num_attribs+1 
		//when defining theta
		//theta is a two dimensional array because there is more than one classifier
		confusionmatrix = new int[num_outcomes][num_outcomes];
		initialise_theta();
		initialise_conmatrix();
	}
	
	public void initialise_theta(){
		for(int j=0;j<num_outcomes;j++){
			for(int i=0;i<num_attribs;i++){
					thetas[j][i]=0.5;
					//explicitly define all coefficients as 0
			}
		}
	}
	public void initialise_conmatrix(){
		for(int j=0;j<num_outcomes;j++){
			for(int i=0;i<num_outcomes;i++){
				confusionmatrix[j][i]=0;
				//explicitly define all values as 0
			}
		}
	}
	public void setLearningRate(double l){
		this.learning_rate = l;
	}
	public void setEpochs(int e){
		this.epochs = e;
	}
	public void setDeltaIter(int d){
		this.deltaiterations = d;
	}
	public double[] [] startTraining(){
		 ArrayList<String> outcomes= rcsv.getOutcomes();
		double owltype=0.0;
		double variable=0.0,prediction,deltheta;
		//define your variables
		for(int k=0;k<epochs;k++){
			for(int i = 0; i<training.size();i++){
				//loop through the training set and use each one to help update the thetas
				DataSource training_case = training.get(i);
				for(int outc=0;outc<num_outcomes;outc++){
						variable = 0.0;
						for(int attrib=0;attrib<=training_case.getAttribs().length;attrib++){			
							if(attrib!=0){
								variable += thetas[outc][attrib]*training_case.getAttribute_n(attrib-1);
								//mulitply the second coefficent by the first attribute
								//and so on
							}
							else{
								//for the first coefficient it isn't multiplied by an attribute
								variable += thetas[outc][attrib];
							}
						}
						//use the sigmoid function to make predictions
						prediction = 1.0/(1+(Math.exp(-variable)));
						if(outcomes.get(outc).equals(training_case.getOutcome())){
							owltype=1.0;
							//check to see if the training case matches the classifier being trained
							//e.g. Training Case outcome is BarnOwl and classifier being trained is BarnOwl
						}
						else{
							owltype = 0.0;
							//e.g. Training Case outcome is SnowyOwl and classifier being trained is BarnOwl
						}
						for(int iteration=0;iteration<deltaiterations;iteration++){
							for(int attrib=0;attrib<=training_case.getAttribs().length;attrib++){
								double theta = thetas[outc][attrib];
								if(attrib!=0){
									deltheta= (1.0/(double)(training.size()))*training_case.getAttribute_n(attrib)*(prediction-owltype);
									//see Equation 1.3 for this formula
								}else{
									deltheta= (1.0/(double)(training.size()))*(prediction-owltype);
									//first coefficient is not affected by the attributes
								}
								theta -=(double)(learning_rate/(iteration+1.0))*deltheta;
								//water down the effect of the learning rate through the iterations
								//to help theta converge
								thetas[outc][attrib]=theta;
								//save the updated value for the coefficient in the 2-d array
							}
						}
					}
				
			}
		}
		return thetas;
	}
	
	public int beginTesting(double [] [] thet) throws IOException{
		//now that we have the coefficients for each outcome
		//we can create 3 h(theta)s and whichever is the maximum should be the correct result
		double variable,max=0.0;
		int accuracy=0,ind=0;
		String outcome = "NotAnOwl";
		double [] prediction =new double [num_outcomes];
		for(int i=0;i<testing.size();i++){
			DataSource testing_case = testing.get(i);
				variable = 0;
				for(int outco=0;outco<num_outcomes;outco++){
					for(int attrib=0;attrib<=testing_case.getAttribs().length;attrib++){			
						if(attrib!=0){
							variable += thet[outco][attrib]*testing_case.getAttribute_n(attrib-1);
						}
						else{
							variable += thet[outco][attrib];
							//calculate the value to be used in the sigmoid function
						}
					}
				
					//use the sigmoid function to make predictions
					prediction[outco] = 1.0/(1+(Math.exp(-variable)));
					if(max<prediction[outco]){
						max=prediction[outco];
						ind=outco;
						outcome = rcsv.getOutcomes().get(outco);
					}
				}				
				if(outcome.equals(testing_case.getOutcome())){
					accuracy++;
					write_to_file("Correct \tActual: "+testing_case.getOutcome()+"\tPredicted:"+outcome+"\n");
					confusionmatrix[ind][ind]+=1;
				}
				else{
					write_to_file("Wrong \t\tActual: "+testing_case.getOutcome()+"\t\tPredicted:"+outcome+"\n");
					for(int g=0;g<rcsv.getOutcomes().size();g++){
						if(rcsv.getOutcomes().get(g).equals(testing_case.getOutcome())){
							//check which index the incorrect guess should be saved in
							confusionmatrix[ind][g]+=1;
						}
					}
				}
				max=0.0;
				ind=0;
			}
		printConMatrix();
		//System.out.println("Accuracy of Algorithm is "+accuracy + "/"+testing.size());
		return accuracy;
	}
	public void printConMatrix() throws IOException{
		write_to_file("\n");
		for(int l=0;l<num_outcomes+1;l++){
			if(l==0){
				//print the outcomes
				write_to_file("\t\t Actual\n");
				for(int j=0;j<num_outcomes;j++){
					write_to_file("\t"+rcsv.getOutcomes().get(j));
				}
				write_to_file("\n ");
			}else{
				//print the outcome and the values
				write_to_file(rcsv.getOutcomes().get(l-1));
				for(int j=0;j<num_outcomes;j++){
					write_to_file("\t"+confusionmatrix[l-1][j]);
				}
				write_to_file("\n");
			}
		}
	}
	public void write_to_file(String s) throws IOException{
		BufferedWriter wr = new BufferedWriter(new FileWriter(outputfile,true));
		wr.write(s);
		if(s.contains("\n")){
		wr.newLine();
		}
		wr.flush();
		wr.close();
	}
}
