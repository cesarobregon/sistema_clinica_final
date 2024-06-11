/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;


/**
 *
 * @author OBREGON
 */
public class Especialidad {
    private int id;
    private String descripcion;
    
    public Especialidad(){
        this.setId(0);
        this.setDescripcion("");
    }
    
    public Especialidad(int id, String descripcion){
        this.setId(id);
        this.setDescripcion(descripcion);
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    public Object[] toObject() {

        Object[] info = new Object[]{getId(),getDescripcion()};
        return info;
    }
    
}
