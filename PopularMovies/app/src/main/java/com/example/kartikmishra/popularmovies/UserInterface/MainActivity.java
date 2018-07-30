package com.example.kartikmishra.popularmovies.UserInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.database.Cursor;
import android.net.ConnectivityManager;

import android.os.AsyncTask;
import android.os.Parcelable;

import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.kartikmishra.popularmovies.Adapters.MoviesAdapter;
import com.example.kartikmishra.popularmovies.Constants;
import com.example.kartikmishra.popularmovies.NetworksUtils.FetchMoviesAsyncTask;
import com.example.kartikmishra.popularmovies.R;
import com.example.kartikmishra.popularmovies.Settings.SettingsActivity;
import com.example.kartikmishra.popularmovies.data.MoviesContract;
import com.example.kartikmishra.popularmovies.models.Movies;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    private static final String TAG = "MainActivity";
    public static Toast toast;
    static public ArrayList<String> images;
    public static List<Long> ids = new ArrayList<>();

    private RecyclerView mRecyclerView;

    public static List<Movies> list = null;
    private ProgressDialog progressDialog;
    public static   MoviesAdapter moviesAdapter;
    private TextView networkConnectionTv;
    private Button tryNetworkConnection;
    private  String poster_path;

    private Movies mMovies;
    Movies movieObj;
    public static Context mContext;


    private String lastSortedOrder;
    private ImageView ivSettings;
    private RelativeLayout relativeLayout;

    private static final String[] MOVIE_COLUMNS = {
            MoviesContract.MoviesEntry._ID,
            MoviesContract.MoviesEntry.COLUMN__MOVIES_ID,
            MoviesContract.MoviesEntry.COLUMN__VOTE_AVERAGE,
            MoviesContract.MoviesEntry.COLUMN__POSTER_PATH,
            MoviesContract.MoviesEntry.COLUMN__TITLE,
            MoviesContract.MoviesEntry.COLUMN__OVERVIEW,
            MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE
    };

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 4;
    public static final int COL_IMAGE = 3;
    public static final int COL_OVERVIEW = 5;
    public static final int COL_RATING = 2;
    public static final int COL_DATE = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            if(savedInstanceState != null && savedInstanceState.containsKey("movies")) {
                list = savedInstanceState.getParcelableArrayList("movies");
                images = savedInstanceState.getStringArrayList("images");
                sortingCriteria = savedInstanceState.getString("sorting");
            }else{
                list = new ArrayList<>();
                images = new ArrayList<>();
                moviesAdapter = new MoviesAdapter(getApplicationContext());
                updateMovies();
            }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        if(sortingCriteria.contentEquals("popular")){
            toolbar.setTitle("Most Popular");
        }else if(sortingCriteria.contentEquals("top_rated")){
            toolbar.setTitle("Highest Rated");
        }else if(sortingCriteria.contentEquals("fav")){
            toolbar.setTitle("Favourites");
        }

        ivSettings = findViewById(R.id.iv_settings);
        tryNetworkConnection = findViewById(R.id.tryNetworkConnectionBtn);

        mRecyclerView = findViewById(R.id.rv_movies);

        int  mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());
        GridLayoutManager layoutManager =
                new GridLayoutManager(this,mNoOfColumns);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(mNoOfColumns, LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

       moviesAdapter = new MoviesAdapter(getApplicationContext(),list,this);
        mRecyclerView.setAdapter(moviesAdapter);
        networkConnectionTv = findViewById(R.id.networkConnection);


        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) MainActivity.list);
        outState.putStringArrayList("images", MainActivity.images);
        outState.putString("sorting",sortingCriteria);
        super.onSaveInstanceState(outState);
    }

    /**
     * Utility class to check the width and height of screen and place number of columns
     */
    public static class Utility {
        public static int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 180);
            return noOfColumns;
        }
    }


    /**
     * checking if device has internet connection or not
     * @return
     */

    public boolean isOnline(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnectedOrConnecting();

    }


    @Override
    protected void onResume() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sortingCriteria = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key),getString(R.string.pref_sorting_criteria_default_value));

        if(lastSortedOrder!= null && !sortingCriteria.equals(lastSortedOrder)){
            list = new ArrayList<>();
            images = new ArrayList<>();
                updateMovies();
        }
        lastSortedOrder = sortingCriteria;
        super.onResume();

    }


    String sortingCriteria;
    /**
     * recyclerView on item click listener
     * @param clickedItemIndex
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("movies_position",clickedItemIndex);
        intent.putExtra("sorting",sortingCriteria);
        startActivity(intent);
    }

    /**
     * method to update movies in main screen
     */
    private void updateMovies() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       sortingCriteria = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key),getString(R.string.pref_sorting_criteria_default_value));

        if(isOnline()){

            if(sortingCriteria.contentEquals("fav")){
                favMoviesUpdateTask();

            }
            else if(sortingCriteria.contentEquals("popular") || sortingCriteria.contentEquals("top_rated")) {
                new FetchMoviesAsyncTask().execute(sortingCriteria, null);
            }

        }


        if(sortingCriteria.contentEquals("fav")){
            favMoviesUpdateTask();
        }

    }

    public void favMoviesUpdateTask(){

        new AsyncTask<Void, Void, List<Movies>>() {

            private List<Movies> getFavoriteMoviesDataFromCursor(Cursor cursor) {
                list.clear();
                images.clear();
                ids.clear();
                List<Movies> results = new ArrayList<>();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Movies movie = new Movies(cursor);
                        MainActivity.images.add(Constants.APIConstants.IMAGE_BASE_URL +
                                Constants.APIConstants.IMAGE_SMALL_SIZE+
                                cursor.getString(MainActivity.COL_IMAGE));
                        MainActivity.ids.add(movie.getMovie_ID());

                        results.add(movie);
                        MainActivity.list.add(movie);


                    } while (cursor.moveToNext());
                    cursor.close();
                }
                return results;
            }
            @Override
            protected List<Movies> doInBackground(Void... voids) {
                Cursor cursor = getApplicationContext().getContentResolver().query(
                        MoviesContract.MoviesEntry.CONTENT_URI,
                        MOVIE_COLUMNS,
                        null,
                        null,
                        null
                );
                return getFavoriteMoviesDataFromCursor(cursor);
            }

            @Override
            protected void onPostExecute(List<Movies> movies) {
                if(movies!=null){
                    if(movies.size()>0){

                        moviesAdapter.notifyDataSetChanged();

                    }
                    list = new ArrayList<>();
                    list.addAll(movies);
                }

            }
        }.execute();
    }

}


