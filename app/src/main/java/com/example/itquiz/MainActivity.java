package com.example.itquiz;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.widget.Toast;

import com.example.itquiz.databinding.ActivityMainBinding;
import com.example.itquiz.databinding.QuizQuestionBinding;
import com.example.itquiz.databinding.ResultScoreBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private QuizQuestionBinding quiz;
    private ResultScoreBinding result;
    private FirebaseAuth mAuth;
    private String Email,Password;
    GoogleSignInClient googleSignInClient;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        // Initialize sign in options the client-id is copied form google-services.json file
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("786870735329-0odph28q2iqrodn9b98vkinch5u1as5q.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(MainActivity.this, googleSignInOptions);
        mAuth = FirebaseAuth.getInstance();



        binding.LoginButton.setOnClickListener(view -> {
            Email=binding.YourEmailidText.getText().toString();
            Password=binding.PasswordText.getText().toString();
            if(Email.isEmpty() || Password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter details",
                        Toast.LENGTH_SHORT).show();
            }
            else loginUser(Email,Password);

        binding.imageButton.setOnClickListener(view1 -> {
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, 100);
        });


        });
        binding.registerButton.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });



//        quiz.QuestionText.setText(question[currentQuestionid].getQuestionId());

//        quiz.SubmitButton.setOnClickListener(view -> {
//            result=DataBindingUtil.setContentView(this,R.layout.result_score);
//        });
    }

    private void moveToQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivityForResult(intent,100);
    }

    private void loginUser(String email,String password){
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(MainActivity.this, "Authentication Done.",
                                        Toast.LENGTH_SHORT).show();
                                moveToQuiz();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        private void googleSignIn(){
//           BeginSignInRequest.builder()
//                    .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                            .setSupported(true)
//                            // Your server's client ID, not your Android client ID.
//                            .setServerClientId(getString(R.string.default_web_client_id))
//                            // Only show accounts previously used to sign in.
//                            .setFilterByAuthorizedAccounts(true)
//                            .build());


            moveToQuiz();

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                    // When task is successful redirect to profile activity display Toast
                                    startActivity(new Intent(MainActivity.this, QuizActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    displayToast("Firebase authentication successful");
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}