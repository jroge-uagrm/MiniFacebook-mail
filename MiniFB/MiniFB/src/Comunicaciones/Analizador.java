package Comunicaciones;

import Negocio.NAmigo;
import Negocio.NChat;
import Negocio.NReporte;
import Negocio.NSolicitud;
import Negocio.NUsuario;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.mail.MessagingException;

/**
 * @author Jhafeth
 */
public class Analizador {

    POP3 entrada;
    Mailer salida;
    LinkedList<String> parametros;
    String from;
    String stat;
    NUsuario usuario;
    NAmigo amigo;
    NSolicitud solicitud;
    NChat chat;
    NReporte reporte;

    public Analizador() {
    }

    public void analizar() throws MessagingException {
        this.salida = new Mailer();
        Validador validar = new Validador();
        String comando = parametros.get(0);

        if (!validar.isComando(comando)) {
            System.out.println("COMANDO" + comando + " INVALIDO");
            salida.sendHtmlEmail(from, "Comando " + comando + " no encontrado",
                    ToHTML.getHTMLMessage("Comando " + comando + " no encontrado", "Para consultar los contactos envie el comando HELP "));
            return;
        } else {
            System.out.println("COMANDO: " + comando);
        }
        switch (comando) {
            case "HELP"://TABLAS CON AYUDA Y EJEMPLOS
                salida.sendHtmlEmail(from, "Tabla de comandos Mini Facebook",
                        ToHTML.getHTMLMessage("Comando: " + comando, ToHTML.getHelpTable()));
                break;
//---------------USUARIO--------------------------------
//------CU1 GESTIONAR PERFIL
//----------REGUSER:email:password:nombres:apellidos:celular:fechanac:sexo
            case "REGUSER":
                if (parametros.size() == 8) {
                    String email = parametros.get(1);
                    String pass = parametros.get(2);
                    String nombres = parametros.get(3);
                    String apellidos = parametros.get(4);
                    String celular = parametros.get(5);
                    String fechanac = parametros.get(6);
                    String sexo = parametros.get(7);
                    if (validar.isEmail(email) && validar.isPassword(pass)
                            && validar.isName(nombres) && validar.isName(apellidos)
                            && validar.isCelular(celular) && validar.isFecha(fechanac)
                            && validar.isSexo(sexo)) {
                        LinkedList<String> arg = new LinkedList<>();
                        for (int i = 1; i < 8; i++) {
                            arg.add(parametros.get(i));
                        }
                        usuario = new NUsuario();
                        usuario.registrar(arg);
                        salida.sendHtmlEmail(from, "Comando: " + comando,
                                ToHTML.getHTMLMessage("Comando: " + comando + " enviado", "El comando " + comando + " se envio exitosamente"));
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------MODUSER:iduser:password:nombres:apellidos:celular:email
            case "MODUSER":
                if (parametros.size() == 7) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String nombres = parametros.get(3);
                    String apellidos = parametros.get(4);
                    String celular = parametros.get(5);
                    String email = parametros.get(6);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isName(nombres) && validar.isName(apellidos)
                            && validar.isCelular(celular) && validar.isEmail(email)) {
                        if (login(Integer.parseInt(id), pass)) {
                            LinkedList<String> arg = new LinkedList<>();
                            for (int i = 1; i < 7; i++) {
                                arg.add(parametros.get(i));
                            }
                            usuario = new NUsuario();
                            usuario.modificar(arg);
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", "El comando " + comando + " se envio exitosamente"));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------DELUSER:iduser:password
            case "DELUSER":
                if (parametros.size() == 3) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    if (validar.isNumber(id) && validar.isPassword(pass)) {
                        if (login(Integer.parseInt(id), pass)) {
                            usuario = new NUsuario();
                            usuario.eliminar(Integer.parseInt(id), pass);
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", "El comando " + comando + " se envio exitosamente"));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------GETUSER:email:password
            case "GETUSER":
                if (parametros.size() == 3) {
                    String email = parametros.get(1);
                    String pass = parametros.get(2);
                    if (validar.isEmail(email) && validar.isPassword(pass)) {
                        LinkedList<String> campos = new LinkedList<>();
                        campos.add("ID");
                        campos.add("Nombres");
                        campos.add("Apellidos");
                        campos.add("Celular");
                        campos.add("Correo");
                        campos.add("F. Nacimiento");
                        campos.add("F. Creación");
                        campos.add("Sexo");
                        LinkedList<String> datos = new LinkedList<>();
                        usuario = new NUsuario();
                        datos = usuario.mostrar(email, pass);
                        salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                ToHTML.getHTMLMessage("MOSTRAR USUARIO", ToHTML.getTable(datos, campos)));
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------MODPASS:iduser:oldpass:newpass
            case "MODPASS":
                if (parametros.size() == 4) {
                    String id = parametros.get(1);
                    String oldPass = parametros.get(2);
                    String newPass = parametros.get(3);
                    if (validar.isNumber(id) && validar.isPassword(oldPass)
                            && validar.isPassword(newPass)) {
                        if (login(Integer.parseInt(id), oldPass)) {
                            usuario = new NUsuario();
                            usuario.cambiarPass(id, oldPass, newPass);
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", "El comando " + comando + " se envio exitosamente"));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//------CU2 GESTIONAR CONTACTOS 
//----------LISTSOL:iduser:password
            case "LISTSOL":
                if (parametros.size() == 3) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    if (validar.isNumber(id) && validar.isPassword(pass)) {
                        if (login(Integer.parseInt(id), pass)) {
                            LinkedList<String> campos = new LinkedList<>();
                            campos.add("ID");
                            campos.add("Nombres");
                            campos.add("Apellidos");
                            LinkedList<String> datos = new LinkedList<>();
                            solicitud = new NSolicitud();
                            datos = solicitud.listarRecibidas(Integer.parseInt(id));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("MOSTRAR SOLICITUDES RECIBIDAS", ToHTML.getTable(datos, campos)));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------REGCONT:iduser:password:idamigo   
            case "REGCONT":
                if (parametros.size() == 4) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idAmigo = parametros.get(3);

                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idAmigo)) {
                        if (login(Integer.parseInt(id), pass)) {
                            amigo = new NAmigo();
                            String mensaje = amigo.aceptar(Integer.parseInt(id), Integer.parseInt(idAmigo));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", mensaje));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------LISTCONT:iduser:password
            case "LISTCONT":
                if (parametros.size() == 3) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    if (validar.isNumber(id) && validar.isPassword(pass)) {
                        if (login(Integer.parseInt(id), pass)) {
                            LinkedList<String> campos = new LinkedList<>();
                            campos.add("ID");
                            campos.add("Nombres");
                            campos.add("Apellidos");
                            campos.add("Celular");
                            campos.add("Correo");
                            LinkedList<String> datos = new LinkedList<>();
                            amigo = new NAmigo();
                            datos = amigo.listar(Integer.parseInt(id));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("LISTAR CONTACTOS", ToHTML.getTable(datos, campos)));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------DELCONT:iduser:password:ideliminar
            case "DELCONT":
                if (parametros.size() == 4) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idAmigo = parametros.get(3);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idAmigo)) {
                        if (login(Integer.parseInt(id), pass)) {
                            amigo = new NAmigo();
                            amigo.eliminar(Integer.parseInt(id), Integer.parseInt(idAmigo));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", "El comando " + comando + " se envio exitosamente"));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos", ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes", ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//------CU3 BUSCAR AMIGO
//----------FINDUSER:coincidencias (nombres.apellidos.celular.correo)
            case "FINDUSER":
                if (parametros.size() == 2) {
                    String data = parametros.get(1);
                    if (validar.isName(data) || validar.isCelular(data)
                            || validar.isEmail(data)) {
                        LinkedList<String> campos = new LinkedList<>();
                        campos.add("ID");
                        campos.add("Nombres");
                        campos.add("Apellidos");
                        campos.add("Celular");
                        campos.add("Correo");
                        campos.add("Sexo");
                        LinkedList<String> datos = new LinkedList<>();
                        amigo = new NAmigo();
                        datos = amigo.buscar(data);
                        salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                ToHTML.getHTMLMessage("Buscar Usuario: " + data, ToHTML.getTable(datos, campos)));
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------VERPERFIL:iduser  
            case "VERPERFIL":
                if (parametros.size() == 2) {
                    String id = parametros.get(1);
                    if (validar.isNumber(id)) {
                        LinkedList<String> campos = new LinkedList<>();
                        campos.add("ID");
                        campos.add("Nombres");
                        campos.add("Apellidos");
                        campos.add("Celular");
                        campos.add("Correo");
                        campos.add("Sexo");
                        LinkedList<String> datos = new LinkedList<>();
                        amigo = new NAmigo();
                        datos = amigo.mostrarPerfil(Integer.parseInt(id));
                        salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                ToHTML.getHTMLMessage("MOSTRAR PERFIL ID: " + id, ToHTML.getTable(datos, campos)));
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//------CU4 BUSCAR MENSAJE
//----------FINDMSJ:iduser:password:idReceptor:datos  (contenido del mensaje) se busca en un chat con otro usuario 
            case "FINDMSJ":
                if (parametros.size() == 5) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idReceptor = parametros.get(3);
                    String data = parametros.get(4);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idReceptor) && validar.isMensaje(data)) {
                        if (login(Integer.parseInt(id), pass)) {//LLAMAR A LOGIN
                            LinkedList<String> datos = new LinkedList<>();
                            LinkedList<String> campos = new LinkedList<>();
                            campos.add("ID Mensaje");
                            campos.add("ID Emisor");
                            campos.add("ID Receptor");
                            campos.add("Contenido");
                            campos.add("Hora");
                            campos.add("Fecha");
                            chat = new NChat();
                            datos = chat.buscar(Integer.parseInt(id), Integer.parseInt(idReceptor), data);
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Buscar: " + data + " en mensajes con USERID: " + idReceptor, ToHTML.getTable(datos, campos)));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//------CU5 ENVIAR SOLICITUD DE AMISTAD
//----------SENDSOL:iduser:password:idamigo
            case "SENDSOL":
                if (parametros.size() == 4) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idAmigo = parametros.get(3);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idAmigo)) {
                        if (login(Integer.parseInt(id), pass)) {
                            solicitud = new NSolicitud();
                            String mensaje = solicitud.enviar(Integer.parseInt(id), Integer.parseInt(idAmigo));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", mensaje));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------SENTSOL:iduser:password
            case "SENTSOL":
                if (parametros.size() == 3) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    if (validar.isNumber(id) && validar.isPassword(pass)) {
                        if (login(Integer.parseInt(id), pass)) {
                            LinkedList<String> campos = new LinkedList<>();
                            campos.add("ID");
                            campos.add("Nombres");
                            campos.add("Apellidos");
                            LinkedList<String> datos = new LinkedList<>();
                            solicitud = new NSolicitud();
                            datos = solicitud.listarEnviadas(Integer.parseInt(id));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("MOSTRAR SOLICITUDES ENVIADAS", ToHTML.getTable(datos, campos)));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario",
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos",
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes",
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------CANCSOL:iduser:password:idamigo
            case "CANCSOL":
                if (parametros.size() == 4) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idAmigo = parametros.get(3);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idAmigo)) {
                        if (login(Integer.parseInt(id), pass)) {
                            solicitud = new NSolicitud();
                            String mensaje = solicitud.cancelar(Integer.parseInt(id), Integer.parseInt(idAmigo));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", mensaje));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario", ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos", ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes", ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;

//------CU6 GESTIONAR MENSAJES
//----------SENDMSJ:iduser:password:idreceptor:mensaje
            case "SENDMSJ":
                if (parametros.size() == 5) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idReceptor = parametros.get(3);
                    String data = parametros.get(4);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idReceptor) && validar.isMensaje(data)) {
                        if (login(Integer.parseInt(id), pass)) {
                            chat = new NChat();
                            String mensaje = chat.enviar(Integer.parseInt(id), Integer.parseInt(idReceptor), data);
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", mensaje));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario", 
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos", 
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes", 
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------MODMSJ:iduser:password:idmensaje:msjmodif
            case "MODMSJ":
                if (parametros.size() == 5) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idMensaje = parametros.get(3);
                    String data = parametros.get(4);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idMensaje) && validar.isMensaje(data)) {
                        if (login(Integer.parseInt(id), pass)) {
                            chat = new NChat();
                            chat.modificar(Integer.parseInt(id), Integer.parseInt(idMensaje), data);
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado", 
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", "El comando " + comando + " se envio exitosamente"));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario", 
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos", 
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes", 
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------DELMSJ:iduser:password:idmensaje
            case "DELMSJ":
                if (parametros.size() == 4) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idMensaje = parametros.get(3);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idMensaje)) {
                        if (login(Integer.parseInt(id), pass)) {
                            chat = new NChat();
                            chat.eliminar(Integer.parseInt(id), Integer.parseInt(idMensaje));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado", 
                                    ToHTML.getHTMLMessage("Comando: " + comando + " enviado", "El comando " + comando + " se envio exitosamente"));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario", 
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos", 
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes", 
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//----------LISTMSJ:iduser:password:idreceptor    
            case "LISTMSJ":
                if (parametros.size() == 4) {
                    String id = parametros.get(1);
                    String pass = parametros.get(2);
                    String idReceptor = parametros.get(3);
                    if (validar.isNumber(id) && validar.isPassword(pass)
                            && validar.isNumber(idReceptor)) {
                        if (login(Integer.parseInt(id), pass)) {
                            LinkedList<String> campos = new LinkedList<>();
                            campos.add("ID Mensaje");
                            campos.add("ID Emisor");
                            campos.add("ID Receptor");
                            campos.add("Contenido");
                            campos.add("Hora");
                            campos.add("Fecha");
                            LinkedList<String> datos = new LinkedList<>();
                            chat = new NChat();
                            datos = chat.listar(Integer.parseInt(id), Integer.parseInt(idReceptor));
                            salida.sendHtmlEmail(from, "Comando: " + comando + " enviado", 
                                    ToHTML.getHTMLMessage("LISTAR MENSAJES", ToHTML.getTable(datos, campos)));
                        } else {
                            System.out.println("LOGIN ERROR");
                            salida.sendHtmlEmail(from, "Error: Falla al autenticar usuario", 
                                    ToHTML.getHTMLMessage("No se pudo autenticar el usuario", "IDUSUARIO o PASSWORD incorrectos"));
                        }
                    } else {
                        salida.sendHtmlEmail(from, "Error: Datos no validos", 
                                ToHTML.getHTMLMessage("Datos enviados incorrectos", "Revisa que los parametros que enviaste sean correctos"));
                    }
                } else {
                    salida.sendHtmlEmail(from, "Error: Datos insuficientes", 
                            ToHTML.getHTMLMessage("Datos enviados insuficientes", "Revisa que enviaste todos los parametros requeridos por el comando"));
                }
                break;
//--ADMINISTRADOR
//----------CU7 GENERAR REPORTES
            case "LISTREPORT":
                LinkedList<String> campos = new LinkedList<>();
                campos.add("Detalle");
                campos.add("Valor");
                LinkedList<String> datos = new LinkedList<>();
                reporte = new NReporte();
                datos = reporte.generarReporte();
                salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                        ToHTML.getHTMLMessage("Generar Reporte", ToHTML.getTable(datos, campos)));
                break;
//----------CU8 VER ESTADISTICAS    
            case "LISTSTATS":
                LinkedList<String> cabecera = new LinkedList<>();
                cabecera.add("Detalle");
                cabecera.add("Valor");
                LinkedList<String> filas = new LinkedList<>();
                reporte = new NReporte();
                filas = reporte.verEstadistica();
                salida.sendHtmlEmail(from, "Comando: " + comando + " enviado",
                        ToHTML.getHTMLMessage("Ver Estadisticas", ToHTML.getTable(filas, cabecera)));
                break;
            default:
                salida.sendHtmlEmail(from, "ERROR COMANDO INVALIDO", "COMANDO INVALIDO");
        }
    }

    public void separar() {
        this.entrada = new POP3();
        this.parametros = new LinkedList<>();
        String subject = "";
        //lectura del ultimo subject
        try {
            this.entrada.connect();
            this.entrada.logIn();
            subject = this.entrada.getSubject(this.stat);
            this.entrada.logOut();
            this.entrada.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        subject = subject.trim();
        //separar parametros
        if (!"".equals(subject)) {
            String[] partes = subject.split(":");
            this.parametros.addAll(Arrays.asList(partes));
            System.out.println("Parametros: " + this.parametros);
        }
    }

    public void setFrom() {
        this.entrada = new POP3();
        try {
            entrada.connect();
            entrada.logIn();
            this.from = entrada.getFrom(this.stat);
            entrada.logOut();
            entrada.close();
            System.out.println("From: " + this.from);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void setStat() {
        this.entrada = new POP3();
        try {
            entrada.connect();
            entrada.logIn();
            this.stat = entrada.getStat();
            entrada.logOut();
            entrada.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void setStat(int stat) {
        this.stat = String.valueOf(stat);
    }

    public int getStat() {
        return Integer.parseInt(this.stat);
    }

    public boolean login(int id, String pass) {
        NUsuario user = new NUsuario();
        return user.login(id, pass);
    }

}
