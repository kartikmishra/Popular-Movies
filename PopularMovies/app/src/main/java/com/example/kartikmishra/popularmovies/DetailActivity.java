package com.example.kartikmishra.popularmovies;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private ImageView backIv;
    private ImageView poster_image;
    private TextView movieTitle;
    private TextView releaseDate;
    private TextView vote_average;
    private TextView overView;
    private TextView textViewName;
    public static Movies movies;
    public static Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailActivity.intent = getIntent();
//        Toolbar toolbar = findViewById(R.id.detail_activity_toolbar);
//        setSupportActionBar(toolbar);

        init();


        movies = new Movies();
        int moviePosition = intent.getIntExtra("movies_position",0);

        movies = MainActivity.list.get(moviePosition);


        releaseDate.setText(movies.getRelease_Date());
        String movie_poster_url;

        if (movies.getPoster_path() == "https://www.kssm.in/wp-content/uploads/2014/12/no-image.jpg") {
            movie_poster_url = "https://www.kssm.in/wp-content/uploads/2014/12/no-image.jpg";
        }else {
            movie_poster_url = Constants.APIConstants.IMAGE_BASE_URL + Constants.APIConstants.IMAGE_SMALL_SIZE + "/" + movies.getPoster_path();
        }

        Picasso.with(getApplicationContext()).load(movie_poster_url).into(poster_image);




        movieTitle.setText(movies.getTitle());

        Double average = movies.getVote_average();
        String finalAverage = Double.toString(average);

        vote_average.setText(finalAverage+"/10");


        overView.setText(movies.getOverView());


        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }



    /**
     * setup widgets
     */
    void init(){
        poster_image = findViewById(R.id.detail_activity_iv);
        movieTitle = findViewById(R.id.tv_originalTitle);
        releaseDate = findViewById(R.id.tv_releaseDate);
        vote_average = findViewById(R.id.tv_vote_average);
        overView = findViewById(R.id.tv_overView);
        backIv = findViewById(R.id.back_iv);
        textViewName = findViewById(R.id.textViewName);

    }
}
