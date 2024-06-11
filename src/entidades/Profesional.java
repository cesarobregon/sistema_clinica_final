package entidades;
/**
 *
 * @author cesar
 */
public class Profesional {
    private int id;
    private String usuario;
    private String clave;
    private String apellido;
    private String nombre;
    private int matricula;
    private String titulo;
    private String email;
    private String celular;
    private String foto;
    private int estado;

    public Profesional(int id, String usuario, String clave, String apellido, String nombre, int matricula, String titulo, String email, String celular, String foto, int estado) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.apellido = apellido;
        this.nombre = nombre;
        this.matricula = matricula;
        this.titulo = titulo;
        this.email = email;
        this.celular = celular;
        this.foto = foto;
        this.estado = estado;
    }

    public Profesional() {
        this.setId(0);
        this.setUsuario("");
        this.setClave("");
        this.setApellido("");
        this.setNombre("");
        this.setMatricula(0);
        this.setTitulo("");
        this.setEmail("");
        this.setCelular("");
        this.setFoto("");
        this.setEstado(0);
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

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public Object[] toObject() {

        Object[] info = new Object[]{getId(),
            getUsuario(),
            getClave(),
            getApellido(),
            getNombre(),
            getMatricula(),
            getTitulo(),
            getEmail(),
            getCelular(),
            getFoto(),
            getEstado()
            };

        return info;
    }
    
}
