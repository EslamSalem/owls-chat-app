package com.example.owldatabase.SQLiteHandler_DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.Modle.OwlMassage;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;


public class SQLiteHandler extends SQLiteOpenHelper {
    private static  SQLiteHandler sqliteHandler;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<OwlUser> usersArrayList;
    private ArrayList<OwlUser> users_friends_ArrayList;
    private ArrayList<OwlMassage> receivedMassagesArrayList;
    private ArrayList<OwlMassage> sentMassagesArrayList;
    private ByteArrayOutputStream user_image_Byte_Stream;
    private   byte[] imageByteArray;

    public SQLiteHandler(@Nullable Context context) {
        super(context, "OWL_APP_DB", null, 1);
        usersArrayList =new ArrayList<OwlUser>();
        users_friends_ArrayList = new ArrayList<OwlUser>();
        receivedMassagesArrayList = new ArrayList<OwlMassage>();
        sentMassagesArrayList = new ArrayList<OwlMassage>();
    }

    public static SQLiteHandler getInstance(Context context){
        if (sqliteHandler == null) {
            sqliteHandler = new SQLiteHandler(context.getApplicationContext());
            return  sqliteHandler;
        }
        return sqliteHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE ="CREATE TABLE USERS_TABLE (" +
                  "USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                  "USER_NAME TEXT  NOT NULL ,"+
                  "USER_PASSWORD TEXT  NOT NULL UNIQUE,"+
                  "USER_BIRTH_DATE TEXT,"+
                  "USER_JOINED_DATE TEXT,"+
                  "USER_PROFILE_PIC BLOB"
                + ");";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_MASSAGES_TABLE ="CREATE TABLE MASSAGES_TABLE (" +
                  "MASSAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                  "MASSAGE_TEXT TEXT,"+
                  "SENDER INTEGER ,"+
                  "RECEIVER INTEGER ,"+
                  "DATA_SENT TEXT,"+
                  "MASSAGE_SUBJECT TEXT , "
                  +"CONSTRAINT NOTSAMEUSERS CHECK (SENDER <> RECEIVER)"
                + ");";
        db.execSQL(CREATE_MASSAGES_TABLE);

        String CREATE_FRIENDS_TABLE ="CREATE TABLE FRIENDS_TABLE (" +
                "USER1 INTEGER ,"+
                "USER2 INTEGER ,"+
                "CONSTRAINT PK_Person PRIMARY KEY (USER1,USER2),"+
                "CONSTRAINT NOTEQUALUSERS CHECK (USER1 <> USER2)"
                + ");";
        db.execSQL(CREATE_FRIENDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS_TABLE" );
        db.execSQL("DROP TABLE IF EXISTS MASSAGES_TABLE" );
        db.execSQL("DROP TABLE IF EXISTS FRIENDS_TABLE");
        onCreate(db);

    }

    public long addUser(OwlUser owlUser){
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_NAME",owlUser.getName());
        contentValues.put("USER_PASSWORD",owlUser.getPassword());
        contentValues.put("USER_BIRTH_DATE", owlUser.getBirth_Date());
        contentValues.put("USER_JOINED_DATE", LocalDate.now().toString());
        //save Image
        user_image_Byte_Stream = new ByteArrayOutputStream();
        owlUser.getUserProfilePicBitMap().compress(Bitmap.CompressFormat.JPEG,100,user_image_Byte_Stream);
        contentValues.put("USER_PROFILE_PIC", user_image_Byte_Stream.toByteArray());
        long newUserId = sqLiteDatabase.insert("USERS_TABLE",null, contentValues);
        sqLiteDatabase.close();
        Log.d("addOwlUser", "addUser: "+owlUser.getName());
        return newUserId;
    }

    public void updateUser(OwlUser owlUser){
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_NAME",owlUser.getName());
        contentValues.put("USER_PASSWORD",owlUser.getPassword());
        contentValues.put("USER_BIRTH_DATE", owlUser.getBirth_Date());
        contentValues.put("USER_JOINED_DATE", owlUser.getJoined_date());
        //save Image
        user_image_Byte_Stream = new ByteArrayOutputStream();
        owlUser.getUserProfilePicBitMap().compress(Bitmap.CompressFormat.JPEG,100,user_image_Byte_Stream);
        contentValues.put("USER_PROFILE_PIC", user_image_Byte_Stream.toByteArray());
        sqLiteDatabase.update("USERS_TABLE", contentValues,
             "WHERE USER_ID =?",new String[owlUser.getUserId()]);
        sqLiteDatabase.close();
    }

    public void addMassage(OwlMassage owlMassage){
        sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MASSAGE_TEXT",owlMassage.getText());
        contentValues.put("RECEIVER",owlMassage.getToUser());
        contentValues.put("SENDER", owlMassage.getFromUser());
        contentValues.put("DATA_SENT", LocalDateTime.now().toString().replace("T"," "));
        contentValues.put("MASSAGE_SUBJECT",owlMassage.getSubject());
        sqLiteDatabase.insert("MASSAGES_TABLE", null, contentValues);
        Log.d("addmassagetodb", "addMassage: ");
        sqLiteDatabase.close();
    }
    //-----------------------------------------------------------------------------------
    public boolean addFriend(int owlUser1, int owlUser2){
        sqLiteDatabase= getReadableDatabase();
        String GET_FRIENDS="SELECT * FROM FRIENDS_TABLE,USERS_TABLE WHERE (USER1 =? OR USER2 =? ) AND (USER1 =?OR USER2 =?)";

        Cursor cursor =  sqLiteDatabase.rawQuery(GET_FRIENDS,new String[]{String.valueOf(owlUser1),
                String.valueOf(owlUser1), String.valueOf(owlUser2),String.valueOf(owlUser2)});
        if(!cursor.moveToFirst()) {
            sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("USER1", owlUser1);
            contentValues.put("USER2", owlUser2);
            sqLiteDatabase.insert("FRIENDS_TABLE", null, contentValues);
            return true;
        }
        return false;
    }
    public ArrayList<OwlMassage> getReceivedMassages(int owlUser){
        sqLiteDatabase= getReadableDatabase();
        String GET_RECEIVED ="SELECT * FROM MASSAGES_TABLE WHERE RECEIVER =?"+
                "ORDER BY  substr (DATA_SENT,0,4) || substr(DATA_SENT,5,7) || substr(DATA_SENT,8,10)" +
                "|| substr(DATA_SENT,10,12) || substr(DATA_SENT,12,14) || substr(DATA_SENT,14,16) DESC ";
        Cursor cursor =  sqLiteDatabase.rawQuery(GET_RECEIVED,new String[]{String.valueOf(owlUser)});
        if (cursor.moveToFirst()) {
            do{
                receivedMassagesArrayList.add(new OwlMassage(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),cursor.getInt(3),
                        cursor.getString(4),cursor.getString(5)));
            }while (cursor.moveToNext());
            sqLiteDatabase.close();
            return receivedMassagesArrayList;
        }
        return  null;
    }

