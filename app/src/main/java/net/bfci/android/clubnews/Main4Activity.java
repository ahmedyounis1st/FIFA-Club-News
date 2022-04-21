package net.bfci.android.clubnews;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Main4Activity extends AppCompatActivity {

    private ProgressBar bar;
    private ListView listView;
    String names[];
    String rates[];
    Cursor c;
    DbAdapterClubTable db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        bar = (ProgressBar) findViewById(R.id.bar);
        listView = (ListView) findViewById(R.id.listView);
        db = new DbAdapterClubTable(this);
        try {
            String destPath = "/data/data/" + getPackageName() +
                    "/databases/MyDBB";
            File f = new File(destPath);
            if (!f.exists()) {
                CopyDB(getBaseContext().getAssets().open("mydatabase.sqlite"),
                        new FileOutputStream(destPath));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.open();

        c = db.getAllTeams();
        if (c.moveToFirst())
        {int i = 0 ;
        do{
            names[i] = c.getString(0);
            rates[i] =c.getString(1);
            i++;
        }while (c.moveToNext());

        }
        CustomAdapterClubList adpter = new CustomAdapterClubList(Main4Activity.this, names, rates);
        listView.setAdapter(adpter);

    }

    public void CopyDB(InputStream inputStream,
                       OutputStream outputStream)
            throws IOException {
//---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}