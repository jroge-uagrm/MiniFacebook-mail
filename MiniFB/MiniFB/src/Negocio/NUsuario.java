package Negocio;

import Datos.DUsuario;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class NUsuario {

    public DUsuario usuario;

    public NUsuario() {
    }


    public void registrar(LinkedList<String> arg) {
        usuario = new DUsuario();
        usuario.setEmail(arg.get(0));
        usuario.setPass(arg.get(1));
        usuario.setNombres(arg.get(2));
        usuario.setApellidos(arg.get(3));
        usuario.setCelular(arg.get(4));
        usuario.setFechaNacimiento(arg.get(5));
        usuario.setSexo(arg.get(6));
        usuario.setFechaCreacion();
        usuario.insertar();
    }

    public void modificar(LinkedList<String> arg) {
        usuario = new DUsuario();
        usuario.setId(Integer.parseInt(arg.get(0)));
        usuario.setPass(arg.get(1));
        usuario.setNombres(arg.get(2));
        usuario.setApellidos(arg.get(3));
        usuario.setCelular(arg.get(4));
        usuario.setEmail(arg.get(5));
        usuario.modificar();
    }

    public void eliminar(int id, String pass) {
        usuario = new DUsuario(id, pass);
        usuario.eliminar();
    }

    public LinkedList<String> mostrar(String email, String pass) {
        usuario = new DUsuario();
        usuario.setEmail(email);
        usuario.setPass(pass);
        return usuario.mostrar();

    }

    public void cambiarPass(String id, String oldPass, String newPass) {
        usuario = new DUsuario(Integer.parseInt(id), oldPass);
        usuario.cambiarPass(newPass);
    }
    
    public boolean login(int id,String pass){
        usuario =new DUsuario(id, pass);
        return usuario.login();
    }

}
