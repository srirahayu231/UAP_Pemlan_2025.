package org.example;

public class Mahasiswa {
    public String nama;
    public String nim;
    public int nilai;
    public String status;

    public Mahasiswa(String nama, String nim, int nilai) {
        this.nama = nama;
        this.nim = nim;
        this.nilai = nilai;
        this.status = nilai >= 75 ? "LULUS" : "TIDAK LULUS";
    }
}
