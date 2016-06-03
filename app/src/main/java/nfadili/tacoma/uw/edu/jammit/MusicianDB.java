package nfadili.tacoma.uw.edu.jammit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.UserAccount;

/**
 * Database interface class. Assists in SQLite functionality.
 */
public class MusicianDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Musician.db";
    private static final String MUSICIAN_TABLE = "Musician";

    private CourseDBHelper mMusicianDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public MusicianDB(Context context) {
        mMusicianDBHelper = new CourseDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mMusicianDBHelper.getWritableDatabase();
    }

    /**
     * Inserts the course into the local sqlite table. Returns true if successful, false otherwise.
     * @return true or false
     */
    public boolean insertMusician(String email, String name, String age, String instruments, String styles,
                                  String city, String bio) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("instruments", instruments);
        contentValues.put("styles", styles);
        contentValues.put("city", city);
        contentValues.put("bio", bio);

        long rowId = mSQLiteDatabase.insert("Musician", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    /**
     * Returns the list of musicians from the local Musician table.
     * @return list
     */
    public ArrayList<UserAccount> getMusicians() {

        String[] columns = {
                "email", "name", "age", "instruments", "styles", "city", "bio"
        };

        Cursor c = mSQLiteDatabase.query(
                MUSICIAN_TABLE,                           // The table to query
                columns,                                 // The columns to return
                null,                                    // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        ArrayList<UserAccount> list = new ArrayList<UserAccount>();
        for (int i=0; i<c.getCount(); i++) {
            String email = c.getString(0);
            String name = c.getString(1);
            String age = c.getString(2);
            String instruments = c.getString(3);
            String styles = c.getString(4);
            String city = c.getString(5);
            String bio = c.getString(6);
            UserAccount account = new UserAccount(email, name, age, instruments, styles, city, bio);
            list.add(account);
            c.moveToNext();
        }
        return list;
    }

    /**
     * Delete all the data from the MUSICIAN_TABLE
     */
    public void deleteMusicians() {
        mSQLiteDatabase.delete(MUSICIAN_TABLE, null, null);
    }


}
class CourseDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_COURSE_SQL =
            "CREATE TABLE IF NOT EXISTS Musician "
                    + "(email TEXT PRIMARY KEY, name TEXT, age TEXT, instruments TEXT, styles TEXT, city TEXT, bio TEXT)";

    private static final String DROP_COURSE_SQL =
            "DROP TABLE IF EXISTS Musician";

    public CourseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COURSE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_COURSE_SQL);
        onCreate(sqLiteDatabase);
    }

}
