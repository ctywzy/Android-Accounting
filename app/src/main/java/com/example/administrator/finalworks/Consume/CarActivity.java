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

import com.example.administrator.finalworks.Adapter.MyAdaptercar;
import com.example.administrator.finalworks.Adapter.MyAdapterfood;
import com.example.administrator.finalworks.MySQLiteOpenHelper;
import com.example.administrator.finalworks.R;
import com.example.administrator.finalworks.bean.Car;
import com.example.administrator.finalworks.bean.Food;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity {
    private static final String TAG = CarActivity.class.getSimpleName();
    private String id;

    private List<Car> cars = new ArrayList<>();
    private MyAdaptercar myAdaptercar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        id  = getIntent().getStringExtra("id").toString();
        myAdaptercar = new MyAdaptercar(cars, getApplicationContext());
        ((ListView) findViewById(R.id.listcar)).setAdapter(myAdaptercar);
        //Log.i(TAG, "onCreate: "+ id);
        show();
    }

    public void exit(View view) {
        finish();
    }

    public void add(View view) {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
        String apply = ((EditText) findViewById(R.id.carapply)).getText().toString();
        String cost = ((EditText) findViewById(R.id.carcost)).getText().toString();

        if("".equals(apply)||"".equals(cost)){
            Toast.makeText(getApplicationContext(),"用途或者金额不能为空",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues values = new ContentValues();
            values.put("apply", apply);
            values.put("cost", cost);
            values.put("idname", id);
            database.insert("car", null, values);
        }
        database.close();
        show();
    }

    public void show(){
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.query("car", new String[]{"id", "apply", "cost"}, "idname=?",new String[]{id},null,null,null);
        cars.clear();
        double sum = 0;
        while(cursor.moveToNext()){
            int s1 = cursor.getInt(0);
            String s2 = cursor.getString(1);
            String s3 = cursor.getString(2);
            Car car = new Car();
            sum+=cursor.getDouble(2);
            car.setId(s1);
            car.setApply(s2);
            car.setCost(Double.parseDouble(s3));
            cars.add(car);
        }
        myAdaptercar.notifyDataSetChanged();
        database.close();
        ((TextView) findViewById(R.id.carsum)).setText("出行总金额：" + String.format("%.2f", sum)+"￥");
    }

    public void refresh(View view) {
        show();
    }
}
