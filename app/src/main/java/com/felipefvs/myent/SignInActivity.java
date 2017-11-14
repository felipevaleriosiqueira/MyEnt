package com.felipefvs.myent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felipefvs.myent.database.FirebaseInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button signIn = findViewById(R.id.mSignInButton);

        final EditText email = findViewById(R.id.mEmailEditText);
        final EditText password = findViewById(R.id.mPasswordEditText);

        firebaseAuth = FirebaseInterface.getFirebaseAuth();
        firebaseReference = FirebaseInterface.getFirebase();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if( task.isSuccessful() ) {

                                    String userId = firebaseAuth.getCurrentUser().getUid();

                                    firebaseReference.child("users").child(userId).child("email").setValue(email.getText().toString());

                                    Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Erro no cadastrado!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });

    }
}
