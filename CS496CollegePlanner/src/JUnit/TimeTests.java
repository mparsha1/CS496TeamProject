package JUnit;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs.cs496.collegeplanner.models.Time;
import junit.framework.TestCase;

public class TimeTests extends TestCase {
	private Time time1;
	private Time time2;
	private Time time3;
	
	@Before
	public void setUp() {
		time1.setHours(14);
		time1.setMinutes(59);
		
		time2.setHours(0);
		time2.setMinutes(0);
		
		time3.setHours(24);
		time3.setMinutes(45);		
	}
	
	@Test
	public void testToString(){
		assertEquals("2:59pm", time1.toString());
		assertEquals("0:0pm", time2.toString());
		assertEquals("0:45", time3.toString());
	}
}
