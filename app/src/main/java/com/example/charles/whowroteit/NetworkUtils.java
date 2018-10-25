package com.example.charles.whowroteit;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by charles on 25/09/2018.
 */

public class NetworkUtils {
    private static final String LOG_TAG=NetworkUtils.class.getSimpleName();
    //constants
    //Base url for books API
    private static final String BOOK_BASE_URL="https://www.googleapis.com/books/v1/volume?";
    //parameter for the search string
    private static final String QUERY_PARAM="q";
    //parameter that limits search results
    private static final String MAX_RESULTS="maxResults";
    //parameter to filter by print type
    private  static  final String PRINT_TYPE="printType";
    //this static method takes a string parameter and returns a JSON string reponse
    static String getBookInfo(String queryString){
        //variables for connecting to the internet, reading incoming data,and holding a response string
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String bookJSONString = null;

        //catch block to handle problems with the  request
        //finally block to close network connetion after receiving JSON  data

        try{
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE,"books")
                    .build();


//convert uri to URLobject
            URL requestURL=new URL( builtURI.toString());
            //open the url and make a request
            urlConnection=(HttpURLConnection)requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //set up a response from the connection  using inut stream, bufferedreader and stringbuilder
            //get the input stream
            InputStream inputStream=urlConnection.getInputStream();
            //create a buffered reader from the input stream
            reader =new BufferedReader( new InputStreamReader(inputStream));
            //use a string builder to hold the incoming response
            StringBuilder builder=new StringBuilder();
            //read  the input line by line while there is still input
            String line;
            while ((line=reader.readLine())!= null){
               builder.append(line) ;
                builder.append("/n");
                //check if there exists any response content
                if (builder.length()==0){
                    return  null;

                }
                pu
                bookJSONString=builder.toString();
            }
        } catch (IOException e){
            e.printStackTrace();
        }finally{
            if (urlConnection !=null){
                urlConnection.disconnect();
            }
            if (reader !=null){
                try {
                    reader.close();

                }catch (IOException e){
                    e.printStackTrace();
                }

            }



        }
        Log.d(LOG_TAG,bookJSONString);
        return bookJSONString;
    }

}
