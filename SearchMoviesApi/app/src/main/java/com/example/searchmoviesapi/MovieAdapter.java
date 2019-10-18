package com.example.searchmoviesapi;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
        ArrayList<Movie> mData= new ArrayList<>();
        static ArrayList<Movie> favMovie = new ArrayList<Movie>();
        static final String MOVIE_KEY = "KEY";

public MovieAdapter(ArrayList<Movie> mData) {
        this.mData = mData;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
        }

@Override
public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Movie movie = mData.get(position);
final String imageUrl, url;
        holder.title.setText(movie.title);
        if(movie.title.matches("null")){
        holder.title.setText("Unknown Title");
        }
        else{
        holder.title.setText(movie.title);
        }

        holder.releaseDate.setText(movie.releaseDate);
        imageUrl = movie.imageUrl;
        url = movie.imageUrl;
        String defaultUrl = "https://image.tmdb.org/t/p/w1280/"+url;
        if (defaultUrl.isEmpty()) {
        holder.urlToImage.setImageResource(R.drawable.noimage);
        holder.urlToImage.getLayoutParams().height = 300;
        holder.urlToImage.getLayoutParams().width = 300;
        }
        else{
        Picasso.get().load(defaultUrl).resize(300, 300).centerCrop().into((holder.urlToImage));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MovieDetails.class);
                i.putExtra(MOVIE_KEY, movie);
                view.getContext().startActivity(i);
            }
        });

        holder.star.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(holder.star.getTag() == "unchecked"){
                favMovie.add(movie);
                System.out.println(movie);
                holder.star.setAlpha(255);
                holder.star.setTag("checked");
            }
            else if(holder.star.getTag() == "checked"){
                favMovie.remove(movie);
                System.out.println(movie);
                holder.star.setAlpha(50);
                holder.star.setTag("unchecked");
            }
        }
    });
}

@Override
public int getItemCount() {
        return mData.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{
    TextView title,releaseDate;
    ImageView urlToImage;
    ImageButton star;
    public ViewHolder(@NonNull final View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.tv_title);
        releaseDate = (TextView) itemView.findViewById(R.id.tv_releaseDate);
        urlToImage = (ImageView) itemView.findViewById(R.id.imageView);
        star = (ImageButton) itemView.findViewById(R.id.star);
        star.setAlpha(50);
        star.setTag("unchecked");
    }
}

}
