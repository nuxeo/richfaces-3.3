package org.samples;

import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.thoughtworks.selenium.DefaultSelenium;

public class SampleTestBase {

    /** The default selenium instance */
    public DefaultSelenium selenium;

    private SeleniumServer seleniumServer;

    /** Host */
    private String host;

    /** Port */
    private String port;

    /** Protocol */
    private String protocol;

    /** Application name */
    private String applicationName;

    protected static final String serverPort = "8080";

    private static final Object MUTEX = new Object();

    public SampleTestBase(String appName) {
        this("http", "localhost", serverPort, appName);
    }

    public SampleTestBase(String protocol, String host, String port, String appName) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.applicationName = appName;
    }

    @BeforeSuite
    public void startSeleniumServer() throws Exception {
        RemoteControlConfiguration config = new RemoteControlConfiguration();
        config.setMultiWindow(false);
        seleniumServer = new SeleniumServer(false, config);
        seleniumServer.start();
    }

    /**
     * This method are invoked before selenium tests started
     */
    @BeforeTest
    @Parameters( { "browser" })
    public void startSelenium(String browser) {
        synchronized (MUTEX) {
            selenium = createSeleniumClient(protocol + "://" + host + ":" + port + "/", browser);
            selenium.start();
        }
    }

    /**
     * This method are invoked after selenium tests completed
     */
    @AfterTest(alwaysRun = true)
    public void stopSelenium() {
        synchronized (MUTEX) {
            selenium.stop();
            selenium = null;
        }
    }

    @AfterSuite
    public void stopSeleniumServer() throws Exception {
        seleniumServer.stop();
    }

    /**
     * @param url
     * @param browser
     * @return
     */
    private DefaultSelenium createSeleniumClient(String url, String browser) {
        return new DefaultSelenium(host, 4444, browser, url);
    }

    protected void open(String url) {
        selenium.open(protocol + "://" + host + ":" + port + "/" + applicationName + url);
    }

}
