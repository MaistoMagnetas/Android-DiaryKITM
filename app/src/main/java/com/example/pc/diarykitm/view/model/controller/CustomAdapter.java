package com.example.pc.diarykitm.view.model.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pc.diarykitm.R;
import com.example.pc.diarykitm.view.model.JournalEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 4/9/2018.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<JournalEntry> entries = new ArrayList<JournalEntry>();
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, ArrayList<JournalEntry> entries) {
        this.context = applicationContext;
        this.entries = entries;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.listview_item,null);
        //Init all listview_item textViews:
        TextView tvDate = (TextView) convertView.findViewById(R.id.listItemDate);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.listItemTitle);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.listItemDescription);
        TextView tvMood = (TextView) convertView.findViewById(R.id.listItemMood);
        TextView tvPace = (TextView) convertView.findViewById(R.id.listItemPace);
        TextView tvType = (TextView) convertView.findViewById(R.id.listItemType);
        tvDate.setText(entries.get(position).getDate().toString());
        tvTitle.setText(entries.get(position).getTitle().toString());
        tvDescription.setText(entries.get(position).getDescription().toString());
        tvMood.setText(entries.get(position).getMood().toString());
        tvPace.setText(entries.get(position).getPace().toString());
        tvType.setText(entries.get(position).getType().toString());
        return convertView;
    }

    //Method for init items of DB to listView
    public void initTextView(View convertView){
        TextView tvDate = (TextView) convertView.findViewById(R.id.listItemDate);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.listItemTitle);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.listItemDescription);
        TextView tvMood = (TextView) convertView.findViewById(R.id.listItemMood);
        TextView tvPace = (TextView) convertView.findViewById(R.id.listItemPace);
        TextView tvType = (TextView) convertView.findViewById(R.id.listItemType);
    }
}
