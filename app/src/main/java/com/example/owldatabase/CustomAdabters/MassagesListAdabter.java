package com.example.owldatabase.CustomAdabters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owldatabase.Modle.OwlMassage;
import com.example.owldatabase.Modle.OwlUser;
import com.example.owldatabase.R;
import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;

public class MassagesListAdabter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<OwlMassage> owlMassages;
    private   boolean received;
    public MassagesListAdabter( Context context, ArrayList<OwlMassage> owlMassages,boolean received) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.owlMassages = owlMassages;
        this.received =  received;
    }



    @Override
    public int getCount() {
        return owlMassages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView  = layoutInflater.inflate(R.layout.massages_list_layout,parent,false);
        }
        TextView textView = convertView.findViewById(R.id.massages_list_text_view);
        TextView textView2 = convertView.findViewById(R.id.massages_list_text_view2);
        TextView textView3 = convertView.findViewById(R.id.massages_list_text_view3);
        ImageView imageView = convertView.findViewById(R.id.massages_list_image_view);

        OwlUser user;
        String userName="none";
        if (received) {
             user = new SQLiteHandler(context.getApplicationContext())
                    .getUser(owlMassages.get(position).getFromUser());
            if(user !=null)
                userName ="From: "+user.getName();
        }else {
             user = new SQLiteHandler(context.getApplicationContext())
                    .getUser(owlMassages.get(position).getToUser());
            if(user !=null)
                userName ="To: "+user.getName();
        }

        textView.setText(userName);

        if (!owlMassages.get(position).getSubject().equals("")){
            textView2.setText("Subject: "+owlMassages.get(position).getSubject());}
        else{   textView2.setText("No Subject");}

        textView3.setText(owlMassages.get(position).getMassageDate());

        imageView.setImageBitmap(user.getUserProfilePicBitMap());
        return convertView;
    }
}
