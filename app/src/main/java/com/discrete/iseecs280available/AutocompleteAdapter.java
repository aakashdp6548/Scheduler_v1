package com.discrete.iseecs280available;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AutocompleteAdapter extends ArrayAdapter {

    private Context context;
    private int layout;
    private String[] data;
    private Typeface tf;

    public AutocompleteAdapter(Context context, int layout, String[] data) {
        super(context, layout, data);
        this.context = context;
        this.layout = layout;
        this.data = data;
        this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
    }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, null);
            TextView courseName = (TextView) rowView.findViewById(R.id.text1);

            courseName.setText(data[position]);

            return rowView;
        }
    }
}
