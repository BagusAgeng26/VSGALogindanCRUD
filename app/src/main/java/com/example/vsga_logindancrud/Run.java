package com.example.vsga_logindancrud;

public class Run {
    private int id;
    private String jenisLari;
    private double jarak; // meter
    private double waktu; // menit

    public Run(int id, String jenisLari, double jarak, double waktu) {
        this.id = id;
        this.jenisLari = jenisLari;
        this.jarak = jarak;
        this.waktu = waktu;
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getJenisLari() { return jenisLari; }
    public double getJarak() { return jarak; }
    public double getWaktu() { return waktu; }

    public void setId(int id) { this.id = id; }
    public void setJenisLari(String jenisLari) { this.jenisLari = jenisLari; }
    public void setJarak(double jarak) { this.jarak = jarak; }
    public void setWaktu(double waktu) { this.waktu = waktu; }
}
