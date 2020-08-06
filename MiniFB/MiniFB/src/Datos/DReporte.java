package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class DReporte {

    public LinkedList<String> generarReporte() {
        LinkedList<String> datos = new LinkedList<>();
        datos.add("Usuarios Registrados");
        datos.add(String.valueOf(getCountUsuario()));
        datos.add("Mensajes enviados");
        datos.add(String.valueOf(getCountMensaje()));
        datos.add("Solicitudes de amistad");
        datos.add(String.valueOf(getCountSolicitudes()));
        datos.add("Amistades entre usuarios");
        datos.add(String.valueOf(getCountAmistades()));
        return datos;
    }

    public LinkedList<String> listarEstadistica() {
        LinkedList<String> datos = new LinkedList<>();
        datos.add("Usuarios Hombres");
        float total = getCountUsuario();
        float a = getCountUsuarioHombre();
        float x = (a * 100) / total;
        datos.add(String.valueOf(x)+"%");
        datos.add("Usuarios Mujeres");
        a = getCountUsuarioMujer();
        x = (a * 100) / total;
        datos.add(String.valueOf(x)+"%");
        datos.add("Solicitudes Aceptadas");
        total = getCountAmistades() + getCountSolicitudes();
        a = getCountAmistades();
        x = (a * 100) / total;
        datos.add(String.valueOf(x)+"%");
        datos.add("Solicitudes Pendientes");
        a = getCountSolicitudes();
        x = (a * 100) / total;
        datos.add(String.valueOf(x)+"%");
        datos.add("Prom. de mensajes por chat");
        total = getCountMensaje();
        a = getCountChat();
        x = total/a;
        datos.add(String.valueOf(x));
        datos.add("Prom. de mensajes enviado");
        a = getCountUsuario();
        x = total/a;
        datos.add(String.valueOf(x));
        datos.add("Prom. de amigos por usuario");
        total = getCountAmistades()*2;
        a = getCountUsuario();
        x = total/a;
        datos.add(String.valueOf(x));
        return datos;
    }

    public int getCountChat() {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "select count(*) from public.chat where chat.cantidad_de_mensajes>0;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getCountMensaje() {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "select count(*) from public.mensaje;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getCountUsuario() {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "select count(*) from public.usuario;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getCountAmistades() {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "select count(*) from public.contacto;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getCountSolicitudes() {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "select count(*) from public.solicitud_de_amistad;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getCountUsuarioHombre() {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "select count(*) from public.usuario where usuario.sexo=true;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getCountUsuarioMujer() {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "select count(*) from public.usuario where usuario.sexo=false;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

}
