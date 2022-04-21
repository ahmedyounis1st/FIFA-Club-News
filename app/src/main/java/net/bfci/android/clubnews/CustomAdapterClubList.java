package net.bfci.android.clubnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genius on 30/07/2018.
 */

public class CustomAdapterClubList extends BaseAdapter {
    Context CustomContext;
    String[] CustomItemsName;
    String[] CustomItemsRate;

    public  CustomAdapterClubList(Context context , String[] name, String[] rate){

        this.CustomContext = context;
        this.CustomItemsName = name;
        this.CustomItemsRate =rate;

    }

    @Override
    public int getCount() {
        return CustomItemsName.length;
    }

    @Override
    public Object getItem(int i) {
        return CustomItemsName[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(CustomContext);
        view = inflater.inflate(R.layout.club_list,null);
        TextView txt11 = (TextView) view.findViewById(R.id.textView1);
        TextView txt22= (TextView) view.findViewById(R.id.textView2);
        txt11.setText(CustomItemsName[i]);
        txt22.setText(CustomItemsRate[i]);
        return view;
    }
}
