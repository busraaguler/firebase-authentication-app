package com.example.firebaseproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private Toolbar mToolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

         if(item.getItemId()==R.id.action_signout){ //icona tıklanıldığında oturum kapatılır ve kullanıcı Login ekranına aktarılır.
             mauth.signOut();
             Toast.makeText(getApplicationContext(),"Oturum Kapatıldı.",Toast.LENGTH_SHORT).show();
             Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
             startActivity(loginIntent);
         }
         return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth=FirebaseAuth.getInstance();
        mToolbar=(Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Main Activity");

        if(mauth.getCurrentUser()==null){               //kullanıcı yoksa login sayfasına yönlendirilir.

            Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class); //MainActivity'den LoginActivity sayfasına gidilir.
            startActivity(loginIntent);                   //loginActivity sayfası başlatılır.
            Toast.makeText(getApplicationContext(),"Lütfen Giriş Yapınız",Toast.LENGTH_SHORT).show();
        }


    }
}