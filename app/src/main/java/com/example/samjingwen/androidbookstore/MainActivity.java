package com.example.samjingwen.androidbookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                return Book.ReadCategory();
            }
            @Override
            protected void onPostExecute(List<String> result) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.row, R.id.textView1, result);
                ListView list = (ListView) findViewById(R.id.listView1);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> av, View view, int position, long id) {
                        position++;
                        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                        intent.putExtra("Category", position);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), position + " selected",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.execute();

//        List<String> data = Book.ReadCategory();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, R.layout.row, R.id.textView1, data);
//        ListView list = (ListView) findViewById(R.id.listView1);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> av, View view, int position, long id) {
//                position++;
//                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
//                intent.putExtra("Category", position);
//                startActivity(intent);
//                Toast.makeText(getApplicationContext(), position + " selected",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}
