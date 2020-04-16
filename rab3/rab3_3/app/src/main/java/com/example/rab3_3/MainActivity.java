package com.example.rab3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText test_text = (EditText) findViewById(R.id.test_text);
        switch (item.getItemId()) {
            case R.id.s10:
                test_text.setTextSize(10*2);
                break;
            case R.id.s16:
                test_text.setTextSize(16*2);
                break;
            case R.id.s20:
                test_text.setTextSize(20*2);
                break;
            case R.id.menu_toast:
                Toast.makeText(MainActivity.this, "你点击了Toast提示！",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.red:
                test_text.setTextColor(Color.RED);
                break;
            case R.id.black:
                test_text.setTextColor(Color.BLACK);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }



}
