package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {

    private ArrayList<Mahasiswa> list;
    private DefaultTableModel model;

    private JTextField txtNama, txtNIM, txtNilai;
    private JLabel lblLaporan;

    private int selectedIndex = -1;

    public Main() {
        setTitle("Aplikasi Kelulusan Mahasiswa");
        setSize(750, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        list = FileManager.load();

        model = new DefaultTableModel(
                new String[]{"Nama", "NIM", "Nilai", "Status"}, 0
        );

        JTabbedPane tab = new JTabbedPane();
        tab.add("Dashboard", dashboard());
        tab.add("Input Data", inputPanel());
        tab.add("Laporan", laporanPanel());

        add(tab);
    }

    // ================= DASHBOARD =================
    private JPanel dashboard() {
        JPanel p = new JPanel();
        JLabel lbl = new JLabel("SELAMAT DATANG DI APLIKASI KELULUSAN MAHASISWA");
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        p.add(lbl);
        return p;
    }

    // ================= INPUT DATA =================
    private JPanel inputPanel() {
        JPanel p = new JPanel(new GridLayout(8, 1, 10, 10));
        p.setBorder(new EmptyBorder(30, 60, 30, 60));

        txtNama = new JTextField();
        txtNIM = new JTextField();
        txtNilai = new JTextField();

        JButton btnTambah = createButton("Tambah Data", new Color(46, 204, 113));
        JButton btnUpdate = createButton("Update Data", new Color(243, 156, 18));
        JButton btnLihat = createButton("Lihat Data", new Color(52, 152, 219));

        btnTambah.addActionListener(e -> tambahData());
        btnUpdate.addActionListener(e -> updateData());
        btnLihat.addActionListener(e -> showTablePopup());

        p.add(new JLabel("Nama Mahasiswa"));
        p.add(txtNama);
        p.add(new JLabel("NIM"));
        p.add(txtNIM);
        p.add(new JLabel("Nilai Akhir"));
        p.add(txtNilai);
        p.add(btnTambah);
        p.add(btnUpdate);
        p.add(btnLihat);

        return p;
    }

    // ================= LAPORAN =================
    private JPanel laporanPanel() {
        JPanel p = new JPanel();
        lblLaporan = new JLabel();
        lblLaporan.setFont(new Font("Arial", Font.BOLD, 14));
        p.add(lblLaporan);
        refreshLaporan();
        return p;
    }

    // ================= CREATE =================
    private void tambahData() {
        try {
            Mahasiswa m = new Mahasiswa(
                    txtNama.getText(),
                    txtNIM.getText(),
                    Integer.parseInt(txtNilai.getText())
            );
            list.add(m);
            FileManager.save(list);
            clearField();
            refreshLaporan();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!");
        }
    }

    // ================= UPDATE =================
    private void updateData() {
        if (selectedIndex >= 0) {
            try {
                Mahasiswa m = new Mahasiswa(
                        txtNama.getText(),
                        txtNIM.getText(),
                        Integer.parseInt(txtNilai.getText())
                );
                list.set(selectedIndex, m);
                FileManager.save(list);
                selectedIndex = -1;
                clearField();
                refreshLaporan();
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Klik Edit pada tabel terlebih dahulu!");
        }
    }

    // ================= READ + DELETE =================
    private void showTablePopup() {
        JDialog dialog = new JDialog(this, "List Data Mahasiswa", true);
        dialog.setSize(800, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        model.setRowCount(0);
        for (Mahasiswa m : list) {
            model.addRow(new Object[]{m.nama, m.nim, m.nilai, m.status});
        }

        JTable table = new JTable(model);
        table.setRowHeight(28);
        setHeaderGreen(table);
        setRowColor(table);   // <<< warna baris

        JButton btnEdit = new JButton("Edit");
        btnEdit.setBackground(new Color(46, 204, 113));
        btnEdit.setForeground(Color.WHITE);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBackground(new Color(231, 76, 60));
        btnHapus.setForeground(Color.WHITE);

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                selectedIndex = row;
                txtNama.setText(model.getValueAt(row, 0).toString());
                txtNIM.setText(model.getValueAt(row, 1).toString());
                txtNilai.setText(model.getValueAt(row, 2).toString());
                dialog.dispose();
            }
        });

        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                list.remove(row);
                FileManager.save(list);
                model.removeRow(row);
                refreshLaporan();
            }
        });

        JPanel panelBtn = new JPanel();
        panelBtn.add(btnEdit);
        panelBtn.add(btnHapus);

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.add(panelBtn, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // ================= UTIL =================
    private void refreshLaporan() {
        long lulus = list.stream().filter(m -> m.status.equals("LULUS")).count();
        long tidakLulus = list.size() - lulus;

        lblLaporan.setText(
                "Jumlah Lulus: " + lulus +
                        " | Tidak Lulus: " + tidakLulus
        );
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    private void clearField() {
        txtNama.setText("");
        txtNIM.setText("");
        txtNilai.setText("");
    }

    // ========== WARNA HEADER ==========
    private void setHeaderGreen(JTable table) {
        DefaultTableCellRenderer header = new DefaultTableCellRenderer();
        header.setBackground(new Color(230, 180, 213));
        header.setForeground(Color.BLACK);
        header.setHorizontalAlignment(JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 13));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(header);
        }
    }

    // ========== WARNA BARIS ==========
    private void setRowColor(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String status = table.getValueAt(row, 3).toString();

                if (status.equals("LULUS")) {
                    c.setBackground(new Color(210, 226, 255));
                } else {
                    c.setBackground(new Color(220, 230, 255));
                }

                if (isSelected) {
                    c.setBackground(new Color(140, 146, 255));
                }

                return c;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
