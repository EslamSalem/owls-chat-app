package com.example.owldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.owldatabase.Modle.OwlMassage;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;


public class MainActivity02 extends AppCompatActivity {

    private Button b;
    private Button b2;
    private SQLiteHandler sqLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main02);
        b = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
sqLiteHandler =new SQLiteHandler(this);

ArrayList<OwlMassage>chat = sqLiteHandler.getChat(2,4);
        for (int i = 0; i < chat.size(); i++) {
        Log.d("owlChat", "onCreate: "+chat.get(i).getText());
        }

        b.setOnClickListener(v -> {
            Intent intent = new Intent(this, FriendsListActivity.class);
            intent.putExtra("USER_ID", 2);
            startActivity(intent);
        });
        b2.setOnClickListener(v -> {
            Intent intent = new Intent(this, MassagesListActivity.class);
            intent.putExtra("USER_ID", 2);
            intent.putExtra("RECEIVED", true);
            startActivity(intent);
        });

    }

}
//addUsers();
//ArrayList<OwlUser> UserArrayList = sqLiteHandler.getAllUsers();
//getUsers(UserArrayList);

//        addM(UserArrayList,1,2,"hi it's me");
//        addM(UserArrayList,1,3,"hi it's me");
//        addM(UserArrayList,1,4,"hi it's me");
//        addM(UserArrayList,1,0,"hi it's me");
//
//        ArrayList<OwlMassage>owlMassageArrayList=new ArrayList<OwlMassage>();
//         getSentM(UserArrayList,1,owlMassageArrayList);
//         getReceivedM(UserArrayList,3,owlMassageArrayList);

//       sqLiteHandler.addFriend(UserArrayList.get(0).getUserId(),UserArrayList.get(4).getUserId()) ;
//        sqLiteHandler.addFriend(UserArrayList.get(0).getUserId(),UserArrayList.get(3).getUserId());
//        sqLiteHandler.addFriend(UserArrayList.get(0).getUserId(),UserArrayList.get(2).getUserId());
//ArrayList<OwlUser> friends = sqLiteHandler.getUsersFriendsList(UserArrayList.get(0).getUserId());
//        for (int i = 0; i < friends.size(); i++) {
//            Log.d("friends", "onCreate:  "+friends.get(i).getName()+" __ "+friends.get(i).getBirth_Date());
//        }
//    }
//
//    private void getSentM(ArrayList<OwlUser> userArrayList,int sender,ArrayList<OwlMassage>owlMassageArrayList) {
//        owlMassageArrayList=sqLiteHandler.getSentMassages(userArrayList.get(sender).getUserId());
//        for (int i = 0; i < owlMassageArrayList.size(); i++) {
//            Log.d("sentMassages", "onCreate: "+owlMassageArrayList.get(i).getText()+"  to  "+
//                    userArrayList.get(owlMassageArrayList.get(i).getToUser()-1).getName());
//        }
//    }
//
//    private void getReceivedM(ArrayList<OwlUser> userArrayList,int receiver,ArrayList<OwlMassage>owlMassageArrayList) {
//        owlMassageArrayList=sqLiteHandler.getReceivedMassages(userArrayList.get(receiver).getUserId());
//        for (int i = 0; i < owlMassageArrayList.size(); i++) {
//            Log.d("receivedMassages", "onCreate: "+owlMassageArrayList.get(i).getText() +" From  "+
//                    userArrayList.get(owlMassageArrayList.get(i).getFromUser()-1).getName());
//        }
//    }
//
//    private void addM(ArrayList<OwlUser> userArrayList,int sender,int receiver,String text) {
//        OwlMassage owlMassage= new OwlMassage();
//        owlMassage.setFromUser(userArrayList.get(sender).getUserId());
//        owlMassage.setToUser(userArrayList.get(receiver).getUserId());
//        owlMassage.setText(text);
//        sqLiteHandler.addMassage(owlMassage);
//    }
//
//    private void getUsers( ArrayList<OwlUser> UserArrayList ) {
//        if(UserArrayList == null)
//            Log.d("A7A_A7A", "onCreate: ");
//        for (int i = 0; i < UserArrayList.size(); i++) {
//            Log.d("getOwlUsers", "onCreate: \t"+UserArrayList.get(i).getName());
//        }
//        imageView.setImageBitmap(UserArrayList.get(2).getUserProfilePicBitMap());
//        t.setText(UserArrayList.get(2).getName());
//    }
//
//    public void addUsers() {
//        int [] images =new int[]{R.drawable._7_,R.drawable._2_,R.drawable._3_,R.drawable._4_,R.drawable._10_};
//        for (int i = 0; i < 5; i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),images[i]) ;
//            OwlUser owlUser = new OwlUser(("hassan"+i), "hassan123"+i,
//                    "2001-9-6", "2021-8-17",bitmap);
//            sqLiteHandler.addUser(owlUser);
//        }

/*
        //-------------------------------------
        sqLiteHandler = new SQLiteHandler(MainActivity.this);
        ArrayList<OwlUser> owlUserArrayList = sqLiteHandler.getAllUsers();
        getUsers(owlUserArrayList);

        addM(owlUserArrayList,1,2,"hi it's me","meeting");
        addM(owlUserArrayList,1,3,"hi it's me","meeting");
        addM(owlUserArrayList,1,4,"hi it's me","meeting");
        addM(owlUserArrayList,1,0,"hi it's me","meeting");

        addM(owlUserArrayList,3,1,"hi it's me","meeting");
        addM(owlUserArrayList,2,1,"hi it's me","meeting");

        sqLiteHandler.addFriend(2,1);
        sqLiteHandler.addFriend(2,3);
        sqLiteHandler.addFriend(2,4);
        //-------------------------------------

 */