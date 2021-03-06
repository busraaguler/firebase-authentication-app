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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private TextView loginToRegister;
    private ProgressDialog loginProgress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail=(EditText) findViewById(R.id.login_email);     //atama işlemleri yapılır.
        loginPassword=(EditText) findViewById(R.id.login_password);
        loginButton=(Button) findViewById(R.id.login_button);
        loginToRegister=(TextView) findViewById(R.id.login_need_account);
        loginProgress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Yeni Bir Hesaba İhtiyacım Var seçeneği seçildiğinde LoginActivity sayfasından RegisterActivity sayfasına geçiş sağlanır.
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=loginEmail.getText().toString();
                String password=loginPassword.getText().toString();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) ){  //email ve password boş değilse
                    loginProgress.setTitle("Oturum Açılıyor..");
                    loginProgress.setMessage("Hesabınıza Giriş Yapılıyor,Lütfen Bekleyiniz.");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    loginUser(email,password);

                }
            }
        });
    }

    private void loginUser(String email, String password) {  //daha önce giriş yapan kullanıcılar için mail ve password sorgulama
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){           //başarılı ise Giriş başarılı mesajı yazar ve LoginActivity 'den MainActivity sayfasına geçiş sağlanır.
                    loginProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                    Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                }
                else{
                    loginProgress.dismiss();       //başarılı değilse Giriş Yapılamadı mesajı verilir ve neden olan hatayı yazar.
                    Toast.makeText(getApplicationContext(),"Giriş Yapılamadı."+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}