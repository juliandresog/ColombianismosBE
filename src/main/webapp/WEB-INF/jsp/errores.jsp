<%@page contentType="text/html" pageEncoding="ISO8859-15"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String fecha_actual = request.getAttribute("fecha_actual").toString();
    String mensaje = request.getAttribute("mensaje").toString();
%>

<h1>Sistema</h1>
<h2>Error</h2>

<br>
<table style="border: 1px solid #FFFFFF;">
    <tbody>
        <tr>
            <th>Fecha</th>
            <td><%=fecha_actual%></td>
        </tr>
        <tr>
            <th>Detalle error:</th>
            <td><%=mensaje%></td>
        </tr>
   </tbody>
</table>