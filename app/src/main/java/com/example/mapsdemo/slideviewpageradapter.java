package com.example.mapsdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class slideviewpageradapter extends PagerAdapter
{
    Context ctx;

    public slideviewpageradapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override//3 slides
    public int getCount()
    {
        return 3;
    }

    @Override//if view is equal to object
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override//calls when we slide the screen
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slidescreen,container,false);

        ImageView slide_img = view.findViewById(R.id.slide_Img);
        ImageView rect1 = view.findViewById(R.id.rect1);
        ImageView rect2 = view.findViewById(R.id.rect2);
        ImageView rect3 = view.findViewById(R.id.rect3);
        ImageView next = view.findViewById(R.id.next);
        ImageView back = view.findViewById(R.id.back);
        //LinearLayout LinearLayoutParent = view.findViewById(R.id.linearLayout);

        LinearLayout linearLayout1 = view.findViewById(R.id.linearLayout1);
        //ImageView image1 = view.findViewById(R.id.image1);
        TextView textView1 = view.findViewById(R.id.text1);

        LinearLayout linearLayout2 = view.findViewById(R.id.linearLayout2);
        ImageView image2 = view.findViewById(R.id.image2);
        TextView textView2 = view.findViewById(R.id.text2);

        LinearLayout linearLayout3 = view.findViewById(R.id.linearLayout3);
        ImageView image3 = view.findViewById(R.id.image3);
        TextView textView3 = view.findViewById(R.id.text3);

        LinearLayout linearLayout4 = view.findViewById(R.id.linearLayout4);
        ImageView image4 = view.findViewById(R.id.image4);
        TextView textView4 = view.findViewById(R.id.text4);

        LinearLayout linearLayout5 = view.findViewById(R.id.linearLayout5);
        ImageView image5 = view.findViewById(R.id.image5);
        TextView textView5 = view.findViewById(R.id.text5);

        LinearLayout linearLayout6 = view.findViewById(R.id.linearLayout6);
        ImageView image6 = view.findViewById(R.id.image6);
        TextView textView6 = view.findViewById(R.id.text6);

        LinearLayout linearLayout7 = view.findViewById(R.id.linearLayout7);
        ImageView image7 = view.findViewById(R.id.image7);
        TextView textView7 = view.findViewById(R.id.text7);

        LinearLayout linearLayout8 = view.findViewById(R.id.linearLayout8);
        ImageView image8 = view.findViewById(R.id.image8);
        TextView textView8 = view.findViewById(R.id.text8);

        Button skip = view.findViewById(R.id.Skip);
        skip.setOnClickListener(view1 -> {
            Intent intent = new Intent(ctx,MapsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        });
        next.setOnClickListener(view12 -> slideActivity.viewPager.setCurrentItem(position + 1));
        back.setOnClickListener(view13 -> slideActivity.viewPager.setCurrentItem(position - 1));

        switch (position)
        {
            case 0:
                //gravity of layout 1
                LinearLayout.LayoutParams linearLayout1LayoutParamscase0 = (LinearLayout.LayoutParams) linearLayout1.getLayoutParams();
                linearLayout1LayoutParamscase0.gravity = Gravity.CENTER;

                //margin of  layout 2
                LinearLayout.LayoutParams linearLayout2LayoutParamscase0 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                //linearLayout2LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout2LayoutParamscase0.leftMargin = 50;
                linearLayout2LayoutParamscase0.rightMargin = 50;

                //setting image resources for first case.
                slide_img.setImageResource(R.drawable.ic_mass_search_cust);
                rect1.setImageResource(R.drawable.selected);
                rect2.setImageResource(R.drawable.unselected);
                rect3.setImageResource(R.drawable.unselected);

                //setting heading text - if want to underline the heading
                /*
                String head1= "AYUSH hospital locator app";
                SpannableString st= new SpannableString(head1);
                st.setSpan(new UnderlineSpan(),0,st.length(),0);*/

                textView1.setText("AYUSH Hospital Locator App");

                //setting body text
                textView2.setText("hospital locator app to locate all the nearby AYUSH hospitals for you.");
                textView2.setTypeface(Typeface.SANS_SERIF);
                textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView2.setTextSize(15);
                textView2.setPadding(0,30,0,0);

                //for the next and back icons
                back.setVisibility(View.GONE);//disable
                next.setVisibility(View.VISIBLE);//enable
                break;

            case 1:
                //gravity of layout 1
                LinearLayout.LayoutParams linearLayout1LayoutParamscase1 = (LinearLayout.LayoutParams) linearLayout1.getLayoutParams();
                linearLayout1LayoutParamscase1.gravity = Gravity.CENTER;

                //margin of  layout 2
                LinearLayout.LayoutParams linearLayout2LayoutParamscase1 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                //linearLayout2LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout2LayoutParamscase1.leftMargin = 50;
                linearLayout2LayoutParamscase1.rightMargin = 50;

                //layout 3
                LinearLayout.LayoutParams linearLayout3LayoutParamscase1 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
                //linearLayout3LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout3LayoutParamscase1.leftMargin = 50;
                linearLayout3LayoutParamscase1.rightMargin = 50;

                //layout 4
                LinearLayout.LayoutParams linearLayout4LayoutParamscase1 = (LinearLayout.LayoutParams) linearLayout4.getLayoutParams();
                //linearLayout4LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout4LayoutParamscase1.leftMargin = 50;
                linearLayout4LayoutParamscase1.rightMargin = 50;

                //layout 5
                LinearLayout.LayoutParams linearLayout5LayoutParamscase1 = (LinearLayout.LayoutParams) linearLayout5.getLayoutParams();
                //linearLayout5LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout5LayoutParamscase1.leftMargin = 50;
                linearLayout5LayoutParamscase1.rightMargin = 50;

                //setting image resources
                slide_img.setImageResource(R.drawable.ic_ui_guide);
                rect1.setImageResource(R.drawable.unselected);
                rect2.setImageResource(R.drawable.selected);
                rect3.setImageResource(R.drawable.unselected);

                //setting main heading

                textView1.setText("UI Guide");
                textView1.setPadding(0,0,0,70);

                //setting guide- 1
                image2.setImageResource(R.drawable.ic_map_search_svgrepo_com);
                image2.setPadding(0,20,0,20);
                textView2.setText("Displays all hospitals under Minstry of AYUSH in India.");
                textView2.setPadding(50,0,0,0);

                //setting guide- 2
                image3.setImageResource((R.drawable.ic_near_me));
                image3.setPadding(0,20,0,20);
                textView3.setPadding(50,30,0,0);
                textView3.setText("Displays nearst hospitals");

                //setting guide-3
                image4.setImageResource((R.drawable.ic_clear));
                image4.setPadding(0,20,0,20);
                textView4.setPadding(50,30,0,0);
                textView4.setText("Clears all marker on the map");

                //setting guide-4
                image5.setImageResource((R.drawable.ic_baseline_settings_24));
                image5.setPadding(0,20,0,20);
                textView5.setPadding(50,30,0,0);
                textView5.setText("Additional settings and functions");

                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 2:

                //gravity of layout 1
                LinearLayout.LayoutParams linearLayout1LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout1.getLayoutParams();
                linearLayout1LayoutParamscase2.gravity = Gravity.CENTER;

                //margin of  layout 2
                LinearLayout.LayoutParams linearLayout2LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                //linearLayout2LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout2LayoutParamscase2.leftMargin = 50;
                linearLayout2LayoutParamscase2.rightMargin = 50;

                //layout 3
                LinearLayout.LayoutParams linearLayout3LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
                //linearLayout3LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout3LayoutParamscase2.leftMargin = 50;
                linearLayout3LayoutParamscase2.rightMargin = 50;

                //layout 4
                LinearLayout.LayoutParams linearLayout4LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout4.getLayoutParams();
                //linearLayout4LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout4LayoutParamscase2.leftMargin = 50;
                linearLayout4LayoutParamscase2.rightMargin = 50;

                //layout 5
                LinearLayout.LayoutParams linearLayout5LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout5.getLayoutParams();
                //linearLayout5LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout5LayoutParamscase2.leftMargin = 50;
                linearLayout5LayoutParamscase2.rightMargin = 50;

                //layout 6
                LinearLayout.LayoutParams linearLayout6LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout6.getLayoutParams();
                //linearLayout6LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout6LayoutParamscase2.leftMargin = 50;
                linearLayout6LayoutParamscase2.rightMargin = 50;

                //layout 7
                LinearLayout.LayoutParams linearLayout7LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout7.getLayoutParams();
                //linearLayout7LayoutParamscase1.gravity = Gravity.CENTER;
                linearLayout7LayoutParamscase2.leftMargin = 50;
                linearLayout7LayoutParamscase2.rightMargin = 50;

                //layout 8
                LinearLayout.LayoutParams linearLayout8LayoutParamscase2 = (LinearLayout.LayoutParams) linearLayout8.getLayoutParams();
                //linearLayout8LayoutParamscase2.gravity = Gravity.CENTER;
                linearLayout8LayoutParamscase2.leftMargin = 50;
                linearLayout8LayoutParamscase2.rightMargin = 50;

                slide_img.setImageResource(R.drawable.ic_additional_feature_guide);
                rect1.setImageResource(R.drawable.unselected);
                rect2.setImageResource(R.drawable.unselected);
                rect3.setImageResource(R.drawable.selected);

                //setting main heading
                textView1.setText("Additional Features Guide");
                textView1.setPadding(0,0,0,20);

                /*//setting guide- 1
                image2.setImageResource(R.drawable.ic_history);
                image2.setPadding(0,20,0,20);
                textView2.setText("Shows visited Hospitals");
                textView2.setPadding(50,20,0,0);*/

                //setting guide- 2
                image3.setImageResource((R.drawable.ic_guidlines));
                image3.setPadding(0,20,0,20);
                textView3.setPadding(50,20,0,0);
                textView3.setText("How to use the App");

                //setting guide-3
                image4.setImageResource((R.drawable.ic_customsearching));
                image4.setPadding(0,20,0,20);
                textView4.setPadding(50,20,0,0);
                textView4.setText("Filtering through Hospitals");

                //setting guide-4
                image5.setImageResource((R.drawable.ic_recommended_user_hosp));
                image5.setPadding(0,20,0,20);
                textView5.setPadding(50,20,0,0);
                textView5.setText("Recommends Hospitals according to your health");

                //setting guide-5
                image6.setImageResource((R.drawable.ic_favourites));
                image6.setPadding(0,20,0,20);
                textView6.setPadding(50,20,0,0);
                textView6.setText("Shows your favorites hospitals");

                //setting guide-6
                image7.setImageResource((R.drawable.ic_bio_info));
                image7.setPadding(0,20,0,20);
                textView7.setPadding(50,20,0,0);
                textView7.setText("Info on current outbreak of diseases");

                //setting guide-7
                image8.setImageResource((R.drawable.ic_customer_care));
                image8.setPadding(0,20,0,20);
                textView8.setPadding(50,20,0,0);
                textView8.setText("Contact us for Technical Issues and help");

                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
