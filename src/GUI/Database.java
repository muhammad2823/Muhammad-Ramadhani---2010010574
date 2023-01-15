/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import javafx.geometry.VPos;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class Database extends javax.swing.JFrame {

    public String no_telepon;
    public String asal;
    public String ke;
    public String jmlh;
    public String kelaz;
    public String tanggal;
    public String bln;
    public String thn;
    public String waktu;
    
  

    public Database() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        dataTable.setAutoResizeMode(dataTable.AUTO_RESIZE_OFF);
        dataTable.setPreferredSize(null);
        dataTable.getColumnModel().getColumn(0).setPreferredWidth(88);
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(146);
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        dataTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        dataTable.getColumnModel().getColumn(4).setPreferredWidth(140);
        LoadData();
    }

    void LoadData() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setNumRows(0);//menghapus semua isi Jtabel
        try {
            Connection conn = Koneksi.geConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM pemesanan");

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getString("no_pemesanan");
                row[1] = rs.getString("Nama");
                row[2] = rs.getString("no_telepon");
                row[3] = rs.getString("dari");
                row[4] = rs.getString("tujuan");
                row[5] = rs.getString("jumlah_penumpang");
                row[6] = rs.getString("kelas");
                row[7] = rs.getString("tgl_berangkat");
                model.addRow(row);
            }

        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }

    public boolean isNIMExist(String id) {
        boolean nimExist = false;
        try {
            Connection conn = Koneksi.geConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM pemesanan where no_pemesanan ='" + id + "'");

            while (rs.next()) {
                nimExist = true;
            }

        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
        return nimExist;
    }

    void InsertData(String Nama, String no_telepon, String dari, String tujuan, String jumlah_penumpang, String kelas) {
        try {
            Random rand = new Random();
            String no_pemesanan = String.valueOf(rand.nextInt(1000));
            String tgl_berangkat = tgl.getValue().toString()+"-"+bln +"-"+thn;
            Connection conn = Koneksi.geConnection();
            PreparedStatement st = null;
            String sql = "insert into pemesanan (no_pemesanan, Nama, no_telepon, dari, tujuan, jumlah_penumpang, kelas, tgl_berangkat) Values (?,?,?,?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, no_pemesanan);
            st.setString(2, Nama);
            st.setString(3, no_telepon);
            st.setString(4, dari);
            st.setString(5, tujuan);
            st.setString(6, jumlah_penumpang);
            st.setString(7, kelas);
            st.setString(8, tgl_berangkat);
            if (isNIMExist(no_pemesanan)) {// memanggil fungsi isNimExist
                InsertData(Nama, no_telepon, dari, tujuan, jumlah_penumpang, kelas);
            } else {
                int intTambah = st.executeUpdate();
                if (intTambah > 0) {
                    JOptionPane.showMessageDialog(this, "Penambahan sukses");
                    LoadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Penambahan gagal");
                }
            }
        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }

    void updateData(String terpilih, String Nama, String no_telepon, String dari, String tujuan, String jumlah_penumpang, String kelas, String day, String month, String year) {
        try {
            String tgl_berangkat = tgl.getValue().toString()+"-"+bln +"-"+thn;
            Connection conn = Koneksi.geConnection();
            PreparedStatement st = null;
//            String sql = "update pemesanan set nama =?, paket_Data=?, provider_Kartu =?, no_handphone =? where Id_Pembelian=" + terpilih;
             String sql = "update pemesanan set no_pemesanan=?, Nama=?, no_telepon=?, dari=?, tujuan=?, jumlah_penumpang=?, kelas=?, tgl_berangkat=? where no_pemesanan=" + terpilih;

            st = conn.prepareStatement(sql);
            st.setString(1, terpilih);
            st.setString(2, Nama);
            st.setString(3, no_telepon);
            st.setString(4, dari);
            st.setString(5, tujuan);
            st.setString(6, jumlah_penumpang);
            st.setString(7, kelas);
//            st.setString(8, tgl_berangkat);
            LoadData();
        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }

    void deleteData(String terpilih) {
        try {
            Random rand = new Random();
            String no_pemesanan = String.valueOf(rand.nextInt(1000));
            Connection conn = Koneksi.geConnection();
            PreparedStatement st = null;
            String sql = "delete from pemesanan where no_pemesanan =?";
            st = conn.prepareStatement(sql);
            st.setString(1, terpilih);
            int intTambah = st.executeUpdate();
            JOptionPane.showMessageDialog(this, "Remove sukses");
            LoadData();

        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }

    void clear() {
        try {
            Connection conn = Koneksi.geConnection();
            PreparedStatement st = null;
            String sql = "delete from pemesanan";
            st = conn.prepareStatement(sql);
            st.executeUpdate();
            LoadData();
        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }

    }

    void reset() {
        nama.setText("");
        paketData.clearSelection();
        providerKartu.clearSelection();
        noHandphone.setText("");
        cari.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paketData = new javax.swing.ButtonGroup();
        providerKartu = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        cari = new javax.swing.JTextField();
        noHandphone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        clear = new javax.swing.JButton();
        remove = new javax.swing.JButton();
        update = new javax.swing.JButton();
        add = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        dari = new javax.swing.JComboBox<>();
        tujuan = new javax.swing.JComboBox<>();
        jumlah = new javax.swing.JSpinner();
        kelas = new javax.swing.JComboBox<>();
        bulan = new javax.swing.JComboBox<>();
        tahun = new javax.swing.JComboBox<>();
        tgl = new javax.swing.JSpinner();
        sudah = new javax.swing.JButton();
        total = new javax.swing.JTextField();
        minimize = new javax.swing.JPanel();
        close = new javax.swing.JPanel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(450, 400));
        setMinimumSize(new java.awt.Dimension(800, 550));
        setUndecorated(true);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 51));
        jLabel1.setText("Cari Data =");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(330, 190, 100, 18);

        nama.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 10)); // NOI18N
        getContentPane().add(nama);
        nama.setBounds(40, 175, 220, 30);

        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariKeyReleased(evt);
            }
        });
        getContentPane().add(cari);
        cari.setBounds(430, 190, 330, 22);

        noHandphone.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 10)); // NOI18N
        noHandphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noHandphoneActionPerformed(evt);
            }
        });
        getContentPane().add(noHandphone);
        noHandphone.setBounds(40, 225, 220, 30);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        dataTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "Telpon", "Dari", "Tujuan", "Banyak", "Kelas", "Berangkat"
            }
        ));
        dataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dataTable);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(280, 220, 510, 300);

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        getContentPane().add(clear);
        clear.setBounds(650, 140, 110, 40);

        remove.setText("Remove");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });
        getContentPane().add(remove);
        remove.setBounds(530, 140, 110, 40);

        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        getContentPane().add(update);
        update.setBounds(420, 140, 100, 40);

        add.setText("Add");
        add.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        add.setMaximumSize(new java.awt.Dimension(27, 18));
        add.setMinimumSize(new java.awt.Dimension(27, 18));
        add.setPreferredSize(new java.awt.Dimension(27, 18));
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        getContentPane().add(add);
        add.setBounds(300, 140, 110, 40);

        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/RefreshPng.png"))); // NOI18N
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        getContentPane().add(refresh);
        refresh.setBounds(280, 180, 40, 32);

        dari.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        dari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Banjarmasin", "Surabaya", "Bali", "Makasar", "Jakarta", "Medan" }));
        dari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dariActionPerformed(evt);
            }
        });
        getContentPane().add(dari);
        dari.setBounds(35, 270, 90, 25);

        tujuan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tujuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Banjarmasin", "Surabaya", "Bali", "Makasar", "Jakarta", "Medan" }));
        tujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tujuanActionPerformed(evt);
            }
        });
        getContentPane().add(tujuan);
        tujuan.setBounds(150, 270, 88, 25);
        getContentPane().add(jumlah);
        jumlah.setBounds(220, 300, 40, 30);

        kelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Economy", "Business", "First Class" }));
        kelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kelasActionPerformed(evt);
            }
        });
        getContentPane().add(kelas);
        kelas.setBounds(100, 325, 80, 30);

        bulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        bulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bulanActionPerformed(evt);
            }
        });
        getContentPane().add(bulan);
        bulan.setBounds(90, 390, 80, 30);

        tahun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024", "2025", "2026", "2027", "2028" }));
        tahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tahunActionPerformed(evt);
            }
        });
        getContentPane().add(tahun);
        tahun.setBounds(180, 390, 80, 30);
        getContentPane().add(tgl);
        tgl.setBounds(40, 390, 40, 30);

        sudah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sudah.setText("Cek Harga");
        sudah.setToolTipText("");
        sudah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sudahMouseClicked(evt);
            }
        });
        sudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sudahActionPerformed(evt);
            }
        });
        getContentPane().add(sudah);
        sudah.setBounds(100, 445, 100, 30);

        total.setFont(new java.awt.Font("Cambria Math", 0, 10)); // NOI18N
        total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalActionPerformed(evt);
            }
        });
        getContentPane().add(total);
        total.setBounds(80, 480, 190, 30);

        minimize.setOpaque(false);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        getContentPane().add(minimize);
        minimize.setBounds(730, 0, 30, 30);

        close.setOpaque(false);
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close);
        close.setBounds(770, 0, 30, 30);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/ui.png"))); // NOI18N
        getContentPane().add(Background);
        Background.setBounds(0, 0, 800, 550);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        int deleteItem = JOptionPane.showConfirmDialog(null, "Apa kau yakin ingin Menghapus semua data pada tabel dan database?", "WARNING", JOptionPane.YES_NO_OPTION);
        if (deleteItem == JOptionPane.YES_OPTION) {
            clear();
            reset();
        }

    }//GEN-LAST:event_clearActionPerformed

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();

        if (dataTable.getRowCount() >= 1) {
            if (dataTable.getSelectedRow() != -1) {
                int deleteItem = JOptionPane.showConfirmDialog(null, "Apa kau yakin ingin Menghapus baris Data yang Kamu pilih?", "WARNING", JOptionPane.YES_NO_OPTION);
                if (deleteItem == JOptionPane.YES_OPTION) {
                    String terpilih = model.getValueAt(dataTable.getSelectedRow(), 0).toString();
                    deleteData(terpilih);
                    reset();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Belum memilih row yang ingin di delete");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada Data pada Tabel");
        }

//        <<Tanpa Database>>
//        if (dataTable.getRowCount() >= 1) {
//            model.removeRow(dataTable.getSelectedRow());
//        } else {
//            if (dataTable.getRowCount() == 0) {
//                JOptionPane.showMessageDialog(null, "Belum memilih row yang ingin di delete");
//            } else {
//                JOptionPane.showMessageDialog(null, "Tolong pilih lah 1 baris yang ingin dihapus");
//            }
//        }
    }//GEN-LAST:event_removeActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
