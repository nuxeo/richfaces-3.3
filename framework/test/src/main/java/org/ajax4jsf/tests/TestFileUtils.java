/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.ajax4jsf.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

	class TestFileUtils {
		private static final String ORG_AJAX4JSF_TEST_CHECK_XHTML_FILE = "org.ajax4jsf.test.checkXHTML.file";

		private static final String ORG_AJAX4JSF_TEST_CHECK_XHTML = "org.ajax4jsf.test.checkXHTML";

		private static final String CRLF = "\r\n";
		
		private static Boolean isCheckXHTML = null;
		
		private static String checkXHTMLOutputFile = null;

		private static boolean initialized = false;
		
		private static File testOutputFile = null;
		
		private TestFileUtils(){
			throw new UnsupportedOperationException();
		}
		
		static void checkXHTML(EntityResolver resolver, String response){
			final List<String> messagesList = new ArrayList<String>();
			try{
				DocumentBuilder documentBuilder = createDocumentBuilder();
				documentBuilder.setEntityResolver(resolver);
				setupBuilder(messagesList, documentBuilder);
				TestFileUtils.parseResponse(new InputSource(new StringReader(response)), documentBuilder);
				TestFileUtils.printXHTMLValidationError(response, messagesList);
			}catch(ParserConfigurationException pce){
				System.err.println(pce.getLocalizedMessage());
			}catch(IOException ioe){
				System.err.println(ioe.getLocalizedMessage());
			}
			
		}
		
		static void setupBuilder(final List<String> messagesList, DocumentBuilder documentBuilder) {
			
			documentBuilder.setErrorHandler(new ErrorHandler() {

				public void error(SAXParseException exception) throws SAXException {
					messagesList.add(exception.toString());
				}

				public void fatalError(SAXParseException exception)
						throws SAXException {
					messagesList.add(exception.toString());
				}

				public void warning(SAXParseException exception)
						throws SAXException {
					messagesList.add(exception.toString());
				}

			});
		}
		
		static void initialize(){
			try {
				if (null == isCheckXHTML) {
					isCheckXHTML = Boolean.getBoolean(ORG_AJAX4JSF_TEST_CHECK_XHTML);
					if (isCheckXHTML) {
						checkXHTMLOutputFile = System.getProperty(ORG_AJAX4JSF_TEST_CHECK_XHTML_FILE);
					}
				}
				initialized = true;
			} catch (Exception e) {
				// If we catch Exception there - it is security Manager error.
				System.out.println("Security Manager error:");
				System.err.println(e.getLocalizedMessage());
			}
		}
		
		static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			return documentBuilder;
		}
		
		private static void printErrors(String response, final List<String> messagesList, OutputStreamWriter fw) throws IOException {
			fw.write(CRLF);
			if (!messagesList.isEmpty()) {
				fw.write(response);
				fw.write(CRLF);

				for (String string : messagesList) {
					fw.write(string);
					fw.write(CRLF);
				}
				fw.write(CRLF);
				fw.write(CRLF);
			}
		}
		
		static boolean needXHTMLCheck(){
			if(!initialized ){
				initialize();
			}
			return isCheckXHTML;
		} 
		
		static void parseResponse(InputSource is, DocumentBuilder documentBuilder) {
				try {
					documentBuilder.parse(is);
				} catch (SAXException e) {
					System.err.println(e.getLocalizedMessage());
				} catch (IOException e) {
					System.err.println(e.getLocalizedMessage());
				}
		}
		
		static void printXHTMLValidationError(String response, final List<String> messagesList) throws IOException {
			//checkXHTMLOutputFile = "C:/123.txt";
			OutputStreamWriter fw = null;
			try {
				if (checkXHTMLOutputFile != null) {
					if(null == testOutputFile){
						testOutputFile = new File(checkXHTMLOutputFile);
						//if(testOutputFile.exists()){
						//	testOutputFile.delete();
						//}
						testOutputFile.createNewFile();
					}
						//FileChannel channel = new RandomAccessFile(testOutputFile, "rw").getChannel();

						 //Use the file channel to create a lock on the file.
						// This method blocks until it can retrieve the lock.
						
						//FileLock lock = channel.lock();
						try{
							fw = new FileWriter(testOutputFile, true);
							printErrors(response, messagesList, fw);
						}finally{
							//lock.release();

							// Close the file
							//channel.close();
							fw.close();
						}
				} else {
					fw = new SystemOutWrapper(new ByteArrayOutputStream());
					printErrors(response, messagesList, fw);
				}
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
		
		private static class SystemOutWrapper extends OutputStreamWriter{
			public SystemOutWrapper(OutputStream out) {
				super(out);
				this.setWrapper(System.out);
			}

			public void setWrapper(PrintStream out){
				writer = out;
			}
			
			private PrintStream writer;
			
			public void write(String message){
				writer.println(message);
			}
		}
}
