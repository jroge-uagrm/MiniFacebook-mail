package Comunicaciones;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Jhafe
 */
public class Validador {

    private static final String[] COMANDOS_VALIDOS
            = {"HELP", "REGUSER", "MODUSER", "DELUSER", "GETUSER",
                "MODPASS", "LISTSOL", "REGCONT", "LISTCONT",
                "DELCONT", "FINDUSER", "VERPERFIL", "FINDMSJ",
                "SENDSOL", "SENTSOL", "CANCSOL", "SENDMSJ", "MODMSJ", "DELMSJ",
                "LISTMSJ", "LISTREPORT", "LISTSTATS"};

    public boolean isComando(String comando) {
        for (String COMANDOS_VALIDOS1 : COMANDOS_VALIDOS) {
            if (comando.equals(COMANDOS_VALIDOS1)) {
                return true;
            }
        }
        return false;
    }

    public boolean isName(String name) {
        return (name.length() <= 50 && name.matches("^[a-zA-Z ]+$"));
    }

    public boolean isFecha(String fecha) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isEmail(String email) {
        return (email.length() <= 50 && email.matches("^[-_\\w\\.]+@\\w+(\\.\\w+)+$"));
    }

    public boolean isSexo(String sexo) {
        return (sexo.equals("M") || sexo.equals("F"));
    }

    public boolean isCelular(String celular) {
        return celular.matches("^[0-9]{8}$");
    }

    public boolean isPassword(String pass) {
        return pass.matches("^[\\w]{4,10}$");
    }

    public boolean isNumber(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isMensaje(String data) {
        return (data.length() <= 160 && data.matches("^[ \\w\\.,\\!\\?]+$"));
    }

}