//        String terpilih = model.getValueAt(dataTable.getSelectedRow(), 0).toString();
//nama.getText(), noHandphone.getText(), asal, ke, jumlah.getValue().toString(), kelaz
        if (dataTable.getRowCount() >= 1) {
            if (dataTable.getSelectedRow() != -1) {
           
                if (nama.getText().trim().equals("") || asal == null || ke == null || jumlah.getValue().toString() == null || kelaz == null || noHandphone.getText().trim().equals("") || tgl == null ||  bln == null || thn ==null) {
                    JOptionPane.showMessageDialog(null, "Tolong isi dengan Lengkap");
                } else {
                    String terpilih = model.getValueAt(dataTable.getSelectedRow(), 0).toString(); 
                    updateData(terpilih, nama.getText(), noHandphone.getText(), asal, ke, jumlah.getValue().toString(),kelaz, tgl.getValue().toString(), bln, thn);

                    reset();
                    JOptionPane.showMessageDialog(null, "Update Berhasil");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Belum memilih row yang ingin di update");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada Data pada Tabel");
        }

//        if (dataTable.getRowCount() >= 1) {
//            model.setValueAt(nama.getText(), dataTable.getSelectedRow(), 1);
//            model.setValueAt(paketD, dataTable.getSelectedRow(), 2);
//            model.setValueAt(provider, dataTable.getSelectedRow(), 3);
//            model.setValueAt(noHandphone.getText(), dataTable.getSelectedRow(), 4);
//            reset();
//            JOptionPane.showMessageDialog(null, "Update Berhasil");
//
//        } else {
//            if (dataTable.getRowCount() == 0) {
//                JOptionPane.showMessageDialog(null, "Belum memilih row yang ingin di update");
//            } else {
//                JOptionPane.showMessageDialog(null, "Tolong pilih lah 1 baris yang ingin diupdate");
//            }
//        }
    }//GEN-LAST:event_updateActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        InsertData(nama.getText(), noHandphone.getText(), asal, ke, jumlah.getValue().toString(), kelaz);
        reset();
