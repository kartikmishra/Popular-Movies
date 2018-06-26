package com.example.kartikmishra.popularmovies;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder>
{
    private static final String TAG = "MoviesAdapter";

    private static ListItemClickListener mOnClickListener;

    private List<Movies> moviesList = new ArrayList<>();
    private Context mContext;



    private ImageView moviesImageView;

    private  Context context;
    public MoviesAdapter(Context context,List<Movies> moviesList,ListItemClickListener listener) {
        this.context = context;
        mOnClickListener = listener;
        this.moviesList = moviesList;


    }

    public MoviesAdapter(Context applicationContext) {
        mContext =applicationContext;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }




    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForMovies = R.layout.recyclerview_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForMovies,parent,shouldAttachToParentImmediately);


        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {

        Movies movies = moviesList.get(position);
        Picasso.with(context).load(MainActivity.images.get(position)).into(moviesImageView);

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
      return moviesList.size();
    }


    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);

            moviesImageView = itemView.findViewById(R.id.movies_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }

    @Override
    public void onViewRecycled(@NonNull MoviesAdapterViewHolder holder) {

    }

    public void clearRecyclerViewData() {
        int size = moviesList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                moviesList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

}
