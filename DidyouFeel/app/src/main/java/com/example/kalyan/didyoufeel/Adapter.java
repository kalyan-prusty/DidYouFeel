package com.example.kalyan.didyoufeel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KALYAN on 31-05-2017.
 */

public class Adapter extends BaseAdapter {
    private ArrayList<earthquake> list = new ArrayList<earthquake>();
    private Context context;
    public Adapter(Context c,ArrayList<earthquake> list){
        this.context = c;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.single_list,parent,false);
        }

        TextView  mag =(TextView)convertView.findViewById(R.id.mag);
        TextView  directiontext =(TextView)convertView.findViewById(R.id.direction);
        TextView  placemain = (TextView) convertView.findViewById(R.id.placemain);
        TextView  date =(TextView)convertView.findViewById(R.id.date);
        TextView  time =(TextView)convertView.findViewById(R.id.time);

        String place = list.get(position).getPlace();
        String[] placeString = list.get(position).getPlace().split("of");
        String direction , mainplace ="";
        direction = placeString[0];
        if(!direction.equals(place)){
        mainplace = placeString[1];}

        mag.setText(list.get(position).getMagnitude());
        if(mainplace.equals("")){
            directiontext.setText("Near to");
            placemain.setText(direction);
        }
        else{
            directiontext.setText(direction);
            placemain.setText(mainplace);
        }

        date.setText(QueryUtils.formatDate(new Date(list.get(position).getDateinmillis())));
        time.setText(QueryUtils.formatTime(new Date(list.get(position).getDateinmillis())));

        return convertView;
    }
}
