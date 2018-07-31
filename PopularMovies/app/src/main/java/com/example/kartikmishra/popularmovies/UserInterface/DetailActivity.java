package com.example.kartikmishra.popularmovies.UserInterface;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;


import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;

import android.view.View;


import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;




import com.example.kartikmishra.popularmovies.Adapters.ReviewAdapter;
import com.example.kartikmishra.popularmovies.Adapters.TrailerAdapter;
import com.example.kartikmishra.popularmovies.Constants;

import com.example.kartikmishra.popularmovies.NetworksUtils.FetchReviewsAsyncTask;
import com.example.kartikmishra.popularmovies.NetworksUtils.FetchTrailersAsyncTask;

import com.example.kartikmishra.popularmovies.R;
import com.example.kartikmishra.popularmovies.data.MoviesContract;
import com.example.kartikmishra.popularmovies.models.Movies;
import com.example.kartikmishra.popularmovies.models.Reviews;
import com.example.kartikmishra.popularmovies.models.Videos;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener,FetchTrailersAsyncTask.OnTaskCompleted,
        FetchReviewsAsyncTask.OnTaskCompleted{
    private static final String TAG = "DetailActivity";

    private ImageView poster_image_big;
    private ImageView poster_image_small;
    private TextView movieTitle;
    private TextView releaseDate;
    private RatingBar vote_average;
    private TextView overView;
    public static Movies mMovies;
    public static ImageView fav_btn;
    public static Intent intent;
    private RecyclerView mRecyclerViewTrailer;
    private RecyclerView mRecyclerViewReview;
    public static Reviews mReview;
    TrailerAdapter trailerAdapter;
    ReviewAdapter  reviewAdapter;
    public static ArrayList<String> keys = new ArrayList<>();
    private Context mContext;

    public static List<Videos> videoList = new ArrayList<>();
    public static List<Reviews> reviewsList = new ArrayList<>();
    public static List<Long> id = new ArrayList<>();
    final boolean[] fav_red_image = {false};
    Toast mToast;
    public final int isfav=0;

    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        ImageView back = findViewById(R.id.backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                finish();
                return;
            }
        });
        mContext = getApplicationContext();
        DetailActivity.intent = getIntent();

        init();

        mMovies = new Movies();

        int moviePosition1 = intent.getIntExtra("movies_position", 0);
        mMovies = MainActivity.list.get(moviePosition1);

        mRecyclerViewTrailer = findViewById(R.id.recyclerView_trailers);
        mRecyclerViewReview = findViewById(R.id.recyclerView_reviews);

        setUpTrailerRecyclerView();
        setUpReviewRecyclerView();

        FetchTrailersAsyncTask asyncTask = new FetchTrailersAsyncTask(DetailActivity.this);
        asyncTask.execute(String.valueOf(mMovies.getMovie_ID()));

        FetchReviewsAsyncTask asyncTask1 = new FetchReviewsAsyncTask(DetailActivity.this);
        asyncTask1.execute(String.valueOf(mMovies.getMovie_ID()));

        TextView toolbarName = findViewById(R.id.tv_detail_toolbar_name);
        toolbarName.setText(mMovies.getTitle());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String date = DateUtils.formatDateTime(this,
                    formatter.parse(mMovies.getRelease_Date()).getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
            releaseDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        String movie_poster_url,movie_poster_url_big = null;

        if (mMovies.getPoster_path() == "https://www.kssm.in/wp-content/uploads/2014/12/no-image.jpg") {
            movie_poster_url = "https://www.kssm.in/wp-content/uploads/2014/12/no-image.jpg";
        } else {
            movie_poster_url = Constants.APIConstants.IMAGE_BASE_URL + Constants.APIConstants.IMAGE_SMALL_SIZE + "/" + mMovies.getPoster_path();
            movie_poster_url_big = Constants.APIConstants.IMAGE_BASE_URL + "w780"+"/"+mMovies.getPoster_path();
        }

        Picasso.with(getApplicationContext()).load(movie_poster_url_big).into(poster_image_big);
        Picasso.with(getApplicationContext()).load(movie_poster_url).into(poster_image_small);


        movieTitle.setText(mMovies.getTitle());

        Double average = mMovies.getVote_average();
        String finalAverage = Double.toString(average/2);

        vote_average.setNumStars(5);
        vote_average.setRating(Float.parseFloat(finalAverage));

        overView.setText(mMovies.getOverView());

        setUpFavBtnState();

        setUpFavButton();


    }


    /**
     * Setting up fav button state
     */
    public void setUpFavBtnState(){
        new  AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Cursor cursor = getApplicationContext().getContentResolver().query(
                        MoviesContract.MoviesEntry.CONTENT_URI,
                        null,   // projection
                        MoviesContract.MoviesEntry.COLUMN__MOVIES_ID + " = ?", // selection
                        new String[]{Integer.toString((int) mMovies.getMovie_ID())},   // selectionArgs
                        null    // sort order

                );


                boolean isfav = false;

                if(cursor!=null&&cursor.moveToFirst()){
                    do {
                        if(cursor.getLong(MainActivity.COL_MOVIE_ID)==mMovies.getMovie_ID()){
                            isfav=true;
                        }
                        else if(cursor.isNull(MainActivity.COL_MOVIE_ID)) {
                            isfav = false;
                        }
                    }while (cursor.moveToNext());

                    cursor.close();
                }

                return isfav;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {

                if (aBoolean == true) {
                    fav_btn.setImageResource(R.drawable.favsymbolred);
                }
                else {
                    fav_btn.setImageResource(R.drawable.favsymboldark);
                }

            }
        }.execute();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Setting Up Fav Button Here
     */


    public void setUpFavButton() {
        fav_btn.setImageResource(R.drawable.favsymboldark);

        boolean[] isFav={false};

       new AsyncTask<Void,Void,Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                Cursor cursor = getApplicationContext().getContentResolver().query(
                        MoviesContract.MoviesEntry.CONTENT_URI,
                        null,   // projection
                        MoviesContract.MoviesEntry.COLUMN__MOVIES_ID + " = ?", // selection
                        new String[]{Integer.toString((int) mMovies.getMovie_ID())},   // selectionArgs
                        null    // sort order

                );


                boolean isfav = false;

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        if (cursor.getLong(MainActivity.COL_MOVIE_ID) == mMovies.getMovie_ID()) {
                            isfav = true;
                        } else if (cursor.isNull(MainActivity.COL_MOVIE_ID)) {
                            isfav = false;
                        }

                    } while (cursor.moveToNext());


                    cursor.close();
                }

                return isfav;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                fav_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aBoolean == true) {
                            new AsyncTask<Void, Void, Integer>() {
                                @Override
                                protected Integer doInBackground(Void... voids) {

                                    return getApplicationContext().getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI,
                                            MoviesContract.MoviesEntry.COLUMN__MOVIES_ID + " = ?",
                                            new String[]{Integer.toString((int) mMovies.getMovie_ID())}
                                    );
                                }

                                @Override
                                protected void onPostExecute(Integer integer) {
                                    fav_btn.setImageResource(R.drawable.favsymboldark);
                                    startActivity(getIntent());
                                    finish();

                                }
                            }.execute();
                        } else {
                            if (mMovies != null) {
                                new AsyncTask<Void, Void, Uri>() {

                                    @Override
                                    protected Uri doInBackground(Void... voids) {
                                        ContentValues cv = new ContentValues();
                                        cv.put(MoviesContract.MoviesEntry.COLUMN__MOVIES_ID, mMovies.getMovie_ID());
                                        cv.put(MoviesContract.MoviesEntry.COLUMN__VOTE_AVERAGE, mMovies.getVote_average());
                                        cv.put(MoviesContract.MoviesEntry.COLUMN__POSTER_PATH, mMovies.getPoster_path());
                                        cv.put(MoviesContract.MoviesEntry.COLUMN__TITLE, mMovies.getTitle());
                                        cv.put(MoviesContract.MoviesEntry.COLUMN__OVERVIEW, mMovies.getOverView());
                                        cv.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, mMovies.getRelease_Date());
                                        return getBaseContext().getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, cv);
                                    }

                                    @Override
                                    protected void onPostExecute(Uri uri) {
                                        fav_btn.setImageResource(R.drawable.favsymbolred);
                                        startActivity(getIntent());
                                        finish();
                                    }
                                }.execute();
                            }
                        }
                    }
                });
            }
        }.execute();

    }
    /**
     * setup widgets
     */
    void init(){
        poster_image_big = findViewById(R.id.detail_activity__iv);
        poster_image_small = findViewById(R.id.detail_activity_small_iv);
        movieTitle = findViewById(R.id.tv_movieName_Label);
        releaseDate = findViewById(R.id.tv_releaseDateValue);
        vote_average = findViewById(R.id.tv_vote_average);
        overView = findViewById(R.id.tv_overView);
        fav_btn = findViewById(R.id.fav_btn);

    }

    /**
     * Setting up trailer RecyclerView
     */

    public void setUpTrailerRecyclerView(){


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerViewTrailer.setLayoutManager(linearLayoutManager);
        mRecyclerViewTrailer.setHasFixedSize(true);

        if(trailerAdapter!=null){
            trailerAdapter.notifyDataSetChanged();
        }else {
            trailerAdapter = new TrailerAdapter(this,videoList,this);
            mRecyclerViewTrailer.setAdapter(trailerAdapter);
        }


    }

    /**
     * Setting up review recyclerView
     */
    public void setUpReviewRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewReview.setLayoutManager(layoutManager);
        mRecyclerViewReview.setHasFixedSize(true);

        if(reviewAdapter!=null){
            reviewAdapter.notifyDataSetChanged();
        }else {
            reviewAdapter = new ReviewAdapter(reviewsList,this);
            mRecyclerViewReview.setAdapter(reviewAdapter);
        }
    }

    /**
     *
     * @param clickedItemIndex
     */

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Log.d(TAG, "onListItemClick: number of item"+clickedItemIndex);
        String url = "https://www.youtube.com/watch?v="+keys.get(clickedItemIndex);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }


    /**
     *
     * @param reviews
     * Getting reviews from class FetchReviewsAsyncTask
     */

    @Override
    public void onReviewTaskCompleted(List<Reviews> reviews) {
        if(reviews!=null && reviews.size()>0){
            mReview = reviews.get(0);
            reviewAdapter.notifyDataSetChanged();

        }

    }

    /**
     *
     * @param response
     * Getting trailers from class FetchTrailerAsyncTask
     */

    @Override
    public void onTrailersTaskCompleted(List<Videos> response) {
        if(response!=null){
            if(response.size()>0){
                keys.get(0);
                trailerAdapter.notifyDataSetChanged();
            }
        }
    }


}
