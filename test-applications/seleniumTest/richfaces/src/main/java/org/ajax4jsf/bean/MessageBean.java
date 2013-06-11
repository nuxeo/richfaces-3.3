/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import org.ajax4jsf.event.PushEventListener;


public class MessageBean implements Runnable {

    private String result;

    private boolean running = true;

    private PushEventListener listener;
    
    private List messages = new ArrayList(10);
    
    private int counter = 0;

    public MessageBean() {
	// TODO Auto-generated constructor stub
    }

    /**
         * @return the result
         */
    public String getResult() {
	return result;
    }

    /**
         * @param result
         *                the result to set
         */
    public void setResult(String result) {
	this.result = result;
    }

    public void addListener(EventListener listener) {
	synchronized (listener) {
	    if (this.listener != listener) {
		this.listener = (PushEventListener) listener;
		Thread th = new Thread(this);
		th.start();

	    }
	}
    }

    public void run() {
	try {
	    while (isRunning()) {
		Thread.sleep(10000);
		Date current = new Date(System.currentTimeMillis());
		if(messages.size()>=10){
		    messages.remove(0);
		}
		messages.add(messages.size(),String.valueOf(counter++)+" at "+current.toString());
		System.out.println("event occurs");
		synchronized (listener) {
		    listener.onEvent(new EventObject(this));
		}
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }

    /**
         * @return the running
         */
    public synchronized boolean isRunning() {
	return running;
    }

    /**
         * @param running
         *                the running to set
         */
    public synchronized void setRunning(boolean running) {
	this.running = running;
    }

    /**
     * @return the messages
     */
    public List getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List messages) {
        this.messages = messages;
    }

}
