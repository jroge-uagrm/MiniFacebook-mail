package Comunicaciones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToHTML {

    public static String getHTMLMessage(String cabecera, String mensaje) {
        try {
            String template = openFile("src/Templates/MessageTemplate.html");
            template = template.replaceFirst("CABECERA", cabecera);
            template = template.replace("MENSAJITO", mensaje);
            return template;
        } catch (IOException ex) {
            Logger.getLogger(ToHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private static String openFile(String archivo) throws FileNotFoundException, IOException {
        String cadena = "";
        String aux = "";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((aux = b.readLine()) != null) {
            cadena += aux;
        }
        b.close();
        return cadena;
    }

    public static String getTable(LinkedList<String> valores, LinkedList<String> campos) {
        try {
            String cabecera = "";
            for (int i = 0; i < campos.size(); i++) {
                cabecera += "<th>" + campos.get(i) + "</th>";
            }

            int cantidadCampos = campos.size();
            
            String cuerpo = "";
            for (int i = 0; i < valores.size(); i++) {
                cuerpo += "<tr>";
                for (int j = 0; j < cantidadCampos; j++) {
                    cuerpo += "<td>" + valores.get(i + j).toString() + "</td>";
                }
                cuerpo += "</tr>";
                i = i + cantidadCampos - 1;
            }

            String table = openFile("src/Templates/OnlyTableTemplate.html");
            table = table.replaceFirst("CABECERA", cabecera);
            table = table.replace("CUERPO", cuerpo);
            return table;
        } catch (IOException ex) {
            Logger.getLogger(ToHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String getHelpTable() {
        try {
            String template = openFile("src/Templates/help.html");
            return template;
        } catch (IOException ex) {
            Logger.getLogger(ToHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
