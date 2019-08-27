package com.example.administrator.finalworks.Consume;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.finalworks.Adapter.MyAdapterfood;
import com.example.administrator.finalworks.MySQLiteOpenHelper;
import com.example.administrator.finalworks.R;
import com.example.administrator.finalworks.bean.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private static final String TAG = FoodActivity.class.getSimpleName();
    private String id;

    private List<Food> foods = new ArrayList<>();
    public static MyAdapterfood myAdapterfood;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        id = getIntent().getStringExtra("id").toString();
        //Log.i(TAG, "onCreate: "+id);
        myAdapterfood = new MyAdapterfood(foods, getApplicationContext());
        ((ListView) findViewById(R.id.listfood)).setAdapter(myAdapterfood);
        show();
    }

    public void exit(View view) {
        finish();
    }

    public void add(View view) {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
        String apply = ((EditText) findViewById(R.id.foodapply)).getText().toString();
        String cost = ((EditText) findViewById(R.id.foodcost)).getText().toString();
        if("".equals(apply)||"".equals(cost)){
            Toast.makeText(getApplicationContext(),"用途或者金额不能为空",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues values = new ContentValues();
            values.put("apply", apply);
            values.put("cost", cost);//double类型
            values.put("idname", id);
            database.insert("food", null, values);
        }
        database.close();
        show();
    }
    public void show(){
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.query("food", new String[]{"id", "apply", "cost"}, "idname=?",new String[]{id},null,null,null);
        foods.clear();
        double sum = 0;
        while(cursor.moveToNext()){
            int s1 = cursor.getInt(0);
            String s2 = cursor.getString(1);
            String s3 = cursor.getString(2);
            Food food = new Food();
            sum+=cursor.getDouble(2);
            food.setId(s1);
            food.setApply(s2);
            food.setCost(Double.parseDouble(s3));
            foods.add(food);
        }
        myAdapterfood.notifyDataSetChanged();
        database.close();
        ((TextView) findViewById(R.id.foodsum)).setText("饮食总金额：" + String.format("%.2f", sum)+"￥");
    }

    public void refresh(View view) {
        show();
    }
}
