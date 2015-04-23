package com.example.makeshift.merrygoround;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by irving on 4/20/15.
 */
public class ContributionsItemAdapter extends ArrayAdapter<ContributionsItems>  implements Filterable {
    private ArrayList<ContributionsItems> objects;
    private ContributionsItemsFilter filter;

    private ArrayList<ContributionsItems> originalList;

    Context context;

    public ContributionsItemAdapter(Context context, int textViewResourceId,
                                    ArrayList<ContributionsItems> objects) {
         super(context,textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
        this.originalList = new ArrayList<ContributionsItems>();
        this.originalList.addAll(objects);
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new ContributionsItemsFilter ();
        }
        return  filter;
    }
    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.contributions_item, null);
        }

        ContributionsItems i = objects.get(position);
        if (i != null) {

            TextView tvAmount = (TextView) v.findViewById(R.id.memContAMount);
            TextView tvDate = (TextView) v.findViewById(R.id.memCountDate);

            if (tvAmount != null) {
                tvAmount.setText(i.getContribution());
            }

            if (tvDate != null) {
                tvDate.setText(i.getDates());
            }


        }
        return v;
    }
    private class ContributionsItemsFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<ContributionsItems> filteredItems = new ArrayList<ContributionsItems>();

                for(int i = 0, l = originalList.size(); i < l; i++)
                {
                    ContributionsItems country = originalList.get(i);
                    if(country.toString().toLowerCase().contains(constraint))
                        filteredItems.add(country);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            objects = (ArrayList<ContributionsItems>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = objects.size(); i < l; i++)
                add(objects.get(i));
            notifyDataSetInvalidated();
        }
    }

}