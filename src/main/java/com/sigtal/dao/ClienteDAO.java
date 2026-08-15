package com.sigtal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sigtal.conexion.ConexionBD;
import com.sigtal.modelo.Cliente;

public class ClienteDAO {

    // Registrar cliente
    public boolean registrarCliente(Cliente cliente) {

        String sql = """
                INSERT INTO clientes
                (nombre, documento, telefono, correo, direccion)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getDocumento());
            sentencia.setString(3, cliente.getTelefono());
            sentencia.setString(4, cliente.getCorreo());
            sentencia.setString(5, cliente.getDireccion());

            return sentencia.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    // Consultar todos los clientes
    public List<Cliente> consultarClientes() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM clientes ORDER BY id_cliente";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(resultado.getInt("id_cliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setDocumento(resultado.getString("documento"));
                cliente.setTelefono(resultado.getString("telefono"));
                cliente.setCorreo(resultado.getString("correo"));
                cliente.setDireccion(resultado.getString("direccion"));

                clientes.add(cliente);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // Buscar cliente por ID
    public Cliente buscarCliente(int idCliente) {

        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (resultado.next()) {

                    Cliente cliente = new Cliente();

                    cliente.setIdCliente(resultado.getInt("id_cliente"));
                    cliente.setNombre(resultado.getString("nombre"));
                    cliente.setDocumento(resultado.getString("documento"));
                    cliente.setTelefono(resultado.getString("telefono"));
                    cliente.setCorreo(resultado.getString("correo"));
                    cliente.setDireccion(resultado.getString("direccion"));

                    return cliente;
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return null;
    }

    // Actualizar cliente
    public boolean actualizarCliente(Cliente cliente) {

        String sql = """
                UPDATE clientes
                SET nombre = ?,
                    documento = ?,
                    telefono = ?,
                    correo = ?,
                    direccion = ?
                WHERE id_cliente = ?
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getDocumento());
            sentencia.setString(3, cliente.getTelefono());
            sentencia.setString(4, cliente.getCorreo());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setInt(6, cliente.getIdCliente());

            return sentencia.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cliente
    public boolean eliminarCliente(int idCliente) {

        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idCliente);

            return sentencia.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}