package entidades;
/**
 *
 * @author OBREGON
 */
public class ObraSocial {
    private int id;
    private String descripcion;


    public ObraSocial(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public ObraSocial() {
        this.setId(0);
        this.setDescripcion("");
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