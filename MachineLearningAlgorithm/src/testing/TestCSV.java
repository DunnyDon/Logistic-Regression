package testing;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import csvReader.ReadCSV;
public class TestCSV {

	@Test
	public void testlinecount() {
		File csv = new File("C:/Users/Conor/Documents/owls15.csv");
		ReadCSV readcsv = new ReadCSV(csv, 30);
		assertEquals(135,readcsv.getlineNum());
	}
	
	@Test
	public void testArrayLength(){
		File csv = new File("C:/Users/Conor/Documents/owls15.csv");
		ReadCSV readcsv = new ReadCSV(csv, 30);
		for(int i=0;i<readcsv.getTestData().size();i++){
			System.out.println(readcsv.getTestData().get(i).getOutcome());
		}
		assertEquals(40,readcsv.getTestData().size());
	}
	
	@Test
	public void countOutComes(){
		File csv = new File("C:/Users/Conor/Documents/owls15.csv");
		ReadCSV readcsv = new ReadCSV(csv, 30);
		//System.out.println(readcsv.getTrainingData().length);
		assertEquals(3,readcsv.getOutcomes().size());
	}

}
