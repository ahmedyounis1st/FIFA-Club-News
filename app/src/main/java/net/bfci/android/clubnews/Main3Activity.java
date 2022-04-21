package net.bfci.android.clubnews;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    ImageView imv;
    Button btn1;
    ProgressBar bar;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    Intent intent;
    DbAdapterClubTable db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toast.makeText(getBaseContext(), "i am here", Toast.LENGTH_SHORT).show();
        imv = (ImageView) findViewById(R.id.img);
        btn1 = (Button) findViewById(R.id.btn);
        bar = findViewById(R.id.bar);
        textView1 = findViewById(R.id.name);
        textView2 = findViewById(R.id.sortname);
        textView3 = findViewById(R.id.foundationyear);
        textView4 = findViewById(R.id.stadium);
        textView5 = findViewById(R.id.tranier);
        textView6 = findViewById(R.id.star);
        textView7 = findViewById(R.id.nickname);
        textView8 = findViewById(R.id.localch);
        textView9 = findViewById(R.id.worldch);
        textView10 = findViewById(R.id.rate);
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
        intent = getIntent();

        new GetImage().execute(intent.getExtras().getString("imageurl"));

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] img = bos.toByteArray();
                db.insertStudent(img, textView1.getText().toString(), textView2.getText().toString(), textView3.getText().toString(), textView4.getText().toString(), textView5.getText().toString(), textView6.getText().toString(), textView7.getText().toString(), textView8.getText().toString(), textView9.getText().toString(), textView10.getText().toString());

            }
        });
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


   public class GetImage extends AsyncTask<String,Void,Bitmap> {


       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           bar.setVisibility(View.VISIBLE);
       }
       @Override
       protected Bitmap doInBackground(String... urls) {
           Bitmap bmp = null;

           try {
               URL url = new URL( urls[0]);
               bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

           } catch (Exception e) {
               e.printStackTrace();
           }
           return bmp;
       }
       @Override
       protected void onPostExecute(Bitmap bmp) {
           super.onPostExecute(bmp);
           imv.setImageBitmap(bmp);
           bar.setVisibility(View.GONE);
           textView1.setText (intent.getExtras().getString("name"));
           textView2.setText ("الاسم المختصر : "+intent.getExtras().getString("shortname"));
           textView3.setText ("تأسس عام : "+intent.getExtras().getString("foundationyear"));
           textView4.setText ("الاستاد : "+intent.getExtras().getString("stadium" ));
           textView5.setText ("المدرب : "+intent.getExtras().getString("trainer" ));
           textView6.setText ("الهداف : "+intent.getExtras().getString( "star"  ));
           textView7.setText ("الالقاب : "+intent.getExtras().getString("nickname" ));
           textView8.setText ("التقييم : "+intent.getExtras().getString("rate" ));
           textView9.setText ("البطولات المحلية : "+intent.getExtras().getString("localch" ));
           textView10.setText ("البطولات الدولية : "+intent.getExtras().getString("worldch"));


       }
   }
}
