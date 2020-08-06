package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class DSolicitud {

    private int idUser;

    public DSolicitud() {
    }

    public DSolicitud(int idUser) {
        this.idUser = idUser;
    }

    //enviar solcitud de amistad
    public void insertar(int idAmigo) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "INSERT INTO public.solicitud_de_amistad("
                        + "id, solicitante, solicitado, fecha)"
                        + "VALUES (nextval('solicitud_de_amistad_seq'::regclass),'"
                        + getIdUser() + "','"
                        + idAmigo + "','"
                        + getDate() + "');";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //cancelar solicitud de amistad
    public void cancelar(int idAmigo) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "DELETE FROM public.solicitud_de_amistad"
                        + " WHERE "
                        + "solicitante='" + getIdUser()
                        + "' AND solicitado='" + idAmigo + "';";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //eliminar la solicitud cuando ya se acepta
    public void eliminar(int idSol) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "DELETE FROM public.solicitud_de_amistad"
                        + " WHERE "
                        + "id='" + idSol + "';";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //listar solicitudes recibidas
    public LinkedList listarRecibido() {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT U.id,U.nombres,U.apellidos"
                        + " FROM Public.Usuario U"
                        + " WHERE U.id in ("
                        + "SELECT S.solicitante"
                        + " FROM Public.solicitud_de_amistad S"
                        + " WHERE S.solicitado='" + getIdUser() + "');";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    for (int i = 1; i < 4; i++) {
                        datos.add(result.getString(i));
                    }
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    //listar solicitudes enviadas
    public LinkedList listarEnviado() {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT U.id,U.nombres,U.apellidos"
                        + " FROM Public.Usuario U"
                        + " WHERE U.id in ("
                        + "SELECT S.solicitado"
                        + " FROM Public.solicitud_de_amistad S"
                        + " WHERE S.solicitante='" + getIdUser() + "');";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    for (int i = 1; i < 4; i++) {
                        datos.add(result.getString(i));
                    }
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getIdSol(int idAmigo) {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT id"
                        + " FROM Public.solicitud_de_amistad "
                        + " WHERE solicitante='" + idAmigo + "'"
                        + " AND solicitado='" + getIdUser() + "';";
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

}
