package com.example.administrator.finalworks.Consume;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.finalworks.Adapter.MyAdapterfood;
import com.example.administrator.finalworks.Adapter.MyAdaptershopping;
import com.example.administrator.finalworks.MySQLiteOpenHelper;
import com.example.administrator.finalworks.R;
import com.example.administrator.finalworks.bean.Food;
import com.example.administrator.finalworks.bean.Shopping;

import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {
    private static final String TAG = ShoppingActivity.class.getSimpleName();
    private String id;

    private List<Shopping> shops = new ArrayList<>();
    private MyAdaptershopping myAdaptershopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        id  = getIntent().getStringExtra("id").toString();
        myAdaptershopping = new MyAdaptershopping(shops, getApplicationContext());
        ((ListView) findViewById(R.id.listshopping)).setAdapter(myAdaptershopping);
        //Log.i(TAG, "onCreate: "+ id);
        show();
    }

    public void exit(View view) {
        finish();
    }

    public void add(View view) {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
        String apply = ((EditText) findViewById(R.id.shoppingapply)).getText().toString();
        String cost = ((EditText) findViewById(R.id.shoppingcost)).getText().toString();

        if("".equals(apply)||"".equals(cost)){
            Toast.makeText(getApplicationContext(),"用途或者金额不能为空",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues values = new ContentValues();
            values.put("apply", apply);
            values.put("cost", cost);//double类型
            values.put("idname", id);
            database.insert("shopping", null, values);
        }
        database.close();
        show();
    }

    private void show() {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.query("shopping", new String[]{"id", "apply", "cost"}, "idname=?",new String[]{id},null,null,null);
        shops.clear();
        double sum = 0;
        while(cursor.moveToNext()){
            int s1 = cursor.getInt(0);
            String s2 = cursor.getString(1);
            String s3 = cursor.getString(2);
            Shopping shop = new Shopping();
            sum+=cursor.getDouble(2);
            shop.setId(s1);
            shop.setApply(s2);
            shop.setCost(Double.parseDouble(s3));
            shops.add(shop);
        }
        myAdaptershopping.notifyDataSetChanged();
        database.close();
        ((TextView) findViewById(R.id.shoppingsum)).setText("购物总金额：" + String.format("%.2f", sum)+"￥");
    }

    public void refresh(View view) {
        show();
    }
}
