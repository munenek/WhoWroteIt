package com.example.charles.whowroteit;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    ImageButton imageButton;
    ListView listView;
    TextView textNoDataFound;
BookAdapter adapter;
    static final String SEARCH_RESULTS = "booksSearchResults";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        textNoDataFound = (TextView) findViewById(R.id.text_no_data_found);

        adapter = new BookAdapter(this, -1);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        if (savedInstanceState != null) {
            Book[] books = (Book[]) savedInstanceState.getParcelableArray(SEARCH_RESULTS);
            adapter.addAll(books);
        }

    }

    public void searchBooks(View view){
        //get the search string from the input string
        String queryString=editText.getText().toString();


    }
    private void updateUi(List<Book> books){
        if (books.isEmpty()){
            // if no books found, show a message
            textNoDataFound.setVisibility(View.VISIBLE);
        } else {
            textNoDataFound.setVisibility(View.GONE);
        }
        adapter.clear();
        adapter.addAll(books);
    }

    public class FetchBook extends AsyncTask<String, Void,String>{
        //all request to a network or api begin with a URI to specify the resource you apend a query paramete to the base uri

        @Override
        protected String doInBackground(String... strings) {
            return NetworkUtils.getBookInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            if (books == null) {
                return;
            }
            updateUi(books);
            super.onPostExecute(s);
            //handling Json exceptions
            try {//JSONobject and JSONArray are used to obtain JSON array of items
                JSONObject jsonObject=new JSONObject(s);
                JSONArray itemArray=jsonObject.getJSONArray("items");

            }catch (JSONException e){
               e.printStackTrace();
            }
             //initialize variables for parsing the loop
            int i=0;

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Book[] books = new Book[adapter.getCount()];
        for (int i = 0; i < books.length; i++) {
            books[i] = adapter.getItem(i);
        }
        outState.putParcelableArray(SEARCH_RESULTS, (Parcelable[]) books);
    }
}
