package com.example.s_m_s;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button chooseButton,saveButton,displayButton;
    private ImageView imageView;
    private EditText imageNameEditText;
    private ProgressBar progressBar;
    private Uri imageUri;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        storageReference = FirebaseStorage.getInstance().getReference("Upload");


        chooseButton = findViewById(R.id.chooseImageButton);
        saveButton = findViewById(R.id.saveImageButton);
        displayButton = findViewById(R.id.displayImageButton);
        progressBar = findViewById(R.id.progressbarId);

        imageView = findViewById(R.id.imageViewId);
        imageNameEditText = findViewById(R.id.imageNameEditTextId);

        saveButton.setOnClickListener(this);
        displayButton.setOnClickListener(this);
        chooseButton.setOnClickListener(this);



    }

    //...........................................................




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.id1){
            Toast.makeText(this, "Setting is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.id2){
            Toast.makeText(this, "About is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.id3){
            Toast.makeText(this, "Home is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.id4){
            Toast.makeText(this, "All Student Info is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,StudentActivity.class);
            startActivity(intent);
        } if(item.getItemId()==R.id.id5){
            Toast.makeText(this, "Blood Group is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,BloodGroupActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.id6){
            Toast.makeText(this, "CGPA calculator is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,CGPAActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }




    //.....................................................

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.chooseImageButton:

                openFileChooser();

                break;

            case R.id.saveImageButton:

                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(), "Uploading is progress", Toast.LENGTH_LONG).show();
                }else{
                    saveData();
                }

                break;

            case R.id.displayImageButton:

                Intent intent = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(intent);

                break;

        }

    }


    void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data != null && data.getData()!=null ){

            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);


        }

    }

    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void saveData() {

        String imageName = imageNameEditText.getText().toString().trim();
        if(imageName.isEmpty()){
            imageNameEditText.setError("Enter the image name");
            imageNameEditText.requestFocus();
            return;
        }

        StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "Image is stored successfully", Toast.LENGTH_LONG).show();

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri downloadUrl = urlTask.getResult();

                Upload upload = new Upload(imageName,downloadUrl.toString());
                String uploadId = databaseReference.push().getKey();
                databaseReference.child(uploadId).setValue(upload);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image is not stored successfully", Toast.LENGTH_LONG).show();
            }
        });


    }

}