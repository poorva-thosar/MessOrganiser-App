package com.example.poorva.messorganizer2;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    EditText name,taste, txtcost;
    Button btn;
    String Name,textVegie,Tast,Cost;

    String[] bankNames={"Vegetable","Breads","Rice","Sweet","Snacks" +
            ""};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        spinner=findViewById(R.id.simpleSpinner);
        name =  findViewById(R.id.txtName);
        txtcost = findViewById(R.id.txtCost);
        btn = findViewById(R.id.btnAdd);
        taste = findViewById(R.id.txtTaste);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        spin.setSelection(0);

    }


    public void setAdd(View view)
    {
        int i = name.getText().length();
        int j = taste.getText().length();
        if(spinner.getSelectedItemPosition() != 0) {
            if (i >= 1) {

                textVegie = spinner.getSelectedItem().toString();
                Name = name.getText().toString();
                Cost = txtcost.getText().toString();
                BackgroundTask backgroundTask = new BackgroundTask();
                backgroundTask.execute(Name, textVegie,Tast,"0",Cost);
                //finish();
            } else
                Toast.makeText(getApplicationContext(), "Enter correct data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(i>=1 && j >=1)
            {
                textVegie = spinner.getSelectedItem().toString();
                Name = name.getText().toString();
                Tast = taste.getText().toString();
                Cost = txtcost.getText().toString();
                BackgroundTask backgroundTask = new BackgroundTask();
                backgroundTask.execute(Name, textVegie, Tast,"1",Cost);
            }
            else
                Toast.makeText(getApplicationContext(), "Enter correct data", Toast.LENGTH_SHORT).show();
        }
        name.setText("");
        txtcost.setText("");
        taste.setText("");
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        name.setText("");
        txtcost.setText("");
        taste.setText("");
        if(position == 0)
        {
            taste.setVisibility(View.VISIBLE);
        }
        else
        {
            taste.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    class BackgroundTask extends AsyncTask<String, Void, String>
    {

        String add_info_url1,add_info_url2;


        @Override
        protected void onPreExecute()
        {
            add_info_url1 = "https://year3.000webhostapp.com/add_info.php";
            add_info_url2 = "https://year3.000webhostapp.com/add_Vegetable.php";
        }

        @Override
        protected String doInBackground(String... args) {

            String name1, text1,tast;
            String data_string;
            int flag,cost;
            name1= args[0];
            text1 = args[1];                //Will put it later
                                            //text1 indicates table name while name1 indicates item name to be inserted
            tast = args[2];
            flag = Integer.parseInt(args[3]);
            cost = Integer.parseInt(args[4]);
            try
            {
                URL url;
                if(flag==0)
                    url = new URL(add_info_url1);
                else
                    url = new URL(add_info_url2);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                if(flag == 0) {
                    data_string = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name1, "UTF-8")
                            + "&" + URLEncoder.encode("item", "UTF-8") + "=" + URLEncoder.encode(text1, "UTF-8")
                            + "&" + URLEncoder.encode("cost", "UTF-8") + "=" + URLEncoder.encode(""+cost, "UTF-8");
            }
                else{
                    data_string = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name1, "UTF-8")
                            + "&" + URLEncoder.encode("item", "UTF-8") + "=" + URLEncoder.encode(text1, "UTF-8")
                            + "&" + URLEncoder.encode("taste", "UTF-8") + "=" + URLEncoder.encode(tast, "UTF-8")
                            + "&" + URLEncoder.encode("cost", "UTF-8") + "=" + URLEncoder.encode(""+cost, "UTF-8");
                }


                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return ""+spinner.getSelectedItem().toString()+" Inserted";

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
           Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

        }
    }
}