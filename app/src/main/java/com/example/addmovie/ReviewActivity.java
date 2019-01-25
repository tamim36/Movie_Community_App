package com.example.addmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ReviewActivity extends AppCompatActivity {

    private ImageButton postComment;
    private EditText commentBox;
    private RecyclerView commentList;
    private String mPostKey;
    private DatabaseReference mDatabase;
    private DatabaseReference mDataReview;
    private DatabaseReference newPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mPostKey = getIntent().getExtras().getString("PostKey");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Review").child(mPostKey);
        mDataReview = FirebaseDatabase.getInstance().getReference().child("Review").child(mPostKey);

        postComment = (ImageButton)findViewById(R.id.post_review);
        commentBox = (EditText)findViewById(R.id.comment_box);
        commentList = (RecyclerView)findViewById(R.id.comment_list);

        commentList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        commentList.setLayoutManager(linearLayoutManager);

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
                //Toast.makeText(ReviewActivity.this, mPostKey, Toast.LENGTH_SHORT).show();
                commentBox.setText("");
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<single_review,single_reviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<single_review, single_reviewHolder>(
                single_review.class,
                R.layout.single_comment,
                single_reviewHolder.class,
                mDataReview
        ) {
            @Override
            protected void populateViewHolder(single_reviewHolder viewHolder, single_review model, int position) {
                viewHolder.setReviewsss(model.getReviewsss());
            }
        };
        commentList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class single_reviewHolder extends RecyclerView.ViewHolder {
        View mView;

        public single_reviewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setReviewsss(String reviewsss) {
            TextView mReview = (TextView)mView.findViewById(R.id.comment_id);
            mReview.setText(reviewsss);
        }
    }



    private void addReview() {
        String review = commentBox.getText().toString().trim();

        newPost = mDatabase.push();
        newPost.child("Reviewsss").setValue(review);
    }
}
