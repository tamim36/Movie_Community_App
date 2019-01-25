package com.example.addmovie;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBloglist;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Community");

        mBloglist = (RecyclerView)findViewById(R.id.blog_list);
        mBloglist.setHasFixedSize(true);
        mBloglist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                final String post_key = getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                //viewHolder.setDesc(model.getDescription());
                viewHolder.setGenre(model.getGenre());
                viewHolder.setRating(model.getRating());
                viewHolder.setRelease_year(model.getRelease_year());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, post_key, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Post_inner_part.class);
                        intent.putExtra("blog_id", post_key);
                        startActivity(intent);
                    }
                });
            }
        };
        mBloglist.setAdapter(firebaseRecyclerAdapter);
    }





    //Set title, genre etc
    public static class BlogViewHolder extends ViewHolder {
        View mView;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.item_movie_title);
            post_title.setText(title);
        }
        public void setRating(String rating) {
            TextView mRating = (TextView)mView.findViewById(R.id.item_movie_rating);
            mRating.setText(rating);
        }

        public void setGenre(String genre) {
            TextView mgenre = (TextView)mView.findViewById(R.id.item_movie_genre);
            mgenre.setText(genre);
        }

        public void setRelease_year(String release_year) {
            TextView rel_year = (TextView)mView.findViewById(R.id.item_movie_release_date);
            rel_year.setText(release_year);
        }

        public void setImage(Context ctx, String Image){
            ImageView post_image = (ImageView)mView.findViewById(R.id.item_movie_poster);
            Picasso.with(ctx).load(Image).into(post_image);
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_button){
            startActivity(new Intent(MainActivity.this, PostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
