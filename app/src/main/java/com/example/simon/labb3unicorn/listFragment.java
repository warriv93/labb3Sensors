
package com.example.simon.labb3unicorn;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class listFragment extends ListFragment {
    ListView lv;

    public OnSensorSelectedListener mOnSensorSelectedListener;
    public List<Sensor> sensors =	new ArrayList<Sensor>();
    public SensorDetails details_fragment;
    public Sensor sensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SensorManager manager =	(SensorManager)	getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensors =	manager.getSensorList(Sensor.TYPE_ALL);
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        initiate(view);
        return view;
    }

    public void initiate(View view){
        lv = (ListView)view.findViewById(android.R.id.list);
        lv.setAdapter(new SensorAdapter(getActivity(), R.layout.sensor_item, sensors));
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
          mOnSensorSelectedListener = (OnSensorSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public void onListItemClick(ListView l,	View	v,	int position,	long id)	{
        mOnSensorSelectedListener.onSensorSelected(sensors.get(position).getType());
        super.onListItemClick(l,	v,	position,	id);

        sensor = sensors.get(position);

        details_fragment = new SensorDetails(sensor);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, details_fragment);
        ft.commit();
    }
    public interface OnSensorSelectedListener	{
        public void onSensorSelected(int sensorType);
    }

}
