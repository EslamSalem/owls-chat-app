package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owldatabase.CustomAdabters.FriendsListAdabter;
import com.example.owldatabase.CustomAdabters.MassagesListAdabter;
import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity {

    private OwlUser user;
    private FriendsListAdabter friendsListAdabter;
    private ArrayList<OwlUser> friendsArrayList;
    private  Bundle bundleUser;
    private ImageView imageView;
    private TextView textView;
    private ListView listView;
    private SQLiteHandler sqLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        imageView  = findViewById(R.id.tool_bar_user_image);
        textView = findViewById(R.id.tool_bar_text_view);
        listView = findViewById(R.id.friends_list_view);
        sqLiteHandler =new SQLiteHandler(FriendsListActivity.this);
        bundleUser = getIntent().getExtras();

        if (bundleUser.get("USER_ID") != null) {
            user = sqLiteHandler.getUser(bundleUser.getInt("USER_ID"));
            if(user !=null){
            textView.setText(user.getName());
            imageView.setImageBitmap(user.getUserProfilePicBitMap());}
            friendsArrayList = sqLiteHandler.getUsersFriendsList(bundleUser.getInt("USER_ID"));
            if(friendsArrayList != null) {
                friendsListAdabter = new FriendsListAdabter(FriendsListActivity.this, friendsArrayList);
                listView.setAdapter(friendsListAdabter);
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        //friendsArrayList.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //friendsArrayList.clear();
    }
}