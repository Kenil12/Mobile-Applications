package com.example.recipe_book_02.FoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.recipe_book_02.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.UUID;

public class Post extends AppCompatActivity {
    ImageButton imageButton;
    Button post;
    TextInputLayout dishName, dishPrice, dishDescription;
    String _dishName, _dishPrice, _dishDescription;

    Uri imageuri;
    private Uri mcropimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference databaseReference, dataa;
    FirebaseAuth Fauth;
    StorageReference ref;
    String _ChefId, _RandomUID, _Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dishName = findViewById(R.id.dishName);
        dishPrice = findViewById(R.id.dishPrice);
        dishDescription = findViewById(R.id.dishDescription);
        post = findViewById(R.id.postButton);
        Fauth = FirebaseAuth.getInstance();
        databaseReference = database.getInstance().getReference("dishes");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = database.getInstance().getReference("user").child(userid);
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DbHelperChefDetails cheff = snapshot.getValue(DbHelperChefDetails.class);
                    _Address = cheff.getAddress();
                    imageButton = (ImageButton)findViewById(R.id.imageButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OnselectImageClick(v);
                        }
                    });
                    post.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            _dishName = dishName.getEditText().getText().toString().trim();
                            _dishDescription = dishDescription.getEditText().getText().toString().trim();
                            _dishPrice = dishPrice.getEditText().getText().toString().trim();

                            if(isValid(_dishName, _dishPrice, _dishDescription)){
                                uploadImage();
                            }
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch(Exception e){
            Log.e("Error", e.getMessage());
        }

    }

    private void uploadImage() {
//        To do: correct the condition for if
        if(imageuri !=null){
            final ProgressDialog progressDialog = new ProgressDialog(Post.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            _RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(_RandomUID);
            _ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("dishname", _dishName);
                            hashMap.put("price", _dishPrice);
                            hashMap.put("description", _dishDescription);
                            hashMap.put("RandomUid", _RandomUID);
                            hashMap.put("ChefId", _ChefId);
                            hashMap.put("url", String.valueOf(uri));
                            databaseReference.child(_RandomUID).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Post.this, "Dish Posted!!", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Post.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded" + (int)progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);

                }
            });
        }
    }
    private boolean isValid(String _dishName, String _dishPrice, String _dishDescription) {
        dishName.setErrorEnabled(false);
        dishName.setError("");
        dishDescription.setErrorEnabled(false);
        dishDescription.setError("");
        dishPrice.setErrorEnabled(false);
        dishPrice.setError("");
        boolean isValidDescription = false, isValidPrice=false, isValidName= false, isValid = false;

        if(TextUtils.isEmpty(_dishName)){
            dishName.setErrorEnabled(true);
            dishName.setError("Enter Dish Name");
        }
        else{
            dishName.setError(null);
            isValidName = true;
        }

        if(TextUtils.isEmpty(_dishDescription)){
            dishDescription.setErrorEnabled(true);
            dishDescription.setError("Enter Dish Description");
        }
        else{
            dishDescription.setError(null);
            isValidDescription = true;
        }

        if(TextUtils.isEmpty(_dishPrice)){
            dishPrice.setErrorEnabled(true);
            dishPrice.setError("Enter Dish Price");
        }
        else{
            dishPrice.setError(null);
            isValidPrice = true;
        }
        isValid = (isValidDescription && isValidName && isValidPrice)?true : false;
        return isValid;
    }

    private void startImageCropActivity(Uri imageuri){
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    private void OnselectImageClick(View v){
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mcropimageuri!=null && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startImageCropActivity(mcropimageuri);
        }
        else {
            Toast.makeText(this, "Permission Not Granted!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            imageuri = CropImage.getPickImageResultUri(this, data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)){
                mcropimageuri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else{
                startImageCropActivity(imageuri);
            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                imageButton.setImageURI(result.getUri());
                Toast.makeText(this, "Image Cropped", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this, "Cropping Failed because" + result.getError(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