//        if (nama.getText().trim().equals("") || noHandphone.getText().trim().equals("") || asal == null || ke == null || jumlah.getValue().toString() == null || kelaz == null || waktu == null) {
//            JOptionPane.showMessageDialog(null, "Tolong isi dengan Lengkap");
//        } else {
//            InsertData(nama.getText(), noHandphone.getText(), asal, ke, jumlah.getValue().toString(), kelaz, waktu);
//            reset();
//        }


//        <<<Andaikan Tidak Memakai database>>
//        if (nama.getText().trim().equals("") || paketData.getSelection() == null || providerKartu.getSelection() == null || noHandphone.getText().trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Tolong isi dengan Lengkap");
//        } else {
//            model.addRow(new Object[]{nama.getText(), paketD, provider, noHandphone.getText()});
//            nama.setText("");
//            paketData.clearSelection();
//            paketD = null;
//            provider = null;
//            providerKartu.clearSelection();
//            noHandphone.setText("");
//        }
    }//GEN-LAST:event_addActionPerformed

    private void noHandphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noHandphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noHandphoneActionPerformed

    private void dataTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        String tNama = model.getValueAt(dataTable.getSelectedRow(), 1).toString();
        String hp = model.getValueAt(dataTable.getSelectedRow(), 2).toString();
        String from = model.getValueAt(dataTable.getSelectedRow(), 3).toString();
        String to = model.getValueAt(dataTable.getSelectedRow(), 4).toString();
        String  amount = model.getValueAt(dataTable.getSelectedRow(), 5).toString();
        String kls = model.getValueAt(dataTable.getSelectedRow(), 6).toString();
