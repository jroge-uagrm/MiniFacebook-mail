package Negocio;

import Datos.DChat;
import Datos.DMensaje;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class NChat {

    DMensaje mensaje;
    DChat chat;

    public NChat() {
    }

    public LinkedList<String> buscar(int idUser, int idReceptor, String data) {
        mensaje = new DMensaje();
        mensaje.setIdEmisor(idUser);
        mensaje.setIdReceptor(idReceptor);
        return mensaje.buscar(data);
    }

    public String enviar(int idUser, int idReceptor, String data) {
        chat = new DChat();
        int idChat = chat.getIdChat(idUser, idReceptor);
        if (idChat > 0) {//son amigos
            mensaje = new DMensaje();
            mensaje.setIdEmisor(idUser);
            mensaje.setIdReceptor(idReceptor);
            mensaje.setContenido(data);
            mensaje.setIdChat(idChat);
            mensaje.insertar();
            return "Mensaje: '" + data + "' enviado con exito.";
        } else {//no son amigos
            return "EL usuario: " + idReceptor + " no es su amigo, no puede enviarle mensajes";
        }
    }

    public void modificar(int idUser, int idMensaje, String data) {
        mensaje = new DMensaje();
        mensaje.setIdEmisor(idUser);
        mensaje.setContenido(data);
        mensaje.modificar(idMensaje);
    }

    public void eliminar(int idUser, int idMensaje) {
        mensaje = new DMensaje();
        mensaje.setIdEmisor(idUser);
        mensaje.eliminar(idMensaje);
    }

    public LinkedList<String> listar(int idUser, int idReceptor) {
        mensaje = new DMensaje();
        mensaje.setIdEmisor(idUser);
        mensaje.setIdReceptor(idReceptor);
        return mensaje.listar();
    }
}
