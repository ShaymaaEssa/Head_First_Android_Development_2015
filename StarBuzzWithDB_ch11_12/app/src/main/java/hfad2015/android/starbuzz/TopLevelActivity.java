package hfad2015.android.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class TopLevelActivity extends ActionBarActivity {

    SQLiteOpenHelper starbuzzHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ListView lstviewFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((int)id == 0) {
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        ListView listMainMenu = (ListView)findViewById(R.id.list_mainmenu);
        listMainMenu.setOnItemClickListener(itemClickListener);


        try{
            starbuzzHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzHelper.getReadableDatabase();
            cursor = db.query("Drink", new String[]{"_id", "Name"}, "Fav = ?", new String[]{Integer.toString(1)}, null, null, null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,cursor,new String[]{"Name"},new int [] {android.R.id.text1},0);
            lstviewFav = (ListView)findViewById(R.id.lstview_fav);
            lstviewFav.setAdapter(listAdapter);
            lstviewFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                    intent.putExtra(DrinkActivity.EXTRA_VALUE, (int) id);
                    startActivity(intent);
                }
            });



        }catch (SQLiteException e){
            Toast.makeText(this, "Error msg in database", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cursor = db.query("Drink", new String[]{"_id", "Name"}, "Fav = ?", new String[]{Integer.toString(1)}, null, null, null);
        CursorAdapter adapter = (CursorAdapter)lstviewFav.getAdapter();
        adapter.changeCursor(cursor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
