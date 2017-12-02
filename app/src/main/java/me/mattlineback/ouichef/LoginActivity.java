package me.mattlineback.ouichef;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity ";

    @BindView(R2.id.email_sign_in_button)
    Button mPasswordLoginButton;
    @BindView(R2.id.email)
    EditText mEmailEditText;
    @BindView(R2.id.password)
    EditText mPasswordEditText;

    //FirebaseAuth object and listener for signin
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //mRegisterTextView.setOnClickListener(this);
        mPasswordLoginButton.setOnClickListener(this);

        //get shared instance of the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }
    private void signIn(String email, String password){
        if(!validateForm()){
            return;
        }
        //showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                            startActivity(intent);
                            //updateUI(user);
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if(!task.isSuccessful()){
                            //mStatusTextView.setText("Auth Failed");
                        }
                        //hideProgressDialog();


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
        updateUI(null);
}

    private void updateUI(FirebaseUser user) {
        if (user != null) {


        }else{
            mEmailEditText.setVisibility(View.VISIBLE);
            mPasswordEditText.setVisibility(View.VISIBLE);
            mPasswordLoginButton.setVisibility(View.VISIBLE);
        }
    }
        @Override
        public void onClick (View view){
            if (view == mPasswordLoginButton) {
                signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        }
    }


