package com.example.android.notepad;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

public class InputDialog extends AlertDialog implements OnClickListener{
    private EditText editText;  //编辑框
    private Button btnConfrim, btnCancel;  //确定取消按钮
    private OnEditInputFinishedListener mListener; //接口
    public interface OnEditInputFinishedListener{
        void editInputFinished(String data);
    }

    protected InputDialog(Context context, OnEditInputFinishedListener mListener) {
        super(context);
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        //输入控件
        editText = (EditText)findViewById(R.id.search_data);
        // 确认按钮
        btnConfrim = (Button)findViewById(R.id.btn_confirm);
        btnConfrim.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
            //点击了搜索按钮
            if (mListener != null) {
                String data = editText.getText().toString();
                mListener.editInputFinished(data);
            }
            dismiss();
        }else {
            //取消
            dismiss();
        }
    }

}
