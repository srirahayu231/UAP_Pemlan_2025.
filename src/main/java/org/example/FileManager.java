package org.example;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "data_kelulusan.csv";

    public static void save(ArrayList<Mahasiswa> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Mahasiswa m : list) {
                pw.println(
                        m.getNama() + "," +
                                m.getNim() + "," +
                                m.getNilai()
                );
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data!");
        }
    }

    public static ArrayList<Mahasiswa> load() {
        ArrayList<Mahasiswa> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                list.add(new Mahasiswa(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2])
                ));
            }
        } catch (IOException e) {
            // File belum ada → tidak error
        }

        return list;
    }
}