package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class DMensaje {

    private int idChat;
    private int idEmisor;
    private int idReceptor;
    private String contenido;

    public DMensaje() {

    }

    public void insertar() {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "INSERT INTO public.mensaje("
                        + "id, emisor, receptor, fecha, hora, contenido, chat_id)"
                        + " VALUES (nextval('mensaje_seq'::regclass),'"
                        + getIdEmisor() + "','"
                        + getIdReceptor() + "','"
                        + getFecha() + "','"
                        + getHora() + "','"
                        + getContenido() + "','"
                        + getIdChat() + "');";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void modificar(int idMensaje) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "UPDATE public.mensaje "
                        + "SET contenido='" + getContenido() + "' "
                        + "WHERE id='" + idMensaje + "' "
                        + "AND emisor='" + getIdEmisor() + "';";
                connection.update(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void eliminar(int idMensaje) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "DELETE FROM public.mensaje "
                        + "WHERE id='" + idMensaje + "' "
                        + "AND emisor='" + getIdEmisor() + "';";
                connection.delete(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public LinkedList<String> listar() {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT id,emisor,receptor, contenido,hora,fecha FROM public.mensaje "
                        + "WHERE (emisor='" + getIdEmisor() + "' AND receptor='" + getIdReceptor() + "')"
                        + " OR (emisor='" + getIdReceptor() + "' AND receptor='" + getIdEmisor() + "')"
                        + " ORDER BY id;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    for (int i = 1; i < 7; i++) {
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

    public LinkedList<String> buscar(String busqueda) {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT id,emisor,receptor,contenido,hora,fecha FROM public.mensaje "
                        + "WHERE("
                        + " (emisor='" + getIdEmisor() + "' AND receptor='" + getIdReceptor() + "')"
                        + " OR (emisor='" + getIdReceptor() + "' AND receptor='" + getIdEmisor() + "')"
                        + ") AND (contenido LIKE '%" + busqueda + "%')"
                        + " ORDER BY id;";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    for (int i = 1; i < 7; i++) {
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

    public int getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
    }

    public int getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(int idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

    public String getHora() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String hora = time.format(formatter);
        return hora;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

}
