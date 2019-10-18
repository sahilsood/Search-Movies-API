package com.example.searchmoviesapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    ArrayList<Movie> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle("Favorite Movies");
        data = (ArrayList<Movie>) getIntent().getExtras().getSerializable(MainActivity.FAV_MOVIE);
        mrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mrecyclerView.setHasFixedSize(true);

        mlayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mlayoutManager);

        mAdapter = new MovieAdapter(data);
        mAdapter.notifyDataSetChanged();
        mrecyclerView.setAdapter(mAdapter);


    }
}