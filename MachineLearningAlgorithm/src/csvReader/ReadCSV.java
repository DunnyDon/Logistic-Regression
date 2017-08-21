package csvReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import DataObj.DataSource;

//write the import statements needed
public class ReadCSV {
		//Reading of the CSV was created as an object so it makes it easier to access different pasts of the dataset information
    	private int linecount = 0;
        String line;
        File f;
        private ArrayList<DataSource> testing, training;
        //initialise and define the variables
        int testsplit;
        public ReadCSV(File file, int t){
        	//pass the file that must be parsed and the testing/training spilt
        	this.f = file;
        	refreshlinecount();
        	//count the amount of lines in the file
        	this.testsplit = t;
        	splitData();
        }
        
        public int getlineNum(){
	       	return linecount;
        }
        public ArrayList<String> getOutcomes(){
        	ArrayList<String> outcomes = new ArrayList<String>();
        	//create an arraylist and fill it with the outcomes, with no duplicates
        	for(int i=0;i<training.size();i++){
        		if(outcomes.contains(training.get(i).getOutcome())==false){
        			//loop through the training data and check to see if the outcome
        			//of the training case is in the arraylist already
        			outcomes.add(training.get(i).getOutcome());
        		}
        	}
        	return outcomes;
        }
        public int getTestSplit(){
        	//returns the test/training spilt
	       	return testsplit;
        }
        
        public void setTestSplit(int t){
	       	this.testsplit = t;
	       	splitData();
	       	//change the test/training split and then you must change the training and testing data
        }
        
        public ArrayList<DataSource> getTestData(){
        	return testing; 
        }
        
        public ArrayList<DataSource> getTrainingData(){
        	return training; 
        }
        
        public void refreshlinecount(){
        	try (BufferedReader br = new BufferedReader(new FileReader(f))) {
	        	while ((line = br.readLine()) != null) {
	        		linecount+=1;
	        		//counts the amount of lines in the file
	        		//technically it counts the rows in the csv file in this case 
	            }	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
        
        public void printfile(){
        	try (BufferedReader br = new BufferedReader(new FileReader(f))) {
        		String SplitBy = ",";
	        	while ((line = br.readLine()) != null) {
	        		String[] content = line.split(SplitBy);
	                for(int i = 0; i<content.length;i++){
	                	System.out.print(content[i]+"\t");
	                	//prints file, cell by cell
	                }
	                System.out.println(" ");
	            }
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
        
        private void splitData(){
        	//splits the data into testing and training data
        	double percent = testsplit/100.0;
        	int num = (int) (percent*linecount);
        	//find the amount of entries that should be in testing and training based on the split
        	testing = new ArrayList<DataSource>();
        	training = new ArrayList<DataSource>();
        	List<Integer> randomdataList = new ArrayList<Integer>();
            for (int i = 0; i < linecount; i++) {
              randomdataList.add(i);
              //add each line number to a list
            }
            Collections.shuffle(randomdataList);
            //shuffle the list to randomize the data and convert it to an array
             //REMOVE THIS ^^ and just pass in the arraylist
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
	        	//read the file
        		for(int i=0; i<linecount;i++){
	        		line = br.readLine();
	        		//read the lines
	        		if(train_test(randomdataList,num,i)){
	        			//check if the line should be in the testing or training data array
	        			testing.add(new DataSource(line));
	        		}
	        		else {
	        			training.add(new DataSource(line));
	        		}
	        	}	
    		}
	    	catch (IOException e) {
		            e.printStackTrace();
	        }
            Collections.shuffle(testing);
            Collections.shuffle(training);
            //shuffle the testing and training data 
        }
        
        private boolean train_test(List<Integer> randomdataList, int n, int i){
        	for (int index = 0; index<n;index++){
        		//loop through the first testsplit % of the array to see if the data should be used as training or testing
        		if(i==randomdataList.get(index)){
        			return true;
        			//data should be used for testing
    			}
    		}
        	return false;
        	//data should be training
        }
}

