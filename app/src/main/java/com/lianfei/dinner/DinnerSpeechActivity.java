package com.lianfei.dinner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.lianfei.speech.setting.IatSettings;

import java.util.List;

/**
 * Created by lianfei1314 on 2016/9/22.
 */
public class DinnerSpeechActivity extends AppCompatActivity{
    private final static String TAG = DinnerSpeechActivity.class.getSimpleName();

    private DinnerCarte dinnercarte = null;
    private int dinnertable_id = 0;
    // 设置桌号
    private TextView item_table_index = null;
    // 语音设置
    private ImageButton imagebutton_iat_setting = null;
    // 已点餐展示
    private ListView listview_foods = null;
    // 录音点餐按钮
    private Button iat_dinner = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinner_speech_layout);

        Intent intent = getIntent();
        dinnertable_id = intent.getIntExtra(DinnerCarte.DINNERTABLE_ID_KEY,0);
        dinnercarte = MainActivity.dinnercarte;

        // 初始化状态
        initView();
    }

    private void initView() {
        // 获取控件
        item_table_index = (TextView)findViewById(R.id.item_table_index);
        imagebutton_iat_setting = (ImageButton)findViewById(R.id.image_iat_set);
        listview_foods = (ListView)findViewById(R.id.iat_listview);
        iat_dinner = (Button)findViewById(R.id.iat_recognize);

        // 设置tableid
        item_table_index.setText(String.valueOf(dinnertable_id));
        // 设置imagebutton的设置界面
        imagebutton_iat_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动新的activity，进入设计界面
                startActivity(new Intent(DinnerSpeechActivity.this, IatSettings.class));
            }
        });
        // 设置listview展示已经点的菜
        listview_foods.setAdapter(new SimpleAdapter());
    }

    private class SimpleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dinnercarte.GetCurrentFoodIndexs(dinnertable_id).size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (ViewHolder)convertView.getTag();
            } else {
                convertView = LayoutInflater.from(DinnerSpeechActivity.this).inflate(R.layout.item_speech_goods_layout,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            FillValue(position,viewHolder);
            return convertView;
        }

        private void FillValue(final int position, ViewHolder viewHolder) {
            int index = dinnercarte.GetCurrentFoodIndexs(dinnertable_id).get(position);
            String name = dinnercarte.GetFoodNameByIndex(dinnertable_id,index);
            int number = dinnercarte.GetFoodNumberByIndex(dinnertable_id,index);
            viewHolder.textview_name.setText("菜名： " + name + "  数量： ");
            viewHolder.textview_number.setText(String.valueOf(number));
            // 设置Listener
            SetEventListener(position,index,viewHolder);
        }

        private void SetEventListener(final int position, final int index, final ViewHolder viewHolder) {
            viewHolder.textview_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 构建一个新的Dialog获取item number
                    AlertDialog.Builder dialog_builder = new AlertDialog.Builder(DinnerSpeechActivity.this);
                    // 获取自定义布局
                    View item_number_input_layout = LayoutInflater.from(DinnerSpeechActivity.this).inflate(R.layout.item_number_input_layout,null);
                    final EditText input_edittext = (EditText)item_number_input_layout.findViewById(R.id.item_number_input_edittext);
                    input_edittext.setText(viewHolder.textview_number.getText().toString());
                    input_edittext.setSelection(viewHolder.textview_number.getText().length());
                    dialog_builder.setIcon(R.mipmap.w0)
                            .setTitle("请输入数量:")
                            .setView(item_number_input_layout)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String str = input_edittext.getText().toString();
                                    int nitems = 0;
                                    if (!str.equals("")) {
                                        nitems = Integer.valueOf(str).intValue();
                                    }
                                    dinnercarte.DinnerOrder(dinnertable_id,index,nitems);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .setCancelable(true)
                            .create()
                            .show();
                }
            });
        }
    }

    static class ViewHolder {
        TextView textview_name = null;
        TextView textview_number = null;

        ViewHolder(View view) {
            textview_name = (TextView)view.findViewById(R.id.item_speech_food);
            textview_number = (TextView)view.findViewById(R.id.item_speech_food_number);
        }
    }
}
