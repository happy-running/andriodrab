package com.example.mylistview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView itemList;
    private List<Map<String, Object>> list = new ArrayList<>(6);
    private String[] names = new String[]{"Lion","Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    // 定义图片适配器数据
    private int[] icons = {R.drawable.cat, R.drawable.dog, R.drawable.elephant,
            R.drawable.monkey, R.drawable.lion, R.drawable.tiger};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemList = (ListView) findViewById(R.id.list);
        // 为每一个item添加监听
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // parent表示ListView对象，view表示当前被选择的item,position表示下标
                for(int i=0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                }

                view.setBackgroundColor(Color.parseColor("#B22222"));
                Toast.makeText(MainActivity.this, MainActivity.this.names[position], Toast.LENGTH_LONG).show();
            }
        });
        setList();
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.list_item,
                new String[]{"names", "icons"},
                new int[]{R.id.item_text, R.id.item_image});
        itemList.setAdapter(adapter);
    }

    private void setList() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("icons", R.drawable.lion);
        map1.put("names", "Lion");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("icons", R.drawable.tiger);
        map2.put("names", "Tiger");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("icons", R.drawable.monkey);
        map3.put("names", "Monkey");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("icons", R.drawable.dog);
        map4.put("names", "Dog");
        list.add(map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("icons", R.drawable.cat);
        map5.put("names", "Cat");
        list.add(map5);

        Map<String, Object> map6 = new HashMap<>();
        map6.put("icons", R.drawable.elephant);
        map6.put("names", "Elephant");
        list.add(map6);
    }
}
