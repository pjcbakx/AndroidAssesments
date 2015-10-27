package com.example.patrick.hueapp.Control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patrick.hueapp.Model.HueLamp;
import com.example.patrick.hueapp.R;

import java.util.ArrayList;

/**
 * Created by patrick on 6-10-2015.
 */
public class LampAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflator;
    ArrayList lightsArrayList;

    public LampAdapter(Context context, LayoutInflater layoutInflater, ArrayList<HueLamp> lightsArrayList)
    {
        this.context = context;
        this.layoutInflator = layoutInflater;
        this.lightsArrayList = lightsArrayList;
    }

    @Override
    public int getCount() {
        return lightsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return lightsArrayList.get(position);
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
            convertView = layoutInflator.inflate(R.layout.lamp_listview_row, null);

            viewholder = new ViewHolder();
            viewholder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewholder.lighttname = (TextView) convertView.findViewById(R.id.lampname);
            viewholder.isOn = (TextView) convertView.findViewById(R.id.isOn);

            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }

        HueLamp lamp = (HueLamp) lightsArrayList.get(position);

        viewholder.lighttname.setText(lamp.name);
        if(lamp.isOn)
            viewholder.isOn.setText("On");
        else
            viewholder.isOn.setText("Off");
        viewholder.imageView = null;

        return convertView;
    }

    // Holds all data to the view. Wordt evt. gerecycled door Android
    private static class ViewHolder {
        public ImageView imageView;
        public TextView lighttname;
        public TextView isOn;
    }
}
