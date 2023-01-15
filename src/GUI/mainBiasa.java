

package GUI;

import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class mainBiasa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Splash sp = new Splash();
        sp.setVisible(true);
        sp.pack();
        sp.setLocationRelativeTo(null);
        sp.setDefaultCloseOperation(Splash.EXIT_ON_CLOSE);
        try {
            String text = "Loading.";
            int syarat = 0;
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(100);
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
                if (i == 10) {
                    sp.nama.setText("Muhammad Ramadhani");
                }
                if (i == 40) {
                    sp.npm.setText("2010010574");
                }
                if (i == 70) {
                    sp.dari.setText("Universitas Islam Kalimantan Muhammad Arsyad Al Banjari Banjarmasin");
                }
                sp.loadingBar.setValue(i);                
            }
            sp.setVisible(false);
            sp.next();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