//        String hari = model.getValueAt(dataTable.getSelectedRow(), 7).toString();

        nama.setText(tNama);
        noHandphone.setText(hp);
                switch (from) {
            case "Banjarmasin": 
                dari.setSelectedItem(1);
                from = "Banjarmasin";
                break;
            case "Surabaya":
                dari.setSelectedItem(2);
                from = "Surabaya";
                break;
            case "Bali":
                dari.setSelectedItem(3);
                from = "Bali";
                break;
           case "Makasar":
                dari.setSelectedItem(4);
                from = "Makasar";
                break;
           case "Jakarta":
                dari.setSelectedItem(5);
                from = "Jakarta";
                break;
           case "Medan":
                dari.setSelectedItem(6);
                from = "Medan";
                break;
        }
        dari.setSelectedItem(from);
         switch (to) {
            case "Banjarmasin": 
                tujuan.setSelectedItem(1);
                to = "Banjarmasin";
                break;
            case "Surabaya":
                tujuan.setSelectedItem(2);
                to = "Surabaya";
                break;
            case "Bali":
                tujuan.setSelectedItem(3);
                to = "Bali";
                break;
           case "Makasar":
                tujuan.setSelectedItem(4);
                to = "Makasar";
                break;
           case "Jakarta":
                tujuan.setSelectedItem(5);
                to = "Jakarta";
                break;
           case "Medan":
                tujuan.setSelectedItem(6);
                to = "Medan";
                break;
         }
        tujuan.setSelectedItem(to);
        int value = Integer.parseInt(amount);
        jumlah.setValue(value);
        
        switch (kelas.toString()) {
            case "Economy": 
                kelas.setSelectedItem(1);
                kls = "Economy";
                break;
            case "Business":
                kelas.setSelectedItem(2);
                kls = "Business";
                break;
            case "First Class":
                kelas.setSelectedItem(3);
                kls = "First Class";
                break;
        }
        kelas.setSelectedItem(kls);


        

