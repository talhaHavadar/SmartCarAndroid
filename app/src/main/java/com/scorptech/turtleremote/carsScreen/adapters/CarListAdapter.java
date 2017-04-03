package com.scorptech.turtleremote.carsScreen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scorptech.turtleremote.R;
import com.scorptech.turtleremote.models.Car;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by talhahavadar on 07/12/2016.
 */

public class CarListAdapter extends BaseAdapter {

    protected ArrayList<Car> mData;
    protected LayoutInflater mInflater;

    public CarListAdapter(ArrayList<Car> data, Context context) {
        mData = data;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Car getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.car_list_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Car item = getItem(i);

        holder.carId.setText("" + item.id);
        holder.image.setImageResource(R.mipmap.list_item_img);
        holder.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.ivListItem)
        ImageView image;
        @BindView(R.id.tvCarId)
        TextView carId;
        @BindView(R.id.tvHardwareId)
        TextView hardwareId;
        @BindView(R.id.btSettings)
        ImageButton settingsButton;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }



}
