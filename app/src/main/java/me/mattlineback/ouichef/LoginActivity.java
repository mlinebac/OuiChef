package me.mattlineback.ouichef;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matt Lineback
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity ";
    private FirebaseAuth mAuth;

    @BindView(R2.id.email_sign_in_button) Button mPasswordLoginButton;
    @BindView(R2.id.email) EditText mEmailEditText;
    @BindView(R2.id.password) EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPasswordLoginButton.setOnClickListener(this);

        //get shared instance of the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    private void signIn(String email, String password){
        if(!validateForm()){
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }//end sign in with email

   private boolean validateForm(){
        boolean valid = true;

        String email = mEmailEditText.getText().toString();
        if(TextUtils.isEmpty(email)){
            mEmailEditText.setError("Required.");
            valid = false;
        }else{
            mEmailEditText.setError(null);
        }
        String password = mPasswordEditText.getText().toString();
       if(TextUtils.isEmpty(password)){
           mPasswordEditText.setError("Required.");
           valid = false;
       }else{
           mPasswordEditText.setError(null);
       }
       return valid;
   }

    private void signOut() {
        mAuth.signOut();
}

        @Override
        public void onClick (View view){
            if (view == mPasswordLoginButton) {
                signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        }
    }


