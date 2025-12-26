# Aplikasi Kelulusan Mahasiswa (Java Swing)

Aplikasi ini digunakan untuk mengelola data mahasiswa dan menentukan status kelulusan berdasarkan nilai akhir. Aplikasi dibangun menggunakan Java Swing dengan fitur CRUD (Create, Read, Update, Delete) dan penyimpanan data ke file.

---

## 🎯 Fitur Aplikasi

- Tambah data mahasiswa
- Edit data mahasiswa
- Hapus data mahasiswa
- Menampilkan daftar data dalam tabel popup
- Menampilkan laporan jumlah mahasiswa lulus & tidak lulus
- Status kelulusan otomatis berdasarkan nilai
- Tampilan tabel berwarna:
    - Hijau → LULUS
    - Merah muda → TIDAK LULUS

---

## 🧩 Struktur Data Mahasiswa

Setiap data mahasiswa menyimpan:

- Nama
- NIM
- Nilai
- Status kelulusan (LULUS / TIDAK LULUS)

---

## 🖥 Cara Menjalankan Program

1. Buka project di IntelliJ / NetBeans / VS Code (Java)
2. Pastikan JDK minimal versi 8
3. Jalankan file:


4. Aplikasi akan tampil dalam bentuk GUI

---

## 🗂 Struktur Project (Sederhana)

/src
├── Main.java
├── Mahasiswa.java
├── FileManager.java


- `Main.java` → tampilan & logika aplikasi
- `Mahasiswa.java` → model data mahasiswa
- `FileManager.java` → penyimpanan & pembacaan data

---

## 💾 Penyimpanan Data

Data mahasiswa disimpan dalam file (misalnya `.txt` atau `.csv`) agar tetap tersimpan meskipun aplikasi ditutup.

---

## 🧪 Pengujian Aplikasi

Pengujian dilakukan secara manual sesuai skenario:
- Tambah data
- Edit data
- Hapus data
- Validasi input
- Cek penyimpanan file
- Cek tampilan tabel

(Rincian pengujian ada di laporan testing)

---

## 👨‍💻 Teknologi yang Digunakan

- Java
- Java Swing
- ArrayList
- File I/O (Serialization / Text Storage)

---

## 📌 Catatan Pengembangan

- Disarankan menggunakan Git & branching untuk pengembangan fitur
- Laporan testing & code review disertakan sebagai dokumentasi

---

## 📣 Kontributor
Nama Pengembang: **(Sri Rahayu 2024-116)**
Nama Pengembang: **(Nanda Andriani 2024-119)**