    public ArrayList<OwlMassage> getSentMassages(int owlUser){
        sqLiteDatabase= getReadableDatabase();
        String GET_SENT ="SELECT * FROM MASSAGES_TABLE WHERE SENDER =?"+
                "ORDER BY  substr (DATA_SENT,0,4) || substr(DATA_SENT,5,7) || substr(DATA_SENT,8,10)" +
                "|| substr(DATA_SENT,10,12) || substr(DATA_SENT,12,14) || substr(DATA_SENT,14,16) DESC ";
        Cursor cursor =  sqLiteDatabase.rawQuery(GET_SENT,new String[]{String.valueOf(owlUser)});
        if (cursor.moveToFirst()) {
            do{
                sentMassagesArrayList.add(new OwlMassage(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),cursor.getInt(3),
                        cursor.getString(4),cursor.getString(5)));
            }while (cursor.moveToNext());
            sqLiteDatabase.close();
            return sentMassagesArrayList;
        }
        return  null;
    }

    public ArrayList<OwlUser> getUsersFriendsList(int owlUser){
        sqLiteDatabase= getReadableDatabase();
        String GET_FRIENDS_LIST ="SELECT USER1,USER2 FROM FRIENDS_TABLE" +
                ""+" WHERE (USER1 =? OR USER2 =?) ";
        Cursor cursor =  sqLiteDatabase.rawQuery(GET_FRIENDS_LIST,new String[]{String.valueOf(owlUser)});
        if (cursor.moveToFirst()) {
            do{
                if(cursor.getInt(0)!=owlUser){
                    users_friends_ArrayList.add(getUser(cursor.getInt(0)));
                }else if(cursor.getInt(1)!=owlUser){
                    users_friends_ArrayList.add(getUser(cursor.getInt(1)));
                }

            }while (cursor.moveToNext());
            sqLiteDatabase.close();
            return users_friends_ArrayList;
        }
        return null;
    }
    public int getCount(){
        sqLiteDatabase = getReadableDatabase();
        String GET_COUNT = "SELECT CUONT(*) FROM USERS_TABLE";
        Cursor cursor =   sqLiteDatabase.rawQuery(GET_COUNT,null);
        if (cursor.moveToFirst()) {
            return  cursor.getInt(0);
        }
        return -1;
    }
