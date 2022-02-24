package com.neelproject.ams;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    final int REQUEST_CODE_GALLERY = 999;

    private Button reg,imgbtn;
    private ImageView imagee;
    private TextView tvLogin;
    private EditText etEmail, etPass;
    private DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
        reg = (Button)findViewById(R.id.btnReg);
        imagee = (ImageView) findViewById(R.id.imagee);
        imgbtn =(Button) findViewById(R.id.imgbtn);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        reg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
                Intent gallery =
                        new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQUEST_CODE_GALLERY);
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnReg:
                register();
                break;
            case R.id.tvLogin:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
                break;
            default:

        }
    }

    private void register(){
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        byte[] NewEntryImg = imageviewbyte(imagee);
        if(email.isEmpty() && pass.isEmpty()){
            displayToast("Username/password field empty");
        }else{
            db.addUser(email,pass,NewEntryImg);
            displayToast("User registered");
            finish();
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        if (requestCode==REQUEST_CODE_GALLERY)
        {
            if(grantResults.length>0 & grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setType("/image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(RegisterActivity.this, "You dont have permissiom to access", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Uri uri = data.getData();
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imagee.setImageBitmap(bitmap);

        }
        catch (FileNotFoundException e)
        {
        e.printStackTrace();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }


    private byte[] imageviewbyte(ImageView imagee) {
        Bitmap bitmap = ((BitmapDrawable) imagee.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}