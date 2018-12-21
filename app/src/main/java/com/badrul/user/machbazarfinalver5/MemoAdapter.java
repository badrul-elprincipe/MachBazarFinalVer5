package com.badrul.user.machbazarfinalver5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MemoAdapter extends BaseAdapter{
    ArrayList<memoitem>list;
    Context c;


    MemoAdapter(Context context){
        c=context;
        list=new ArrayList<memoitem>();



    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) c.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.memolayout,parent,false);
        TextView naam=row.findViewById(R.id.nam);
        TextView how=row.findViewById(R.id.koy);
        TextView kg=row.findViewById(R.id.kilo);


        memoitem tmp=list.get(position);
        naam.setText(tmp.naam);
        how.setText(tmp.koyta);
        kg.setText(tmp.koto);














        return row;
    }



}
