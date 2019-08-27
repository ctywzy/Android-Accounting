package com.example.administrator.finalworks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.finalworks.Consume.CarActivity;
import com.example.administrator.finalworks.Consume.FoodActivity;
import com.example.administrator.finalworks.Consume.ShoppingActivity;
import com.example.administrator.finalworks.bean.Car;

public class ClassesActivity extends AppCompatActivity {

    private static final String TAG = ClassesActivity.class.getSimpleName();
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        id  = getIntent().getStringExtra("id").toString();
        Log.i(TAG, "onCreate: "+ id);
        show();
    }

    private void show() {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.query("car", new String[]{"cost"}, "idname=?",new String[]{id},null,null,null);
        double sum=0;
        while(cursor.moveToNext()){
            sum+=cursor.getDouble(0);
        }
        cursor = database.query("food", new String[]{"cost"}, "idname=?",new String[]{id},null,null,null);
        while(cursor.moveToNext()){
            sum+=cursor.getDouble(0);
        }
        cursor = database.query("shopping", new String[]{"cost"}, "idname=?",new String[]{id},null,null,null);
        while(cursor.moveToNext()){
            String s3 = cursor.getString(0);
            sum+=cursor.getDouble(0);
        }
        database.close();
        ((TextView) findViewById(R.id.allsum)).setText("总金额：        " + String.format("%.2f", sum)+"￥");
    }

    public void foodbill(View view) {
        Intent intent = new Intent();
        intent.setClass(ClassesActivity.this, FoodActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void travelbill(View view) {
        Intent intent = new Intent();
        intent.setClass(ClassesActivity.this, CarActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);;
    }

    public void shoppingbill(View view) {
        Intent intent = new Intent();
        intent.setClass(ClassesActivity.this, ShoppingActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    public void exit(View view) {
        finish();
    }
}
