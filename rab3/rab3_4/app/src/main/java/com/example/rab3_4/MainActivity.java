package com.example.rab3_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private int currentView;
    private List<Map<String, Object>> list = new ArrayList<>();
    private String[] names = new String[]{"One","Two", "Three", "Four", "Five", "Six"};
    // 定义图片适配器数据
    private int[] icons = {R.drawable.r34, R.drawable.r34, R.drawable.r34,
            R.drawable.r34, R.drawable.r34, R.drawable.r34};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.list_item,
                new String[]{"names", "icons"},
                new int[]{R.id.item_text, R.id.item_image});
        setList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // parent表示ListView对象，view表示当前被选择的item,position表示下标
                for(int i=0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                }

                view.setBackgroundColor(Color.parseColor("#00FFFF"));
                currentView = position;
                Toast.makeText(MainActivity.this, MainActivity.this.names[position], Toast.LENGTH_LONG).show();
            }
        });
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode arg0) {
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;//这里必须返回true，否则会导致操作模式创建失败。
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode,
                                               MenuItem item) {
                switch (currentView) {
                    case 0:
                        System.out.println("0");;
                        break;
                    case 1:
                        System.out.println("1");
                        break;
                    default:
                        System.out.println("null");
                }
                switch (item.getItemId()) {
                    case R.id.delete:
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {

            }
        });

        listView.setAdapter(adapter);



    }

    private void setList() {
        for(int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("icons", icons[i]);
            map.put("names", names[i]);
            list.add(map);
        }
    }
}
