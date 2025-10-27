package com.example.vsga_logindancrud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnAbout = findViewById(R.id.btnAbout);

        // Pindah ke DataActivity
        btnAdd.setOnClickListener(v -> {
            startActivity(new android.content.Intent(MainActivity.this, DataActivity.class));
        });

        // Logout -> kembali ke LoginActivity
        btnLogout.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, LoginActivity.class);
            // clear history biar tombol back tidak bisa kembali ke MainActivity
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // 🔹 About pakai dialog custom dari layout activity_about.xml
        btnAbout.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.activity_about, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(view);
            builder.setPositiveButton("Tutup", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}
