package entidades;

/**
 *
 * @author cesar
 */
public class Administrador {
    private int id;
    private String usuario;
    private String clave;

    public Administrador(int id, String usuario, String clave) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
    }

    public Administrador() {
        this.setId(0);
        this.setUsuario("");
        this.setClave("");
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
    
    public Object[] toObject() {
        Object[] info = new Object[]{getId(),getUsuario(), getClave()};
        return info;
    }
}