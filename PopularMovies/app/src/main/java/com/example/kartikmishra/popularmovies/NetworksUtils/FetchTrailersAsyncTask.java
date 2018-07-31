package com.example.kartikmishra.popularmovies.NetworksUtils;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.kartikmishra.popularmovies.Constants;
import com.example.kartikmishra.popularmovies.UserInterface.DetailActivity;
import com.example.kartikmishra.popularmovies.UserInterface.MainActivity;
import com.example.kartikmishra.popularmovies.models.Videos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchTrailersAsyncTask extends AsyncTask<String,Void,List<Videos>> {


    private OnTaskCompleted taskCompleted;
    public FetchTrailersAsyncTask(OnTaskCompleted applicationContext) {
        this.taskCompleted = applicationContext;
    }

    public interface OnTaskCompleted{
        void onTrailersTaskCompleted(List<Videos> response);
    }

    private List<Videos> getDataFromJson(String jsonStr) throws JSONException {
        DetailActivity.videoList.clear();
        DetailActivity.keys.clear();
        JSONObject trailerJson = new JSONObject(jsonStr);
        JSONArray trailerArray = trailerJson.getJSONArray("results");

        List<Videos> results = new ArrayList<>();
        for(int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailer = trailerArray.getJSONObject(i);
            // Only show Trailers which are on Youtube
            if (trailer.getString("site").contentEquals("YouTube")) {
                Videos trailerModel = new Videos();
                trailerModel.setKey(trailer.getString("key"));
                trailerModel.setName(trailer.getString("name"));
                results.add(trailerModel);
                DetailActivity.videoList.add(trailerModel);
                if(!(trailer.getString("key").length()==0)){
                    DetailActivity.keys.add(trailer.getString("key"));
                }else {
                    //keys.add("-9E_Tcv8eJ8");
                    //trailerModel.setKey("-9E_Tcv8eJ8");
                }

            }
            //DetailActivity.trailerAdapter.notifyDataSetChanged();

        }

        return results;
    }

    @Override
    protected List<Videos> doInBackground(String... strings) {
        if(strings.length==0){
            return null;
        }

        try {
            Uri uri = Uri.parse(Constants.APIConstants.BASE_URL).buildUpon()
                    .appendPath(strings[0])
                    .appendPath(Constants.APIConstants.VIDEO_PARAM)
                    .appendQueryParameter(Constants.APIConstants.API_KEY_PARAM, Constants.APIConstants.THE_MOVIE_DB_API_KEY)
                    .build();

            String response = getJsonString(uri);
            return getDataFromJson(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
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
    protected void onPostExecute(List<Videos> videos) {
        if(videos!=null) {
            if (videos.size() > 0) {
                DetailActivity.keys.get(0);

                taskCompleted.onTrailersTaskCompleted(videos);
               // DetailActivity.trailerAdapter.notifyDataSetChanged();
            }
        }

    }
}
