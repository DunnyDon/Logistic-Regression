package DataObj;

public class DataSource {
	private String [] attribs;
	private String outcome;
	public DataSource(String ln){
		split(ln);
		//a data object which automatically splits the string into 
		//attributes and outcomes
		//ASSUMES outcome will be the final value in the string
	}
	
	public void split(String l){
		String SplitBy = ",";
		String[] content = l.split(SplitBy);
		//split the string into an array
		attribs = new String[content.length-1];
        for(int i = 0; i<(content.length)-1;i++){
        	attribs[i] = content[i];
        	//save all bar the last into the attributes array
        }
        //save the last value in the string as the outcome
        outcome = content[(content.length)-1];
	}
	
	public String[] getAttribs(){
		return this.attribs;
	}
	public String getOutcome(){
		return this.outcome;
	}
	public void printAttribs(){
		for(int i = 0; i<attribs.length;i++){
			System.out.println(attribs[i]);
			//loop through the attribute array and print each value
		}
	}
	public double getAttribute_n(int index){
		if(index < attribs.length){
			//return the value as double for future calculations
			double value = Double.parseDouble(attribs[index]);
			return value;
		}
		return -1.0;
		//return negative number if index that is passed in is less than 0 or > attribs.length
	}
}
