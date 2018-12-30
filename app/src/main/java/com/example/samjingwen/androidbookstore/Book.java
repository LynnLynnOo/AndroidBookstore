package com.example.samjingwen.androidbookstore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String, String> {
    static String host = "10.175.65.11";
    static String baseURL;
    static String imageURL;
    static {
        baseURL = String.format("http://%s/abookshop/api/book", host);
        imageURL = String.format("http://%s/abookshop/images", host);
    }

    public Book(String BookID, String Title, String Category, String Author, String Stock, String Price, String Image) {
        put("BookID", BookID);
        put("Title", Title);
        put("Category", Category);
        put("Author", Author);
        put("Stock", Stock);
        put("Price", Price);
        put("Image", Image);
    }

    public static List<String> ReadCategory() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL);
            for (int i =0; i<a.length(); i++) {
                String b = a.getString(i);
                list.add(b);
            }
        } catch (Exception e) {
            Log.e("Book", "JSONArray error");
        }
        return(list);
    }

    public static List<Book> ReadBooksByCat(int id) {
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(String.format("%s/brief/%s",baseURL, id));
            List<Book> bookList = new ArrayList<Book>();
            for (int i=0; i<a.length(); i++){
                JSONObject b = a.getJSONObject(i);
                bookList.add(new Book(b.getString("BookID"),
                        b.getString("Title"), "",
                        b.getString("Author"), "", "", ""));
            }
            return bookList;
        } catch (Exception e) {
            Log.e("Book", "JSONArray error");
        }
        return(null);
    }

    public static Book ReadBook(int id) {
        try {
            JSONObject a = JSONParser.getJSONFromUrl(String.format("%s/book/%s",baseURL, id));
            Book book = new Book(String.valueOf(a.getInt("BookID")),
                    a.getString("Title"),
                    a.getString("Category"),
                    a.getString("Author"),
                    String.valueOf(a.getInt("Stock")),
                    String.valueOf(a.getDouble("Price")),
                    a.getString("Image"));
            return book;
        } catch (Exception e) {
            Log.e("Employee", "JSONArray error");
        }
        return(null);
    }

    public static Bitmap getPhoto(int id) {
        try {
            URL url = new URL(String.format("%s/%s.jpg",imageURL, id));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Book.getPhoto()", "Bitmap error");
        }
        return(null);
    }

    public static void saveBook(Book book) {
        JSONObject jemp = new JSONObject();
        try {
            jemp.put("BookID", book.get("BookID"));
            jemp.put("Title", book.get("Title"));
            jemp.put("Category", book.get("Category"));
            jemp.put("Author", book.get("Author"));
            jemp.put("Stock", Integer.parseInt(book.get("Stock")));
            jemp.put("Price", Double.parseDouble(book.get("Price")));
            jemp.put("Image", book.get("Image"));
        } catch (Exception e) {
        }
        JSONParser.postStream(baseURL+"/update", jemp.toString());
    }



//    public static List<Book> ReadBooks2() {
//        List<Book> list = new ArrayList<Book>();
//        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "/brief");
//        try {
//            for (int i =0; i<a.length(); i++) {
//                JSONObject b = a.getJSONObject(i);
//                list.add(new Book(b.getString("BookID"),
//                        b.getString("Name"),"", ""));
//            }
//        } catch (Exception e) {
//            Log.e("Book", "JSONArray error");
//        }
//        return(list);
//    }

}
