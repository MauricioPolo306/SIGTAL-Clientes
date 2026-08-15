package com.sigtal.vista;

import java.io.IOException;
import java.util.List;

import com.sigtal.dao.ClienteDAO;
import com.sigtal.modelo.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ClienteDAO clienteDAO;

    @Override
    public void init() {
        clienteDAO = new ClienteDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {

            List<Cliente> clientes = clienteDAO.consultarClientes();

            request.setAttribute("clientes", clientes);

            request.getRequestDispatcher("/clientes.jsp")
                   .forward(request, response);

        } else if (accion.equals("editar")) {

            int idCliente = Integer.parseInt(
                    request.getParameter("idCliente")
            );

            Cliente cliente = clienteDAO.buscarCliente(idCliente);

            request.setAttribute("clienteEditar", cliente);

            List<Cliente> clientes = clienteDAO.consultarClientes();

            request.setAttribute("clientes", clientes);

            request.getRequestDispatcher("/clientes.jsp")
                   .forward(request, response);
        }
    
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {

            case "registrar":
                registrarCliente(request, response);
                break;

            case "actualizar":
                actualizarCliente(request, response);
                break;

            case "eliminar":
                eliminarCliente(request, response);
                break;

            default:
                response.sendRedirect("cliente?accion=listar");
                break;
        }
    }

    private void registrarCliente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        Cliente cliente = obtenerClienteDesdeFormulario(request);

        boolean registrado = clienteDAO.registrarCliente(cliente);

        if (registrado) {

            response.sendRedirect("cliente?accion=listar");

        } else {

            request.setAttribute(
                    "mensaje",
                    "No fue posible registrar el cliente."
            );

            request.getRequestDispatcher("/clientes.jsp")
                   .forward(request, response);
        }
    }
    private void actualizarCliente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int idCliente = Integer.parseInt(
                request.getParameter("idCliente")
        );

        Cliente cliente = obtenerClienteDesdeFormulario(request);

        cliente.setIdCliente(idCliente);

        clienteDAO.actualizarCliente(cliente);

        response.sendRedirect("cliente?accion=listar");
    }

    private void eliminarCliente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int idCliente = Integer.parseInt(
                request.getParameter("idCliente")
        );

        clienteDAO.eliminarCliente(idCliente);

        response.sendRedirect("cliente?accion=listar");
    }

    private Cliente obtenerClienteDesdeFormulario(
            HttpServletRequest request) {

        String nombre = request.getParameter("nombre");
        String documento = request.getParameter("documento");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");

        return new Cliente(
                nombre,
                documento,
                telefono,
                correo,
                direccion
        );
    }
}