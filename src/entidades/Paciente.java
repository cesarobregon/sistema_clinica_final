package entidades;

/**
 *
 * @author OBREGON
 */
public class Paciente {
    private int id;
    private String apellido;
    private String nombre;
    private String domicilio;
    private String nroDocumento;
    private String localidad;
    private String provincia;
    private String celular;    
    private String telefono;  
    private String email;
    
    public Paciente(){
        this.setId(0);
        this.setApellido("");
        this.setNombre("");
        this.setDomicilio("");
        this.setNroDocumento("");
        this.setLocalidad("");
        this.setProvincia("");
        this.setCelular("");
        this.setTelefono("");
        this.setEmail("");
    }

    public Paciente(int id, String apellido, String nombre, String domicilio, String nroDocumento,
            String localidad, String provincia, String celular, String telefono, String email) {
        this.id = id;
        this.apellido = apellido;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nroDocumento = nroDocumento;
        this.localidad = localidad;
        this.provincia = provincia;
        this.celular = celular;
        this.telefono = telefono;
        this.email = email;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Object[] toObject() {

        Object[] info = new Object[]{getId(),
            getApellido(),
            getNombre(),
            getDomicilio(),
            getNroDocumento(),
            getLocalidad(),
            getProvincia(),
            getCelular(),
            getTelefono(),
            getEmail()
            };

        return info;
    }

}
