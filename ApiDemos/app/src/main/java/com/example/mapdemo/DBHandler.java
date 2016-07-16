package com.example.mapdemo;

/**
 * Created by nexar on 7/15/2016.
 */
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    public static final String DATABASE_NAME = "washrooms_db";

    // Table name
    private static final String TABLE_WASHROOMS = "washrooms_tb";

    // Washrooms Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LNG = "lng";
    private static final String KEY_LAT = "lat";
    private static final String KEY_IS_PUBLIC = "is_public";
    private static final String KEY_THUMPS_UP = "thumps_up";
    private static final String KEY_THUMPS_DOWN = "thumps_down";


    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WASHROOMS_TABLE = "CREATE TABLE " + TABLE_WASHROOMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_LNG + " DOUBLE,"
                + KEY_LAT + " DOUBLE,"
                + KEY_IS_PUBLIC + " INTEGER,"
                + KEY_THUMPS_UP + " INTEGER,"
                + KEY_THUMPS_DOWN + " INTEGER" + ")";
        db.execSQL(CREATE_WASHROOMS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WASHROOMS);
        // Creating tables again
        onCreate(db);
    }

    // Adding a new washroom
    public void addWashroom(Washroom washroom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, washroom.getName()); // Washroom Name
        values.put(KEY_LNG, washroom.getLng()); //Washroom longitude
        values.put(KEY_LAT, washroom.getLat()); //Washroom latitude
        values.put(KEY_IS_PUBLIC, washroom.getIs_public()); // Washroom type

        // Inserting Row
        db.insert(TABLE_WASHROOMS, null, values);
        db.close(); // Closing database connection
    }


    // Getting one washroom
    public Washroom getWashroom(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_WASHROOMS, new String[] { KEY_ID,
                        KEY_NAME, KEY_LNG, KEY_LAT, KEY_IS_PUBLIC, KEY_THUMPS_UP, KEY_THUMPS_DOWN },
                        KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Washroom washroom = new Washroom(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Double.valueOf(cursor.getString(2)),
                Double.valueOf(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6))
        );

        cursor.close();

        // return washroom
        return washroom;
    }


    // Getting All Washrooms
    public List<Washroom> getAllWashrooms() {
        List<Washroom> washroomsList = new ArrayList<Washroom>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_WASHROOMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Washroom washroom = new Washroom();
                washroom.setId(Integer.parseInt(cursor.getString(0)));
                washroom.setName(cursor.getString(1));
                washroom.setLng(Double.valueOf(cursor.getString(2)));
                washroom.setLng(Double.valueOf(cursor.getString(3)));
                washroom.setIs_public(Integer.parseInt(cursor.getString(4)));
                washroom.setThumps_up(Integer.parseInt(cursor.getString(5)));
                washroom.setThumbs_down(Integer.parseInt(cursor.getString(6)));

                // Adding contact to list
                washroomsList.add(washroom);

            } while (cursor.moveToNext());
        }

        // return washrooms list
        return washroomsList;
    }

    // Getting washrooms Count
    public int getWashroomsCount() {
        String countQuery = "SELECT * FROM " + TABLE_WASHROOMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }


    // Updating a washroom
    public int updateWashroom(Washroom washroom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, washroom.getName());
        values.put(KEY_LNG, Double.toString(washroom.getLng()));
        values.put(KEY_LAT, Double.toString(washroom.getLat()));
        values.put(KEY_IS_PUBLIC, Integer.toString(washroom.getIs_public()));
        values.put(KEY_THUMPS_UP, Integer.toString(washroom.getThumbs_up()));
        values.put(KEY_THUMPS_DOWN, Integer.toString(washroom.getThumbs_down()));

        // updating row
        return db.update(TABLE_WASHROOMS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(washroom.getId())});
    }

    // Deleting a washroom
    public void deleteWashroom(Washroom washroom) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WASHROOMS, KEY_ID + " = ?",
                new String[] { String.valueOf(washroom.getId()) });
        db.close();
    }

    public List<Washroom> getNearest(double longitude, double latitude){
        List<Washroom> nearestList = new ArrayList<Washroom>();

        SQLiteDatabase db = this.getReadableDatabase();
        String nearestQuery = "SELECT id, ( 3959 * acos( cos( radians("+ Double.toString(longitude) +") ) * cos( radians( lat ) ) * cos( radians( lng ) - radians("+ Double.toString(latitude) +") ) + sin( radians("+ Double.toString(longitude) +") ) * sin( radians( lat ) ) ) ) AS distance FROM "+ TABLE_WASHROOMS +" HAVING distance < 1 ORDER BY distance LIMIT 0 , 20";


        Cursor cursor = db.rawQuery(nearestQuery, null);

        if (cursor.moveToFirst()){
            do {
                Washroom washroom = new Washroom();
                washroom.setId(Integer.parseInt(cursor.getString(0)));
                washroom.setName(cursor.getString(1));
                washroom.setLng(Double.valueOf(cursor.getString(2)));
                washroom.setLng(Double.valueOf(cursor.getString(3)));
                washroom.setIs_public(Integer.parseInt(cursor.getString(4)));
                washroom.setThumps_up(Integer.parseInt(cursor.getString(5)));
                washroom.setThumbs_down(Integer.parseInt(cursor.getString(6)));

                // Adding contact to list
                nearestList.add(washroom);

            } while (cursor.moveToNext());
        }

        return nearestList;
    }
}
