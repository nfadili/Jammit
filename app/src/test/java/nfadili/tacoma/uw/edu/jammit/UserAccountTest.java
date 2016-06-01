package nfadili.tacoma.uw.edu.jammit;

import android.util.Log;

import org.junit.Test;

import model.UserAccount;

import static org.junit.Assert.*;

/**
 * Tests integrity of the UserAccount model class.
 */
public class UserAccountTest {

    @Test
    public void testCreateEmptyUserAccount() {
        UserAccount emptyAccount = new UserAccount();
        assertEquals(emptyAccount.getEmail(), "");
        assertEquals(emptyAccount.getPassword(), "");
        assertFalse(emptyAccount.getAuthenticated());
        assertEquals(emptyAccount.getmAge(), "");
        assertEquals(emptyAccount.getmCity(), "");
        assertEquals(emptyAccount.getmStyles(), "");
        assertEquals(emptyAccount.getmInstruments(), "");
        assertEquals(emptyAccount.getmBio(), "");
    }

    @Test
    public void testCreateLoginUserAccount() {
        UserAccount authAccount = new UserAccount("test@email.com", "password", true);
        assertEquals(authAccount.getEmail(), "test@email.com");
        assertEquals(authAccount.getPassword(), "password");
        assertTrue(authAccount.getAuthenticated());
    }

    @Test
    public void testLoadedUserAccount() {
        UserAccount loadedAccount = new UserAccount("Nabil Fadili", "23", "Guitar, Drums", "Death Metal",
                "Tacoma, WA", "This is a test bio of short length");
        assertEquals(loadedAccount.getmName(), "Nabil Fadili");
        assertEquals(loadedAccount.getmAge(), "23");
        assertEquals(loadedAccount.getmInstruments(), "Guitar, Drums");
        assertEquals(loadedAccount.getmStyles(), "Death Metal");
        assertEquals(loadedAccount.getmCity(), "Tacoma, WA");
        assertEquals(loadedAccount.getmBio(), "This is a test bio of short length");
    }

    @Test
    public void testParseUserAccountJSONNull() {
        String JSONString = null;
        UserAccount nullAccount = UserAccount.parseUserAccountJSON(JSONString);
        assertNull(nullAccount);
    }

    @Test
    public void testParseUserAccountJSONFailure() {
        String JSONString = "{\"result\": \"fail\", \"error\": \"Incorrect password.\"}";
        UserAccount emptyAccount = UserAccount.parseUserAccountJSON(JSONString);
        assertEquals(emptyAccount.getEmail(), "");
        assertEquals(emptyAccount.getPassword(), "");
        assertFalse(emptyAccount.getAuthenticated());
    }

    @Test
    public void testParseUserAccountJSONSuccess() {
        String JSONString = "{\"result\": \"success\", \"email\": \"'test@email.com'\"}";
        UserAccount authorizedAccount = UserAccount.parseUserAccountJSON(JSONString);
        assertEquals(authorizedAccount.getEmail(), null);
        assertEquals(authorizedAccount.getPassword(), "");
        assertTrue(authorizedAccount.getAuthenticated());
    }
}