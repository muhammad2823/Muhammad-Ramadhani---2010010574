package GUI;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class mainDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Connection aktif = Koneksi.geConnection();
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        Splash sp = new Splash();
        sp.setVisible(true);
        sp.pack();
        sp.setLocationRelativeTo(null);
        sp.setDefaultCloseOperation(Splash.EXIT_ON_CLOSE);
        try {
            String text = "Loading.";
            int syarat = 0;
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(50);
                sp.no.setText(i + "%");
                
                if (i % 4 == 0) {
                    if (syarat < 4) {
                        text += ".";
                        syarat++;
                    } else {
                        text = "Loading";
                        syarat = 0;
                    }
                    sp.kata.setText(text);   
                }
                if (i == 30) {
                    sp.nama.setText("Muhammad Ramadhani");
                }
                if (i == 45) {
                    sp.npm.setText("2010010574");
                }
                if (i == 60) {
                    sp.dari.setText("Pemrograman Berbasis Objek 2");
                }
                sp.loadingBar.setValue(i);                
            }
            sp.setVisible(false);
            sp.nextDB();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
}
