package com.example.samjingwen.androidbookstore;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Book> {

    private List<Book> items;
    int resource;

    public MyAdapter(Context context, List<Book> items) {
        super(context, R.layout.row2, items);
        this.resource = R.layout.row2;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        final Book book = items.get(position);
        if (book != null) {
            int[] dest = new int[]{R.id.textView1, R.id.textView2, R.id.textView3};
            String []src = new String[]{"BookID", "Title", "Author"};
            for (int n=0; n<dest.length; n++) {
                TextView txt = v.findViewById(dest[n]);
                txt.setText(book.get(src[n]));
            }
            final ImageView image = (ImageView) v.findViewById(R.id.imageView2);
            int id = Integer.parseInt((book.get("BookID")));
            new AsyncTask<Integer, Void, Bitmap>(){

                @Override
                protected Bitmap doInBackground(Integer...id){
                    return Book.getPhoto(id[0]);
                }
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    image.setImageBitmap(bitmap);
                }
            }.execute(id);
        }
        return v;
    }
}