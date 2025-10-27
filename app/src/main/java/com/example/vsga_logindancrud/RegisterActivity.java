package com.example.vsga_logindancrud;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity {

    EditText editUsername, editPassword, editEmail, editNamaLengkap, editAlamat;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi komponen
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editEmail = findViewById(R.id.editEmail);
        editNamaLengkap = findViewById(R.id.editNamaLengkap);
        editAlamat = findViewById(R.id.editAlamat);
        btnRegister = findViewById(R.id.btnRegister);

        // Saat tombol daftar ditekan
        btnRegister.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String nama = editNamaLengkap.getText().toString().trim();
            String alamat = editAlamat.getText().toString().trim();

            // Validasi
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() ||
                    nama.isEmpty() || alamat.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan ke file
            String data = username + ";" + password + ";" + email + ";" + nama + ";" + alamat;

            try {
                FileOutputStream fos = openFileOutput("user.txt", MODE_PRIVATE);
                fos.write(data.getBytes());
                fos.close();
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                finish(); // kembali ke LoginActivity
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
