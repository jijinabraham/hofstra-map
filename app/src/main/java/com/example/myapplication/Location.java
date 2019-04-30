package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.io.InputStream;
import java.util.LinkedList;
import java.io.File;

import com.example.myapplication.mod1.NodeList;

public class Location extends AppCompatActivity {

    private final int SEND_LOCATION = 1;

    ListView listView;
    String mTitle[];
    String mSubTitle[];
    LinkedList<String> temp;
    //convert LinkedList to array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        InputStream h = getResources().openRawResource(R.raw.node_hor);
        InputStream v = getResources().openRawResource(R.raw.node_ver);
        InputStream s = getResources().openRawResource(R.raw.struct);

        NodeList name_me = new NodeList(h,v,s);
        temp = name_me.structGUI();
        LinkedList<String> pmet = name_me.searchDepartment();

        mTitle = convertor(temp);
        mSubTitle = convertor(pmet);

        listView = findViewById(R.id.locationList);

        LocationListAdaptor locationListAdaptor = new LocationListAdaptor(this, mTitle, mSubTitle);
        listView.setAdapter(locationListAdaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), drawer.class);
                intent.putExtra("location",listView.getItemAtPosition(position).toString());
                //System.out.println(listView.getItemAtPosition(position).toString());
                //startActivityForResult(intent, SEND_LOCATION);
                setResult(SEND_LOCATION, intent);
                finish();
            }
        });
    }
    public String[] convertor(LinkedList<String> list)
    {
        String arList[] = new String[list.size()];
        for(int i = 0; i < list.size(); i++)
        {
            arList[i] = list.get(i);
        }
        return arList;
    }

    class LocationListAdaptor extends ArrayAdapter<String>
    {
        Context context;
        String mTitle[], mSubTitle[];

        LocationListAdaptor (Context context, String mTitle[], String mSubTitle[])
        {
            super(context, R.layout.locationlist_row, R.id.mainTitle, mTitle);
            this.context = context;
            this.mSubTitle = mSubTitle;
            this.mTitle = mTitle;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.locationlist_row, parent, false);
            TextView mainTitle = row.findViewById(R.id.mainTitle);
            TextView subTitle = row.findViewById(R.id.subTitle);

            mainTitle.setText(mTitle[position]);
            //subTitle.setText(mSubTitle[position]);



            return row;
        }
    }
}
