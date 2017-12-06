package ipca.example.bimbyrecipes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText editTextEmail           ;
    EditText editTextPassword        ;
    EditText editTextPasswordRepeat  ;
    Button   buttonLoginRegister     ;
    CheckBox checkedRegister         ;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail          = findViewById(R.id.editTextEmail         );
        editTextPassword       = findViewById(R.id.editTextPassword      );
        editTextPasswordRepeat = findViewById(R.id.editTextPasswordRepeat);
        buttonLoginRegister    = findViewById(R.id.buttonLoginRegister   );
        checkedRegister        = findViewById(R.id.checkedRegister       );

        editTextPasswordRepeat.setVisibility(View.GONE);

        checkedRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedRegister.isChecked()){
                    editTextPasswordRepeat.setVisibility(View.VISIBLE);
                    buttonLoginRegister.setText("Register");
                }else{
                    editTextPasswordRepeat.setVisibility(View.GONE);
                    buttonLoginRegister.setText("Login");
                }
            }
        });

        buttonLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempLogin();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    void attempLogin(){

        if (checkedRegister.isChecked()){
            //Register
            if (editTextPassword.getText().toString()
                    .compareTo( editTextPasswordRepeat.getText().toString())!=0){
                Toast.makeText(this,"As passwords não coincidem!",
                        Toast.LENGTH_SHORT).show();
               return;
            }
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
                    editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });


        }else{
            //Login
            mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(),
                    editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }

    }
}
