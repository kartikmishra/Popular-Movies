package com.example.kartikmishra.popularmovies.NetworksUtils;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.kartikmishra.popularmovies.Constants;
import com.example.kartikmishra.popularmovies.UserInterface.DetailActivity;
import com.example.kartikmishra.popularmovies.models.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchReviewsAsyncTask extends AsyncTask<String,Void,List<Reviews>> {



    private OnTaskCompleted taskCompleted;

    public FetchReviewsAsyncTask(OnTaskCompleted applicationContext) {
        this.taskCompleted = applicationContext;
    }

    public interface OnTaskCompleted{
        void onReviewTaskCompleted(List<Reviews> reviews);
    }
    private List<Reviews> getDataFromJson(String jsonStr) throws JSONException {

        DetailActivity.reviewsList.clear();
        JSONObject reviewObject = new JSONObject(jsonStr);
        JSONArray reviewArray = reviewObject.getJSONArray("results");

        List<Reviews> results = new ArrayList<>();

        for(int i=0;i<reviewArray.length();i++){
            JSONObject review = reviewArray.getJSONObject(i);
            Reviews reviewsModel = new Reviews();

            if(review.length()>0){
                reviewsModel.setAuthor(review.getString("author"));
                reviewsModel.setContent(review.getString("content"));
                reviewsModel.setReviewId(review.getString("id"));
                results.add(reviewsModel);
                DetailActivity.reviewsList.add(reviewsModel);
            }
        }

        return results;
    }
    @Override
    protected List<Reviews> doInBackground(String... strings) {
        if(strings.length==0){
            return null;
        }

        try {

            Uri uri = Uri.parse(Constants.APIConstants.BASE_URL).buildUpon()
                    .appendPath(strings[0])
                    .appendPath(Constants.APIConstants.REVIEW_PARAM)
                    .appendQueryParameter(Constants.APIConstants.API_KEY_PARAM, Constants.APIConstants.THE_MOVIE_DB_API_KEY)
                    .build();

            String response = getJsonString(uri);
            return getDataFromJson(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getJsonString(Uri uri) {
        String jsonStr = null;

        try {
            String trailerString = uri.toString();
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(trailerString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonStr;
    }

    @Override
    protected void onPostExecute(List<Reviews> reviews) {
        if(reviews!=null){
            if(reviews.size()>0){

                taskCompleted.onReviewTaskCompleted(reviews);
            }
        }
    }
}
