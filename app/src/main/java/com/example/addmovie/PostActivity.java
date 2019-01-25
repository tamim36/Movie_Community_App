package com.example.addmovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private EditText mMovTitle;
    private EditText mMovDesc;
    private EditText mYear;
    private EditText mGenre;
    private EditText mRating;
    private ImageButton mSubmitButton;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private DatabaseReference newPost;
    private ProgressDialog mProgress;

    private static final int gallary_request = 1;
    private Uri imageUri = null;

    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Community");

        imageButton = (ImageButton)findViewById(R.id.imageButton1);
        mMovTitle = (EditText)findViewById(R.id.edit_text);
        mMovDesc = (EditText) findViewById(R.id.edit_text2);
        mYear = (EditText)findViewById(R.id.edit_release_date);
        mGenre = (EditText)findViewById(R.id.edit_Genre);
        mRating = (EditText)findViewById(R.id.edit_rating);
        mSubmitButton = (ImageButton) findViewById(R.id.id_submit);

        mProgress = new ProgressDialog(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallaryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/*");
                startActivityForResult(gallaryIntent, gallary_request);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {
        mProgress.setMessage("Posting ...");


        final String title = mMovTitle.getText().toString().trim();
        final String description = mMovDesc.getText().toString().trim();
        final String year = mYear.getText().toString().trim();
        final String rating = mRating.getText().toString().trim();
        final String genre = mGenre.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && imageUri != null){
            mProgress.show();
            final StorageReference filePath = mStorage.child("Movie_Images").child(imageUri.getLastPathSegment());

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //String downloadUrl;


                    newPost = mDatabase.push();
                    newPost.child("Title").setValue(title);
                    newPost.child("Description").setValue(description);
                    newPost.child("release_year").setValue(year);
                    newPost.child("rating").setValue(rating);
                    newPost.child("genre").setValue(genre);
                    //newPost.child("Image").setValue(downloadUrl);
                    mStorage.child("Movie_Images").child(imageUri.getLastPathSegment()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            //downloadUrl = task.getResult().toString();
                            newPost.child("Image").setValue(task.getResult().toString());
                        }
                    });

                    mProgress.dismiss();

                    startActivity(new Intent(PostActivity.this, MainActivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallary_request && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        }
    }
}
