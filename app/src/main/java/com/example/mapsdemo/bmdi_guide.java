package com.example.mapsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapsdemo.databinding.ActivityBmdiGuideBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class bmdi_guide extends AppCompatActivity
{
    private ActivityBmdiGuideBinding binding;
    private Toolbar info_toolbar;
    private String[] folder_list_1;
    //private bmdi_guide_info_storage numbering = new bmdi_guide_info_storage();
    private final int num  = 8;
    private int[][] img_id_guide = {{R.drawable.ic_guidlines,R.drawable.ic_customsearching,R.drawable.ic_recommended_user_hosp,R.drawable.ic_favourites,R.drawable.ic_bio_info,R.drawable.ic_customer_care},{R.drawable.ic_map_search_svgrepo_com,R.drawable.ic_near_me,R.drawable.ic_clear,R.drawable.ic_baseline_settings_24}};
    private int[][] invs_id = {{R.drawable.ic_invisibleicon}};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityBmdiGuideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        info_toolbar = (Toolbar)findViewById(R.id.tile_of_info);
        setSupportActionBar(info_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView t11 = (TextView)findViewById(R.id.type);
        info_toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //AssetManager aseetmanager = getAssets();
        try
        {
            String[] det_folder = folder_list("Features");
            String[] det_file_guide = folder_list("Features/Guidelines");
            String[] det_file_bmdi = folder_list("Features/Bio_Medical_Data_Info");
            String[] det_file_c_c = folder_list("Features/Customer_Care");
            ArrayList<bmdi_guide_info_storage> bmdi_guiding_infoing = new ArrayList<>();
            Intent getkey = getIntent();
            int key = getkey.getIntExtra("key",0);
            //int key = 0;
            if(key==0)
            {
                View_Setter(t11,det_folder[2],"Features/Guidelines/",det_file_guide,bmdi_guiding_infoing,img_id_guide,0);
            }
            else if(key==1)
            {
                View_Setter(t11,det_folder[0],"Features/Bio_Medical_Data_Info/",det_file_bmdi,bmdi_guiding_infoing,invs_id,1);
            }
            else if(key==2)
            {
                View_Setter(t11,det_folder[1],"Features/Customer_Care/",det_file_c_c,bmdi_guiding_infoing,invs_id,2);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    private ArrayList<String> Features_file_loader(String path, String fl)
    {
        ArrayList<String> reader_storage_guide = new ArrayList<>();
        try
        {
            InputStream f = getAssets().open(path + fl);
            BufferedReader b_r = new BufferedReader(new InputStreamReader(f));
            String line;
            while((line=b_r.readLine())!=null)
            {
                line = line.replace("\n","");
                line = line.replace("\0","");
                reader_storage_guide.add(line);
            }


        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return reader_storage_guide;
    }
    private String[] folder_list(String folder_name) throws IOException
    {
        AssetManager j = getAssets();
        if(j.list(folder_name)!=null)
        {
            folder_list_1 = (j.list(folder_name));
        }
        return folder_list_1;
    }
    private void View_Setter(TextView obj,String heading,String Base_Path,String[] files_in_it,ArrayList<bmdi_guide_info_storage> bmdi_guiding_infoing1, int[][] imgid,int i)
    {
        switch(i)
        {
            case 0:
            {
                obj.setText(heading.replace("_", " "));
                for (int q = 0; q < files_in_it.length; q++)
                {
                    if (Features_file_loader(Base_Path, files_in_it[q]).size() <= num)
                    {
                        bmdi_guide_info_storage j = new bmdi_guide_info_storage(files_in_it[q].replace("_", " "), Features_file_loader(Base_Path, files_in_it[q]), imgid[q]);
                        bmdi_guiding_infoing1.add(j);
                    }
                }
                ListAdapter0 bmdi_guideadapter = new ListAdapter0(bmdi_guide.this, bmdi_guiding_infoing1);
                binding.bmdiGuideView.setAdapter(bmdi_guideadapter);
            }
            break;
            default:
            {
                obj.setText(heading.replace("_", " "));
                for (int q = 0; q < files_in_it.length; q++)
                {
                    if (Features_file_loader(Base_Path, files_in_it[q]).size() <= num)
                    {
                        bmdi_guide_info_storage j = new bmdi_guide_info_storage(files_in_it[q].replace("_", " "), Features_file_loader(Base_Path, files_in_it[q]), imgid[0]);
                        bmdi_guiding_infoing1.add(j);
                    }
                }
                ListAdapter0 bmdi_guideadapter = new ListAdapter0(bmdi_guide.this, bmdi_guiding_infoing1);
                binding.bmdiGuideView.setAdapter(bmdi_guideadapter);
            }
            break;
        }
    }

}