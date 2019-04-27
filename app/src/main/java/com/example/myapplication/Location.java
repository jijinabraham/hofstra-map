package com.example.myapplication;

import android.content.Context;
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

public class Location extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Adams Hall", "Weed Hall", "Hofstra Hall", "Memorial Hall"};
    String mSubTitle[] = {"Department of Computer Science", "Department of Engineering", "Main Hall", "Bits & Bytes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        listView = findViewById(R.id.locationList);

        LocationListAdaptor locationListAdaptor = new LocationListAdaptor(this, mTitle, mSubTitle);
        listView.setAdapter(locationListAdaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
            subTitle.setText(mSubTitle[position]);


            return row;
        }
    }
}
