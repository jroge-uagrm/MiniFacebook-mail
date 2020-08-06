package Comunicaciones;

import java.io.*;
import java.net.*;

public class POP3 {

    private static final String HOST = "www.tecnoweb.org.bo";
    private static final int PORT = 110;
    private static final String USER = "grupo08sa";
    private static final String PASS = "grupo08grupo08";
    private static final String SALTO = "\n";
    private String COMAND = "";
    private Socket Conexion;
    private BufferedReader Entrada;
    private DataOutputStream Salida;

    public POP3() {
        this.Conexion = null;
        this.Entrada = null;
        this.Salida = null;
    }

    public void connect() throws IOException {
        this.Conexion = new Socket(HOST, PORT);
        this.Entrada = new BufferedReader(new InputStreamReader(Conexion.getInputStream()));
        this.Salida = new DataOutputStream(Conexion.getOutputStream());
        System.out.println("S:" + this.Entrada.readLine());
    }

    public void close() throws IOException {
        this.Conexion.close();
        this.Entrada.close();
        this.Salida.close();
    }

    public void logIn() throws IOException {
        COMAND = "user " + USER + SALTO;
        this.Salida.writeBytes(COMAND);
        this.Entrada.readLine();
        COMAND = "pass " + PASS + SALTO;
        this.Salida.writeBytes(COMAND);
        System.out.println("S:" + this.Entrada.readLine());
    }

    public void logOut() throws IOException {
        COMAND = "quit" + SALTO;
        this.Salida.writeBytes(COMAND);
        System.out.println("S:" + this.Entrada.readLine());
    }

    public String getList() throws IOException {
        COMAND = "list" + SALTO;
        this.Salida.writeBytes(COMAND);
        return getData(this.Entrada);
    }

    public void delete(String j) throws IOException {
        this.Salida.writeBytes("dele " + j + SALTO);
        System.out.println("S:" + this.Entrada.readLine());
    }

    public String getStat() {
        String line = "";
        try {
            this.Salida.writeBytes("STAT" + SALTO);
            line = this.Entrada.readLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (line != "") {
            line = line.substring(4);
            int i = 1;
            while (line.charAt(i) != ' ') {
                i++;
            }
            line = line.substring(0, i);
        }
        return line;
    }

    public String getSubject(String Mail) throws IOException {
        String Line = "";
        String line2 = "";
        String paraf = "";

        COMAND = "retr " + Mail + SALTO;
        this.Salida.writeBytes(COMAND);
        while ((Line = this.Entrada.readLine()) != null) {
            //LINEA DEL SUBJECT
            if (Line.startsWith("Subject:") || Line.startsWith("SUBJECT:") || Line.startsWith("subject:")) {
                break;
            }
        }
        Line = Line.substring(8); // primer linea del subject

        while ((line2 = this.Entrada.readLine()) != null) {
            if (line2.equals(".") || line2.startsWith("To:") || line2.startsWith("Thread-Topic:") || line2.startsWith("MIME-Version:")) {
                break;
            } else {
                paraf = paraf + line2;// siguientes lineas
            }
        }
        if (paraf != "") {
            Line = Line + paraf;
        }
        Line = Line.trim();
        if (!line2.equals(".")) {
            getData(Entrada);
        }
        return Line;
    }

    public String getFrom(String Mail) throws IOException {
        String Line = "";
        COMAND = "retr " + Mail + SALTO;
        this.Salida.writeBytes(COMAND);
        while ((Line = this.Entrada.readLine()) != null) {
            //LINEA DEL SUBJECT
            if (Line.indexOf("From:") == 0) {
                break;
            }
        }
        Line = Line.substring(6);
        if (Line.contains("<")) {
            Line = Line.substring(Line.indexOf("<") + 1, Line.indexOf(">"));
        }
        getData(Entrada);//vaciar buffer
        return Line;
    }

    public String getMail(String mail) throws IOException {
        COMAND = "retr " + mail + SALTO;
        this.Salida.writeBytes(COMAND);
        return getData(this.Entrada);
    }

    static protected String getData(BufferedReader buffer) throws IOException {
        String Data = "";
        String Line = "";
        while ((Line = buffer.readLine()) != null) {
            //ULTIMA LINEA
            if (Line.equals(".")) {
                break;
            }
            //LINEA COMIENZA CON .
            if ((Line.length() > 0) && (Line.charAt(0) == '.')) {
                Line = Line.substring(1);
            }
            Data = Data + Line + SALTO;
        }
        return Data;
    }

}
