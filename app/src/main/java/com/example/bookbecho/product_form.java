package com.example.bookbecho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookbecho.models.imageUploaderModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class product_form extends AppCompatActivity {

    private ImageView productPhoto;
    private Button upload, submit;
    private Uri imageUri;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference folder = FirebaseStorage.getInstance().getReference().child("Images");

    private static final int ImageBack = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        productPhoto = findViewById(R.id.product_photo);
        upload = findViewById(R.id.add_product_photo);
        submit = findViewById(R.id.submit);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData(v);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(product_form.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void uploadData(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ImageBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ImageBack && resultCode == RESULT_OK){
            Uri ImageData = data.getData();

            StorageReference ImageName = folder.child("image"+ ImageData.getLastPathSegment());

            ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(product_form.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}