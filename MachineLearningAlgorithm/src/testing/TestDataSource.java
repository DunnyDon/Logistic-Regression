package testing;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import DataObj.DataSource;
public class TestDataSource {

	@Test
	public void Outcometest() {
		try (BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/Conor/Documents/owls15.csv")))) {
    		String line = br.readLine();
    		DataSource ds = new DataSource(line);
    		assertEquals(ds.getOutcome(),"LongEaredOwl");
		} catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@Test
	public void AttribConversionTest(){
		try (BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/Conor/Documents/owls15.csv")))) {
    		String line = br.readLine();
    		DataSource ds = new DataSource(line);
    		assertEquals(ds.getAttribute_n(2),1.6,0.001);
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@Test
	public void testAttribSize(){
		try (BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/Conor/Documents/owls15.csv")))) {
    		String line = br.readLine();
    		DataSource ds = new DataSource(line);
    		assertEquals(ds.getAttribs().length,4);
		} catch (IOException e) {
            e.printStackTrace();
        }
	}

}
