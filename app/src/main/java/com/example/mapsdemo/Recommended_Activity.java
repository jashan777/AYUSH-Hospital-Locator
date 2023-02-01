package com.example.mapsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapsdemo.databinding.ActivityRecommendedBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Recommended_Activity extends AppCompatActivity
{

    private String text_from_user,currentstate;
    private EditText textinput;
    private ActivityRecommendedBinding binding;
    private ArrayList<String[]> data_sending_req = new ArrayList<>();
    private Toolbar rec_tool;
    private Spinner sp_state;
    private ArrayAdapter<String> sp_state_spinner_adapter;
    private String selected_state;
    private ImageView robo,robo_land;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        binding = ActivityRecommendedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent currentstate_retrieve = getIntent();
        currentstate = (String)currentstate_retrieve.getSerializableExtra("current_state");
        rec_tool = (Toolbar)findViewById(R.id.rec_toolbar);
        setSupportActionBar(rec_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       robo = (ImageView) findViewById(R.id.backimagerec);
       if(robo!=null)
        {
            robo.setImageResource(R.drawable.ic_robot);
            robo.setAdjustViewBounds(true);
            robo.getDrawable().setAlpha(255);
        }

        //robo_land = (ImageView)findViewById(R.id.landimage);
        rec_tool.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();

            }
        });
        //TextView t1 = (TextView) findViewById(R.id.rec_hosp);
        textinput = (EditText) findViewById(R.id.userinputspace);
        sp_state = (Spinner)findViewById(R.id.sp_state);
        try {
            sp_state_spinner_adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,folder_list());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sp_state.setAdapter(sp_state_spinner_adapter);
        sp_state.setSelection(11);
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(currentstate!=null)
                {
                    if(adapterView.getItemAtPosition(i).toString().equals("null"))
                    {
                        //no change in current state.
                        selected_state = currentstate;
                    }
                    else
                    {
                        selected_state = adapterView.getItemAtPosition(i).toString();
                    }
                }
                else
                {
                    Toast.makeText(Recommended_Activity.this, "Please check your location Marker.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        FloatingActionButton searching = (FloatingActionButton) findViewById(R.id.search_button);
        searching.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(currentstate!=null)
                {

                    data_sending_req.clear();
                    ArrayList<rec_storage> obj_rec = new ArrayList<>();

                    text_from_user = textinput.getText().toString();
                    if(!(text_from_user.equals("")))
                    {
                        try {
                            data_sending_req = hosp_getter(finding_match(text_from_user),selected_state);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(data_sending_req.size()!=0)
                        {
                            if(robo!=null)
                            {
                                robo.getDrawable().setAlpha(0);
                            }

                        }
                        int c = data_sending_req.size();
                        for(int kl =0;kl<c;kl++)
                        {
                            rec_storage objj = new rec_storage(data_sending_req.get(kl)[0]);
                            obj_rec.add(objj);
                        }
                        ListAdapter1 listAdapter1 = new ListAdapter1(Recommended_Activity.this,obj_rec);
                        binding.recommededListView.setAdapter(listAdapter1);
                        binding.recommededListView.setClickable(true);
                        binding.recommededListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                            {
                                Intent sending_data_to_map = new Intent();
                                sending_data_to_map.putExtra("locationdetailsfromrec",data_sending_req.get(i));
                                sending_data_to_map.putExtra("statenametosetting",selected_state);
                                setResult(1,sending_data_to_map);
                                //img_back.getBackground().setAlpha(100);
                                finish();

                            }
                        });

                    }

                }
                else if(currentstate==null)
                {
                    Toast.makeText(Recommended_Activity.this, "Please check your location Marker.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private ArrayList<String> finding_match(String input) throws IOException
    {
        ArrayList<String> resul = new ArrayList<>();
        ArrayList<String> keywrd;
        String[] input1;
        input = input.replace("\n","");
        input = input.replace("\0","");
        input1 = input.split(" ");
        int j = keywordloader().size();
        keywrd = keywordloader();
        int g = input1.length;
        for(int d = 0;d<j;d++)
        {
            for(int qd = 0;qd<g;qd++)
            {
                if(keywrd.get(d).equals(input1[qd]))
                {
                    resul.add(keywrd.get(d));
                }
            }
        }
        return resul;
    }
    public String[] folder_list() throws IOException
    {
        String[] returning_state_list;
        AssetManager j = getAssets();

            returning_state_list = (j.list("state"));
            //Toast.makeText(this,folder_list_1[0],Toast.LENGTH_LONG).show();
            return returning_state_list;

    }
    private ArrayList<String[]> hosp_getter(ArrayList<String> keylist, String s) throws IOException {
        ArrayList<String[]> hosp_list = new ArrayList<>();
        ArrayList<String> temp_db = new ArrayList<>();
        AssetManager o = getAssets();
        InputStream inp = o.open("state/"+s+"/hospital_location");
        InputStream inp1 = o.open("Recommended_Search_DB/DB_Connections");
        BufferedReader b_r_1 = new BufferedReader(new InputStreamReader(inp1));
        int y = keylist.size();
        for(int x = 0;x<y;x++)
        {
            String line;
            while((line=b_r_1.readLine())!=null)
            {
                String[] type;
                line = line.replace("\n","");
                line = line.replace("\0","");
                type = line.split("=");
                if(keylist.get(x).equals(type[0]))
                {
                    if(temp_db==null)
                    {
                        temp_db.add(type[2]);
                    }
                    else if(temp_db!=null)
                    {
                        int a = temp_db.size();
                        int m = 0;
                        for(int q =0;q<a;q++)
                        {
                            if(type[2].equals(temp_db.get(q)))
                            {
                                m=1;
                                break;
                            }
                        }
                        if(m==1)
                        {

                        }
                        else if(m==0)
                        {
                            temp_db.add(type[2]);
                        }
                    }
                }
            }

        }
        BufferedReader b_r_r = new BufferedReader(new InputStreamReader(inp));
        int qwerty = temp_db.size();
        for(int l = 0;l<qwerty;l++)
        {
            String line1;
            while((line1=b_r_r.readLine())!=null)
            {
                String[] full_info;
                line1 = line1.replace("\n","");
                line1 = line1.replace("\0","");
                line1 = line1.replace("]","");
                full_info = line1.split(",");
                if(temp_db.get(l).equals(full_info[6]))
                {
                    hosp_list.add(full_info);
                }
            }
        }
        return hosp_list;
    }
    private ArrayList<String> keywordloader() throws IOException {
        ArrayList<String> keywordlist = new ArrayList<>();
        String line;
        String[] wordlist;
        AssetManager a = getAssets();
        InputStream fp = a.open("Recommended_Search_DB/Keyword_DB");
        BufferedReader b_r = new BufferedReader(new InputStreamReader(fp));
        while ((line=b_r.readLine())!=null)
        {
            line = line.replace("\n","");
            line = line.replace("\0","");
            wordlist = line.split(",");
            int u = wordlist.length;
            for(int f = 0;f<u;f++)
            {
                keywordlist.add(wordlist[f]);
            }
        }
        b_r.close();
        fp.close();
        return  keywordlist;
    }
}