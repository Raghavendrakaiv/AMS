package com.neelproject.ams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
//    private Button  register;
    private ImageView login;
    private TextView register;
    private EditText etEmail, etPass;
    private DbHelper db;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbHelper(this);
        session = new Session(this);
        login = (ImageView) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.btnReg);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        if(session.loggedin()){
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            String s = etEmail.getText().toString();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("name",s);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnReg:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            default:

        }
    }

    private void login(){
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        if(db.getUser(email,pass)){
            session.setLoggedin(true);
            String s = etEmail.getText().toString();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("name",s);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Wrong Username/password",Toast.LENGTH_SHORT).show();
        }
    }
}