package com.example.administrator.finalworks.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.finalworks.MySQLiteOpenHelper;
import com.example.administrator.finalworks.R;
import com.example.administrator.finalworks.bean.Car;
import com.example.administrator.finalworks.bean.Shopping;

import java.util.List;

/**
 * Created by Administrator on 2018/12/16.
 */

public class MyAdaptershopping extends BaseAdapter {
    private List<Shopping> shops;
    private Context context;

    public MyAdaptershopping(List<Shopping> shops, Context applicationContext) {
        this.shops = shops;
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Object getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return shops.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(context, R.layout.cost_list, null);
        final Shopping shop = shops.get(position);
        ((TextView) view.findViewById(R.id.itemapply)).setText(shop.getApply());
        ((TextView) view.findViewById(R.id.itemcost)).setText(String.valueOf(shop.getCost())+"￥");
        Button deletebutton = (Button) view.findViewById(R.id.buttondelete);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(context, "mydb.db", null, 1);
                SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
                database.delete("shopping", "id=?", new String[]{String.valueOf(shop.getId())});
                database.close();

                notifyDataSetChanged();

            }
        });
        return view;
    }
}