//        tgl.setValue(Integer.parseInt(tanggal));
//        
//                switch (asal) {
//            case "Banjarmasin": 
//                dari.setSelectedItem(1);
//                asal = "Banjarmasin";
//                break;
//            case "Surabaya":
//                dari.setSelectedItem(2);
//                asal = "Surabaya";
//                break;
//            case "Bali":
//                dari.setSelectedItem(3);
//                asal = "Bali";
//                break;
//           case "Makasar":
//                dari.setSelectedItem(4);
//                asal = "Makasar";
//                break;
//           case "Jakarta":
//                dari.setSelectedItem(5);
//                asal = "Jakarta";
//                break;
//           case "Medan":
//                dari.setSelectedItem(6);
//                asal = "Medan";
//                break;
//        }
//         switch (bln) {
//            case "Januari": 
//                bulan.setSelectedItem(1);
//                bln = "Januari";
//                break;
//            case "Februari":
//                bulan.setSelectedItem(2);
//                bln = "Februari";
//                break;
//            case "Maret":
//                bulan.setSelectedItem(3);
//                bln = "Maret";
//                break;
//           case "April":
//                bulan.setSelectedItem(4);
//                bln = "April";
//                break;
//           case "Mei":
//                bulan.setSelectedItem(5);
//                bln = "Mei";
//                break;
//           case "Juni":
//                bulan.setSelectedItem(6);
//                bln = "Juni";
//                break;
//            case "Juli": 
//                bulan.setSelectedItem(7);
//                bln = "Juli";
//                break;
//            case "Agustus":
//                bulan.setSelectedItem(8);
//                bln = "Agustus";
//                break;
//            case "September":
//                bulan.setSelectedItem(9);
//                bln = "September";
//                break;
//           case "Oktober":
//                bulan.setSelectedItem(10);
//                bln = "Oktober";
//                break;
//           case "November":
//                bulan.setSelectedItem(11);
//                bln = "November";
//                break;
//           case "Desember":
//                bulan.setSelectedItem(12);
//                bln = "Desember";
//                break;
//         }
//                  switch (thn) {
//            case "2023": 
//                tahun.setSelectedItem(1);
//                thn = "2023";
//                break;
//            case "2024":
//                tahun.setSelectedItem(2);
//                thn = "2024";
//                break;
//            case "2025":
//                tahun.setSelectedItem(3);
//                thn = "2025";
//                break;
//           case "2026":
//                tahun.setSelectedItem(4);
//                thn = "2026";
//                break;
//           case "2027":
//                tahun.setSelectedItem(5);
//                thn = "2027";
//                break;
//           case "2028":
//                tahun.setSelectedItem(6);
//                thn = "2028";
//                break;
//                  }
    }//GEN-LAST:event_dataTableMouseClicked

    private void cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariKeyReleased
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setNumRows(0);
        if (cari.getText().equals("")) {
            LoadData();
        } else {
            try {
                Connection conn = Koneksi.geConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM pemesanan where Nama like '%" + cari.getText() + "%'");
                while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getString("no_pemesanan");
                row[1] = rs.getString("Nama");
                row[2] = rs.getString("no_telepon");
                row[3] = rs.getString("dari");
                row[4] = rs.getString("tujuan");
                row[5] = rs.getString("jumlah_penumpang");
                row[6] = rs.getString("kelas");
                row[7] = rs.getString("tgl_berangkat");
                model.addRow(row);
                }
            } catch (SQLException e) {
                System.out.println("Koneksi Gagal " + e);
            }

        }
    }//GEN-LAST:event_cariKeyReleased

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        LoadData();
        reset();
    }//GEN-LAST:event_refreshActionPerformed

    private void bulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bulanActionPerformed
       bln = bulan.getSelectedItem().toString();
    }//GEN-LAST:event_bulanActionPerformed

    private void sudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sudahActionPerformed
        int harga;
        int bnyk = (int) jumlah.getValue();
        switch (ke) {
            case "Banjarmasin": 
                harga = 450000;
                total.setText("Rp"+ harga );

                break;
            case "Surabaya":
                harga = 500000;
                total.setText("Rp" + harga*bnyk);
                break;
            case "Bali":
                harga = 800000;
                total.setText("Rp"+ harga*bnyk );
                break;
           case "Makasar":
                harga = 600000;
                total.setText("Rp"+ harga*bnyk );
                break;
           case "Jakarta":
                harga = 400000;
                total.setText("Rp"+ harga*bnyk);

                break;
           case "Medan":
                harga = 500000;
                total.setText("Rp"+ harga*bnyk );
                break;
         }
    }//GEN-LAST:event_sudahActionPerformed

    private void sudahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sudahMouseClicked
       
    }//GEN-LAST:event_sudahMouseClicked

    private void dariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dariActionPerformed
        asal = dari.getSelectedItem().toString();
    }//GEN-LAST:event_dariActionPerformed

    private void tujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tujuanActionPerformed
       ke = tujuan.getSelectedItem().toString();
    }//GEN-LAST:event_tujuanActionPerformed

    private void kelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kelasActionPerformed
       kelaz = kelas.getSelectedItem().toString();
    }//GEN-LAST:event_kelasActionPerformed

    private void tahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tahunActionPerformed
       thn = tahun.getSelectedItem().toString();
    }//GEN-LAST:event_tahunActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariActionPerformed

    private void totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalActionPerformed

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        this.setState(1);
    }//GEN-LAST:event_minimizeMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    /**
     * @param args the command line arguments
     */
  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       

        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Database.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Database.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Database.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Database.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Database().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JButton add;
    private javax.swing.JComboBox<String> bulan;
    private javax.swing.JTextField cari;
    private javax.swing.JButton clear;
    private javax.swing.JPanel close;
    private javax.swing.JComboBox<String> dari;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jumlah;
    private javax.swing.JComboBox<String> kelas;
    private javax.swing.JPanel minimize;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField noHandphone;
    private javax.swing.ButtonGroup paketData;
    private javax.swing.ButtonGroup providerKartu;
    private javax.swing.JButton refresh;
    private javax.swing.JButton remove;
    private javax.swing.JButton sudah;
    private javax.swing.JComboBox<String> tahun;
    private javax.swing.JSpinner tgl;
    private javax.swing.JTextField total;
    private javax.swing.JComboBox<String> tujuan;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
