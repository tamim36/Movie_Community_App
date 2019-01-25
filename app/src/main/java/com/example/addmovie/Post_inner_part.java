package com.example.addmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Post_inner_part extends AppCompatActivity {

    private ImageView imageView;
    private TextView post_title;
    private TextView post_desc;
    private TextView post_genre;
    private TextView post_rating;
    private TextView post_year;
    private ImageButton review;

    //private Button ratiing;
    private DatabaseReference mDatabase;
    private String mPostKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_inner_part);

        mPostKey = getIntent().getExtras().getString("blog_id");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Community");

        imageView = (ImageView)findViewById(R.id.image_post);
        post_title =  (TextView)findViewById(R.id.inner_post_title);
        post_desc = (TextView)findViewById(R.id.inner_post_desc);
        post_genre =  (TextView)findViewById(R.id.inner_post_genre);
        post_year =  (TextView)findViewById(R.id.inner_post_year);
        post_rating =  (TextView)findViewById(R.id.inner_post_rating);
        review = (ImageButton)findViewById(R.id.review_button);
        //ratiing = (Button)findViewById(R.id.rating_button);

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(Post_inner_part.this, ReviewActivity.class);
                reviewIntent.putExtra("PostKey", mPostKey);
                startActivity(reviewIntent);
            }
        });

        mDatabase.child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title_view = (String)dataSnapshot.child("Title").getValue();
                String title_desc = (String) dataSnapshot.child("Description").getValue();
                String title_genre = (String) dataSnapshot.child("genre").getValue();
                String title_rating = (String) dataSnapshot.child("rating").getValue();
                String title_year = (String) dataSnapshot.child("release_year").getValue();
                String post_image= (String)dataSnapshot.child("Image").getValue();

                post_title.setText(title_view);
                post_desc.setText(title_desc);
                post_genre.setText(title_genre);
                post_year.setText(title_year);
                post_rating.setText(title_rating);
                Picasso.with(Post_inner_part.this).load(post_image).into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
