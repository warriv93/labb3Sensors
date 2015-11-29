package com.example.simon.labb3unicorn;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;

public class SensorDetails extends Fragment  {

    private Sensor senAccelerometer;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    SensorListener mSensorEventListener = new SensorListener();

    private TextView name;
    private TextView vendor;
    private TextView using;
    private TextView range;
    private TextView resolution;
    private TextView data;


    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;



    public SensorDetails(Sensor sensor) {
        // Required empty public constructor
        mSensor = sensor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sensor_details, container, false);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = mSensorManager.        getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(new SensorListener(), mSensor, Sensor.TYPE_ACCELEROMETER);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        name = (TextView) view.findViewById(R.id.tv_details_name);
        name.setText(mSensor.getName());

        vendor = (TextView) view.findViewById(R.id.tv_details_vendor);
        vendor.setText(mSensor.getVendor());

        using = (TextView) view.findViewById(R.id.tv_details_usage);
        using.setText(String.valueOf(mSensor.getPower()));

        range = (TextView) view.findViewById(R.id.tv_details_range);
        range.setText(String.valueOf(mSensor.getMaximumRange()));

        resolution = (TextView) view.findViewById(R.id.tv_details_res);
        resolution.setText(String.valueOf(mSensor.getResolution()));

        data = (TextView) view.findViewById(R.id.tv_details_data);
        data.setText(String.valueOf(mSensor.getFifoMaxEventCount()));



    }


    @Override
    public void onResume()	{
        mSensorManager.registerListener(mSensorEventListener,	mSensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }


    @Override
     public void onPause()	{
        mSensorManager.unregisterListener(mSensorEventListener);
        super.onPause();
    }

    private class SensorListener implements SensorEventListener	{
        @Override
        public void onSensorChanged(SensorEvent event)	{
           /** for (Float	v	:	event.values)	{
                data.append("\n" +	v);
            }**/

            Sensor mySensor = event.sensor;

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];



            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Toast.makeText(getActivity(), "working?", Toast.LENGTH_SHORT).show();
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor	sensor,	int accuracy)	{

        }
    }


}
