package com.example.benny.goweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by benny on 17/1/20.
 */
public class ChooseAreaFragmentAdapter extends ArrayAdapter<String> {

    private int resourceId;
    private List<String> Names;

    public ChooseAreaFragmentAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
        Names = objects;
    }

    @Override
    public String getItem(int position) {
        return Names.get(position);
    }

    public int getResourceId() {
        return resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String string = getItem(position);
        View view = LayoutInflater.from(MyAppContext.getContext()).inflate(getResourceId(), parent, false);
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        itemName.setText(string);
        return view;

    }
}
