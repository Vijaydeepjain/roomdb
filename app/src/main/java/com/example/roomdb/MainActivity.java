package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
 EditText t1,t2,r;
 TextView data;
 Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        r=findViewById(R.id.r);
        data=findViewById(R.id.data);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bgthread().start();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                List<User> users =userDao.getallusers();
                String str="";
                for (User user:users
                     ) {
                    str=str+"\t  "+ user.getUid()+" "+user.getFirstName()+" "+user.getLastName()+"\n\n";
                    data.setText(str);
                }
            }
        });
    }


    class bgthread extends Thread{
        public void run(){
            super.run();
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "room_db").allowMainThreadQueries().build();
            UserDao userDao = db.userDao();
            Boolean check=userDao.is_exist(Integer.parseInt(r.getText().toString()));
            if(check==false){
                userDao.insertAll(new User(Integer.parseInt(r.getText().toString()),t1.getText().toString(),t2.getText().toString()));
                t1.setText("");
                t2.setText("");
            }
            else{
            //userDao.insertAll(new User(Integer.parseInt(r.getText().toString()),t1.getText().toString(),t2.getText().toString()));
            t1.setText("lodu");
            t2.setText("repeat");
            }

        }
    }
}