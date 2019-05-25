//package com.example.poorva.messorganizer2;
//
//import android.app.Activity;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//
//public class AddInfoToTodaysMenuVegetable extends Activity{
//
//    public void connectAndAdd()
//    {
//        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
//        if(networkInfo!=null && networkInfo.isConnected())
//        {
//            Toast.makeText(getApplicationContext(),"Connection Established",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(),"Connection Not Established",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void saveInfo(String[] arrayOfRetreivedStrings)
//    {
//         BackgroundTask backgroundTask=new BackgroundTask();
//         backgroundTask.execute(arrayOfRetreivedStrings);
//         finish();
//    }
//
//    ///Inner Class
//
//    class BackgroundTask extends AsyncTask<String,Void,String>
//    {
//        String add_info_url;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            add_info_url="https://year3.000webhostapp.com/add_info_to_todaysVegetableMenu.php";
//        }
//
//        @Override
//        protected String doInBackground(String... args) {
//     //       String[] retreivedArrayOfStrings=args[0];
//
//            String date, name1, taste,cost;
//
//            date= args[2];
//            name1 = args[1];
//            taste = args[3];
//            cost = args[0];
//
//            try {
//                URL url=new URL(add_info_url);
//                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream=httpURLConnection.getOutputStream();
//
//               String data_string = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")
//                        + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name1, "UTF-8")
//                        + "&" + URLEncoder.encode("taste", "UTF-8") + "=" + URLEncoder.encode(taste, "UTF-8")
//                        + "&" + URLEncoder.encode("cost", "UTF-8") + "=" + URLEncoder.encode(""+cost, "UTF-8");
//
//
//                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//  /*              String data_string= URLEncoder.encode("srno","UTF-8")+"="+URLEncoder.encode("1","UTF-8")+"&"+
//                        URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode("Potato","UTF-8")+"&"+
//                        URLEncoder.encode("itemName","UTF-8")+"="+URLEncoder.encode("Potato","UTF-8")+"&"+
//                        URLEncoder.encode("itemTaste","UTF-8")+"="+URLEncoder.encode("Potato","UTF-8")+"&"+
//                        URLEncoder.encode("itemCost","UTF-8")+"="+URLEncoder.encode("Spicy","UTF-8");
//*/
//
//                bufferedWriter.write(data_string);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//
//                InputStream inputStream=httpURLConnection.getInputStream();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return "One Row is Added To Database";
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
//        }
//    }
//
//}
