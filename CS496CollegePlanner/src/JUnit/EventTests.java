package JUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs.cs496.collegeplanner.models.Date;
import edu.ycp.cs.cs496.collegeplanner.models.Event;
import edu.ycp.cs.cs496.collegeplanner.models.Notification;
import edu.ycp.cs.cs496.collegeplanner.models.Time;
import junit.framework.TestCase;

public class EventTests extends TestCase {
	private Event event;
	

	@Before
	public void setUp() throws Exception {
		event = new Event();
		
		//set body
		event.setBody("Nothing here");

	
		
		//set Title
		event.setTitle("Dress fitting");
		
		
		
		//add date
		event.setDate(new Date(7,10,2014));
		
		
		//set notification
		
		Notification notif = new Notification();
		notif.setMessage("Dont forget!");
		
		
		event.setNotification(notif);
		
		//ONLY FOR EVENT TYPE
		
		Time start = new Time();
		start.setHours(12);
		start.setMinutes(30);
		Time end = new Time();
		end.setHours(1);
		end.setMinutes(30);
		
		event.setStartTime(start);
		event.setEndTime(end);
		
	}

	@Test
	public final void testGetDate() {
		assertEquals(event.getDate().getDay(), (Integer)7);
		assertEquals(event.getDate().getMonth(), (Integer)10);
		assertEquals(event.getDate().getYear(), (Integer)2014);
			
		
	}

	@Test
	public final void testSetDate() {
		event.setDate(new Date(8, 11, 2015));
		
		assertEquals(event.getDate().getDay(), (Integer)8);
		assertEquals(event.getDate().getMonth(), (Integer)11);
		assertEquals(event.getDate().getYear(), (Integer)2015);
		
		
		
		
	}

	@Test
	public final void testGetTitle() {
		assertEquals("Dress fitting", event.getTitle());
		
	}

	@Test
	public final void testSetTitle() {
		event.setTitle("Wedding day!");
		
		assertEquals("Wedding day!", event.getTitle());
		
		
	}

	@Test
	public final void testGetBody() {
		assertEquals("Nothing here", event.getBody());
		
	}

	@Test
	public final void testSetBody() {
		event.setBody("Actually there is something now");
		
		assertEquals("Actually there is something now", event.getBody());
		
		
	}

	@Test
	public final void testGetNotification() {
		assertEquals("Dont forget!", event.getNotification().getMessage());
		
		
	}

	@Test
	public final void testSetNotification() {
		Notification note = new Notification();
		note.setMessage("hello");
		
		event.setNotification(note);
		
		assertEquals("hello", event.getNotification().getMessage());
		
		
	}

	
	
	//ONLY FOR EVENT CLASS
	
	@Test
	public final void testGetStartTime(){
		
		assertEquals(9, event.getStartTime().getHours());
		
	}
	
	@Test
	public final void testSetStartTime(){
		Time time = new Time();
		time.setHours(6);
		
		event.setStartTime(time);
		
		assertEquals(6, event.getStartTime().getHours());
	
	}
	
	@Test
	public final void testGetEndTime(){
		assertEquals(10, event.getEndTime().getHours());
	}
	
	@Test
	public final void testSetEndTime(){
		Time time = new Time();
		time.setHours(8);
	
		
		event.setEndTime(time);
		
		assertEquals(8, event.getEndTime().getHours());
	}
}
