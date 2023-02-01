package com.example.mapsdemo;

//import static com.example.mapsdemo.MapsActivity.Sorted_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.mapsdemo.databinding.ActivityCustomSearchBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//import android.Container.Spinner;


public class Custom_search extends AppCompatActivity
{
    private Spinner custom_search_state ,custom_search_type,custom_search_rating;
    private MapsActivity obj = new MapsActivity();
    private String[] folder_list_1;
    private  Toolbar toolbar;

    private ArrayAdapter<String> state_adapter;
    private ArrayAdapter <String> type_adapter;
    private ArrayAdapter <String> rating_adapter;
    private ArrayList<String> info = new ArrayList<String>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActivityCustomSearchBinding binding;
        super.onCreate(savedInstanceState);
        binding = ActivityCustomSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().hide();
        toolbar = (Toolbar)findViewById(R.id.custom_search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        folder_list();
        custom_search_state =(Spinner)findViewById(R.id.state_spinner_1);
        custom_search_type = (Spinner)findViewById(R.id.type_spinner1);
        custom_search_rating = (Spinner)findViewById(R.id.rating_spinner2);

        state_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,folder_list_1);

        custom_search_state.setAdapter(state_adapter);
        custom_search_state.setSelection(10);
        custom_search_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                ArrayList<String> type = new ArrayList<>();
                String temp_storage="";

                String selected_state = adapterView.getItemAtPosition(i).toString();

                for(int j = 0;j<loader_1(selected_state).size();j++)
                {

                    if(!(temp_storage.equals(loader_1(selected_state).get(j)[3])))
                    {
                        type.add(loader_1(selected_state).get(j)[3]);
                        temp_storage = loader_1(selected_state).get(j)[3];
                    }
                }
                type_adapter = new ArrayAdapter<>(Custom_search.this, android.R.layout.simple_list_item_1,type);

                custom_search_type.setAdapter(type_adapter);

                custom_search_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
                    {
                        ArrayList<String> rating = new ArrayList<>();

                        String temp_storage_rate = "";
                        String selected_type = adapterView.getItemAtPosition(position).toString();

                        for(int y = 0;y<loader_1(selected_state).size();y++)
                        {

                            if(loader_1(selected_state).get(y)[3].equals(selected_type))
                            {

                                if(temp_storage_rate.equals(loader_1(selected_state).get(y)[4]))
                                {

                                }
                                else
                                {
                                    temp_storage_rate = loader_1(selected_state).get(y)[4];
                                    rating.add(loader_1(selected_state).get(y)[4]);

                                }
                            }
                        }
                        rating_adapter = new ArrayAdapter<>(Custom_search.this, android.R.layout.simple_list_item_1,rating);
                        custom_search_rating.setAdapter(rating_adapter);
                        custom_search_rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position1, long l)
                            {
                                    String selected_rating  = adapterView.getItemAtPosition(position1).toString();
                                    info.add(0,selected_state);
                                    info.add(1,selected_type);
                                    info.add(2,selected_rating);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView)
                            {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void onclik(View d){
        switch(d.getId()){
            case R.id.button:
            {
                //folder_list();
                if(info.get(0).equals("null"))
                {
                    Intent daat_sender = new Intent();
                    daat_sender.putExtra("data",info);
                    setResult(0,daat_sender);
                    finish();
                }
                else
                {
                    Intent daat_sender = new Intent();
                    daat_sender.putExtra("data",info);
                    setResult(1,daat_sender);
                    finish();
                }
                Toast.makeText(Custom_search.this,info.get(0)+","+info.get(1)+","+info.get(2),Toast.LENGTH_LONG).show();
            }
        }
    }
    public void folder_list()
    {
        AssetManager j = getAssets();
        try
        {
            folder_list_1 = (j.list("state"));
            //Toast.makeText(this,folder_list_1[0],Toast.LENGTH_LONG).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String[]> loader_1(String h)
    {

        String str = null ;
        String spliter[] = null;
        String temp[] = null;
        ArrayList<String[]> Sorted_data = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("state/"+h+"/hospital_location");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            str = new String(buffer);
            str = str.replace("\"","");
            str = str.replace("\n","");
            spliter = str.split("]");
            for(int i = 0;i<spliter.length;i++)
            {
                temp = spliter[i].split(",");
                Sorted_data.add(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Sorted_data;

    }


}