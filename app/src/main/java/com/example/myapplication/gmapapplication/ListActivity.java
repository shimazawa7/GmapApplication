package com.example.myapplication.gmapapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_add){
            Intent regi_intent = new Intent(ListActivity.this,RegisterActivity.class);
            startActivity(regi_intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private ListView myListView;
    private SQLiteDatabase db;

    public void onStart() {
        super.onStart();

        myListView = (ListView) findViewById(R.id.listView);
        final MyDbHelper myDbHelper = new MyDbHelper(ListActivity.this);
        db = myDbHelper.getWritableDatabase();

        Intent register_intent = getIntent();
        final String[] regData = new String[6];
        if (register_intent != null) {
            regData[0] = register_intent.getStringExtra("name");
            regData[1] = register_intent.getStringExtra("addr");
            regData[2] = register_intent.getStringExtra("tel");
            regData[3] = register_intent.getStringExtra("day");
            regData[4] = register_intent.getStringExtra("etc");
            regData[5] = register_intent.getStringExtra("nullStop");
        }

        try {

            if ("0".equals(regData[5])) {
                insertToDB(regData);
            }
            Cursor c = selectToDB();
            //データベースに登録されているものを取得


            String[] from = {myDbHelper.COMPANY_NAME, myDbHelper.ADDRESS};

            int[] to = {R.id.list_name, R.id.list_add};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.listitem, c, from, to, 0);
            myListView.setAdapter(adapter);

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListActivity.this);

                    TextView item = (TextView) view.findViewById(R.id.list_name);
                    final TextView add = (TextView) view.findViewById(R.id.list_add);
                    alert.setTitle(item.getText());
                    //アラートの表示

                    alert.setMessage(add.getText().toString());
                    alert.setPositiveButton("mapに表示", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent ListIntent = new Intent(ListActivity.this, MapsActivity.class);
                            ListIntent.putExtra("addr", add.getText().toString());
                            startActivity(ListIntent);
                        }
                    });
                    alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private void insertToDB(String[] regData) throws Exception {

        ContentValues values = new ContentValues();

        values.put(MyDbHelper.COMPANY_NAME, regData[0]);
        values.put(MyDbHelper.ADDRESS, regData[1]);
        values.put(MyDbHelper.TEL, regData[2]);
        values.put(MyDbHelper.DAY, regData[3]);
        values.put(MyDbHelper.ETC, regData[4]);

        db.insert(MyDbHelper.TABLE_NAME, null, values);

    }

    private Cursor selectToDB() throws Exception {
        Cursor c = db.rawQuery("SELECT * FROM " + MyDbHelper.TABLE_NAME , null);

        return c;
    }

}