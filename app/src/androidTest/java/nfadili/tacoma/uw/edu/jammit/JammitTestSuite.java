package nfadili.tacoma.uw.edu.jammit;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestSuite;

public class JammitTestSuite extends ActivityInstrumentationTestCase2<Activity> {

    public JammitTestSuite(Class<Activity> activityClass) {
        super(activityClass);
    }

    /**
     * Runs the tests in the correct order so that login is handled before profile tests
     * @return order of tests
     */
    public static TestSuite suite() {
        TestSuite t = new TestSuite();
        t.addTestSuite(LoginActivityTest.class);
        t.addTestSuite(EditProfileActivityTest.class);
        return t;
    }
}
