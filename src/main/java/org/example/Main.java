package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {

    private ArrayList<Mahasiswa> list = new ArrayList<>();
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtNama, txtNIM, txtNilai, txtSearch;
    private JLabel lblLaporan;
    private int selectedIndex = -1;

    public Main() {
        setTitle("Aplikasi Kelulusan Mahasiswa");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel(
                new String[]{"Nama", "NIM", "Nilai", "Status"}, 0
        );

        JTabbedPane tab = new JTabbedPane();
        tab.add("Dashboard", dashboardPanel());
        tab.add("List Data", listDataPanel());
        tab.add("Input Data", inputPanel());
        tab.add("Laporan", laporanPanel());

        add(tab);
    }

    // ================= DASHBOARD =================
    private JPanel dashboardPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("APLIKASI KELULUSAN MAHASISWA", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    // ================= LIST DATA =================
    private JPanel listDataPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        table = new JTable(model);
        table.setRowHeight(28);
        setHeaderStyle(table);

        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        txtSearch = new JTextField();
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }

            private void search() {
                sorter.setRowFilter(RowFilter.regexFilter(
                        "(?i)" + txtSearch.getText()
                ));
            }
        });

        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");

        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Cari: "), BorderLayout.WEST);
        top.add(txtSearch, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(btnEdit);
        bottom.add(btnHapus);

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);

        return p;
    }

    // ================= INPUT DATA =================
    private JPanel inputPanel() {
        JPanel p = new JPanel(new GridLayout(8, 1, 10, 10));
        p.setBorder(new EmptyBorder(20, 80, 20, 80));

        txtNama = new JTextField();
        txtNIM = new JTextField();
        txtNilai = new JTextField();

        JButton btnTambah = new JButton("Simpan Data");
        JButton btnUpdate = new JButton("Update Data");

        btnTambah.addActionListener(e -> tambahData());
        btnUpdate.addActionListener(e -> updateData());

        p.add(new JLabel("Nama Mahasiswa"));
        p.add(txtNama);
        p.add(new JLabel("NIM"));
        p.add(txtNIM);
        p.add(new JLabel("Nilai"));
        p.add(txtNilai);
        p.add(btnTambah);
        p.add(btnUpdate);

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

    // ================= CRUD =================
    private void tambahData() {
        try {
            String nama = txtNama.getText();
            String nim = txtNIM.getText();
            int nilai = Integer.parseInt(txtNilai.getText());

            if (!nama.matches("[a-zA-Z ]+") || !nim.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!");
                return;
            }

            Mahasiswa m = new Mahasiswa(nama, nim, nilai);
            list.add(m);
            model.addRow(new Object[]{
                    m.getNama(), m.getNim(), m.getNilai(), m.getStatus()
            });
            clearField();
            refreshLaporan();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nilai harus angka!");
        }
    }

    private void editData() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedIndex = table.convertRowIndexToModel(row);
            txtNama.setText(model.getValueAt(selectedIndex, 0).toString());
            txtNIM.setText(model.getValueAt(selectedIndex, 1).toString());
            txtNilai.setText(model.getValueAt(selectedIndex, 2).toString());
        }
    }

    private void updateData() {
        if (selectedIndex >= 0) {
            int nilai = Integer.parseInt(txtNilai.getText());
            Mahasiswa m = new Mahasiswa(
                    txtNama.getText(),
                    txtNIM.getText(),
                    nilai
            );
            list.set(selectedIndex, m);
            model.setValueAt(m.getNama(), selectedIndex, 0);
            model.setValueAt(m.getNim(), selectedIndex, 1);
            model.setValueAt(m.getNilai(), selectedIndex, 2);
            model.setValueAt(m.getStatus(), selectedIndex, 3);
            selectedIndex = -1;
            clearField();
            refreshLaporan();
        }
    }

    private void hapusData() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int idx = table.convertRowIndexToModel(row);
            list.remove(idx);
            model.removeRow(idx);
            refreshLaporan();
        }
    }

    // ================= UTIL =================
    private void refreshLaporan() {
        long lulus = list.stream()
                .filter(m -> m.getStatus().equals("LULUS"))
                .count();
        lblLaporan.setText(
                "Total Data: " + list.size() +
                        " | Lulus: " + lulus +
                        " | Tidak Lulus: " + (list.size() - lulus)
        );
    }

    private void clearField() {
        txtNama.setText("");
        txtNIM.setText("");
        txtNilai.setText("");
    }

    private void setHeaderStyle(JTable table) {
        DefaultTableCellRenderer h = new DefaultTableCellRenderer();
        h.setHorizontalAlignment(JLabel.CENTER);
        h.setBackground(new Color(180, 230, 180));
        h.setFont(new Font("Arial", Font.BOLD, 13));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(h);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}