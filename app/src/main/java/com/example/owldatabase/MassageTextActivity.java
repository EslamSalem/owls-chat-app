package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MassageTextActivity extends AppCompatActivity {
    private TextView massageSubject;
    private TextView massageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage_text2);
        massageSubject = findViewById(R.id.massage_text_activity_subject);
        massageText = findViewById(R.id.massage_text_activity_text);
        if (getIntent().getExtras()!=null) {
            if (!getIntent().getExtras().getString("Subject").equals("")) {
                massageSubject.setText("subject: " + getIntent().getExtras().getString("Subject"));
            } else {
                massageSubject.setText("subject: NONE ");
            }
        }
        if (!getIntent().getExtras().getString("Text").equals("")) {
            massageText.setText(getIntent().getExtras().getString("Text"));
        } else {
            massageText.setText("");
        }
    }

}