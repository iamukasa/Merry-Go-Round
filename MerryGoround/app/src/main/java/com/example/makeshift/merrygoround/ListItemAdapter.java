package com.example.makeshift.merrygoround;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by irving on 4/20/15.
 */
public class ListItemAdapter extends ArrayAdapter<ListItem> {
    private ArrayList<ListItem> objects;
    Context context;

    public ListItemAdapter(Context context, int textViewResourceId,
                           ArrayList<ListItem> objects) {
        super(context, textViewResourceId, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        ListItem i = objects.get(position);
        if (i != null) {

            TextView tvName = (TextView) v.findViewById(R.id.memName);
            TextView tvphoneNo = (TextView) v.findViewById(R.id.memNo);

            if (tvName != null) {
                tvName.setText(i.getName());
            }

            if (tvphoneNo != null) {
                tvphoneNo.setText(i.getPhoneNo());
            }


        }
        return v;
    }
}