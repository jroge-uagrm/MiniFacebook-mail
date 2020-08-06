/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Jhafeth
 */
public class DChat {

    int id;
    int cant;
    int idA;
    int idB;

    public DChat() {
    }

    public void insertar() {
        DBConnection connection = new DBConnection();
        try {
            if (connection.connect()) {
                String sql = "INSERT INTO public.chat(id, fecha_creacion, cantidad_de_mensajes, user_a_id, user_b_id)"
                        + " VALUES (nextval('chat_seq'::regclass),'"
                        + getFecha() + "','"
                        + getCant() + "','"
                        + getIdA() + "','"
                        + getIdB() + "');";
                connection.insert(sql);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int getIdChat(int idA, int idB) {
        int datos = 0;
        try {
            DBConnection connection = new DBConnection();
            if (connection.connect()) {
                String sql = "SELECT C.id "
                        + "FROM Public.chat C "
                        + "WHERE (C.user_a_id='" + idA + "'"
                        + " AND C.user_b_id='" + idB + "') "
                        + "OR (C.user_a_id='" + idB + "'"
                        + " AND C.user_b_id='" + idA + "');";
                ResultSet result = connection.select(sql);
                while (result.next()) {
                    datos = result.getInt(1);
                }
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        this.idB = idB;
    }

    public String getFecha() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }
}
