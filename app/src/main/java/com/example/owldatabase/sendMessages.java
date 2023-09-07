package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owldatabase.Modle.OwlMassage;
import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class sendMessages extends AppCompatActivity {

    private SQLiteHandler sqLiteHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_messages);
        sqLiteHandler = SQLiteHandler.getInstance(getApplicationContext());

        EditText recipient_text = findViewById(R.id.recipient_text);
        EditText subject_text = findViewById(R.id.subject_text);
        EditText content_text = findViewById(R.id.content_text);
        Button send_button = findViewById(R.id.send_button);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int recipient = -1000;
                boolean foundUser = false;
                try {
                    recipient = Integer.parseInt(recipient_text.getText().toString());
                } catch (InputMismatchException e) {
                    Toast.makeText(getApplicationContext(), "Fields can't be empty!",
                            Toast.LENGTH_SHORT).show();
                }
                ArrayList<OwlUser> users = sqLiteHandler.getAllUsers();
                if (users != null) {
                    for (int i = 0; i < users.size(); i++) {
                        if (recipient == users.get(i).getUserId()) {
                            foundUser = true;
                            break;
                        }
                    }
                }
                if (!foundUser)
                    Toast.makeText(getApplicationContext(), "There Is No Such ID",
                            Toast.LENGTH_SHORT).show();
                String subject = subject_text.getText().toString();
                String content = content_text.getText().toString();

                if (!subject.equals("") && !content.equals("") && foundUser) {
                    OwlMassage owlMassage = new OwlMassage();
                    owlMassage.setText(content);
                    owlMassage.setSubject(subject);
                    owlMassage.setToUser(recipient);

                    if (getIntent().getExtras().get("USER_ID") != null)
                        owlMassage.setFromUser(getIntent().getExtras().getInt("USER_ID"));
                    try {
                        sqLiteHandler.addMassage(owlMassage);
                    } catch (SQLiteException se) {
                        Toast.makeText(getApplicationContext(), se.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), "Done!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fields can't be empty!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}