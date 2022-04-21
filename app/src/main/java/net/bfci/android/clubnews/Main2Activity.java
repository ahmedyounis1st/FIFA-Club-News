package net.bfci.android.clubnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    ProgressBar bar;
    static String[] names = null;
    static String[] rates = null;
    static String[] images = null;
    static String[] shortnames = null;
    static String[] foundations = null;
    static String[] stadiums = null;
    static String[] trainers = null;
    static String[] stars = null;
    static String[] nicknames = null;
    static String[] localchs = null;
    static String[] worldchs = null;

    public boolean main3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getBaseContext(), "i am here", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bar = (ProgressBar) findViewById(R.id.bar);
        listView = (ListView) findViewById(R.id.listView);
        // ArrayAdapter adapter = new ArrayAdapter(this,R.layout.club_list,R.id.textView1,rates);
         //listView.setAdapter(adapter);
        if (names != null) {
            CustomAdapterClubList adpter = new CustomAdapterClubList(Main2Activity.this, names, rates);
            listView.setAdapter(adpter);
        } else {
            Toast.makeText(getBaseContext(), "i am here", Toast.LENGTH_SHORT).show();
            try {
                URL url = new URL("https://api.npoint.io/6d60f29680e9d780e84a");
                GetClubs connection = new GetClubs();
                connection.execute(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getBaseContext(), "done "+(i+1), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                intent.putExtra("name", names[i]);
                intent.putExtra("imageurl", images[i]);
                intent.putExtra("shortname", shortnames[i]);
                intent.putExtra("foundationyear", foundations[i]);
                intent.putExtra("stadium", stadiums[i]);
                intent.putExtra("trainer", trainers[i]);
                intent.putExtra("star", stars[i]);
                intent.putExtra("nickname", nicknames[i]);
                intent.putExtra("localch", localchs[i]);
                intent.putExtra("worldch", worldchs[i]);
                intent.putExtra("rate", rates[i]);
                main3 = true;
                startActivity(intent);


            }
        });
    }

    ArrayList<String[]> connect(URL url) {
        Toast.makeText(getBaseContext(), "i am here", Toast.LENGTH_SHORT).show();
        HttpURLConnection con = null;
        //List<List<String>> leagues = new ArrayList<List<String>>();
        ArrayList<String[]> leagues = new ArrayList<String[]>();
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.connect();
            InputStream inputStream = con.getInputStream();
            String jsondata = readfrostream(inputStream);
            leagues = parsejson(jsondata);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return leagues;


    }


    String readfrostream(InputStream inputStream) {

        StringBuilder builder = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    ArrayList<String[]> parsejson(String jsontext) {
        /*List<List<String>> leagues = new ArrayList<List<String>>();
        ArrayList<String> singleClub = new ArrayList<String>();*/
        ArrayList<String[]> leagues = new ArrayList<String[]>();
        try {
            JSONObject data = new JSONObject(jsontext);
            Intent intent = getIntent();
            String JSON_KEY = intent.getExtras().getString("JSON_KEY");
            JSONArray fet = data.getJSONArray(JSON_KEY);
            names = new String[fet.length()];
            rates = new String[fet.length()];
            shortnames = new String[fet.length()];
            foundations = new String[fet.length()];
            stadiums = new String[fet.length()];
            trainers = new String[fet.length()];
            stars = new String[fet.length()];
            nicknames = new String[fet.length()];
            images = new String[fet.length()];
            localchs = new String[fet.length()];
            worldchs = new String[fet.length()];

            for (int i = 0; i < fet.length(); i++) {
                JSONObject object = fet.getJSONObject(i);
                String place = object.getString("name");
                names[i] = place;
                place = object.getString("rate");
                rates[i] = place;
                shortnames[i] = object.getString("shortname");
                foundations[i] = object.getString("foundationyear");
                stadiums[i] = object.getString("stadium");
                trainers[i] = object.getString("trainer");
                stars[i] = object.getString("star");
                nicknames[i] = object.getString("nickname");
                localchs[i] = object.getString("localch");
                worldchs[i] = object.getString("worldch");
                images[i] = object.getString("imgurl");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        leagues.add(names);
        leagues.add(rates);
        return leagues;


    }

    class GetClubs extends AsyncTask<URL, Void, ArrayList<String[]>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }


        @Override
        protected ArrayList<String[]> doInBackground(URL... urls) {
           // return connect(urls[0]);
           HttpConnectionHandler handler = new HttpConnectionHandler();
            String result = handler.ServiceCall("https://api.npoint.io/6f7107f3897063c93fb7");
            ArrayList<String[]> leagues = new ArrayList<String[]>();
            leagues = parsejson(result);
            return leagues;

        }

        @Override
        protected void onPostExecute(ArrayList<String[]> strings) {
            super.onPostExecute(strings);
            CustomAdapterClubList adapter = new CustomAdapterClubList(Main2Activity.this, strings.get(0), strings.get(1));
            listView.setAdapter(adapter);
            bar.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (main3 == false) {
            names = null;
            rates = null;
            images = null;
            shortnames = null;
            foundations = null;
            stadiums = null;
            trainers = null;
            stars = null;
            nicknames = null;
            localchs = null;
            worldchs = null;
        }

    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        names = null;
//        rates = null;
//        images = null;
//        shortnames = null;
//        foundations = null;
//        stadiums = null;
//        trainers = null;
//        stars = null;
//        nicknames = null;
//        localchs = null;
//        worldchs = null;
//    }
}