package com.example.registration_login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {



    private Button btnLogin,button2 ;

    private TextView etLoginEmail,etLoginPassword;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        btnLogin=findViewById(R.id.btnLogin);
        button2=findViewById(R.id.button2);
        etLoginEmail=findViewById(R.id.etLoginEmail);
        etLoginPassword=findViewById(R.id.etLoginPassword);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login_user();




            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Login.this,Registration.class);
                startActivity(intent);


            }
        });






        }
    private void login_user()
    {

        String email=etLoginEmail.getText().toString().trim();
        String password=etLoginPassword.getText().toString().trim();


        if(email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();

            return;

        }



        Toast.makeText(this, " Login successfull", Toast.LENGTH_SHORT).show();


        Intent intent=new Intent(Login.this,Homescreen.class);
        startActivity(intent);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference userRef= database.getReference();

        userRef.orderByChild("email").equalTo(email).get().addOnCompleteListener(task ->
        {

            if(task.isSuccessful())
            {

                boolean userFound=true;

                for(DataSnapshot snapshot:task.getResult().getChildren())
                {

                    User user = snapshot.getValue(User.class);

                    if(user==null)
                    {

                        Log.d("Firebase","Snapshot if full");
                        continue;

                    }

                    if(user.password.equals(password))
                    {
                        Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,Homescreen.class));
                        finish();
                        userFound=true;
                        break;

                    }


                }

                if(!userFound)
                {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseError",task.getException().getMessage());

                }

            }
        });




        finish();

    }
    }
