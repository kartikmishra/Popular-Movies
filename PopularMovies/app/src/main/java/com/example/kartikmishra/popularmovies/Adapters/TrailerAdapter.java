package com.example.kartikmishra.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kartikmishra.popularmovies.R;
import com.example.kartikmishra.popularmovies.UserInterface.DetailActivity;
import com.example.kartikmishra.popularmovies.UserInterface.MainActivity;
import com.example.kartikmishra.popularmovies.models.Videos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{


    private static final String TAG = "TrailerAdapter";

    private ImageView trailer_iv;
    private List<Videos> mTrailers = new ArrayList<>();
    private  ListItemClickListener mListItemClickListener;
    private Context mContext;
    private Videos videos = new Videos();




    public TrailerAdapter(Context context,List<Videos> trailers,ListItemClickListener listItemClickListener){

        this.mContext = context;
        this.mTrailers = trailers;
        this.mListItemClickListener = listItemClickListener;
    }


    public TrailerAdapter(Context applicationContext){
        mContext = applicationContext;
    }
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }



    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForTrailer = R.layout.trailer_recyclerview_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForTrailer,parent,shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {


        String yt_thumbnail_url = "http://img.youtube.com/vi/" + DetailActivity.keys.get(position) + "/0.jpg";
        Picasso.with(mContext).load(yt_thumbnail_url).into(trailer_iv);

        holder.setIsRecyclable(false);
    }


    @Override
    public int getItemCount() {
        return mTrailers.size();
    }



    class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            trailer_iv = itemView.findViewById(R.id.trailer_iv);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedPosition);
        }
    }

}
