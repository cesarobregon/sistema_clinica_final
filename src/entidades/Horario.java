package entidades;
/**
 *
 * @author cesar
 */
public class Horario {
    private int id;
    private String descripcion;
    private int orden;

    public Horario(int id, String descripcion, int orden) {
        this.id = id;
        this.descripcion = descripcion;
        this.orden = orden;
    }
    
    public Horario() {
        this.setId(0);
        this.setDescripcion("");
        this.setOrden(0);
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

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Object[] toObject() {
        Object[] info = new Object[]{getId(),getDescripcion(),getOrden()};
        return info;
    }
}