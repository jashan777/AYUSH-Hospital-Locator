package com.example.mapsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class location_details extends AppCompatActivity
{
    //ToggleButton toggleButton;
    private ImageButton imgbtn;
    private int key_to_img = 0;
    private Toolbar toolingbar;
    //private String temp_det;
    //private String key_to_img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        toolingbar = (Toolbar)findViewById(R.id.loc_details_toolbar);
        setSupportActionBar(toolingbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolingbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String res;
        Context c;
        Intent Location_details_retriever = getIntent();
        String[] Location_Data  = (String[]) Location_details_retriever.getSerializableExtra("Details");
        TextView Details = (TextView)findViewById(R.id.textView3);
        res = "Name:"+Location_Data[0]+"\n"+"Type:"+Location_Data[2]+"\n"+"Rating:"+Location_Data[3]+"\n"+"Time Period:"+Location_Data[4];
        Details.setText(res);
        imgbtn = (ImageButton)findViewById(R.id.fav_button);
        c = getApplicationContext();

        File f_obj = new File(c.getFilesDir(),"favourite");
        if(!(f_obj.exists()))
        {
            if(f_obj.mkdir())
            {
                Toast.makeText(c, "made folder.", Toast.LENGTH_SHORT).show();
            }
        }
        File favDB = new File(f_obj,"favDB");
        try {

                if(favDB.createNewFile())
                {
                    //Toast.makeText(c, "file created", Toast.LENGTH_SHORT).show();
                }else
                {
                    //Toast.makeText(c, "already exist", Toast.LENGTH_SHORT).show();
                }


        } catch (IOException e) {
            e.printStackTrace();
        }
        if(favDB.canWrite())
        {
            //Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(c, "Write Permission not granted.", Toast.LENGTH_SHORT).show();
        }

        if(favDB.length()==0)
        {
            imgbtn.setImageResource(R.drawable.ic_img_star_grey);
            key_to_img =0;
        }
        else if(favDB.length()!=0)
        {
            try
            {
                FileReader f = new FileReader(favDB);
                BufferedReader bw = new BufferedReader(f);
                String line;
                while((line= bw.readLine())!=null)
                {
                    String[] word;
                    word = line.split(",");
                    if((Double.parseDouble(Location_Data[5])==Double.parseDouble(word[0]))&&(Double.parseDouble(Location_Data[6])==Double.parseDouble(word[1])))
                    {
                        imgbtn.setImageResource(R.drawable.ic_img_star_yellow);
                        key_to_img =1;
                        break;
                    }
                    else
                    {

                    }
                }
                if(key_to_img==0)
                {
                    imgbtn.setImageResource(R.drawable.ic_img_star_grey);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                switch (key_to_img)
                {
                    case 0:
                    {
                        try {
                            Fav_file_editor newfaveditor = new Fav_file_editor(Double.parseDouble(Location_Data[5]),Double.parseDouble(Location_Data[6]),Location_Data[1],location_details.this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imgbtn.setImageResource(R.drawable.ic_img_star_yellow);
                        key_to_img=1;

                    }
                    break;

                    case 1:
                    {
                        replace_file(edit_file_specific_line(Location_Data[5],Location_Data[6],Location_Data[1]));
                        imgbtn.setImageResource(R.drawable.ic_img_star_grey);
                        key_to_img=0;
                    }
                    break;
                }
            }
        });
    }

    private ArrayList<String> edit_file_specific_line(String x,String y,String stte)
    {
        ArrayList<String> new_Lines = new ArrayList<>();
        try
        {
            File f_r_t_r = new File(location_details.this.getFilesDir()+"/favourite/favDB");
            FileReader r_f = new FileReader(f_r_t_r);
            BufferedReader b_r_r = new BufferedReader(r_f);
            String data = x+","+y+","+stte;
            String l_t_r;
            while((l_t_r=b_r_r.readLine())!=null)
            {
                if(data.equals(l_t_r))
                {
                    //do nothing
                }else
                {
                    new_Lines.add(l_t_r);
                }
            }
            r_f.close();
            b_r_r.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return  new_Lines;

    }
    private void replace_file(ArrayList<String> s)
    {
        try
        {
            File f_obj_final = new File(location_details.this.getFilesDir()+"/favourite","temp_fav");
            FileWriter f_w_f = new FileWriter(f_obj_final,true);
            int y = s.size();

            for(int v = 0;v<y;v++)
            {
                ///String j = inpt.get(v).replace("\n","\0");
                f_w_f.append(s.get(v));
                f_w_f.flush();
                f_w_f.append(System.lineSeparator());
                f_w_f.flush();
            }
            f_w_f.flush();
            f_w_f.close();
            File f_0_f2 = new File(location_details.this.getFilesDir()+"/favourite/favDB");
            if(f_0_f2.delete())
            {

            }else
            {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }
            File p = new File(location_details.this.getFilesDir()+"/favourite/favDB");
            if(p.exists())
            {
                throw new java.io.IOException("file exist!!!");
            }
            else  if(f_obj_final.renameTo(p))
            {
                Toast.makeText(this, "renaming done.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}