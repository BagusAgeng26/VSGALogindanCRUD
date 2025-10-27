package com.example.vsga_logindancrud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handler untuk delay 3 detik
        new Handler().postDelayed(() -> {
            // Ambil status login dari SharedPreferences
            SharedPreferences prefs = getSharedPreferences("USER_PREF", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

            Intent intent;
            if (isLoggedIn) {
                // Jika sudah login → langsung ke MainActivity
                intent = new Intent(SplashScreen.this, MainActivity.class);
            } else {
                // Jika belum login → ke LoginActivity
                intent = new Intent(SplashScreen.this, LoginActivity.class);
            }

            startActivity(intent);
            finish(); // Supaya tidak balik ke splash screen
        }, 3000);
    }
}
