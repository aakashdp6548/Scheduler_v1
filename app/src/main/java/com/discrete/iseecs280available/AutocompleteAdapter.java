package com.discrete.iseecs280available;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class AutocompleteAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<String> data;
    private ArrayList<String> filteredData;

    public AutocompleteAdapter(Context context, String[] data_array) {
        super(context, R.layout.drop_down_format, data_array);
        this.context = context;
        this.data = new ArrayList<String>(Arrays.asList(data_array));
        filteredData = this.data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drop_down_format, null);
        TextView courseName = (TextView) rowView.findViewById(R.id.text1);

        System.out.println(filteredData);
        courseName.setText(filteredData.get(position));

        return rowView;
    }

    /*
    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();
                ArrayList<String> resultingData = new ArrayList();
                for(String i: resultingData){
                    if(charSequence.toString().substring(0, charSequence.length())
                            .equals(i.substring(0, charSequence.length()))){
                        resultingData.add(i);
                    }
                }
                results.values = resultingData;
                results.count = resultingData.size();
                return results;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredData = (ArrayList<String>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    */
}
