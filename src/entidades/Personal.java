/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Calendar;

/**
 *
 * @author cesar
 */
public class Personal {
    private int id;
    private String usuario;
    private String clave;
    private String apellido;
    private String nombre;
    private int cargo_id;
    private Calendar fecha_inicio;
    private Calendar fecha_fin;

    public Personal(int id, String usuario, String clave, String apellido,
            String nombre, int cargo_id, Calendar fecha_inicio, Calendar fecha_fin) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.apellido = apellido;
        this.nombre = nombre;
        this.cargo_id = cargo_id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public Personal() {
        this.setId(0);
        this.setUsuario("");
        this.setClave("");
        this.setApellido("");
        this.setNombre("");
        this.setCargo_id(0);
        this.setFecha_inicio(null);
        this.setFecha_fin(null);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCargo_id() {
        return cargo_id;
    }

    public void setCargo_id(int cargo_id) {
        this.cargo_id = cargo_id;
    }

    public Calendar getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Calendar fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Calendar getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Calendar fecha_fin) {
        this.fecha_fin = fecha_fin;
    }
    
    public Object[] toObject() {

        Object[] info = new Object[]{getId(),
            getUsuario(),
            getClave(),
            getApellido(),
            getNombre(),
            getCargo_id(),
            };
        return info;
    }
    
    
}
