package com.example.searchmoviesapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    EditText movieName;
    ArrayList<Movie> result;
    static String FAV_MOVIE = "fav";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rating:
                if(result.size()>0) {
                    Collections.sort(result, Movie.ratingSort);
                    mAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(this, "No Result", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.popular:
                if(result.size()>0) {
                    Collections.sort(result, Movie.popularSort);
                    mAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(this, "No Result", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.favorites:
                Intent intentFav = new Intent(this, Favorite.class);
                intentFav.putExtra(FAV_MOVIE, MovieAdapter.favMovie);
                startActivity(intentFav);
                return true;
            case R.id.quit:
                Log.i("tt","quit");
                return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Movies");
        setContentView(R.layout.activity_main);findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    result = new ArrayList<>();
                    movieName = (EditText) findViewById(R.id.et_movie);
                    String movieN = movieName.getText().toString();
                    movieN.replaceAll(" ","+");
                    String url = "https://api.themoviedb.org/3/search/movie?query="+movieN+"&api_key=2ff880201e2022b36e41b4b8a047340a&page=1";
                    new DoAsynchTask().execute(url);
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != connectivityManager.TYPE_WIFI && networkInfo.getType() != connectivityManager.TYPE_MOBILE)){
            return false;
        }
        return true;
    }


    private class DoAsynchTask extends AsyncTask<String, Void, ArrayList> {
        HttpURLConnection httpURLConnection = null;
        @Override
        protected ArrayList doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    String json =  IOUtils.toString(httpURLConnection.getInputStream(),"UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray results = root.getJSONArray("results");

                    for(int i=0;i<results.length();i++){
                        JSONObject jsonMovie = results.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.title = jsonMovie.getString("original_title");
                        movie.overview = jsonMovie.getString("overview");
                        movie.rating = jsonMovie.getInt("vote_average");
                        movie.releaseDate = jsonMovie.getString("release_date");
                        movie.imageUrl = jsonMovie.getString("poster_path");
                        movie.popularity = jsonMovie.getInt("popularity");

                        result.add(movie);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(httpURLConnection !=null){
                    httpURLConnection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            if(result.size()>0){
                TextView result_tv = (TextView) findViewById(R.id.tv_results);
                result_tv.setVisibility(View.VISIBLE);
                mrecyclerView = findViewById(R.id.my_recycler_view);
                mrecyclerView.setHasFixedSize(true);

                mlayoutManager = new LinearLayoutManager(MainActivity.this);
                mrecyclerView.setLayoutManager(mlayoutManager);

                mAdapter = new MovieAdapter(result);
                //mAdapter.notifyDataSetChanged();
                mrecyclerView.setAdapter(mAdapter);
                Log.i("result",result.toString());
            }
            else{
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                Toast.makeText(MainActivity.this, "No Result Found!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}