package com.my.myintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit = findViewById(R.id.edit);
        findViewById(R.id.btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //String url = "http://www.baidu.com";
                        String url2 = edit.getText().toString();

                        Uri uri = Uri.parse(url2);//url为你要链接的地址
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);
//                        Intent choose=Intent.createChooser(intent,"选择一个浏览器");
//                        System.out.println(choose);
//                        startActivity(choose);
                        startActivity(intent);
                    }
                }
        );
    }
}
