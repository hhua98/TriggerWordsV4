package com.example.tarik.triggerwordsv1.triggerWords;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tarik.triggerwordsv1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class WordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int IMAGE_UPLOAD_REQUEST = 1;
    private DatabaseReference mRef;
    private StorageReference mStorageRef;
    private Uri imageUri = null;
    private ProgressDialog mProgressDialog;
    private static final String TAG = "WordActivity";

    private EditText mWordEditText;
    private EditText mImageEditText;
    private EditText mCommentEditText;
    private Button mAddWord;
    private Button mRemoveWord;
    private Button mAddImage;
    private Button mUpdateComment;
    private Button mClearComment;
    private Button mCaptureImage;
    private Button mRemoveImage;
    private String imgPath;
    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    String fileName;
    private Button b_gallery, b_capture;
    private ImageView iv_image;
    private StorageReference storage;
    private static final int GALLERY_INTENT = 2;
    private static final int CAMERA_REQUEST_CODE = 1;
    private ProgressDialog progressDialog;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        storage = FirebaseStorage.getInstance().getReference();

        mWordEditText = (EditText) findViewById(R.id.wordEditText);
        mImageEditText = (EditText) findViewById(R.id.imageEditText);
        mCommentEditText = (EditText) findViewById(R.id.commentEditText);
        mAddWord = (Button) findViewById(R.id.addButton);
        mRemoveWord = (Button) findViewById(R.id.removeButton);
        mUpdateComment = (Button) findViewById(R.id.updateCommentButton);
        mClearComment = (Button) findViewById(R.id.clearCommentButton);
        mAddImage = (Button) findViewById(R.id.addImageButton);
        mCaptureImage = (Button) findViewById(R.id.captureImageButton);
        mRemoveImage = (Button) findViewById(R.id.removeImageButton);

        mProgressDialog = new ProgressDialog(this);


        // Write a message to the database
        this.mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://triggerwordsv1.firebaseio.com/");
        this.mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://triggerwordsv1.appspot.com");
        mAddWord.setOnClickListener(this);
        mRemoveWord.setOnClickListener(this);
        mUpdateComment.setOnClickListener(this);
        mAddImage.setOnClickListener(this);
        mCaptureImage.setOnClickListener(this);
        mClearComment.setOnClickListener(this);
        mRemoveImage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        //DatabaseReference mRefChild = mRef.child("Name");
        //mRefChild.setValue(nameIn.getText().toString());
        switch (view.getId())
        {
            case R.id.addButton:
                addNewWord();
                break;

            case R.id.removeButton:
                removeWord();
                break;

            case R.id.updateCommentButton:
                updateComment();
                break;

            case R.id.clearCommentButton:
                mCommentEditText.setText("");
                break;

            case R.id.addImageButton:
                addImage();
                break;

            case R.id.captureImageButton:
                dispatchTakePictureIntent();
                break;

            case R.id.removeImageButton:
                removeImage();
                break;
        }
    }

    private TriggerWord createNewWord(String word) {
        String image = mImageEditText.getText().toString();
        String comment = mCommentEditText.getText().toString();
        TriggerWord newTriggerWord = new TriggerWord(word, image, comment);
        return newTriggerWord;
    }

    private void addNewWord() {
        mRef.child(mWordEditText.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    mRef.child(mWordEditText.getText().toString().trim()).setValue(createNewWord(mWordEditText.getText().toString().trim()));
                    Toast.makeText(WordActivity.this, "Word added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WordActivity.this, "That word already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void removeWord() {
        mRef.child(mWordEditText.getText().toString().trim()).removeValue();
        Toast.makeText(this, "Word removed", Toast.LENGTH_SHORT).show();
    }

    private void updateComment() {
        mRef.child(mWordEditText.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    mRef.child(mWordEditText.getText().toString().trim()).child("comment").setValue(mCommentEditText.getText().toString());
                    Toast.makeText(WordActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(WordActivity.this, "That word does not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void addImage() {
        mRef.child(mWordEditText.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent,IMAGE_UPLOAD_REQUEST);
                } else {
                    Toast.makeText(WordActivity.this, "That word does not exists yet", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    /*private void captureImage() {
        mRef.child(mWordEditText.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(captureIntent,IMAGE_UPLOAD_REQUEST);
                } else {
                    Toast.makeText(WordActivity.this, "That word does not exists yet", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }*/



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "com.example.tarik.triggerwordsv1.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);


            }
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == IMAGE_UPLOAD_REQUEST)
                && (resultCode == RESULT_OK) && (data != null)) {
            mProgressDialog.setMessage("Uploading...");
            mProgressDialog.show();
            Uri uri = data.getData();
            imageUri = data.getData();
            StorageReference filePath = mStorageRef.child(mWordEditText.getText().toString().trim() + "Images").child(randomStringGenerator());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadImageUrl = taskSnapshot.getDownloadUrl();
                    mRef.child(mWordEditText.getText().toString().trim()).child("image").setValue(downloadImageUrl.toString());


                    Toast.makeText(WordActivity.this, "Upload Done", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(WordActivity.this, "Upload failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }






    private void removeImage () {
       // mRef.child(mWordEditText.getText().toString().trim()).child("image").setValue(null);
        /*final String toDelete = "Images/*";
        mProgressDialog.setMessage("Deleting...");
        mProgressDialog.show();
        mStorageRef.child(toDelete).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //mRef.child(toDelete).child("image").removeValue();
                Toast.makeText(WordActivity.this, "Deleted image", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(WordActivity.this, "Delete failed", Toast.LENGTH_LONG).show();
            }
        });
*/

    }

    private String randomStringGenerator() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
