/**
 * 
 */
package org.richfaces.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIRichMessages.FacesMessageWithId;

/**
 * @author asmirnov
 *
 */
public class UIMessagesTest extends AbstractAjax4JsfTestCase {

	private static final class MockRichMessages extends UIRichMessages {
		private String level = "ALL";

		@Override
		public String getLevel() {
			return level;
		}

		@Override
		public void setLevel(String level) {
			this.level = level;
			
		}

		public boolean isKeepTransient() {
			return true;
		}

		public void setKeepTransient(boolean ajaxRendered) {
		}
	}

	private static final String INPUT_ID = "input";
	private static final String MESSAGES_ID = "messages";
	private UIRichMessages uiMessages;

	/**
	 * @param name
	 */
	public UIMessagesTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		uiMessages = new MockRichMessages();
		uiMessages.setId(MESSAGES_ID);
		List<UIComponent> children = facesContext.getViewRoot().getChildren();
		children.add(uiMessages);
		HtmlInputText inputText = new HtmlInputText();
		inputText.setId(INPUT_ID);
		children.add(inputText);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		this.uiMessages = null;
		super.tearDown();
	}


	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#getMessages(javax.faces.context.FacesContext)}.
	 */
	public void testGetMessages() {
		prepareMessages();
		uiMessages.getMessages(facesContext);
		assertEquals(8, uiMessages.getRenderedMessages().size());
	}

	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#getMessages(javax.faces.context.FacesContext)}.
	 */
	public void testGetMessagesWithFor() {
		prepareMessages();
		uiMessages.setFor(INPUT_ID);
		uiMessages.getMessages(facesContext);
		assertEquals(4, uiMessages.getRenderedMessages().size());
		assertEquals(INPUT_ID, uiMessages.getRenderedMessages().get(0).getClientId());
	}

	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#getMessages(javax.faces.context.FacesContext)}.
	 */
	public void testGetMessagesForGlobal() {
		prepareMessages();
		uiMessages.setGlobalOnly(true);
		Iterator<FacesMessage> iterator = uiMessages.getMessages(facesContext);
		assertEquals(4, uiMessages.getRenderedMessages().size());
		assertTrue(iterator.hasNext());
	}
	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#updateMessages(javax.faces.context.FacesContext, java.lang.String)}.
	 */
	public void testUpdateMessages() {
		prepareMessages();
		ArrayList<FacesMessageWithId> savedMessages = prepareSavedMessages();
		uiMessages.setRenderedMessages(savedMessages);
		uiMessages.updateMessages(facesContext, INPUT_ID);
		assertTrue(uiMessages.isUpdated());
		assertSame(savedMessages,uiMessages.getRenderedMessages());
		assertEquals(6, uiMessages.getRenderedMessages().size());
	}

	/**
	 * @return
	 */
	private ArrayList<FacesMessageWithId> prepareSavedMessages() {
		ArrayList<FacesMessageWithId> savedMessages = new ArrayList<FacesMessageWithId>();
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,"Warn","Old Warn level message");	
		savedMessages.add(new FacesMessageWithId(INPUT_ID,facesMessage));
		savedMessages.add(new FacesMessageWithId(null,facesMessage));
		savedMessages.add(new FacesMessageWithId("bar",facesMessage));
		return savedMessages;
	}

	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#updateMessages(javax.faces.context.FacesContext, java.lang.String)}.
	 */
	public void testUpdateEmptyMessages() {
		prepareMessages();
		uiMessages.updateMessages(facesContext, INPUT_ID);
		assertTrue(uiMessages.isUpdated());
		assertNotNull(uiMessages.getRenderedMessages());
		assertEquals(4, uiMessages.getRenderedMessages().size());
	}
	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#encodeBegin(javax.faces.context.FacesContext)}.
	 * @throws IOException 
	 */
	public void testEncodeBeginFacesContext() throws IOException {
		prepareMessages();
		uiMessages.setRenderedMessages(prepareSavedMessages());
		uiMessages.encodeBegin(facesContext);
		assertNull(uiMessages.getRenderedMessages());
	}

	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#encodeBegin(javax.faces.context.FacesContext)}.
	 * @throws IOException 
	 */
	public void testEncodeBeginAfterUpdate() throws IOException {
		prepareMessages();
		ArrayList<FacesMessageWithId> savedMessages = prepareSavedMessages();
		uiMessages.setRenderedMessages(savedMessages);
		uiMessages.updateMessages(facesContext, null);
		uiMessages.encodeBegin(facesContext);
		assertNotNull(uiMessages.getRenderedMessages());
		assertSame(savedMessages,uiMessages.getRenderedMessages());
		assertEquals(6, uiMessages.getRenderedMessages().size());
	}
	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#getSeverenities()}.
	 */
	public void testGetSeverenities() {
		uiMessages.setLevel("ALL,ERROR");
		List<String> severenities = uiMessages.getSeverenities();
		assertEquals(2, severenities.size());
		assertTrue(severenities.contains("ALL"));
		assertTrue(severenities.contains("ERROR"));
	}

	/**
	 * Test method for {@link org.richfaces.component.UIRichMessages#saveState(javax.faces.context.FacesContext)}.
	 */
	public void testSaveStateFacesContext() {
		prepareMessages();
		uiMessages.getMessages(facesContext);
		Object state = uiMessages.saveState(facesContext);
		MockRichMessages newMessages = new MockRichMessages();
		newMessages.restoreState(facesContext, state);
		assertNotNull(newMessages.getRenderedMessages());
		assertEquals(uiMessages.getRenderedMessages().size(), newMessages.getRenderedMessages().size());
		
	}

	
	public void testAddMessagesForId() throws Exception {
		List<String> severenities = prepareMessages();
		severenities.add("ALL");
		uiMessages.setRenderedMessages(new ArrayList<FacesMessageWithId>());
		uiMessages.addMessagesForId(facesContext, INPUT_ID, severenities);
		assertEquals(4, uiMessages.getRenderedMessages().size());
	}

	public void testAddMessagesForIdNull() throws Exception {
		List<String> severenities = prepareMessages();
		uiMessages.setRenderedMessages(new ArrayList<FacesMessageWithId>());
		uiMessages.addMessagesForId(facesContext, null, severenities);
		assertEquals(4, uiMessages.getRenderedMessages().size());
	}

	public void testAddMessagesForIdWithLevels() throws Exception {
		List<String> severenities = prepareMessages();
		uiMessages.setRenderedMessages(new ArrayList<FacesMessageWithId>());
		severenities.add("ERROR");
		uiMessages.addMessagesForId(facesContext, INPUT_ID, severenities);
		assertEquals(1, uiMessages.getRenderedMessages().size());
	}
	
	public void testMessageSerialization() throws Exception {
		UIRichMessages.FacesMessageWithId message = saveRestore(new UIRichMessages.FacesMessageWithId("clientId", 
			new FacesMessage(FacesMessage.SEVERITY_INFO, "summary", "detail")));
	
		assertEquals("clientId", message.getClientId());
		assertNotNull(message.getMessage());
		assertEquals(FacesMessage.SEVERITY_INFO, message.getMessage().getSeverity());
		assertEquals("summary", message.getMessage().getSummary());
		assertEquals("detail", message.getMessage().getDetail());
		
		message = saveRestore(new UIRichMessages.FacesMessageWithId(null, 
				new FacesMessage()));
	
		assertNull(message.getClientId());
		assertNotNull(message.getMessage());
		assertNull(message.getMessage().getSummary());
		assertNull(message.getMessage().getDetail());
		
		message = saveRestore(new UIRichMessages.FacesMessageWithId(null, 
				new FacesMessage("summary")));

		assertNull(message.getClientId());
		assertNotNull(message.getMessage());
		assertEquals("summary", message.getMessage().getSummary());
		assertEquals("summary", message.getMessage().getDetail());

		message = saveRestore(new UIRichMessages.FacesMessageWithId(null, 
				new FacesMessage("summary", "detail")));

		assertNull(message.getClientId());
		assertNotNull(message.getMessage());
		assertEquals("summary", message.getMessage().getSummary());
		assertEquals("detail", message.getMessage().getDetail());

	}
	
	private <T> T saveRestore(T t) throws IOException, ClassNotFoundException {
		T newTInstance = null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		
		oos.writeObject(t);
		oos.close();

		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
		newTInstance = (T) ois.readObject();
		ois.close();
		
		return newTInstance;
	}
	
	/**
	 * @return
	 */
	private List<String> prepareMessages() {
		addMessages(INPUT_ID);
		addMessages(null);
		List<String> severenities = new ArrayList<String>();
		return severenities;
	}

	/**
	 * @param id TODO
	 * 
	 */
	private void addMessages(String id) {
		facesContext.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Error level message"));
		facesContext.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal","Fatal level message"));
		facesContext.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info","Info level message"));
		facesContext.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_WARN,"Warn","Warn level message"));
	}

}
