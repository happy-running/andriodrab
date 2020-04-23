package com.my.myaction;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

class MultiChoiceListener implements AbsListView.MultiChoiceModeListener {

    // 记录现在选中的是哪一个item
    private int currentItem;
    private ListView listView;
    private Resources resources;
    // 当前的上下文操作模式菜单
    private Menu menu;

    public int getCurrentItem() {
        return currentItem;
    }

    final String TAG = "tag";

    public MultiChoiceListener(ListView listView, Resources resources) {
        this.listView = listView;
        this.resources = resources;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        Log.d(TAG, "onItemCheckedStateChanged" + position);
        currentItem = position;
        for (int i = 0; i < 6; i++) {
            listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        View selectItem = listView.getChildAt(currentItem);
        // 设置选中的item的背景颜色
        selectItem.setBackgroundColor(Color.CYAN);
        // 修改提示信息
        MenuItem item = menu.getItem(0);
        item.setTitle((currentItem + 1) + " selected\t\t\t\t\t\t\t\t\t\t\t\t\t");
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        Log.d(TAG, "onCreateActionMode");
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;

    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        Log.d(TAG, "onPrepareActionMode");
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Log.d(TAG, "onActionItemClicked");
        switch (item.getItemId()) {
            case R.id.action_delete:
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        Log.d(TAG, "onDestroyActionMode");
        // 恢复原来的样式
        for (int i = 0; i < 6; i++) {
            listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
    }
}

