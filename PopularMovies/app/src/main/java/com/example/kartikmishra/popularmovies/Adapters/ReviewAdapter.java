package com.example.kartikmishra.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartikmishra.popularmovies.R;
import com.example.kartikmishra.popularmovies.UserInterface.DetailActivity;
import com.example.kartikmishra.popularmovies.UserInterface.MainActivity;
import com.example.kartikmishra.popularmovies.models.Reviews;
import com.example.kartikmishra.popularmovies.models.Videos;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {


    private CircleImageView imageView;
    private TextView author_first_name,author_full_name,content;
    private List<Reviews> list = new ArrayList<>();
    private Context mContext;

    public ReviewAdapter(List<Reviews> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public ReviewAdapter(Context applicationContext){
        this.mContext = applicationContext;
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_recyclerview_item,parent,false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {

        if(DetailActivity.reviewsList.size()>0){

            String firstName = DetailActivity.reviewsList.get(position).getAuthor().substring(0,1);
            if(!firstName.equals(firstName.toUpperCase())){
               firstName= firstName.toUpperCase();
                author_first_name.setText(firstName);
            }
            else {
                author_first_name.setText(firstName);
            }

            author_full_name.setText("A Review By "+DetailActivity.reviewsList.get(position).getAuthor());
            content.setText(DetailActivity.reviewsList.get(position).getContent());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder{

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);

            author_first_name = itemView.findViewById(R.id.tv_review_author_name);
            author_full_name = itemView.findViewById(R.id.textView_review_big_label);
            content = itemView.findViewById(R.id.content_review);
        }
    }
}
