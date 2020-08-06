package Negocio;

import Datos.DContacto;
import Datos.DSolicitud;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class NAmigo {

    DContacto amigo;

    public NAmigo() {
    }

    public String aceptar(int id, int idAmig) {
        DSolicitud sol = new DSolicitud(id);
        int idSol = sol.getIdSol(idAmig);
        if (idSol > 0) {
            amigo = new DContacto(id);
            amigo.insertar(idAmig);
            return "Solicitud de amistad aceptada, se registro contacto con exito";
        }
        return "No existe la solicitud de amistad, no se registro contacto";
    }

    public LinkedList<String> listar(int id) {
        amigo = new DContacto(id);
        return amigo.listar();
    }

    public void eliminar(int id, int idAmigo) {
        amigo = new DContacto(id);
        amigo.eliminar(idAmigo);
    }

    public LinkedList<String> buscar(String busqueda) {
        amigo = new DContacto();
        return amigo.buscar(busqueda);
    }

    public LinkedList<String> mostrarPerfil(int idPerfil) {
        amigo = new DContacto();
        return amigo.mostrar(idPerfil);
    }


}
