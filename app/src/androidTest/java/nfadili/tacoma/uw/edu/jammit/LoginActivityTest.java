package nfadili.tacoma.uw.edu.jammit;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();

    }

    /**
     * Tests that the LoginActivity loads correctly by checking for known text within the activity.
     */
    public void testLoginFragmentLoads() {
        boolean fragmentLoaded = solo.searchText("Not a user?");
        assertTrue("LoginActivity Loaded", fragmentLoaded);
    }

    /**
     * Tests that known valid login credentials are authenticated and the activity switches to
     * MainMenuActivity.
     */
    public void testLoginInWorks() {
        solo.enterText(0, "nfadili@uw.edu");
        solo.enterText(1, "testing123");
        solo.clickOnButton("Sign in");
        boolean worked = solo.searchText("Hello");
        assertTrue("Login in worked!", worked);
    }

}
