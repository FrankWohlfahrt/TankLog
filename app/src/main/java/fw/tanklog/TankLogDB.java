package fw.tanklog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 02.04.2015.
 */
public class TankLogDB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TankLogDB";

    // Table Name
    private static final String TABLE_NAME = "TankLog";

    public TankLogDB(Context context) {
        // TODO Auto-generated method stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        // Create Table Name
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Date TEXT(12)," +
                " Mileage INTEGER," +
                " Fuel REAL);");

        Log.d("CREATE TABLE", "Create Table Successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Re Create on method  onCreate
        onCreate(db);
    }

    // Insert Data
    public long insertData(TankLogTableEntry entry) {
         try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data

            ContentValues Val = new ContentValues();
            Val.put("Date", entry.getDate());
            Val.put("Mileage", entry.getMileage());
            Val.put("Fuel", entry.getFuel());

            long rows = db.insert(TABLE_NAME, null, Val);

            db.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }
    }

    public ArrayList<String> getAllTableEntries() {
        ArrayList<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY ID DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //TankLogTableEntry entry = new TankLogTableEntry();

                String id = cursor.getString(0);
                String date = cursor.getString(1);
                String mileage = cursor.getString(2);
                String fuel = cursor.getString(3);
                String entry = date + "    " + mileage + " km    " + fuel + " Liter";

                //entry.setDate(date);
                //entry.setMileage(Integer.parseInt(mileage));
                //entry.setFuel(Float.parseFloat(fuel));

                // Adding entry to list
                list.add(entry);
            } while (cursor.moveToNext());
        }

        // return list
        return list;

    }
}
