package com.example.kartikmishra.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.net.ConnectivityManager;

import android.os.Parcelable;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener{

    private static final String TAG = "MainActivity";
    public static Toast toast;
    static public ArrayList<String> images;

    private RecyclerView mRecyclerView;

    public static List<Movies> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    public static   MoviesAdapter moviesAdapter;
    private TextView networkConnectionTv;
    private Button tryNetworkConnection;
    private  String poster_path;

    private Movies movies;
    Movies movieObj;


    private String lastSortedOrder;
    private ImageView ivSettings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

            if(savedInstanceState != null && savedInstanceState.containsKey("movies")) {
                list = savedInstanceState.getParcelableArrayList("movies");
                images = savedInstanceState.getStringArrayList("images");
            }else{
                list = new ArrayList<>();
                images = new ArrayList<>();
                moviesAdapter = new MoviesAdapter(getApplicationContext());
                updateMovies();
            }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ivSettings = findViewById(R.id.iv_settings);
        tryNetworkConnection = findViewById(R.id.tryNetworkConnectionBtn);

        mRecyclerView = findViewById(R.id.rv_movies);

        int  mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());
        GridLayoutManager layoutManager =
                new GridLayoutManager(this,mNoOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);
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


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.sort, menu);
//        return true;
//    }

    /**
     * checking if device has internet connection or not
     * @return
     */

    public boolean isOnline(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnectedOrConnecting();

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        switch (item.getItemId()){
//            case R.id.setting:
//                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
//                startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }

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


    /**
     * recyclerView on item click listener
     * @param clickedItemIndex
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("movies_position",clickedItemIndex);
        startActivity(intent);
    }

    /**
     * method to update movies in main screen
     */
    private void updateMovies() {

        if(isOnline()){
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String sortingCriteria = sharedPrefs.getString(getString(R.string.pref_sorting_criteria_key),getString(R.string.pref_sorting_criteria_default_value));
            new FetchMoviesAsyncTask().execute(sortingCriteria, null);

        }

    }



}


    /**
     * Fetching json from the api
     */
//    class FetchAsyncTask extends AsyncTask<String,Void,String> {
//
//
//        // Creates Uri based on sort order, etc
//        private Uri createMoviesUri(String sortOrder) {
//            Uri builtUri = null;
//
//            if (sortOrder.equals(getString(R.string.pref_popular_value))) {
//                builtUri = Uri.parse(Constants.APIConstants.API_POPULAR_MOVIES_BASE_URL);
//            } else if (sortOrder.equals(getString(R.string.pref_top_rated_value))) {
//                builtUri = Uri.parse(Constants.APIConstants.API_TOP_RATED_MOVIES_BASE_URL);
//            } else {
//                builtUri = Uri.parse(Constants.APIConstants.API_POPULAR_MOVIES_BASE_URL);
//
//            }
//
//            Uri apiUri = null;
//
//
//            apiUri = builtUri.buildUpon()
//                    .appendQueryParameter(Constants.APIConstants.API_KEY_PARAM, Constants.APIConstants.THE_MOVIE_DB_API_KEY)
//                    .build();
//
//
//            return apiUri;
//        }
//
//        // Method to create poster thumbnail Uri
//        private Uri createPosterUri(String posterPath) {
//            Uri builtUri = Uri.parse(Constants.APIConstants.API_POSTER_MOVIES_BASE_URL).buildUpon()
//                    .appendEncodedPath(Constants.APIConstants.API_POSTER_SIZE).appendEncodedPath(posterPath)
//                    .build();
//            return builtUri;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }
//
//
//        @Override
//        protected String doInBackground(String... voids) {
//
//            if (isOnline()) {
//
//                String moviesJsonStr;
//                HttpHandler sh = new HttpHandler();
//                moviesJsonStr = sh.makeServiceCall(url);
//
//                try {
//                    JSONObject moviesJson = null;
//                    moviesJson = new JSONObject(moviesJsonStr);
//
//                    JSONArray jsonMoviesArray = null;
//
//                    jsonMoviesArray = moviesJson.getJSONArray("results");
//                    for (int i = 0; i < jsonMoviesArray.length(); i++) {
//
//                        JSONObject r = jsonMoviesArray.getJSONObject(i);
//
//                        String poster_path = r.getString("poster_path");
//                        Double vote_average = r.getDouble("vote_average");
//                        String title = r.getString("original_title");
//                        String release_Date = r.getString("release_date");
//                        String overView = r.getString("overview");
//
//                        //movies = new Movies(0, null, vote_average, 0, title, poster_path, overView, release_Date, null);
//
//                        movies = new Movies();
//
//
//                        movies.setPoster_path(poster_path);
//                        movies.setRelease_Date(release_Date);
//                        movies.setTitle(title);
//                        movies.setOverView(overView);
//                        movies.setVote_average(vote_average);
//
//                       list.add(movies);
//
//
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            moviesAdapter.notifyDataSetChanged();
//
//            if(progressDialog.isShowing()){
//                progressDialog.dismiss();
//            }
//
//        }
//    }

