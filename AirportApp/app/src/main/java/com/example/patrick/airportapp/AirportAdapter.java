package com.example.patrick.airportapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by patrick on 22-10-2015.
 */
public class AirportAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflator;
    ArrayList airportArrayList;

    public AirportAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Airport> airportArrayList)
    {
        this.context = context;
        this.layoutInflator = layoutInflater;
        this.airportArrayList = airportArrayList;
    }

    @Override
    public int getCount() {
        return airportArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return airportArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;

        if(convertView == null)
        {
            convertView = layoutInflator.inflate(R.layout.airport_listview_row, null);

            viewholder = new ViewHolder();
            viewholder.airporttname = (TextView) convertView.findViewById(R.id.airportname);
            viewholder.icao = (TextView)convertView.findViewById(R.id.Icao);

            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }

        //Airport airport = (Airport) airportArrayList.get(position);
        Airport airport = (Airport)airportArrayList.get(position);
        viewholder.airporttname.setText(airport.name);
        viewholder.icao.setText(airport.icao);

        return convertView;
    }

    private static class ViewHolder {
        public TextView airporttname;
        public TextView icao;
    }
}
