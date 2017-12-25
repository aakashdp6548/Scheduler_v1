package com.discrete.iseecs280available;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AutocompleteAdapter extends ArrayAdapter {

    private Context context;
    private String[] data;
    //private Typeface tf;

    public AutocompleteAdapter(Context context, String[] data) {
        super(context, R.layout.drop_down_format, data);
        this.context = context;
        this.data = data;
        //this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drop_down_format, null);
        TextView courseName = (TextView) rowView.findViewById(R.id.text1);

        courseName.setText(data[position]);

        return rowView;
    }

}
