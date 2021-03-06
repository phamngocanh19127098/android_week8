package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    // Main GUI - A NEWS application based on National Public Radio RSS material
    ArrayAdapter<String> adapterMainSubjects;
    ListView myMainListView;
    Context context;
    SingleItem selectedNewsItem;
    // hard-coding main NEWS categories (TODO: use a resource file)
    String [][] myUrlCaptionMenu = {
            {"https://vnexpress.net/rss/the-gioi.rss", "Thế giới"},
            {"https://vnexpress.net/rss/suc-khoe.rss", "Sức khỏe"},
            {"https://vnexpress.net/rss/gia-dinh.rss", "Đời sống"},
            {"https://vnexpress.net/rss/du-lich.rss", "Du lịch"},
            {"https://vnexpress.net/rss/khoa-hoc.rss", "Khoa học"},
            {"https://vnexpress.net/rss/so-hoa.rss", "Số hóa"},
            {"https://vnexpress.net/rss/oto-xe-may.rss", "Xe"}
    };
    //define convenient URL and CAPTIONs arrays
    String[] myUrlCaption = new String[myUrlCaptionMenu.length];
    String[] myUrlAddress = new String[myUrlCaptionMenu.length];
    public static String niceDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy",
                Locale.US);
        return sdf.format(new Date()); //Monday Apr 7, 2014
    }
    @Override // layout.activity_main
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        for (int i=0; i<myUrlAddress.length; i++) {
            myUrlAddress[i] = myUrlCaptionMenu[i][0]; myUrlCaption[i] = myUrlCaptionMenu[i][1];
        }
        context = getApplicationContext();
        this.setTitle("NPR Headline News\n" + niceDate() );
// user will tap on a ListView’s row to request category’s headlines
        myMainListView = (ListView)this.findViewById(R.id.myListView);
        myMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
                String urlAddress = myUrlAddress[_index], urlCaption = myUrlCaption[_index];
//create an Intent to talk to activity: ShowHeadlines
                Intent callShowHeadlines = new Intent(MainActivity.this, ShowHeadlines.class);
//prepare a Bundle and add the input arguments: url & caption
                Bundle myData = new Bundle();
                myData.putString("urlAddress", urlAddress); myData.putString("urlCaption", urlCaption);
               // myData.putString("titleClicked",);
                callShowHeadlines.putExtras(myData); startActivity(callShowHeadlines);
            }
        });
// fill up the Main-GUI’s ListView with main news categories
        adapterMainSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myUrlCaption);
        myMainListView.setAdapter(adapterMainSubjects);
    }//onCreate
} // M