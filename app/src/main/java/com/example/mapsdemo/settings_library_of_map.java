package com.example.mapsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mapsdemo.databinding.ActivitySettingsLibraryOfMapBinding;

import java.util.ArrayList;

public class settings_library_of_map extends AppCompatActivity
{
    private ActivitySettingsLibraryOfMapBinding binding;
    private String currentstate;
    private Toolbar toolbar_setting;

    /*****UNDER DEVELOPMENT*****/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsLibraryOfMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar_setting = (Toolbar)findViewById(R.id.toolbar_setting_list);
        setSupportActionBar(toolbar_setting);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent get_state = getIntent();
        currentstate = (String) get_state.getSerializableExtra("currentstate");
        /***DATA FOR LIST***/
        int[] imageid = {R.drawable.ic_guidlines,R.drawable.ic_customsearching,R.drawable.ic_recommended_user_hosp,R.drawable.ic_favourites,R.drawable.ic_bio_info,R.drawable.ic_customer_care};
        String[] items = {"Guidelines","Custom Search","Recommended Search","Favourites","Bio-medical info","Customer Care"};
        String[] explains = {"How to-use the app","Search for particular hospital","Search by asking Question..","Favourite Hospitals","Information on Bio-Medical Occurances in country","Try contacting us for help"};

        /*****DATA LOADING****/
        ArrayList<settings_map> settingsArrayList = new ArrayList<>();
        //using array list to store data in form of objects of a class of details.
        for(int i = 0;i< imageid.length;i++)
        {
            settings_map setings = new settings_map(items[i], explains[i], imageid[i]);
            settingsArrayList.add(setings);
        }

        ListAdapter listAdapter = new ListAdapter(settings_library_of_map.this,settingsArrayList);
        //class for the
        binding.libraryView.setAdapter(listAdapter);
        //binding.listview.setAdapter(listAdapter);
        binding.libraryView.setClickable(true);

        /******SETTING LIST BUTTONS*****/

        binding.libraryView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Intent i = new Intent(settings_library_of_map.this,settings_activity.class);
                switch(position)
                {
                    case 0:
                    {
                        Toast.makeText(settings_library_of_map.this, "guidiines", Toast.LENGTH_SHORT).show();
                        Intent guideline = new Intent(settings_library_of_map.this,bmdi_guide.class);
                        guideline.putExtra("key",0);
                        startActivity(guideline);
                    }
                    break;
                    case 1:
                    {
                        Toast.makeText(settings_library_of_map.this, "custom", Toast.LENGTH_SHORT).show();
                        Intent custom = new Intent(settings_library_of_map.this,Custom_search.class);
                        startActivityForResult(custom,1);
                    }
                    break;
                    case 2:
                    {
                        Intent rec_searching = new Intent(settings_library_of_map.this,Recommended_Activity.class);
                        rec_searching.putExtra("current_state",currentstate);
                        startActivityForResult(rec_searching,3);
                    }
                    break;
                    case 3:
                    {
                        Intent favrte = new Intent(settings_library_of_map.this,settings_activity.class);
                        favrte.putExtra("key","favourite");
                        favrte.putExtra("key1","favDB");
                        startActivityForResult(favrte,2);

                        Toast.makeText(settings_library_of_map.this, "fav", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case 4:
                    {
                        //Toast.makeText(settings_library_of_map.this, "guidiines", Toast.LENGTH_SHORT).show();
                        Intent bio_info = new Intent(settings_library_of_map.this,bmdi_guide.class);
                        bio_info.putExtra("key",1);
                        startActivity(bio_info);
                    }
                    break;
                    case 5:
                    {
                        Toast.makeText(settings_library_of_map.this, "customer care", Toast.LENGTH_SHORT).show();
                        Intent c_c = new Intent(settings_library_of_map.this,bmdi_guide.class);
                        c_c.putExtra("key",2);
                        startActivity(c_c);
                    }
                    break;

                }
            }
        });
        /******SETTING LIST BUTTONS END*****/

    }

    /***DATA RECIEVER**/

    public void onActivityResult(int reqCode,int resultcode,Intent data)
    {
        super.onActivityResult(reqCode, resultcode, data);
        if(reqCode==1)
        {
            if(resultcode==1)
            {
                ArrayList<String> info_retrieved_0 = new ArrayList<String>();
                info_retrieved_0 = (ArrayList<String>) data.getSerializableExtra("data");
                //custom_search_retrieved(info_retrieved);
                Intent daat_sender_1 = new Intent();
                daat_sender_1.putExtra("data1",info_retrieved_0);
                setResult(1,daat_sender_1);
                finish();
            }
            else if(resultcode==0)
            {
                //do nothing.
            }
        }
        else if(reqCode==2)
        {
            if(resultcode==1)
            {
                String[] rec_loc;
                String statename;
                rec_loc = (String[]) data.getSerializableExtra("namefromlist");
                statename = (String) data.getSerializableExtra("statename");
                Intent loc_name_sender = new Intent();
                loc_name_sender.putExtra("gotfromfavrte",rec_loc);
                loc_name_sender.putExtra("statename",statename);
                setResult(2,loc_name_sender);
                finish();
            }
        }
        else if(reqCode==3)
        {
            if(resultcode==1)
            {
                String[] loc;
                String statename;
                loc = (String[])data.getSerializableExtra("locationdetailsfromrec");
                statename = (String)data.getSerializableExtra("statenametosetting");
                Intent loc_sender_rec = new Intent();
                loc_sender_rec.putExtra("gotfromrec",loc);
                loc_sender_rec.putExtra("statenametomap",statename);
                setResult(3,loc_sender_rec);
                finish();
            }
        }
    }
    /****DATA RECIEVER END***/
}
/*****UNDER DEVELOPMENT***/