package com.zhuhaega.gradeapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Deklarasi variabel untuk input dan output
    EditText etSemester, etNim, etNama, etPresensi, etTugas, etUts, etUas;
    RadioGroup rgSemester;
    Button btnHitung;
    TextView tvResult;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        etSemester = findViewById(R.id.rgSemester);
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etPresensi = findViewById(R.id.etPresensi);
        etTugas = findViewById(R.id.etTugas);
        etUts = findViewById(R.id.etUts);
        etUas = findViewById(R.id.etUas);
        rgSemester = findViewById(R.id.rgSemester);
        btnHitung = findViewById(R.id.btnHitung);
        tvResult = findViewById(R.id.tvResult);

        // Set listener untuk tombol Hitung
        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validasi input
                if (isInputValid()) {
                    // Ambil nilai dari input
                    String semester = getSelectedSemester();
                    String nim = etNim.getText().toString();
                    String nama = etNama.getText().toString();
                    int presensi = Integer.parseInt(etPresensi.getText().toString());
                    int tugas = Integer.parseInt(etTugas.getText().toString());
                    int uts = Integer.parseInt(etUts.getText().toString());
                    int uas = Integer.parseInt(etUas.getText().toString());

                    // Hitung nilai akhir
                    double nilaiAkhir = calculateFinalGrade(presensi, tugas, uts, uas);

                    // Tentukan grade
                    String grade = determineGrade(nilaiAkhir);

                    // Tampilkan hasil di TextView
                    displayResult(nim, nama, semester, nilaiAkhir, grade);
                }
            }
        });
    }

    // Validasi input untuk memastikan semua field diisi dan dalam rentang yang benar
    private boolean isInputValid() {
        if (etSemester.getText().toString().isEmpty() ||
                etNim.getText().toString().isEmpty() ||
                etNama.getText().toString().isEmpty() ||
                etPresensi.getText().toString().isEmpty() ||
                etTugas.getText().toString().isEmpty() ||
                etUts.getText().toString().isEmpty() ||
                etUas.getText().toString().isEmpty()) {
            Toast.makeText(this, "Seluruh data wajib diisi", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int presensi = Integer.parseInt(etPresensi.getText().toString());
            int tugas = Integer.parseInt(etTugas.getText().toString());
            int uts = Integer.parseInt(etUts.getText().toString());
            int uas = Integer.parseInt(etUas.getText().toString());

            // Validasi nilai agar berada dalam rentang 10 - 100
            if (presensi < 10 || presensi > 100 || tugas < 10 || tugas > 100 ||
                    uts < 10 || uts > 100 || uas < 10 || uas > 100) {
                Toast.makeText(this, "Nilai tidak boleh lebih kecil dari 10 dan tidak boleh lebih besar dari 100", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Nilai harus berupa angka", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Mendapatkan semester yang dipilih
    private String getSelectedSemester() {
        int selectedId = rgSemester.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : "Tidak dipilih";
    }

    // Menghitung nilai akhir berdasarkan bobot
    private double calculateFinalGrade(int presensi, int tugas, int uts, int uas) {
        double presensiWeight = 0.1;  // Bobot 10% untuk presensi
        double tugasWeight = 0.2;     // Bobot 20% untuk tugas
        double utsWeight = 0.3;       // Bobot 30% untuk UTS
        double uasWeight = 0.4;       // Bobot 40% untuk UAS

        return (presensi * presensiWeight) +
                (tugas * tugasWeight) +
                (uts * utsWeight) +
                (uas * uasWeight);
    }

    // Menentukan grade berdasarkan nilai akhir
    private String determineGrade(double finalGrade) {
        if (finalGrade >= 85) {
            return "A";
        } else if (finalGrade >= 70) {
            return "B";
        } else if (finalGrade >= 55) {
            return "C";
        } else if (finalGrade >= 40) {
            return "D";
        } else {
            return "E";
        }
    }

    // Menampilkan hasil di TextView
    private void displayResult(String nim, String nama, String semester, double nilaiAkhir, String grade) {
        String result = "NIM: " + nim + "\n" +
                "Nama: " + nama + "\n" +
                "Semester: " + semester + "\n" +
                "Nilai Akhir: " + nilaiAkhir + "\n" +
                "Grade: " + grade;
        tvResult.setText(result);
    }
}