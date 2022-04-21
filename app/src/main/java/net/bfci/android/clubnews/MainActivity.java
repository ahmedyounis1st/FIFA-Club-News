package net.bfci.android.clubnews;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ListView league ;
Button btn;

    String [] itemsArab = {"الدوري المصري","الدوري الاسباني","الدوري الايطالي", "الدوري الفرنسي","الدوري الانجليزي" ,"الدوري الالماني" };
    String [] itemsEng = {"Egyptian League", "La Liga","Italian League","French League","Premier League","Bundes Liga"};
    int [] images = {
            R.drawable.pics1,
            R.drawable.pics2,
            R.drawable.pics3,
            R.drawable.pics4,
            R.drawable.pics5,
            R.drawable.pics6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        league = (ListView) findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.btn);

        CustomAdapterLeagueList adapter = new CustomAdapterLeagueList(this,itemsEng,itemsArab,images);
        league.setAdapter(adapter);

        league.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFromList = (String) league.getItemAtPosition(i);
                Toast.makeText(getBaseContext(), (i+1)+" "+selectedFromList, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("JSON_KEY",selectedFromList);
                startActivity(intent);


            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main4Activity.class);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public boolean onNavigateUpFromChild(Activity child) {
//
//        Main2Activity.names = null;
//        Main2Activity.rates = null;
//        Main2Activity.images = null;
//        Main2Activity.shortnames = null;
//        Main2Activity.foundations = null;
//        Main2Activity.stadiums = null;
//        Main2Activity.trainers = null;
//        Main2Activity.stars = null;
//        Main2Activity.nicknames = null;
//        Main2Activity.localchs = null;
//        Main2Activity.worldchs = null;
//        return super.onNavigateUpFromChild(child);
//
//    }
}