//------------------------------------------------------------------------------------------------------------

    public ArrayList<OwlUser> getAllUsers(){
        sqLiteDatabase= getReadableDatabase();
        String GET_ALL_USERS ="SELECT * FROM USERS_TABLE";

        Cursor cursor =  sqLiteDatabase.rawQuery(GET_ALL_USERS,null);
        if (cursor.moveToFirst()) {
            do{
                Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(5),0,cursor.getBlob(5).length);
                usersArrayList.add(new OwlUser(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        bitmap));
                Log.d("AddUsersToDB", "getAllUsers: "+usersArrayList.get(0).getName());
            }while (cursor.moveToNext());
            sqLiteDatabase.close();
            return usersArrayList;
        }
        return null;
    }

    public  ArrayList<OwlMassage> getChat(int User1, int User2){
        sqLiteDatabase= getReadableDatabase();
        ArrayList <OwlMassage> owlMassageArrayList =new ArrayList<OwlMassage>();

        String  GET_MASSAGE = "SELECT * FROM MASSAGES_TABLE WHERE (SENDER=? AND RECEIVER =?) OR ( RECEIVER =? AND SENDER=?  )" +
                "ORDER BY  substr (DATA_SENT,0,4) || substr(DATA_SENT,5,7) || substr(DATA_SENT,8,10)" +
                "|| substr(DATA_SENT,10,12) || substr(DATA_SENT,12,14) || substr(DATA_SENT,14,16) DESC   ";
        Cursor cursor = sqLiteDatabase.rawQuery(GET_MASSAGE,new String[]{String.valueOf(User1),String.valueOf(User2),
                String.valueOf(User1),String.valueOf(User2)});
        if (cursor.moveToFirst()) {
            do {
                OwlMassage owlMassage = new OwlMassage(cursor.getInt(0), cursor.getString(1),
                        cursor.getInt(2), cursor.getInt(3),
                        cursor.getString(4), cursor.getString(5));
                owlMassageArrayList.add(owlMassage);
            }while (cursor.moveToNext());
            return  owlMassageArrayList;
        }
        return null;
    }

    public OwlUser getUser(int UserID){
        sqLiteDatabase= getReadableDatabase();
        String GET_USER ="SELECT * FROM USERS_TABLE WHERE USER_ID =? ";

        Cursor cursor =  sqLiteDatabase.rawQuery(GET_USER,new String[]{String.valueOf(UserID)});
        if (cursor.moveToFirst()) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(5),0,cursor.getBlob(5).length);
              OwlUser owlUser = new OwlUser(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        bitmap);
            sqLiteDatabase.close();
            return owlUser;
        }
        return null;
    }

}

/*
  movie_image_Byte_Stream = new ByteArrayOutputStream();
        movie.getMovieImage().compress(Bitmap.CompressFormat.JPEG,100,movie_image_Byte_Stream);
        imageByteArray = movie_image_Byte_Stream.toByteArray();

___________________________________________________________________________________________________________

   Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(2), 0 , cursor.getBlob(2).length) ;

 */