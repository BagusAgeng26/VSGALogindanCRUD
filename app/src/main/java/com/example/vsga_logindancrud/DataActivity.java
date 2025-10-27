package com.example.vsga_logindancrud;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    ListView listData;
    ArrayList<Run> runList; // simpan data objek Run
    ArrayAdapter<String> adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        listData = findViewById(R.id.listData);
        Button btnAddData = findViewById(R.id.btnAddData);

        dbHelper = new DatabaseHelper(this);
        runList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listData.setAdapter(adapter);

        loadData();

        // Tambah data
        btnAddData.setOnClickListener(v -> showCreateDialog());

        // Klik item → Edit / Hapus
        listData.setOnItemClickListener((parent, view, position, id) -> showOptionDialog(position));
    }

    // 🔹 Ambil data dari DB
    private void loadData() {
        runList.clear();
        runList.addAll(dbHelper.getAllRunRecords());

        ArrayList<String> displayList = new ArrayList<>();
        for (Run run : runList) {
            displayList.add(run.getJenisLari() + " - " + run.getJarak() + "m - " + run.getWaktu() + " menit");
        }

        adapter.clear();
        adapter.addAll(displayList);
        adapter.notifyDataSetChanged();
    }

    // 🔹 Popup Tambah
    private void showCreateDialog() {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_create, null);
        dialog.setContentView(view);

        EditText etJenisLari = view.findViewById(R.id.etJenisLari);
        EditText etJarak = view.findViewById(R.id.etJarak);
        EditText etWaktu = view.findViewById(R.id.etWaktu);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String jenis = etJenisLari.getText().toString();
            String jarak = etJarak.getText().toString();
            String waktu = etWaktu.getText().toString();

            if (jenis.isEmpty() || jarak.isEmpty() || waktu.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.addRunRecord(jenis,
                        Double.parseDouble(jarak),
                        Double.parseDouble(waktu));

                if (inserted) {
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    // 🔹 Popup Edit
    private void showEditDialog(int position) {
        Run run = runList.get(position);

        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_edit, null);
        dialog.setContentView(view);

        EditText etJenisLari = view.findViewById(R.id.etJenisLari);
        EditText etJarak = view.findViewById(R.id.etJarak);
        EditText etWaktu = view.findViewById(R.id.etWaktu);
        Button btnUpdate = view.findViewById(R.id.btnSave);

        etJenisLari.setText(run.getJenisLari());
        etJarak.setText(String.valueOf(run.getJarak()));
        etWaktu.setText(String.valueOf(run.getWaktu()));

        btnUpdate.setText("Update Data");
        btnUpdate.setOnClickListener(v -> {
            String jenis = etJenisLari.getText().toString();
            String jarak = etJarak.getText().toString();
            String waktu = etWaktu.getText().toString();

            if (jenis.isEmpty() || jarak.isEmpty() || waktu.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            } else {
                boolean updated = dbHelper.updateRunRecord(
                        run.getId(),
                        jenis,
                        Double.parseDouble(jarak),
                        Double.parseDouble(waktu));

                if (updated) {
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Gagal update data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    // 🔹 Popup Hapus
    private void showDeleteDialog(int position) {
        Run run = runList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus Data")
                .setMessage("Yakin ingin menghapus data ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteRunRecord(run.getId());
                    if (deleted) {
                        loadData();
                        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    // 🔹 Pilihan Aksi
    private void showOptionDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi")
                .setItems(new String[]{"Edit", "Hapus"}, (dialog, which) -> {
                    if (which == 0) {
                        showEditDialog(position);
                    } else {
                        showDeleteDialog(position);
                    }
                })
                .show();
    }
}
