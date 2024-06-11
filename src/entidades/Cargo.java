package entidades;

/**
 *
 * @author cesar
 */
public class Cargo {
    private int id;
    private String descripcion;

    public Cargo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public Cargo() {
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
