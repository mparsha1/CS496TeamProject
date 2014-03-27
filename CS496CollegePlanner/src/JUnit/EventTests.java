package JUnit;

import java.util.ArrayList;

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
		start.setHour(12);
		start.setMinutes(30);
		
		event.setStartTime(start);
		event.setEndTime(new Time(10, 30, "AM"));
		
	}

	@Test
	public final void testGetDate() {
		assertEquals(event.getDate().getDay(), (Integer)7);
		assertEquals(event.getDate().getMonth(), (Integer)10);
		assertEquals(event.getDate().getYear(), (Integer)2014);
		
		assertEquals(task.getDate().getDay(), (Integer)26);
		
		
	}

	@Test
	public final void testSetDate() {
		event.setDate(new Date(8, 11, 2015));
		
		assertEquals(event.getDate().getDay(), (Integer)8);
		assertEquals(event.getDate().getMonth(), (Integer)11);
		assertEquals(event.getDate().getYear(), (Integer)2015);
		
		task.setDate(new Date(29, 8, 2015));
		
		assertEquals(task.getDate().getDay(), (Integer)29);
	}

	@Test
	public final void testGetTitle() {
		assertEquals("Dress fitting", event.getTitle());
		assertEquals("Pick up flowers", task.getTitle());
	}

	@Test
	public final void testSetTitle() {
		event.setTitle("Wedding day!");
		
		assertEquals("Wedding day!", event.getTitle());
		
		task.setTitle("Dont pick up flowers");
		
		assertEquals("Dont pick up flowers", task.getTitle());
		
	}

	@Test
	public final void testGetBody() {
		assertEquals("Nothing here", event.getBody());
		
		assertEquals("Make sure you arrive early", task.getBody());
	}

	@Test
	public final void testSetBody() {
		event.setBody("Actually there is something now");
		
		assertEquals("Actually there is something now", event.getBody());
		
		task.setBody("Make sure they smell good!");
		
		assertEquals("Make sure they smell good!", task.getBody());
		
	}

	@Test
	public final void testGetNotification() {
		assertEquals("Dont forget!", event.getNotification().getMessage());
		assertEquals("Dont forget!", task.getNotification().getMessage());
		
	}

	@Test
	public final void testSetNotification() {
		Notification note = new Notification();
		note.setMessage("hello");
		
		event.setNotification(note);
		
		
		task.setNotification(note);
		
		assertEquals("hello", event.getNotification().getMessage());
		assertEquals("hello", task.getNotification().getMessage());
		
	}

	@Test
	public final void testGetContacts() {
		assertEquals("Bob", event.getContacts().get(0).getName());
		assertEquals("Bill", task.getContacts().get(0).getName());
		
	}

	@Test
	public final void testSetContacts() {
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		Contact contact1 = new Contact();
		Contact contact2 = new Contact();
		Contact contact3 = new Contact();
		contact1.setName("Pocket");
		contact2.setName("Billy");
		contact3.setName("Bobbie");
		contacts.add(contact1);
		contacts.add(contact2);
		contacts.add(contact3);
		
		event.setContacts(contacts);
		task.setContacts(contacts);
		
		assertEquals("Pocket", event.getContacts().get(0).getName());
		assertEquals("Billy", event.getContacts().get(1).getName());
		assertEquals("Bobbie", event.getContacts().get(2).getName());
		
		assertEquals("Pocket", task.getContacts().get(0).getName());
		assertEquals("Billy", task.getContacts().get(1).getName());
		assertEquals("Bobbie", task.getContacts().get(2).getName());
		
	}
	
	//ONLY FOR EVENT CLASS
	
	@Test
	public final void testGetStartTime(){
		
		assertEquals((Integer)9, event.getStartTime().getHour());
		
	}
	
	@Test
	public final void testSetStartTime(){
		Time time = new Time(6,15,"PM");
		
		event.setStartTime(time);
		
		assertEquals((Integer)6, event.getStartTime().getHour());
	
	}
	
	@Test
	public final void testGetEndTime(){
		assertEquals((Integer)10, event.getEndTime().getHour());
	}
	
	@Test
	public final void testSetEndTime(){
		Time time = new Time(8,15,"PM");
		
		event.setEndTime(time);
		
		assertEquals((Integer)8, event.getEndTime().getHour());
	}
}
