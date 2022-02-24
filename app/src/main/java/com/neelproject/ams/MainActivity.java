package com.neelproject.ams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    private ImageButton imageButton;
    private Session session;
    private TextView textView3;
    private Object ImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView3 = (TextView)findViewById(R.id.textView3);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            textView3.setText(bundle.getString("name"));
        }


        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
        /*ImageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });*/
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    public void onClicking(View view) {
        Intent intent = new Intent(MainActivity.this,punchin.class);
        startActivity(intent);
    }

    public void onout(View view) {
        Intent intent = new Intent(MainActivity.this,punchout.class);
        startActivity(intent);
    }

    public void logout(View view) {
        logout();
    }
}