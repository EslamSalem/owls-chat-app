package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class DateActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Button saveDate;
    private int year , month , day ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        saveDate = findViewById(R.id.save_date_);
        calendarView = findViewById(R.id.calendarView);
        year = -1;
        month = -1;
        day = -1;


        calendarView.setOnDateChangeListener((view, year1, month1, dayOfMonth) -> {
            year =year1;
            month = month1;
            day = dayOfMonth;
        });

        saveDate.setOnClickListener(v -> {
            if (year != -1 && month != -1 && day != -1) {
                Intent intent = new Intent(getApplicationContext(),registerpage.class);
                intent.putExtra("YEAR",year);
                intent.putExtra("MONTH",month);
                intent.putExtra("DAY",day);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), year+"-"+month+"-"+day,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}