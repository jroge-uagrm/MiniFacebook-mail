
package Negocio;

import Datos.DReporte;
import java.util.LinkedList;

/**
 *
 * @author Jhafeth
 */
public class NReporte {
    DReporte reporte;
    public  LinkedList<String> generarReporte(){
        reporte = new DReporte();
        return reporte.generarReporte();
    }
    
    public  LinkedList<String> verEstadistica(){
        reporte = new DReporte();
        return reporte.listarEstadistica();
    }
    
    
}
