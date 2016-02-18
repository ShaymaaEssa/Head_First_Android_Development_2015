package hfad2015.android.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;


/**
 * Created by android on 1/19/2016.
 */
public class DrinkCategoryActivity extends ListActivity {

    Cursor cursor;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get a database object
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        db = starbuzzDatabaseHelper.getReadableDatabase();
        cursor = db.query("Drink",new String[] {"_id","Name"},null,null,null,null,null);
        CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,cursor,new String[]{"Name"},new int [] {android.R.id.text1},0);

        ListView listDrinks = getListView();
        listDrinks.setAdapter(listAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this,DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_VALUE,(int)id);
        startActivity(intent);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
