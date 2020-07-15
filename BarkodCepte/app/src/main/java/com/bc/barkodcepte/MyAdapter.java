package com.bc.barkodcepte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements Filterable {
    CustomFilter cs;
    Context c;
    ArrayList<SingleRow>orginalArray,tempArray;
    public  MyAdapter(Context c, ArrayList<SingleRow>orginalArray)
    {
        this.c = c;
        this.orginalArray = orginalArray;
        this.tempArray = orginalArray;
    }
    @Override
    public int getCount() {
        return orginalArray.size();
    }

    @Override
    public Object getItem(int i) {
        return orginalArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.column_row2,null);
        TextView t1 = (TextView)row.findViewById(R.id.TextFirst);
        TextView t2 = (TextView)row.findViewById(R.id.textView12);

        t1.setText(orginalArray.get(i).getName());
        t2.setText(i+"");

        return row;
    }
    @Override
    public Filter getFilter(){
        if (cs ==null)
        {
            cs = new CustomFilter();
        }
        return cs;
    }
    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint!=null && constraint.length()>0)
            {
                constraint = constraint.toString().toUpperCase();
                ArrayList<SingleRow> filters = new ArrayList<>();
                for (int i = 0; i<tempArray.size();i++)
                {
                    if(tempArray.get(i).getName().toUpperCase().contains(constraint))
                    {
                        SingleRow singleRow = new SingleRow(tempArray.get(i).getName());
                        filters.add(singleRow);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }
            else {results.count=tempArray.size();
                  results.values=tempArray;}
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    orginalArray = (ArrayList<SingleRow>)filterResults.values;
                    notifyDataSetChanged();
        }
    }


}
