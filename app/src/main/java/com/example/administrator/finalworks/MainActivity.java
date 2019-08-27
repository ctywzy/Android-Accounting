package com.example.administrator.finalworks;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ServiceConnection conn;
    private MusicService.MyBinder binder;
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
        if(flag==true){
            playMusic();
        }
        flag=false;
    }

    public void Login(View view) {
        String name = ((EditText) findViewById(R.id.loginame)).getText().toString();

        String password = ((EditText) findViewById(R.id.loginpassword)).getText().toString();
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext(), "mydb.db", null, 1);
        SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();


        Cursor cursor = database.query("users", new String[] { "id", "name" , "password"}, "name=?", new String[] { name }, null, null, null);

        String id=null;
        String s1=null;
        while (cursor.moveToNext()) {
            s1 = cursor.getString(cursor.getColumnIndex("password"));
            id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
        }
        if(password.equals(s1)){
            database.close();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ClassesActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);

        }else{
            database.close();
            Toast.makeText(getApplicationContext(),"用户名不存在或密码错误",Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }
    public void playMusic() {

        if (binder == null) {
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    binder = (MusicService.MyBinder) service;
                    binder.play();
                }
                @Override
                public void onServiceDisconnected(ComponentName name) {}
            };
            bindService(new Intent(this, MusicService.class), conn, BIND_AUTO_CREATE);
        } else {
            binder.resumeMusic();
        }
    }
    public void pauseMusic() {
        binder.pauseMusic();
    }

    public void play(View view) {
        playMusic();
    }

    public void stop(View view) {
        pauseMusic();
    }
}
