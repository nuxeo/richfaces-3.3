package org.richfaces.helloworld.service;

import javax.ejb.Local;

@Local
public interface Manager {
	public String sayHello();
}