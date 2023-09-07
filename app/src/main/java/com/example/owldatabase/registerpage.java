package com.example.owldatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.io.IOException;
import java.util.Calendar;

public class registerpage extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 111;
    private OwlUser owlUser;
    private TextView account;
    private EditText userName, email, password, confirmPassword;
    private Button registerBtn, dateButton;
    private  boolean clickedDate;
    private  String date;
    // PICK IMAGE___________________________
    private Bitmap imageBitmap;
    private Uri ImageUrl ;
    private Intent pickImageIntent;
    private ImageView imageView;
    private  boolean clickedImage;
    private SQLiteHandler sqLiteHandler;
    //--------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        sqLiteHandler = SQLiteHandler.getInstance(getApplicationContext());
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        account = findViewById(R.id.haveaccount);
        registerBtn = findViewById(R.id.button_reg);
        dateButton = findViewById(R.id.button_date);
        imageView = findViewById(R.id.user_image);
        clickedDate = false;
        clickedImage = false;
        date = "";

        imageView.setOnClickListener(v ->{
            clickedImage = true;
            pickImageIntent= new Intent();
            pickImageIntent.setType("image/*");
            pickImageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(pickImageIntent,PICK_IMAGE_REQUEST);
        });

        dateButton.setOnClickListener(v -> {
//            Intent i1 = new Intent(getApplicationContext(),DateActivity.class);
//            startActivity(i1);
             clickedDate = true;
            //---------------------------------------------------
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day)
                {
                    month = month + 1;
                     date = day +"-"+ month+"-"+ year;
                    dateButton.setText(date);
                }
            };

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            int style = AlertDialog.THEME_HOLO_LIGHT;

           new DatePickerDialog(this, style, dateSetListener, year, month, day).show();

            //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            //--------------------------------------------
        });

        registerBtn.setOnClickListener(v ->{
            if (userName.getText().toString().equals("")||password.getText().toString().equals("")||
                    confirmPassword.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Fields can't be empty!",
                        Toast.LENGTH_SHORT).show();
            }else{
                if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "  confirm your Password !",
                            Toast.LENGTH_SHORT).show();
                }else{
                    //-------------------
                    if (clickedDate ) {
                        if (imageBitmap != null && clickedImage) {
                           owlUser = new OwlUser(userName.getText().toString()
                                    ,password.getText().toString(),date,imageBitmap);
                            try {
                              int user_id = (int) sqLiteHandler.addUser(owlUser);
                                Toast.makeText(getApplicationContext(), "Done!",
                                        Toast.LENGTH_SHORT).show();
                                Intent i3 = new Intent(getApplicationContext(),MainActivity.class);
                                i3.putExtra("USER_ID",user_id);
                                startActivity(i3);
                            }catch(SQLiteConstraintException sqLiteConstraintException){
                                Toast.makeText(getApplicationContext(), sqLiteConstraintException.getMessage()+"\nTry different Password",
                                        Toast.LENGTH_SHORT).show();
                            } catch (SQLiteException se){
                                Toast.makeText(getApplicationContext(), "Something Went Wrong!!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "enter Image!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //clickedDate = false;
                       // clickedImage =false;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "enter your birth date!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        account.setOnClickListener(v ->{
            Intent i2 = new Intent(getApplicationContext(),Login.class);
            startActivity(i2);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            try {
                ImageUrl = data.getData();
                Log.d("pathimage", "onActivityResult: "+data.getData().getPath());
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUrl);
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }else{
            //  Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
    }
}