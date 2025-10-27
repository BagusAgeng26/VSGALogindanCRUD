package com.example.vsga_logindancrud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    EditText editUserName, editPassword;
    Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi view
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.button);
        buttonRegister = findViewById(R.id.button2);

        // Tombol Login
        buttonLogin.setOnClickListener(v -> {
            String inputUser = editUserName.getText().toString().trim();
            String inputPass = editPassword.getText().toString().trim();

            if (inputUser.isEmpty() || inputPass.isEmpty()) {
                Toast.makeText(this, "Username dan password wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                FileInputStream fis = openFileInput("user.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line = reader.readLine();
                fis.close();

                if (line != null) {
                    String[] userData = line.split(";");

                    if (userData.length >= 2) {
                        String savedUser = userData[0];
                        String savedPass = userData[1];

                        if (inputUser.equals(savedUser) && inputPass.equals(savedPass)) {
                            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                            //  Simpan status login di SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("USER_PREF", MODE_PRIVATE);
                            prefs.edit()
                                    .putBoolean("isLoggedIn", true)
                                    .putString("username", inputUser)
                                    .apply();

                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Data user rusak. Silakan register ulang.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Belum ada data user. Silakan register dulu.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Tombol Register → pindah ke halaman registrasi
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
