package com.example.dburtnja.androidticketfinder.view.ListView;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dburtnja.androidticketfinder.R;
import com.example.dburtnja.androidticketfinder.model.Passenger;

import java.util.List;

/**
 * Created by denys on 9/6/17.
 */

public class PassengerAdapter extends ArrayAdapter<Passenger> {
    public static final String TIME_VIEW = "dd.MM.yyyy hh:mm";

    public PassengerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Passenger> contentValues) {
        super(context,resource, contentValues);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Passenger   passenger;

        passenger = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
        }
        if (passenger != null) {
            ((TextView) convertView.findViewById(R.id.first_name)).setText(passenger.getFirstName());
            ((TextView) convertView.findViewById(R.id.last_name)).setText(passenger.getLastName());
            ((TextView) convertView.findViewById(R.id.from)).setText(passenger.getFrom().getName());
            ((TextView) convertView.findViewById(R.id.till)).setText(passenger.getTill().getName());
            ((CheckBox) convertView.findViewById(R.id.p_check)).setChecked(passenger.isPlaceP());
            ((CheckBox) convertView.findViewById(R.id.k_check)).setChecked(passenger.isPlaceK());
            ((CheckBox) convertView.findViewById(R.id.c1_check)).setChecked(passenger.isPlaceC1());
            ((CheckBox) convertView.findViewById(R.id.c2_check)).setChecked(passenger.isPlaceC2());
            ((TextView) convertView.findViewById(R.id.dateStart)).setText(passenger.dateStartAsString(TIME_VIEW));
            ((TextView) convertView.findViewById(R.id.dateEnd)).setText(passenger.dateEndAsString(TIME_VIEW));
        }
        return convertView;
    }
}
