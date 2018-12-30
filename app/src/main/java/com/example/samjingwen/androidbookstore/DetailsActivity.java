package com.example.samjingwen.androidbookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int cat = extras.getInt("Category");
        new AsyncTask<Integer, Void, List<Book>>(){

            @Override
            protected List<Book> doInBackground(Integer...id){
                return Book.ReadBooksByCat(id[0]);
            }
            @Override
            protected void onPostExecute(List<Book> data) {
                MyAdapter adapter = new MyAdapter(getApplicationContext(), data);
                ListView list = (ListView) findViewById(R.id.listView1);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> av, View view, int position, long id) {
                        Book selected = (Book) av.getAdapter().getItem(position);
                        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                        intent.putExtra("BookID", selected.get("BookID"));
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), selected.get("BookID") + " selected",
                                Toast.LENGTH_SHORT).show();
                    }
                });



            }
        }.execute(cat);
    }
}
