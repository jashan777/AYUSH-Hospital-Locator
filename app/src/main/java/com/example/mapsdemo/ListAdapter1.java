package com.example.mapsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter1 extends ArrayAdapter<rec_storage>
{
    public ListAdapter1(Context context, ArrayList<rec_storage> rec_hosp_list)
    {
        super(context,R.layout.displayer_list,rec_hosp_list);
    }
    @NonNull
    @Override
    public  View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        rec_storage obj = getItem(position);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.displayer_list,parent,false);
        }
        TextView hosp_name = convertView.findViewById(R.id.hosp_name);
        hosp_name.setText(obj.hosp_name);
        return convertView;
    }
}
