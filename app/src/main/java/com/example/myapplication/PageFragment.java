package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();
        int pageNumber = bundle.getInt("pageNumber");
        System.out.println(pageNumber);

        view = inflater.inflate(R.layout.page_fragment_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.page_image);
        switch (pageNumber)
        {
            case 1:
                imageView.setImageResource(R.drawable.system_tutorial_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.system_tutorial_2);
                break;
        }

        //TextView textView = (TextView)view.findViewById(R.id.pageNumber);
        //textView.setText(Integer.toString(pageNumber));

        return view;
    }
}
