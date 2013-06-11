package org.richfaces;

public interface RichSeleniumTest {

    public void startSelenium(String browser, String filterPrefix);

    public void stopSelenium();

    public String getTestUrl();

}
