package id.littlequery.tugaskelompok;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText username, passwords, token;
    private Button signin_butoon;
    Context mContext;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.email);
        passwords = findViewById(R.id.password);
        signin_butoon = findViewById(R.id.signin_button);
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        signin_butoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProcess();
            }
        });
        if(SharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
    private void loginProcess(){
        final String email = username.getText().toString().trim();
        final String password = passwords.getText().toString().trim();
        if(email.equals("pengguna") && password.equals("masuk")){
            Toast.makeText(mContext,"Login Berhasil",Toast.LENGTH_LONG).show();
            startActivity(new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }else{
            Toast.makeText(mContext,"Username Atau Password Salah",Toast.LENGTH_LONG).show();
        }
    }
}