package com.scorptech.turtleremote.addCarScreen.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scorptech.turtleremote.R;
import com.scorptech.turtleremote.carsScreen.adapters.CarListAdapter;
import com.scorptech.turtleremote.models.Car;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by talhahavadar on 20/12/2016.
 */

public class AddCarListAdapter extends CarListAdapter{



    public AddCarListAdapter(ArrayList<Car> cars, Context context) {
        super(cars, context);

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.add_car_list_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Car item = getItem(i);

        holder.ivSignal.setImageResource(R.mipmap.signal_strong);
        holder.tvSSID.setText("Deneme");
        return view;
    }

    static class ViewHolder {

        @BindView(R.id.ivSignalStrength)
        ImageView ivSignal;
        @BindView(R.id.tvSSID)
        TextView tvSSID;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
