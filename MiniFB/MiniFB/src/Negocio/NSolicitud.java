package Negocio;

import Datos.DChat;
import Datos.DSolicitud;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class NSolicitud {

    DSolicitud solicitud;

    public NSolicitud() {
    }

    public String enviar(int id, int idAmigo) {
        DChat chat = new DChat();
        int idchat = chat.getIdChat(id, idAmigo);
        if (idchat == 0) {//verifica si son amigos
            DSolicitud sol = new DSolicitud(id);
            int idSol = sol.getIdSol(idAmigo);//solcitud: idAmigo->id
            sol.setIdUser(idAmigo);
            idSol = idSol + sol.getIdSol(id);//solcitud: id->idAmigo
            if (idSol == 0) {//verifica si existe solicitud pendiente
                solicitud = new DSolicitud(id);
                solicitud.insertar(idAmigo);
                return "Solicitud de amistad enviada con exito";
            } else {
                return "Error al enviar solicitud de amistad. Ya existe una solicitud pendiente";
            }
        } else {
            return "Error al enviar solicitud de amistad. Ya son amigos";
        }

    }

    public String cancelar(int id, int idAmigo) {
        DSolicitud sol = new DSolicitud(idAmigo);
        int idSol = sol.getIdSol(id);//solicitud: id->idAmigo
        if (idSol > 0) {//verifica que existe solicitud pendiente
            solicitud = new DSolicitud();
            solicitud.eliminar(idSol);
            return "Solicitud de amistad cancelada. Se elimino la solicitud con exito";
        } else {
            return "No existe solicitud de amistad para eliminar"; 
        }
    }

    public LinkedList<String> listarEnviadas(int id) {
        solicitud = new DSolicitud(id);
        return solicitud.listarEnviado();
    }

    public LinkedList<String> listarRecibidas(int id) {
        solicitud = new DSolicitud(id);
        return solicitud.listarRecibido();

    }

}
