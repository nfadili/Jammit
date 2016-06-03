package nfadili.tacoma.uw.edu.jammit;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import nfadili.tacoma.uw.edu.jammit.EditProfile.EditProfileActivity;

public class EditProfileActivityTest extends ActivityInstrumentationTestCase2<EditProfileActivity> {
    private Solo solo;

    public EditProfileActivityTest() {
        super(EditProfileActivity.class);
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
     * Tests that the edit profile activity loads correctly.
     */
    public void testEditProfileList() {
        boolean activityLoaded = solo.searchText("Save");
        assertTrue("EditProfileActivity loaded!", activityLoaded);
    }

}
