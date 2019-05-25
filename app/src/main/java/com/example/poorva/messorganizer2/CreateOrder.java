package com.example.poorva.messorganizer2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CreateOrder extends AppCompatActivity {

    Button NextPage;
    Spinner sp;
    Context c;
    ///
    String JSON_STRING;
    String json_string;


    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapterForForVegetable contactAdapterForVegetable;
    ListView listView;
    String[] arrayOfRetreivedString=new String[100];
    int indexForArrayOfRetreivedString=0;
    Button submitButtonForCreateTodaysMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        NextPage = findViewById(R.id.btnNext);
        //   sp = findViewById(R.id.vegSpinner);

        ///////////////
        //  new Downloader(CreateMenu.this, address, sp).execute();
        //////////////

        new BackGroundTask().execute();





    }//onCreate ends here

    public void parseJSON(View view)                //onclickListener
    {
        if(json_string==null)
        {
            Toast.makeText(getApplicationContext(),"First Get JSON",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i=new Intent(this,DisplayListViewForVegetable.class);
            i.putExtra("json_data",json_string);
            i.putExtra("activity","CreateOrder");

            startActivity(i);
        }
    }


    //////////////////////

    public class BackGroundTask extends AsyncTask<Void,Void,String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url="https://year3.000webhostapp.com/json_get_data.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url=new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder=new StringBuilder();

                while((JSON_STRING= bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //    TextView textView=findViewById(R.id.textView2);
            //    textView.setText(result);
            json_string=result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    //////////////////

}