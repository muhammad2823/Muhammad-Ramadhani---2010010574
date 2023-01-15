/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class Koneksi {
    private static Connection koneksi;
    public static Connection geConnection() throws SQLException{
        String user="root";
        String pswd="";
        String host="localhost";
        String db="airlines";
        if (koneksi==null){
            //build koneksi disini
            try{
                Class.forName("com.mysql.jdbc.Driver");     
                String url="jdbc:mysql://"+ host +"/"+ db + "?&user=" + user + "&password="+ pswd;
                koneksi=DriverManager.getConnection(url);
            }catch(SQLException e){
                System.out.println("Koneksi gagal 1" + e.toString());
            }catch(ClassNotFoundException e) {
                System.out.println("jdbc.Driver tidak ditemukan");
            }
        }
        return koneksi;
    }
}
