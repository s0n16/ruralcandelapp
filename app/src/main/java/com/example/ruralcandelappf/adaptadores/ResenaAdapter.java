package com.example.ruralcandelappf.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruralcandelappf.R;
import com.example.ruralcandelappf.Resena;

import java.util.List;

public class ResenaAdapter extends RecyclerView.Adapter<ResenaAdapter.ReviewViewHolder> {

    private List<Resena> mReviews;

    public ResenaAdapter(List<Resena> reviews) {
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_resenas, parent, false);
        return new ReviewViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Resena review = mReviews.get(position);
        holder.bindReview(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameTextView;
        private TextView mReviewTextView;
        private TextView comentario;
        private RatingBar ratingBar;
        private Button btnDejarResena;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mUsernameTextView = itemView.findViewById(R.id.tvUsuario);
            mReviewTextView = itemView.findViewById(R.id.tvFecha);
            comentario = itemView.findViewById(R.id.tvComentario);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            btnDejarResena = itemView.findViewById(R.id.btnDejarResena);
        }

        public void bindReview(Resena review) {
            mUsernameTextView.setText(review.getUsuario());
            mReviewTextView.setText(review.getFecha());
            comentario.setText(review.getComentario());
            ratingBar.setVisibility(View.VISIBLE);
            ratingBar.setIsIndicator(true);
            ratingBar.setRating(review.getValoracion());
            btnDejarResena.setVisibility(View.GONE);
            //hacer invisible el background del layout
            itemView.setBackgroundResource(0);





        }
    }
}
