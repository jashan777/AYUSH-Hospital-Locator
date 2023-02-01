package com.example.mapsdemo;

import java.util.ArrayList;

public class bmdi_guide_info_storage
{
    String heading1;
    int[] img_id;
    ArrayList<String> details;
    public bmdi_guide_info_storage(String heading1, ArrayList<String> details,int[] img_id)
    {
        this.heading1 = heading1;
        this.details = details;
        this.img_id = img_id;
    }
    bmdi_guide_info_storage()
    {

    }


}
