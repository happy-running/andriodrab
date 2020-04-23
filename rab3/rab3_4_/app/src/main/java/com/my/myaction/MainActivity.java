package com.my.myaction;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ActionMode;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.my.myaction.R.*;


public class MainActivity extends AppCompatActivity {

    private ActionMode actionMode;
    private Resources resources;
    private SimpleAdapter adapter;
    private ListView listView;
    private int currentItem;
    private List<Map<String, Object>> list = new ArrayList<>();
    private String[] names = new String[]{"One", "Two", "Three", "Four", "Five", "Six"};
    // 定义图片适配器数据
    private int[] icons = {drawable.ic_android, drawable.ic_android, drawable.ic_android,
            drawable.ic_android, drawable.ic_android, drawable.ic_android};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        resources = getResources();
        listView = (ListView) findViewById(id.list);
        adapter = new SimpleAdapter(this, list, layout.list_item,
                new String[]{"names", "icons"},
                new int[]{id.item_text, id.item_image});
        setList();
        listView.setAdapter(adapter);

        MultiChoiceListener multiChoiceListener = new MultiChoiceListener(listView, resources);
        // 设置ListView的模式
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        // 为listView添加一个回调方法，用于监听
        listView.setMultiChoiceModeListener(multiChoiceListener);


    }

    private void setList() {
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("icons", icons[i]);
            map.put("names", names[i]);
            list.add(map);
        }
    }
}
