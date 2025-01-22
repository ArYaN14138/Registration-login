package com.example.registration_login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

public class Registration extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView imageViewPhoto;
    private Button btnRegister;
    private Button buttonClick;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    private Uri selectedImageUri;




    private TextView etUsername,etEmail,etPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);//initialize the firebase setup.

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");



        imageViewPhoto=findViewById(R.id.imageViewPhoto);
        btnRegister=findViewById(R.id.btnRegister);
        buttonClick=findViewById(R.id.button);
        etUsername=findViewById(R.id.etUsername);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);



        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);





            }



        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();


            }
        });

        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Registration.this , Login.class);
                startActivity(intent);


            }

        });





        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageViewPhoto.setImageURI(selectedImageUri);
        }
    }

    private void registerUser()
    {

        String username= etUsername.getText().toString().trim();
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();


        if(username.isEmpty()||email.isEmpty()||password.isEmpty()||imageViewPhoto==null)
        {
            Toast.makeText(this, " Fill the all required details", Toast.LENGTH_SHORT).show();
            return;


        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task ->
        {


            if(task.isSuccessful())
            {

                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser!=null)
                {

                }
                Toast.makeText(this, "Registration is done", Toast.LENGTH_SHORT).show();
                clearform();
            }
            else
            {
                Toast.makeText(this, "Registration is failed"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

       // else {
        //    Toast.makeText(this, "Registration is Successfull", Toast.LENGTH_SHORT).show();

       // }










    }

    private void clearform()
    {
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText(" ");
        imageViewPhoto.setImageResource(R.drawable.ic_launcher_background);
        imageViewPhoto=null;

    }




}
