package com.example.administrator.finalworks.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.finalworks.MySQLiteOpenHelper;
import com.example.administrator.finalworks.R;
import com.example.administrator.finalworks.bean.Car;
import com.example.administrator.finalworks.bean.Food;

import java.util.List;

import static com.example.administrator.finalworks.Consume.FoodActivity.myAdapterfood;

/**
 * Created by Administrator on 2018/12/15.
 */

public class MyAdapterfood extends BaseAdapter {
    private static final String TAG = MyAdapterfood.class.getSimpleName();
    private List<Food> foods;
    private Context context;

    public MyAdapterfood(List<Food> foods, Context applicationContext) {
        this.foods = foods;
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return foods.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(context, R.layout.cost_list, null);
        final Food food = foods.get(position);
        ((TextView) view.findViewById(R.id.itemapply)).setText(food.getApply());
        ((TextView) view.findViewById(R.id.itemcost)).setText(String.valueOf(food.getCost())+"￥");//可能会有问题
        Button deletebutton = (Button) view.findViewById(R.id.buttondelete);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(context, "mydb.db", null, 1);
                SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
                Log.i(TAG, "onClick: "+ food.getId());
                database.delete("food", "id=?", new String[]{String.valueOf(food.getId())});
                database.close();
                myAdapterfood.notifyDataSetChanged();

            }
        });
        return view;
    }

}
