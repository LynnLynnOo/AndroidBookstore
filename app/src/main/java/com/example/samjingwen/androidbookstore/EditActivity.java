package com.example.samjingwen.androidbookstore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

public class EditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getExtras().getString("BookID"));
        new AsyncTask<Integer, Void, Book>(){

            @Override
            protected Book doInBackground(Integer...id){
                return Book.ReadBook(id[0]);
            }
            @Override
            protected void onPostExecute(Book book) {
                show(book);
            }
        }.execute(id);


    }

    void show(Book book) {
        int []dest = new int[]{R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4, R.id.editText5, R.id.editText6};
        String []src = new String[]{"BookID", "Title", "Category", "Author", "Stock", "Price"};
        for (int n=0; n<dest.length; n++) {
            EditText txt = findViewById(dest[n]);
            txt.setText(book.get(src[n]));
        }
        int id = Integer.parseInt(book.get("BookID"));
        new AsyncTask<Integer, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Integer...id){
                return Book.getPhoto(id[0]);
            }
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                ImageView image = findViewById(R.id.imageView);
                image.setImageBitmap(bitmap);
            }
        }.execute(id);
    }

    public void save(View v){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Confirm update?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int []src = new int[]{R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4, R.id.editText5, R.id.editText6};
                        String []dest = new String[6];
                        for (int n=0; n<dest.length; n++) {
                            EditText txt = findViewById(src[n]);
                            dest[n] = txt.getText().toString();
                        }
                        Book book = new Book(dest[0], dest[1], dest[2], dest[3],dest[4],dest[5],"");
                        new AsyncTask<Book, Void, Void>() {
                            @Override
                            protected Void doInBackground(Book... params) {
                                Book.saveBook(params[0]);
                                return null;
                            }
                        }.execute(book);
                        Toast.makeText(getApplicationContext(), "Book updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
