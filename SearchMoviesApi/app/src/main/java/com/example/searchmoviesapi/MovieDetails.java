package com.example.searchmoviesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    TextView title, overview, rating, releaseDate;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title = (TextView) findViewById(R.id.et_title);
        overview = (TextView) findViewById(R.id.et_overview);
        rating = (TextView) findViewById(R.id.et_rating);
        releaseDate = (TextView) findViewById(R.id.et_releaseDate);
        image = (ImageView) findViewById(R.id.imageViewMovie);
        if(getIntent().getExtras() != null){
            Movie movie = (Movie) getIntent().getExtras().getSerializable(MovieAdapter.MOVIE_KEY);
            setTitle(movie.title);
            title.setText(movie.title);
            overview.setText(movie.overview);
            rating.setText(movie.rating+"/10");
            releaseDate.setText(movie.releaseDate);
            String defaultUrl = "https://image.tmdb.org/t/p/w1280/"+movie.imageUrl;
            if (defaultUrl.isEmpty()) {
                image.setImageResource(R.drawable.noimage);
                image.getLayoutParams().height = 300;
                image.getLayoutParams().width = 300;
            }
            else{
                Picasso.get().load(defaultUrl).resize(300, 300).centerCrop().into((image));
            }
        }
    }
}
