<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sigtal.modelo.Cliente" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">

    <title>SIGTAL - Clientes</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f4f6f8;
        }

        h1 {
            color: #1f2937;
        }

        .formulario {
            background: white;
            padding: 20px;
            margin-bottom: 30px;
            border-radius: 8px;
        }

        input {
            padding: 8px;
            margin: 5px;
            width: 220px;
        }

        button {
            padding: 9px 15px;
            margin: 5px;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th,
        td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #1f2937;
            color: white;
        }

    </style>
</head>

<body>

<h1>SIGTAL - Gestión de Clientes</h1>

<div class="formulario">

<%
    Cliente clienteEditar =
        (Cliente) request.getAttribute("clienteEditar");
%>

<h2>
    <%= clienteEditar == null
        ? "Registrar cliente"
        : "Actualizar cliente" %>
</h2>

<form action="cliente" method="post">

    <input type="hidden"
           name="accion"
           value="<%= clienteEditar == null
                   ? "registrar"
                   : "actualizar" %>">

    <% if (clienteEditar != null) { %>

        <input type="hidden"
               name="idCliente"
               value="<%= clienteEditar.getIdCliente() %>">

    <% } %>

    <input type="text"
           name="nombre"
           placeholder="Nombre completo"
           value="<%= clienteEditar != null
                   ? clienteEditar.getNombre()
                   : "" %>"
           required>

    <input type="text"
           name="documento"
           placeholder="Documento"
           value="<%= clienteEditar != null
                   ? clienteEditar.getDocumento()
                   : "" %>"
           required>

    <input type="text"
           name="telefono"
           placeholder="Teléfono"
           value="<%= clienteEditar != null
                   ? clienteEditar.getTelefono()
                   : "" %>">

    <input type="email"
           name="correo"
           placeholder="Correo"
           value="<%= clienteEditar != null
                   ? clienteEditar.getCorreo()
                   : "" %>">

    <input type="text"
           name="direccion"
           placeholder="Dirección"
           value="<%= clienteEditar != null
                   ? clienteEditar.getDireccion()
                   : "" %>">

    <br>

    <button type="submit">

        <%= clienteEditar == null
            ? "Registrar cliente"
            : "Actualizar cliente" %>

    </button>

</form>

</div>

<h2>Clientes registrados</h2>

<table>

    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Documento</th>
        <th>Teléfono</th>
        <th>Correo</th>
        <th>Dirección</th>
        <th>Acciones</th>
    </tr>

<%
    List<Cliente> clientes =
        (List<Cliente>) request.getAttribute("clientes");

    if (clientes != null) {

        for (Cliente cliente : clientes) {
%>

    <tr>

        <td>
            <%= cliente.getIdCliente() %>
        </td>

        <td>
            <%= cliente.getNombre() %>
        </td>

        <td>
            <%= cliente.getDocumento() %>
        </td>

        <td>
            <%= cliente.getTelefono() %>
        </td>

        <td>
            <%= cliente.getCorreo() %>
        </td>

        <td>
            <%= cliente.getDireccion() %>
        </td>

      <td>

    <form action="cliente" method="get" style="display:inline;">

        <input type="hidden"
               name="accion"
               value="editar">

        <input type="hidden"
               name="idCliente"
               value="<%= cliente.getIdCliente() %>">

        <button type="submit">
            Editar
        </button>

    </form>

    <form action="cliente" method="post" style="display:inline;">

        <input type="hidden"
               name="accion"
               value="eliminar">

        <input type="hidden"
               name="idCliente"
               value="<%= cliente.getIdCliente() %>">

        <button type="submit">
            Eliminar
        </button>

    </form>

</td>
<%
        }

    }
%>

</table>

</body>
</html>