package com.example.itquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.example.itquiz.databinding.RegisterPageBinding;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity{
    private RegisterPageBinding Register;
    private FirebaseAuth mAuth;
    private String Email;
    private String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Register= DataBindingUtil.setContentView(RegisterActivity.this,R.layout.register_page);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();







        Register.backRegisterButton.setOnClickListener(view -> {
            Email=Register.registerEmailidText.getText().toString();
            Password=Register.registerPasswordText.getText().toString();
            if(Email.isEmpty() || Password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter details",
                        Toast.LENGTH_SHORT).show();
            }
            else registerUser(Email,Password);

        });

    }



    private void registerUser(String Email,String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("YAG", "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Authentication done.",
                                    Toast.LENGTH_SHORT).show();
                            backLoginPage();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("YAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void backLoginPage() {
        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
