package hfad2015.android.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by android on 2/16/2016.
 */
public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Starbuzz";
    private static final int DB_VERSION = 1;

    public StarbuzzDatabaseHelper (Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        updateMyDatabase(db,0,DB_VERSION);


    }

    private void insertDrink(SQLiteDatabase db, String name, String desc, int imgResourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("Name", name);
        drinkValues.put("Desc", desc);
        drinkValues.put("Img_Resource_id", imgResourceId);
        db.insert("Drink", null, drinkValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,oldVersion,newVersion);
    }

    public void updateMyDatabase (SQLiteDatabase db , int oldVersion,int newVersion){
        if (oldVersion<1){
            //create table
            db.execSQL("Create table Drink (_id Integer primary key AutoIncrement,Name Text,Desc Text,Img_Resource_id Integer);");

            //insert values
            insertDrink(db,"Latte","A couple of espresso shots with steamed milk",R.drawable.latte);
            insertDrink(db, "Cappuccino","Espresso, hot milk, and a steamed milk foam",R.drawable.cappuccino);
            insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
        }
        if(oldVersion<newVersion){
            db.execSQL("Alter table Drink add column Fav Numeric;");
        }
    }
}
