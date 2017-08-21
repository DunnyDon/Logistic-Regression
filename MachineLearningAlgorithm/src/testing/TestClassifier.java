package testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import DataObj.DataSource;
import csvReader.ReadCSV;
import org.junit.Test;

import algorithm_implementation.LogisticRegression;

public class TestClassifier {

	@Test
	public void onesampleTest() throws IOException{
		double sum=0.0;
		int sizeofarr=0;
		for(int i=0;i<10;i++){
			//LogisticRegression lr = new LogisticRegression(new File("C:/Users/Conor/Documents/owls15.csv"),33);
			LogisticRegression lr = new LogisticRegression(new File("C:/Users/Conor/Documents/owls15.csv"),33);
			lr.setLearningRate(0.1);
			lr.setEpochs(150);
			lr.setDeltaIter(5000);
			double[][] test = lr.startTraining();
			double accuracy = lr.beginTesting(test);
			sum+=accuracy;
			//System.out.print(accuracy+"\t");
			lr.write_to_file(i+": "+(double)(accuracy/lr.testing.size())*100+"%\n");
			lr.write_to_file("\n");
			sizeofarr=lr.testing.size();
			if(i==9){
				lr.write_to_file("Average "+(double)((sum/10.0)/sizeofarr)*100+"%\n");
			}
		}
		
		
	}/*
	@Test
	public void lratetest() {
		int iteration=20;
		//define the amount of times you wish for each learning rate to be tested
		//define the learning rates that should be tested
		double [] learning = {0.05,0.1,0.15,0.2,0.25,0.3,0.4,0.5,0.6,0.75,0.8,0.9,1,2,5};
		int [] accur = new int[learning.length];
		//save the accuracies in an array 
		for(int i=0;i<iteration;i++){
			for(int j=0;j<accur.length;j++){
				LogisticRegression lr = new LogisticRegression(new File("C:/Users/Conor/Documents/owls15.csv"),30);
				lr.setLearningRate(learning[j]);
				double[][] test = lr.startTraining();
				accur[j]+=lr.beginTesting(test);
				//add to each one the average can be taken later				
			}
		}
		for(int j=0;j<accur.length;j++){
			System.out.println("Learning Rate:"+learning[j]+" Accuracy:"+(double)(accur[j]/iteration));
			//print and compare each learning rate
		}
	}
	@Test
	public void epochtest() {
		int iteration=20;
		//define the amount of times you wish for each epoch to be tested
		//define the epochs that should be tested
		int [] epoch = {1,2,3,4,5,6,7,8,9,10,12,14,16,18,20,25,50,100};
		int [] accur = new int[epoch.length];
		for(int i=0;i<iteration;i++){
			for(int j=0;j<accur.length;j++){
				LogisticRegression lr = new LogisticRegression(new File("C:/Users/Conor/Documents/owls15.csv"),30);
				lr.setEpochs(epoch[j]); 
				double[][] test = lr.startTraining();
				accur[j]+=lr.beginTesting(test);
				//add to each one the average can be taken later
			}
		}
		for(int j=0;j<accur.length;j++){
			System.out.println("Epoch:"+epoch[j]+" Accuracy:"+(double)(accur[j]/iteration));
			//print and compare each epoch
		}
	}*/
	/*
	@Test
	public void deltaitertest() {
		int iteration=1;
		int [] delit = {15000};
		int [] accur = new int[delit.length];
		for(int i=0;i<iteration;i++){
			for(int j=0;j<accur.length;j++){
				LogisticRegression lr = new LogisticRegression(new File("C:/Users/Conor/Documents/owls15.csv"),30);
				lr.setEpochs(delit[j]); 
				double[][] test = lr.startTraining();
				accur[j]+=lr.beginTesting(test);
				
			}
		}
		for(int j=0;j<accur.length;j++){
			System.out.println("DeltaIter:"+delit[j]+" Accuracy:"+(double)(accur[j]/iteration));
		}
	}*/
}


