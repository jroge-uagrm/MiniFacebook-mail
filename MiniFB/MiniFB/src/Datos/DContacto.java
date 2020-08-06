package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class DContacto {
//MANEJO DE LA TABLA CONTACTO

    private int idUser;

    public DContacto() {
    }

    public DContacto(int idUser) {
        this.idUser = idUser;
    }

    //aceptar amigo
    public void insertar(int idAmigo) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "INSERT INTO public.contacto("
                        + "contacto_a_id, contacto_b_id)"
                        + "VALUES ('"
                        + getIdUser() + "','"
                        + idAmigo + "')";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //eliminar amigo
    public void eliminar(int idAmigo) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "DELETE FROM public.contacto"
                        + " WHERE "
                        + "(contacto_a_id='" + getIdUser() + "' AND contacto_b_id='" + idAmigo + "')"
                        + " OR "
                        + "(contacto_a_id='" + idAmigo + "' AND contacto_b_id='" + getIdUser() + "');";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //listar amigos
    public LinkedList<String> listar() {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT U.id,U.nombres,U.apellidos, U.celular, U.correo"
                        + " FROM Public.Usuario U"
                        + " WHERE U.id in ("
                        + "SELECT contacto_b_id"
                        + " FROM Public.contacto C"
                        + " WHERE contacto_a_id='" + getIdUser() + "')"
                        + " OR U.id in ("
                        + "SELECT contacto_a_id"
                        + " FROM Public.contacto C"
                        + " WHERE contacto_b_id ='" + getIdUser() + "');";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    for (int i = 1; i < 6; i++) {
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

    //buscar amigos
    public LinkedList<String> buscar(String busqueda) {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT * FROM public.usuario "
                        + "WHERE nombres LIKE '%" + busqueda + "%'"
                        + " OR apellidos LIKE '%" + busqueda + "%'"
                        + " OR celular LIKE '%" + busqueda + "%'"
                        + " OR correo LIKE '%" + busqueda + "%';";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    String cid = result.getString("id");
                    datos.add(cid);
                    String cnombres = result.getString("nombres");
                    datos.add(cnombres);
                    String capellidos = result.getString("apellidos");
                    datos.add(capellidos);
                    String ccelular = result.getString("celular");
                    datos.add(ccelular);
                    String ccorreo = result.getString("correo");
                    datos.add(ccorreo);
                    String csexo = result.getString("sexo");
                    if (csexo.equals("t")) {
                        datos.add("M");
                    } else {
                        datos.add("F");
                    }
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    //muestra el perfil de un contacto
    public LinkedList<String> mostrar(int idPerfil) {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT * FROM public.usuario"
                        + " WHERE id='" + idPerfil + "';";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    String cid = result.getString("id");
                    datos.add(cid);
                    String cnombres = result.getString("nombres");
                    datos.add(cnombres);
                    String capellidos = result.getString("apellidos");
                    datos.add(capellidos);
                    String ccelular = result.getString("celular");
                    datos.add(ccelular);
                    String ccorreo = result.getString("correo");
                    datos.add(ccorreo);
                    String csexo = result.getString("sexo");
                    if (csexo.equals("t")) {
                        datos.add("M");
                    } else {
                        datos.add("F");
                    }
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

}
