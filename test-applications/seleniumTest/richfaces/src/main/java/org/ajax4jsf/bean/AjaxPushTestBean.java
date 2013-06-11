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

import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;

import org.ajax4jsf.event.PushEventListener;

public class AjaxPushTestBean implements Runnable {

    private boolean enabled = false;

    private PushEventListener listener;

    private String content = "content";
    
    private Date startDate;
    
    private Thread thread;
    
    private int eventsSent;

    
    public void start() {
    	if(thread == null) {
    		thread = new Thread(this);
    		thread.setDaemon(true);
    		setStartDate(new Date());
    		thread.start();
    		setEnabled(true);
    	}
    }
    
    public void stop() {
    	if(thread != null) {
    		thread = null;
    		setEnabled(false);
    		setStartDate(null);
    	}
    }
    
    public void run() {
    	while(thread != null) {
            if (((new Date()).getTime() - getStartDate().getTime())>=10000) {
                stop();
            }
            
            listener.onEvent(new EventObject(this));
            eventsSent++;
            
            try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
    	}
    }
    
    public void addListener(EventListener listener) {
        synchronized (listener) {
            if (this.listener != listener) {
                this.listener = (PushEventListener) listener;
            }
        }
    }
        
    
    /**
     * Gets value of content field.
     * 
     * @return value of content field
     */
    public String getContent() {
        return content;
    }

    /**
     * Set a new value for content field.
     * 
     * @param content
     *                a new value for content field
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets value of eventsSent field.
     * 
     * @return value of eventsSent field
     */
    public int getEventsSent() {
        return eventsSent; 
    }    

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	public  Date getStartDate() {
		return startDate;
	}

	public  void setStartDate(Date startDate) {
		synchronized (this) {
			this.startDate = startDate;
		}	
	}

}
