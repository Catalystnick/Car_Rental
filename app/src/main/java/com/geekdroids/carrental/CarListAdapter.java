package com.geekdroids.carrental;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CarListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Cars> carsList;

    public CarListAdapter(Activity mContext,List<Cars> carsList){
        super(mContext,R.layout.activity_view_cars,carsList);
        this.mContext = mContext;
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.carlist_custom,null,true);

        TextView carModel = listItemView.findViewById(R.id.car_Model);

        Cars cars = carsList.get(position);

        carModel.setText(cars.getModel());

        return listItemView;

    }

}
