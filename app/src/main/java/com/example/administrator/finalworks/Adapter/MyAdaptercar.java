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

/**
 * Created by Administrator on 2018/12/16.
 */

public class MyAdaptercar extends BaseAdapter {
    private List<Car> cars;
    private Context context;
    public MyAdaptercar(List<Car> cars, Context applicationContext) {
        this.cars = cars;
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Object getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cars.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(context, R.layout.cost_list, null);
        final Car car = cars.get(position);
        ((TextView) view.findViewById(R.id.itemapply)).setText(car.getApply());
        ((TextView) view.findViewById(R.id.itemcost)).setText(String.valueOf(car.getCost())+"￥");//可能会有问题
        Button deletebutton = (Button) view.findViewById(R.id.buttondelete);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(context, "mydb.db", null, 1);
                SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
                database.delete("car", "id=?", new String[]{String.valueOf(car.getId())});
                database.close();

                notifyDataSetChanged();

            }
        });
        return view;
    }
}
