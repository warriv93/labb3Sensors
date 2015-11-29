package com.example.simon.labb3unicorn;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by simon on 2015-02-06.
 */
public class SensorAdapter extends ArrayAdapter<Sensor> {
    Context context;
    List<Sensor> sensors;

    public SensorAdapter(Context context,	int textViewResourceId, List<Sensor>	sensors)	{
        super(context,	textViewResourceId,	sensors);
        this.context =	context;
        this.sensors =	sensors;
    }

    @Override
    public View	getView(int position,	View	convertView,	ViewGroup	parent)	{
        View	v	=	convertView;
        ViewHolder	holder;
        if (v	==	null)	{
            LayoutInflater	vi	=	LayoutInflater.from(context);
            v	=	vi.inflate(R.layout.sensor_item,	null);
            holder	=	new ViewHolder();
            holder.name =	(TextView)	v.findViewById(R.id.sensor_item_name);
            holder.vendor =	(TextView)	v.findViewById(R.id.sensor_item_vendor);
            v.setTag(holder);
        }	else {
            holder	=	(ViewHolder)	v.getTag();
        }
        holder.name.setText(sensors.get(position).getName());
        holder.vendor.setText("by	" +	sensors.get(position).getVendor());
        return v;
    }
    static class ViewHolder	{
        TextView	vendor;
        TextView	name;
    }
}
