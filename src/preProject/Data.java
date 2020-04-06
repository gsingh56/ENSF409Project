package preProject;

/**
 * Represents data within a node
 */
public class Data {
	
	/**
	 * id is the student's id
	 * faculty is the student's faculty 
	 */
	String  id,faculty, major, year;
	
	/**
	 * creates object of type Data.
	 * @param i ID
	 * @param f faculty
	 * @param m major
	 * @param y year
	 */
	public Data( String i, String f, String m, String y)
	{
		id = i;
		faculty = f;
		major = m;
		year = y;
	}
	
	/**
	 * toStringMethod of Data
	 */
	public String toString()
	{
		return (String.format("%-15s%-15s%-10s%-15s", id, faculty, major, year));
	}	
}
