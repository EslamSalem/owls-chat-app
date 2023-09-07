package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class AddFriend extends AppCompatActivity {

    private SQLiteHandler sqLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        sqLiteHandler = SQLiteHandler.getInstance(getApplicationContext());

        EditText friend_id = findViewById(R.id.id_text);
        Button add_friend_button = findViewById(R.id.add_friend_button);
        add_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!friend_id.getText().toString().equals("")) {
                    String friend = friend_id.getText().toString();
                    int friend_ID_ = -1000;
                    boolean foundUser = false;
                    try {
                        friend_ID_ = Integer.parseInt(friend);
                    } catch (InputMismatchException e) {
                        Toast.makeText(getApplicationContext(), "id is numbers only!",
                                Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "check your input!",
                                Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<OwlUser> UsersList = sqLiteHandler.getAllUsers();
                    if (UsersList != null) {
                        for (int i = 0; i < UsersList.size(); i++) {
                            if (friend_ID_ == UsersList.get(i).getUserId()) {
                                foundUser = true;
                                break;
                            }
                        }
                    }
                    if (!foundUser) {
                        Toast.makeText(getApplicationContext(), "There Is No Such ID",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            if(sqLiteHandler.addFriend(getIntent().getExtras().getInt("USER_ID"), friend_ID_))
                            Toast.makeText(getApplicationContext(), "You Are Now Friends!",
                                    Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "You Are Already Friends!",
                                        Toast.LENGTH_SHORT).show();
                        }catch (SQLiteException se){
                            Toast.makeText(getApplicationContext(), se.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                    else {
                        Toast.makeText(getApplicationContext(), "Please Enter a person's ID!",
                                Toast.LENGTH_LONG).show();
                    }
                }

        });
    }
}