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

public class ListAdapter0  extends ArrayAdapter<bmdi_guide_info_storage>
{
    int[] ids = {R.id.details,R.id.details2,R.id.details3,R.id.details4,R.id.details5,R.id.details6,R.id.details7,R.id.details8};
    int[] ids_img = {R.id.icon_img,R.id.icon_img2,R.id.icon_img3,R.id.icon_img4,R.id.icon_img5,R.id.icon_img6,R.id.icon_img7,R.id.icon_img8};

    public ListAdapter0(Context context, ArrayList<bmdi_guide_info_storage> bmdi_guide_list)
    {
        super(context,R.layout.list_item_cardtype,bmdi_guide_list);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        bmdi_guide_info_storage obj = getItem(position);

        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cardtype,parent,false);
        }
        //TextView type = convertView.findViewById(R.id.guide);
        TextView head1  = convertView.findViewById(R.id.heading1);
        head1.setText(obj.heading1);
        if(obj.img_id[0]==R.drawable.ic_invisibleicon)
        {
            for(int d=0;d<obj.details.size();d++)
            {
                TextView txt = convertView.findViewById(ids[d]);
                ImageView img = convertView.findViewById(ids_img[d]);
                img.setImageResource(obj.img_id[0]);
                txt.setText(obj.details.get(d));

            }
        }
        else
        {
            for(int d=0;d<obj.details.size();d++)
            {
                TextView txt = convertView.findViewById(ids[d]);
                txt.setText(obj.details.get(d));
                ImageView img = convertView.findViewById(ids_img[d]);
                img.setImageResource(obj.img_id[d]);

            }
        }

        //type.setText(obj.type);
        //head2.setText(obj.heading2);
        //details2.setText(obj.details2);
        return convertView;
    }
}
