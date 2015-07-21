package com.example.myapplication.gmapapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        Button regi_button = (Button)findViewById(R.id.regi_button);
        regi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regi_intent = new Intent(RegisterActivity.this, ListActivity.class);
                //入力内容の受け取り
                EditText name = (EditText)findViewById(R.id.name_edit);
                EditText addr = (EditText)findViewById(R.id.addr_edit);
                EditText tel = (EditText)findViewById(R.id.tel_edit);
                EditText day = (EditText)findViewById(R.id.day_edit);
                EditText etc = (EditText)findViewById(R.id.etc_edit);

                regi_intent.putExtra("name", name.getText().toString());
                regi_intent.putExtra("addr", addr.getText().toString());
                regi_intent.putExtra("tel", tel.getText().toString());
                regi_intent.putExtra("day", day.getText().toString());
                regi_intent.putExtra("etc", etc.getText().toString());
                regi_intent.putExtra("nullStop", "0");

                startActivity(regi_intent);

            }
        });

        Button cancel_button = (Button)findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(RegisterActivity.this, ListActivity.class);
                startActivity(cancel);
            }
        });
    }
}
