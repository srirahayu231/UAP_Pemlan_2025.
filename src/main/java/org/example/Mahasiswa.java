package org.example;

public class Mahasiswa {

    private String nama;
    private String nim;
    private int nilai;
    private String status;

    public Mahasiswa(String nama, String nim, int nilai) {
        this.nama = nama;
        this.nim = nim;
        this.nilai = nilai;
        this.status = nilai >= 75 ? "LULUS" : "TIDAK LULUS";
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public int getNilai() {
        return nilai;
    }

    public String getStatus() {
        return status;
    }
}