package org.samples.booking;

import org.samples.SampleTestBase;
import org.testng.annotations.Test;

public class LoginPageTest extends SampleTestBase {

    public LoginPageTest(String appName) {
        super("seam-booking");
    }

    @Test
    public void test() {
        // open start page
        open("");
    }
}
