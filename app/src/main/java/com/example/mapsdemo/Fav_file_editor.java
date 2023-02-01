package com.example.mapsdemo;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Fav_file_editor
{
    private Context context;
    private double lat_x,long_y;
    private String state;
    public Fav_file_editor(double lat_xx,double long_yy,String state,Context c) throws IOException
    {
        this.context = c.getApplicationContext();
        this.lat_x = lat_xx;
        this.long_y = long_yy;
        this.state = state;
        File f_obj = new File(c.getFilesDir(),"favourite");
        File favDB = new File(f_obj,"favDB");
        w_t_f(Double.toString(lat_xx),Double.toString(long_yy),state);

    }
    private void w_t_f(String x,String y,String stte) throws IOException
    {
        File f = new File(context.getFilesDir()+"/favourite/favDB");
        FileWriter f_w = new FileWriter(f,true);
        String param = x+","+y+","+stte;
        f_w.flush();
        f_w.append(param);
        f_w.append(System.lineSeparator());
        f_w.flush();
        f_w.flush();
        f_w.close();
    }
}
