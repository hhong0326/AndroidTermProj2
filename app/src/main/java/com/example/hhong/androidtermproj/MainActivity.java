package com.example.hhong.androidtermproj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
Log.d("박정환","박정환");
        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {

                Object value = getIntent().getExtras().get(key);

                Log.d(TAG, "Key: " + key + " Value: " + value);

            }

        }



        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "InstanceID token: " + token);



        mAuth = FirebaseAuth.getInstance();



        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    // User is signed in

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());



                    Intent it = new Intent(MainActivity.this, LoginActivity.class);

                    startActivity(it);

                } else {

                    // User is signed out

                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }

                // ...

            }

        };

    }



    public void onButtonLogin(View v) {

        String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();

        String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());



                        // If sign in fails, display a message to the user. If sign in succeeds

                        // the auth state listener will be notified and logic to handle the

                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                            Log.w(TAG, "signInWithEmail", task.getException());

                            Toast.makeText(MainActivity.this, "Authentication failed.",

                                    Toast.LENGTH_SHORT).show();

                        }



                        // ...

                    }

                });

    }



    @Override

    public void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }



    @Override

    public void onStop() {

        super.onStop();

        if (mAuthListener != null) {

            mAuth.removeAuthStateListener(mAuthListener);

        }

    }
}
