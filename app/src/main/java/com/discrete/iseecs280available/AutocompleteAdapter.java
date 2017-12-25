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

    //change this to ArrayList<String> data_array or you will pay for it later, I promise
    public AutocompleteAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.drop_down_format, data);
        this.context = context;
        this.data = data;
        filteredData = this.data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drop_down_format, null);
        TextView courseName = (TextView) rowView.findViewById(R.id.text1);

        System.out.println(filteredData);
        try {
            courseName.setText(filteredData.get(filteredData.indexOf(data.get(position))));
        } catch (Exception e){
            courseName.setText("");
            courseName.setTextSize(0);
        }

        return rowView;
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();
                ArrayList<String> resultingData = new ArrayList();

                // check to make sure that entry is not just whitespace
                if (!charSequence.toString().trim().isEmpty()) {
                    for (String i : data) {
                        if (charSequence.toString().toLowerCase()
                                .equals(i.substring(0, charSequence.length()).toLowerCase())) {
                            resultingData.add(i);
                        }
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


}
