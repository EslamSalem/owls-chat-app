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

import java.util.ArrayList;

public class FriendsListAdabter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<OwlUser> owlUserArrayList;


    public FriendsListAdabter(Context context, ArrayList<OwlUser> owlUserArrayList) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.owlUserArrayList = owlUserArrayList;
    }

    @Override
    public int getCount() {
        return owlUserArrayList.size();
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
            convertView = layoutInflater.inflate(R.layout.friend_list_layout,parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.friends_list_image_view);
        TextView textView = convertView.findViewById(R.id.friends_list_text_view);
        imageView.setImageBitmap(owlUserArrayList.get(position).getUserProfilePicBitMap());
        textView.setText(owlUserArrayList.get(position).getName());
        return convertView;
    }

}
