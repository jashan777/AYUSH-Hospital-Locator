package com.example.mapsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mapsdemo.databinding.ActivitySettingsBinding;
import com.example.mapsdemo.databinding.ActivitySettingsLibraryOfMapBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class settings_activity extends AppCompatActivity
{
    private ActivitySettingsBinding binding;
    private ArrayList<String[]> data_passing_arr_list = new ArrayList<>();
    private ArrayList<String[]> states = new ArrayList<>();
    private Toolbar fav_toolbaar;
    private ImageView star;
    int key_0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fav_toolbaar = (Toolbar)findViewById(R.id.favourite);
        setSupportActionBar(fav_toolbaar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        star = (ImageView) findViewById(R.id.backimage);
        star.setImageResource(R.drawable.ic_star_grad);
        star.setAdjustViewBounds(true);

        fav_toolbaar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent getting_key = getIntent();
        String key = (String)getting_key.getSerializableExtra("key");
        String key1 = (String)getting_key.getSerializableExtra("key1");

        //String key = "history";
        ArrayList<settings_map> list_0 = new ArrayList<>();
        try {
            states = utility_0(key,key1);
            data_passing_arr_list = utility(states);

        } catch (IOException e) {
            e.printStackTrace();
        }
        File f_c = new File(this.getFilesDir(),key);
        if(!(f_c.exists()))
        {
            f_c.mkdir();
        }
        else
        {

        }
        File f_c_f = new File(f_c,key1);
        try
        {
            f_c_f.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if(f_c_f.length()==0)
        {
            settings_map obj = new settings_map("","",R.drawable.ic_invisible_image);
            list_0.add(obj);
            key_0=0;
            star.getDrawable().setAlpha(255);
        }
        else if(f_c_f.length()!=0)
        {
            for(int d = 0;d<data_passing_arr_list.size();d++)
            {
                settings_map obj = new settings_map(data_passing_arr_list.get(d)[0],data_passing_arr_list.get(d)[3],R.drawable.ic_invisible_image);
                list_0.add(obj);
            }
            key_0 = 1;
            star.getDrawable().setAlpha(0);
        }

        ListAdapter l_a = new ListAdapter(settings_activity.this,list_0);

        binding.setViewItem.setAdapter(l_a);
        binding.setViewItem.setClickable(true);
        if(key_0==1)
        {
            binding.setViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {

                    Intent data_sender_setting = new Intent();
                    data_sender_setting.putExtra("namefromlist",data_passing_arr_list.get(i));
                    data_sender_setting.putExtra("statename",states.get(i)[2]);
                    setResult(1,data_sender_setting);
                    finish();
                }
            });
        }
        else if(key_0==0)
        {
            //do nothing
        }


    }
    private ArrayList<String[]> utility_0(String key_input,String key_inpt_file) throws IOException {
        String folder_0 = "/"+key_input;
        String file_0 = "/"+key_inpt_file;
        ArrayList<String[]> returning_list_array=new ArrayList<>();
        Context c = getApplicationContext();
        File f_e  = new File(c.getFilesDir()+folder_0+file_0);
        FileReader f_r_r = new FileReader(f_e);
        BufferedReader b_r_r = new BufferedReader(f_r_r);
        String line;
        while ((line=b_r_r.readLine())!=null)
        {
            String[] store_arr;
            line = line.replace("\n","");
            store_arr = line.split(",");
            returning_list_array.add(store_arr);
        }
        return returning_list_array;
    }
    private ArrayList<String[]> utility(ArrayList<String[]> alpha) throws IOException
    {
        ArrayList<String[]> storage = new ArrayList<>();
        for(int g = 0;g<alpha.size();g++)
        {
            double x = Double.parseDouble(alpha.get(g)[0]);
            double y = Double.parseDouble(alpha.get(g)[1]);
            InputStream f_r = getAssets().open("state/"+alpha.get(g)[2]+"/hospital_location");
            BufferedReader b_r = new BufferedReader(new InputStreamReader(f_r));
            String line;
            while((line=b_r.readLine())!=null)
            {
                String[] array_for_storage;
                line = line.replace("]","");
                line = line.replace("\n","");
                array_for_storage = line.split(",");
                if((Double.parseDouble(array_for_storage[1])==x)&&(Double.parseDouble(array_for_storage[2])==y))
                {
                    storage.add(array_for_storage);
                }

            }
            b_r.close();
        }


        return storage;
    }
}