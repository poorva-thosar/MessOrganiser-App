package com.example.poorva.messorganizer2;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DisplayListViewForBread extends AppCompatActivity {

    String json_string,json_string1,JSON_STRING;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    int indexForArrayOfRetreivedString=0;
    Button submitButtonForCreateTodaysMenu;
    String[] arrayOfRetreivedString=new String[100];
    int countOfArrayLength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_listview_for_bread);

        new BackGroundTask1().execute();

        submitButtonForCreateTodaysMenu=(Button)findViewById(R.id.submit_vegetables_for_create_todays_menu);
        json_string=getIntent().getExtras().getString("json_data");
        listView=(ListView)findViewById(R.id.listview);
        contactAdapter=new ContactAdapter(this,R.layout.activity_row_layout_bread);
        listView.setAdapter(contactAdapter);
        try {
            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server response");
            int count=0;
            String srno,itemName,taste;
            while (count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                srno=jo.getString("srno");
                itemName=jo.getString("itemName");
                taste=jo.getString("itemCost");
                Contacts contacts=new Contacts(srno,itemName,taste);
                contactAdapter.add(contacts);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //////

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //For showing color to selected item
                if(view.isActivated())
                {
                    //For unselect
                    view.setActivated(false);
                    view.setSelected(false);
                    String retreivedName =((TextView)view.findViewById(R.id.tx_name)).getText().toString();
                    String retrievedSrNo =((TextView)view.findViewById(R.id.tx_srno)).getText().toString();
                    String retrivedTaste =((TextView)view.findViewById(R.id.tx_taste)).getText().toString();
                    Toast.makeText(getApplicationContext(),retreivedName+" Unselected",Toast.LENGTH_SHORT).show();
                    for(int i=0;i<arrayOfRetreivedString.length;i=i+3)
                    {
                        if(retrievedSrNo==arrayOfRetreivedString[i])
                        {
                            arrayOfRetreivedString[i]="useless";
                            arrayOfRetreivedString[i+1]="useless";
                            arrayOfRetreivedString[i+2]="useless";
                            break;
                        }
                    }
                    countOfArrayLength+=3;
                }
                else
                {
                    //For select
                    view.setActivated(true);
                    view.setSelected(true);
                    String retreivedName =((TextView)view.findViewById(R.id.tx_name)).getText().toString();
                    String retrievedSrNo =((TextView)view.findViewById(R.id.tx_srno)).getText().toString();
                    String retrivedTaste =((TextView)view.findViewById(R.id.tx_taste)).getText().toString();
                    Toast.makeText(getApplicationContext(),retreivedName+" Selected",Toast.LENGTH_SHORT).show();
                    arrayOfRetreivedString[indexForArrayOfRetreivedString++]=retrievedSrNo;
                    arrayOfRetreivedString[indexForArrayOfRetreivedString++]=retreivedName;
                    arrayOfRetreivedString[indexForArrayOfRetreivedString++]=retrivedTaste;
                    countOfArrayLength+=3;
                }
            }
        });

    }

    public void saveInfo(/*String[] arrayOfRetreivedStrings*/View view)
    {

        //////
        if(json_string1==null)
        {
            Toast.makeText(getApplicationContext(),"First Get JSON",Toast.LENGTH_SHORT).show();
        }
        else
        {
            BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute(arrayOfRetreivedString);
            finish();

            Intent i=new Intent(this,DisplayListViewForSweet.class);
            i.putExtra("json_data",json_string1);
            i.putExtra("activity","DisplayListViewForSweet");

            startActivity(i);
        }
    }
//
//    public void parseJSONForBreads(View view)
//    {
//
//        if(json_string1==null)
//        {
//            Toast.makeText(getApplicationContext(),"First Get JSON",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Intent i=new Intent(this,DisplayListViewForSweet.class);
//            i.putExtra("json_data",json_string1);
//            i.putExtra("activity","DisplayListViewForSweet");
//
//            startActivity(i);
//        }
//    }



    ///Inner Class

    class BackgroundTask extends AsyncTask<String[],Void,String>
    {
        String add_info_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            add_info_url="https://year3.000webhostapp.com/add_info_to_todaysBreadMenu.php";
        }

        @Override
        protected String doInBackground(String[]... args) {
            String[] retreivedArrayOfStrings = args[0];
            String myDate = new TodaysDate().getTodatsDate();

            for (int i=0;i<countOfArrayLength;i+=3) {

                if(retreivedArrayOfStrings[i+1]=="useless")
                    continue;

                try {
                    URL url = new URL(add_info_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data_string = URLEncoder.encode("srno", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(myDate/*retreivedArrayOfStrings[1]*/, "UTF-8") + "&" +
                            URLEncoder.encode(/*"itemName"*/"name", "UTF-8") + "=" + URLEncoder.encode(retreivedArrayOfStrings[i + 1], "UTF-8") + "&" +
                            //URLEncoder.encode(/*"itemTaste"*/"taste", "UTF-8") + "=" + URLEncoder.encode("Sweet", "UTF-8") + "&" +
                            URLEncoder.encode(/*"itemCost"*/"cost", "UTF-8") + "=" + URLEncoder.encode(retreivedArrayOfStrings[i + 2], "UTF-8");
                    bufferedWriter.write(data_string);

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "Rows Added To Database";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }



    /////Inner class 2
    //Inner class for extracting json objects from  sweets table
    //////////////////////

    public class BackGroundTask1 extends AsyncTask<Void,Void,String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            json_url="https://year3.000webhostapp.com/json_get_data_sweet.php";
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
            json_string1=result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    //////////////////


}
