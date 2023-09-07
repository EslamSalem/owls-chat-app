package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private SQLiteHandler sqLiteHandler;
    private ArrayList<OwlUser> owlUserArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqLiteHandler = SQLiteHandler.getInstance(Login.this);
        owlUserArrayList = new ArrayList<OwlUser>();

        EditText username = findViewById(R.id.username_text);
        EditText password = findViewById(R.id.password_text);
        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (!user.equals("") && !pass.equals("")) {
                        username.getText().clear();
                        password.getText().clear();

                        int userID = -1000 ;
                    owlUserArrayList = sqLiteHandler.getAllUsers();
                    if(owlUserArrayList != null) {

                        for (int i = 0; i < owlUserArrayList.size(); i++) {

                            if (user.equals(owlUserArrayList.get(i).getName()) &&
                                    pass.equals(owlUserArrayList.get(i).getPassword())) {
                                userID = owlUserArrayList.get(i).getUserId();
                                break;
                            }
                        }
                    }
                    if(userID > 0) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("USER_ID",userID);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Please Check your username and password!",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Enter your username and password!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

//        int [] images =new int[]{R.drawable._7_,R.drawable._2_,R.drawable._3_,R.drawable._4_,R.drawable._10_};
//        for (int i = 0; i < 5; i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),images[i]) ;
//            OwlUser owlUser = new OwlUser(("hassan"+i), "hassan123"+i,
//                    "2001-9-6", "2021-8-17",bitmap);
//            sqLiteHandler.addUser(owlUser);