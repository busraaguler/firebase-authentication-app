package com.example.firebaseproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText registerName;
    private EditText registerEmail;
    private EditText registerPassword;
    private TextView registerToLogin;
    private Button registerButton;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton=(Button) findViewById(R.id.register_button);
        registerEmail=(EditText) findViewById(R.id.register_email);
        registerName=(EditText) findViewById(R.id.register_name);
        registerPassword=(EditText) findViewById(R.id.register_password);
        registerToLogin=(TextView) findViewById(R.id.register_to_login);
        registerProgress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent =new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=registerName.getText().toString();
                String password=registerPassword.getText().toString();
                String email=registerEmail.getText().toString();

                if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){ //name,email,password boş değilse
                    registerProgress.setTitle("Kaydediliyor..");
                    registerProgress.setMessage("Hesabınızı oluşturuyoruz ,lütfen bekleyiniz...");
                    registerProgress.setCanceledOnTouchOutside(false);
                    registerProgress.show();
                    register_user(name,password,email);

                }
            }
        });
    }

    private void register_user(String name, String password, String email) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {           //başarılı ise mainActivity sayfasına geçiş sağlanır.
                    registerProgress.dismiss();      //registerProgress kapatıldı.
                    Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class); //registerActivity sayfasından MainActivity'e geçiş
                    startActivity(mainIntent);
                }
                else{
                    registerProgress.dismiss();   //başarılı değilse hata mesajıyla birlikte hata uyarısı verir.
                    Toast.makeText(getApplicationContext(),"hata: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}