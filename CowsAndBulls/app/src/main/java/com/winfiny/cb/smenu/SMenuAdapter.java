package com.winfiny.cb.smenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.winfiny.cb.R;

import java.util.ArrayList;

/**
 * Created by umasankar on 2/6/15.
 */
public class SMenuAdapter  extends BaseAdapter {

    private Context context;
    private ArrayList<SMenuItem> smenuItems;

    public SMenuAdapter(Context context, ArrayList<SMenuItem> smenuItems){
        this.context = context;
        this.smenuItems = smenuItems;
    }

    @Override
    public int getCount() {
        return smenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return smenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.smenu_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        imgIcon.setImageResource(smenuItems.get(position).getIcon());
        txtTitle.setText(smenuItems.get(position).getTitle());

        // displaying count
        // check whether it set visible or not
        if (smenuItems.get(position).getCounterVisibility()){
            txtCount.setText(smenuItems.get(position).getCount());
        } else {
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }
        return convertView;
    }

}