package hfad2015.android.starbuzz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DrinkActivity extends ActionBarActivity {
    public static final String EXTRA_VALUE = "itemClicked";
    CheckBox chkFav;
    SQLiteOpenHelper starbuzzHelper;
    SQLiteDatabase db;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        final int drink_id =(Integer) getIntent().getExtras().get(EXTRA_VALUE);


       try {
           starbuzzHelper = new StarbuzzDatabaseHelper(this);
           db = starbuzzHelper.getWritableDatabase();
           cursor = db.query("Drink", new String[]{"Name", "Desc", "Img_Resource_id", "Fav"}, "_Id=?", new String[]{Integer.toString(drink_id)},
                   null, null, null);
           if (cursor.moveToFirst()) {

               TextView txt_name = (TextView) findViewById(R.id.txt_name);
               txt_name.setText(cursor.getString(0));

               TextView txt_desc = (TextView) findViewById(R.id.txt_desc);
               txt_desc.setText(cursor.getString(1));

               ImageView img_drink = (ImageView) findViewById(R.id.img_drink);
               img_drink.setImageResource(cursor.getInt(2));
               img_drink.setContentDescription(txt_name.getText().toString());

               chkFav = (CheckBox) findViewById(R.id.chkbox_drinkactivity_fav);
               chkFav.setChecked(cursor.getInt(3) == 1);
           }

           chkFav.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   ContentValues drinkValues = new ContentValues();
                   drinkValues.put("Fav", chkFav.isChecked());
                   db.update("Drink", drinkValues, "_id=?", new String[]{Integer.toString(drink_id)});
               }
           });


       }
       catch (SQLiteException e){
           Toast.makeText(this,"Error msg in database",Toast.LENGTH_SHORT).show();
       }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
