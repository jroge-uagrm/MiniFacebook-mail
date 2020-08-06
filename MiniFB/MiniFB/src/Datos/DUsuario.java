package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class DUsuario {

    private int id;
    private String email;
    private String pass;
    private String nombres;
    private String apellidos;
    private String celular;
    private String fechaNacimiento;
    private boolean sexo;
    private String fechaCreacion;

    public DUsuario() {
    }

    public DUsuario(int id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo.equals("M");
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion() {
        LocalDate today = LocalDate.now();
        this.fechaCreacion = today.toString();
    }

    public void insertar() {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "INSERT INTO public.usuario("
                        + "id, nombres, apellidos, celular, correo, fecha_de_creacion, fecha_de_nacimiento, pass, sexo) "
                        + "VALUES (nextval('usuario_seq'::regclass),'"
                        + getNombres() + "','"
                        + getApellidos() + "','"
                        + getCelular() + "','"
                        + getEmail() + "','"
                        + getFechaCreacion() + "','"
                        + getFechaNacimiento() + "','"
                        + getPass() + "','"
                        + getSexo() + "');";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void modificar() {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "UPDATE public.usuario"
                        + " SET "
                        + "nombres='" + getNombres() + "',"
                        + "apellidos='" + getApellidos() + "',"
                        + "celular ='" + getCelular() + "',"
                        + "correo ='" + getEmail() + "'"
                        + " WHERE id='" + getId() + "'"
                        + " and pass='" + getPass() + "'";
                connection.update(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void eliminar() {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "DELETE FROM public.usuario"
                        + " WHERE id='" + getId() + "'"
                        + " and pass='" + getPass() + "'";
                connection.delete(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public LinkedList mostrar() {
        LinkedList<String> datos = new LinkedList<>();
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT * FROM public.usuario"
                        + " where correo='" + getEmail() + "'"
                        + " and pass='" + getPass() + "'";
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
                    String cfechaNacimiento = result.getString("fecha_de_nacimiento");
                    datos.add(cfechaNacimiento);
                    String cfechaCreacion = result.getString("fecha_de_creacion");
                    datos.add(cfechaCreacion);
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

    public void cambiarPass(String newPass) {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "UPDATE public.usuario"
                        + " SET "
                        + "pass='" + newPass + "'"
                        + " WHERE id='" + getId() + "'"
                        + " and pass='" + getPass() + "'";
                connection.update(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean login() {
        boolean b = false;
        try {
            DBConnection connection = new DBConnection();

            if (connection.connect()) {
                String sql = "SELECT * FROM usuario where id=" + getId();
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    int cid = result.getInt("id");
                    String cpass = result.getString("pass");
                    b = cpass.equals(getPass()) && cid == getId();
                }
                connection.close();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return b;
    }
    
    public boolean login(int user,String password) throws SQLException {
        boolean b = false;
        try {
            DBConnection connection = new DBConnection();

            if (connection.connect()) {
                String sql = "SELECT * FROM usuario where id=" + user;
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    int cid = result.getInt("id");
                    String cpass = result.getString("pass");
                    b = cpass.equals(password) && cid == user;
                }
                connection.close();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return b;
    }

}
