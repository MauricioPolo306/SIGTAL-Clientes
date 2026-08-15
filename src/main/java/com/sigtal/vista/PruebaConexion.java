package com.sigtal.vista;

import java.sql.Connection;

import com.sigtal.conexion.ConexionBD;

public class PruebaConexion {

    public static void main(String[] args) {

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion != null) {
                System.out.println("Conexion exitosa con la base de datos SIGTAL.");
            }

        } catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}