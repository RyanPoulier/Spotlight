package ryanpoulier.spotlight2;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

public class DBhelper extends SQLiteOpenHelper {


    //                         KEY              name_in_table
    public static final String DATABASE_NAME = "SpotlightComplaintsDB"; //database name has been changed multiple times
    public static final int DATABASE_VERSION = 1;

    //code from tut 32
    private static final String CREATE_TABLE = "CREATE TABLE " +
            ComplaintReport.TABLE_NAME + "(" +
            ComplaintReport.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ComplaintReport.COMPLAINT_TITLE + " TEXT," +
            ComplaintReport.SUBMISSION_TIMESTAMP + " TEXT,"+
            ComplaintReport.ISSUE_TYPE  + " TEXT," +
            ComplaintReport.DESCRIPTION + " TEXT," + ComplaintReport.GPS_LOCATION+ " TEXT," +
            ComplaintReport.IMAGE_1_URI + " TEXT," +ComplaintReport.VIDEO_URI + " TEXT," +
            ComplaintReport.STATUS + " TEXT);";

    //based on tutorials available at https://www.youtube.com/watch?v=cp2rL3sAFmI and https://www.youtube.com/watch?v=p8TaTgr4uKM
    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created/opened...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.e("DATABASE OPERATIONS", "Table created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + ComplaintReport.TABLE_NAME);
         onCreate(db);
    }

    public void addData (Integer Id,String complaint_title, String timestamp,String issue_type,String description, String location, String image1uri, String videouri, String status, SQLiteDatabase db){
        ContentValues   contentValues= new ContentValues();
        contentValues.put (ComplaintReport.ID, Id);
        contentValues.put (ComplaintReport.COMPLAINT_TITLE,complaint_title);
        contentValues.put (ComplaintReport.SUBMISSION_TIMESTAMP,timestamp);
        contentValues.put(ComplaintReport.ISSUE_TYPE, issue_type);
        contentValues.put(ComplaintReport.DESCRIPTION, description);
        contentValues.put(ComplaintReport.GPS_LOCATION, location);
        contentValues.put(ComplaintReport.IMAGE_1_URI, image1uri);
        contentValues.put(ComplaintReport.VIDEO_URI, videouri);
        contentValues.put(ComplaintReport.STATUS, status);
        db.insert(ComplaintReport.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");
    }


    // code from https://www.youtube.com/watch?v=KUq5wf3Mh0c and tut 33
    public Cursor getSummaryData(SQLiteDatabase db){
        Cursor cursor;
        String [] projections = {ComplaintReport.ID, ComplaintReport.COMPLAINT_TITLE, ComplaintReport.SUBMISSION_TIMESTAMP};
        // projections, order, sort_by etc.
        cursor = db.query(ComplaintReport.TABLE_NAME,projections,null,null,null,null,ComplaintReport.ID +" DESC", null);
        // SORTED IN DESCENDING ORDER OF PRIMARY KEY ID
        return cursor;
    }

    public Cursor getDetailedData(SQLiteDatabase db, String complaintID) {
        String[] whereArgs = new String[]{complaintID};
        Cursor curs;
        String[] projections = {ComplaintReport.ID, ComplaintReport.COMPLAINT_TITLE, ComplaintReport.SUBMISSION_TIMESTAMP,ComplaintReport.ISSUE_TYPE,ComplaintReport.DESCRIPTION,ComplaintReport.GPS_LOCATION,ComplaintReport.IMAGE_1_URI, ComplaintReport.VIDEO_URI, ComplaintReport.STATUS};
        curs = db.query(ComplaintReport.TABLE_NAME, projections, ComplaintReport.ID + "= ?", whereArgs, null, null, null, null);

        return curs;

    }

    public Cursor getAllData(SQLiteDatabase db) {
        Cursor curs;
        String[] projections = {ComplaintReport.ID, ComplaintReport.COMPLAINT_TITLE, ComplaintReport.SUBMISSION_TIMESTAMP,ComplaintReport.ISSUE_TYPE,ComplaintReport.DESCRIPTION,ComplaintReport.GPS_LOCATION,ComplaintReport.IMAGE_1_URI, ComplaintReport.VIDEO_URI, ComplaintReport.STATUS};
        curs = db.query(ComplaintReport.TABLE_NAME, projections, null, null, null, null, null, null);

        return curs;

    }

    public Cursor search (SQLiteDatabase db, String searchterm){
        String[] whereArgs = new String[]{"%" + searchterm + "%"};
        Cursor curs;
        String[] projections = {ComplaintReport.ID, ComplaintReport.COMPLAINT_TITLE, ComplaintReport.SUBMISSION_TIMESTAMP, ComplaintReport.GPS_LOCATION};
        curs = db.query(ComplaintReport.TABLE_NAME, projections, ComplaintReport.COMPLAINT_TITLE + " like ?", whereArgs, null, null, null, null);

        return curs;
    }
}