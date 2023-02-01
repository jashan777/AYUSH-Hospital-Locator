package com.example.mapsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{
    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_address,null);
        TextView t1_name = (TextView)row.findViewById(R.id.Name);
        TextView t2_details = (TextView) row.findViewById(R.id.details);
        //TextView t3_rating = (TextView) row.findViewById(R.id.rating);
        t1_name.setText(marker.getTitle());
        MapsActivity g = new MapsActivity();
        t2_details.setText(marker.getSnippet());

        return row;
    }
}
