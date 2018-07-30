package com.example.kartikmishra.popularmovies.NetworksUtils;


import android.net.Uri;
import android.os.AsyncTask;

import android.widget.Toast;

import com.example.kartikmishra.popularmovies.Constants;
import com.example.kartikmishra.popularmovies.UserInterface.MainActivity;
import com.example.kartikmishra.popularmovies.models.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class FetchMoviesAsyncTask extends AsyncTask<String,Void,String> {

    private static final String TAG = "FetchMoviesAsyncTask";



    @Override
    protected String doInBackground(String... strings) {

            if (strings.length == 0) {
                return null;
            }


            String sortingCriteria = strings[0];

            Uri builtUri = Uri.parse(Constants.APIConstants.BASE_URL).buildUpon()
                    .appendPath(sortingCriteria)
                    .appendQueryParameter("api_key", Constants.APIConstants.THE_MOVIE_DB_API_KEY)
                    .build();

            String response;

            try {

                response = getJson(builtUri);
                return response;

            } catch (Exception e) {
                MainActivity.toast.setText("Connection Error");
                MainActivity.toast.setDuration(Toast.LENGTH_SHORT);
                MainActivity.toast.show();
                return null;
            }


    }



    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            loadInfo(s);
        } else {
            MainActivity.toast.setText("No Internet Connection");
            MainActivity.toast.setDuration(Toast.LENGTH_SHORT);
            MainActivity.toast.show();
        }
    }

    public static String getJson(Uri builtUri) {

        String jsonStr = null;
        try {
            String moviesString = builtUri.toString();
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(moviesString);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonStr;

    }



    public static void loadInfo(String s) {

        MainActivity.images.clear();
        MainActivity.list.clear();

        try {
            if(s!=null){
                JSONObject moviesObject = new JSONObject(s);
                JSONArray moviesArray = moviesObject.getJSONArray("results");

                for(int i=0;i<moviesArray.length();i++){

                    JSONObject movie = moviesArray.getJSONObject(i);
                    Movies moviesItem = new Movies();

                    moviesItem.setTitle(movie.getString("original_title"));
                    moviesItem.setMovie_ID(movie.getLong("id"));
                    moviesItem.setVote_average(movie.getDouble("vote_average"));
                    moviesItem.setPoster_path(movie.getString("poster_path"));
                    if(movie.getString("poster_path").equals("null")){
                        MainActivity.images.add("https://www.kssm.in/wp-content/uploads/2014/12/no-image.jpg");
                        moviesItem.setPoster_path("https://www.kssm.in/wp-content/uploads/2014/12/no-image.jpg");
                    }
                    else
                    {
                        MainActivity.images.add(Constants.APIConstants.IMAGE_BASE_URL+
                                Constants.APIConstants.IMAGE_SMALL_SIZE+movie.getString("poster_path"));
                    }

                    if (movie.getString("overview").equals("")) {
                        moviesItem.setOverView("No Overview was Found");
                    } else {
                        moviesItem.setOverView(movie.getString("overview"));
                    }
                    if (movie.getString("release_date").equals("")) {
                        String s1 = "Unknown";
                        moviesItem.setRelease_Date(s1);

                    } else {

                        moviesItem.setRelease_Date(movie.getString("release_date"));

                    }

                    MainActivity.list.add(moviesItem);
                    MainActivity.moviesAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
