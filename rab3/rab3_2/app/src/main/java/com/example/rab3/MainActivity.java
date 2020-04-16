package com.example.rab3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button btnOne;
    private TextView textView;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////        // 找到按钮控件
        btnOne = (Button) findViewById(R.id.btn_one);
//        textView = findViewById(R.id.text_one);
        // 匿名内部类实现事件监听
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    // 按钮点击事件，还可以通过继承事件监听类来实现事件监听
    public void click_one(View v) {
        System.out.println(v.getId());
        btnOne.setText("登录");
        builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.activity_dialog);
        builder.show();
    }

    public void click_text_one(View v) {
        System.out.println(v.getId());
        textView.setText("点击文本域");
        textView.setBackgroundColor(105);
        Toast.makeText(this, "" + v.getId(), Toast.LENGTH_LONG).show();
    }

}
