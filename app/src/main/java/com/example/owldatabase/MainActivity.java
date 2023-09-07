package com.example.owldatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.friends_list:
                            Intent i1 = new Intent(getApplicationContext(),FriendsListActivity.class);
                            i1.putExtra("USER_ID",bundle.getInt("USER_ID"));
                            startActivity(i1);
                        Toast.makeText(getApplicationContext(), "Friends List", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.received_messages:
                        Intent i2 = new Intent(getApplicationContext(),MassagesListActivity.class);
                        i2.putExtra("USER_ID",bundle.getInt("USER_ID"));
                        i2.putExtra("RECEIVED",true);
                        startActivity(i2);
                        Toast.makeText(getApplicationContext(), "Received Messages", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sent_messages:
                        Intent i3 = new Intent(getApplicationContext(),MassagesListActivity.class);
                        i3.putExtra("USER_ID",bundle.getInt("USER_ID"));
                        i3.putExtra("RECEIVED",false);
                        startActivity(i3);
                        Toast.makeText(getApplicationContext(), "Sent Messages", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.send_messages:
                        Intent i4 = new Intent(MainActivity.this, sendMessages.class);
                        i4.putExtra("USER_ID",bundle.getInt("USER_ID"));
                        startActivity(i4);
                        break;
                    case R.id.add_friend:
                        Intent i5 = new Intent(MainActivity.this, AddFriend.class);
                        i5.putExtra("USER_ID",bundle.getInt("USER_ID"));
                        startActivity(i5);
                        break;
                    case R.id.log_out:
                      //  Intent i6 = new Intent(getApplicationContext(),Login.class);
                       // startActivity(i6);
                        finish();
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}