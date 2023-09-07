package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owldatabase.CustomAdabters.FriendsListAdabter;
import com.example.owldatabase.CustomAdabters.MassagesListAdabter;
import com.example.owldatabase.Modle.OwlMassage;
import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;

public class MassagesListActivity extends AppCompatActivity {

    private OwlUser user;
    private MassagesListAdabter massagesListAdabter;
    private ArrayList<OwlMassage> massagesArrayList;
    private  Bundle bundleUser;
    private ImageView imageView;
    private TextView userName;
    private ListView listView;
    private SQLiteHandler sqLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massages_list);
        bundleUser = getIntent().getExtras();
        sqLiteHandler = new SQLiteHandler(MassagesListActivity.this);
        userName = findViewById(R.id.tool_bar_text_view);
        imageView = findViewById(R.id.tool_bar_user_image);
        listView = findViewById(R.id.massages_list_view);



        if (bundleUser.get("USER_ID") != null && bundleUser.get("RECEIVED") != null) {
            user = sqLiteHandler.getUser(bundleUser.getInt("USER_ID"));
            if(user !=null) {
                userName.setText(user.getName());
                imageView.setImageBitmap(user.getUserProfilePicBitMap());
            }
            if(bundleUser.getBoolean("RECEIVED")) {
                massagesArrayList = sqLiteHandler.getReceivedMassages(bundleUser.getInt("USER_ID"));
                if(massagesArrayList != null) {
                    massagesListAdabter = new MassagesListAdabter(MassagesListActivity.this,
                            massagesArrayList, bundleUser.getBoolean("RECEIVED"));
                    listView.setAdapter(massagesListAdabter);
                }
            }else{
                massagesArrayList = sqLiteHandler.getSentMassages(bundleUser.getInt("USER_ID"));
                if(massagesArrayList != null) {
                    massagesListAdabter = new MassagesListAdabter(MassagesListActivity.this,
                            massagesArrayList, bundleUser.getBoolean("RECEIVED"));
                    listView.setAdapter(massagesListAdabter);
                }
            }
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent =new Intent(getApplicationContext(),MassageTextActivity.class);
            intent.putExtra("Subject",massagesArrayList.get(position).getSubject());
            intent.putExtra("Text",massagesArrayList.get(position).getText());
            startActivity(intent);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //massagesArrayList.clear();
    }
}