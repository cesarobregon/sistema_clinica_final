/*
 * Clase para manejar la informacion de Especialidad
 */
package entidades;


/**
 *
 * @author OBREGON
 */

public class Especialidad {
    //atributos de la clase
    private int id;
    private String descripcion;
    
    //metodo constructor vacio, para inicializar un objeto de tipo Especialidad
    public Especialidad(){
        this.setId(0);
        this.setDescripcion("");
    }
    
    //metodo constructor para instanciar un objeto de tipo Especialidad
    public Especialidad(int id, String descripcion){
        this.setId(id);
        this.setDescripcion(descripcion);
    }
    
    //metodos get y set, para obtener o cambiar los valores de los atributos del objeto
    
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

    //
    public Object[] toObject() {
        Object[] info = new Object[]{getId(),getDescripcion()};
        return info;
    }
    
}
