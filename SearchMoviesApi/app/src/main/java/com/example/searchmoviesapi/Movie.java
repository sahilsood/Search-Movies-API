package com.example.searchmoviesapi;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

public class Movie implements Serializable {
    String title, releaseDate, imageUrl, overview;
    int rating, popularity;

    public Movie(String title, String releaseDate, String imageUrl, String overview, int rating, int popularity) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
        this.overview = overview;
        this.rating = rating;
        this.popularity = popularity;
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", overview='" + overview + '\'' +
                ", rating=" + rating +
                ", popularity=" + popularity +
                '}';
    }

    public static Comparator<Movie> popularSort = new Comparator<Movie>() {

        public int compare(Movie r1, Movie r2) {

            int rating1 = r1.popularity;
            int rating2 = r2.popularity;

            /*For ascending order*/
            return rating2-rating1;

            /*For descending order*/
            //rating2-rating1;
        }};


    public static Comparator<Movie> ratingSort = new Comparator<Movie>() {

        public int compare(Movie r1, Movie r2) {

            int rating1 = r1.rating;
            int rating2 = r2.rating;

            /*For ascending order*/
            return rating2-rating1;

            /*For descending order*/
            //rating2-rating1;
        }};
}
