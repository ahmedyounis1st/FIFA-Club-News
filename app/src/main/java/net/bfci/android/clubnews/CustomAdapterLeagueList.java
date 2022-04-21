package net.bfci.android.clubnews;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Genius on 28/07/2018.
 */

public class CustomAdapterLeagueList extends BaseAdapter {

    Context CustomContext;
    String [] CustomItemsArb;
    String [] CustomItemsEng;
    int [] CustomImages;
    public  CustomAdapterLeagueList(Context context , String [] arb, String [] eng , int [] img){

        this.CustomContext = context;
        this.CustomItemsArb = arb;
        this.CustomItemsEng =eng;
        this.CustomImages = img;

    }


    @Override
    public int getCount() {
        return CustomItemsArb.length;
    }

    @Override
    public Object getItem(int i) {
        return CustomItemsArb[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(CustomContext);
        view = inflater.inflate(R.layout.league_list,null);

        TextView txt11 = (TextView) view.findViewById(R.id.textView1);
        TextView txt22= (TextView) view.findViewById(R.id.textView2);

        ImageView img = (ImageView) view.findViewById(R.id.imageView);

        txt11.setText(CustomItemsArb[i]);
        txt22.setText(CustomItemsEng[i]);
        img.setImageResource(CustomImages[i]);

        return view;
    }
}

