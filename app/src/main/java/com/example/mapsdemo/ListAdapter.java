package com.example.mapsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<settings_map>
{
    public ListAdapter(Context context, ArrayList<settings_map> userArrayList)
    {
        super(context,R.layout.list_item,userArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        settings_map user = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }
        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView itemname = convertView.findViewById(R.id.item_name);
        TextView itemexlpain = convertView.findViewById(R.id.explanation);
        //TextView lastvisit = convertView.findViewById(R.id.last_visited);

        imageView.getLayoutParams().height = 100; // OR
        imageView.getLayoutParams().width = 100;
        imageView.setImageResource(user.imageId);
        itemname.setText(user.itemname);
        itemexlpain.setText(user.explain);
        //lastvisit.setText(user.lastvisited);

        return convertView;
    }
}
