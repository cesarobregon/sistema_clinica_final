package entidades;
/**
 *
 * @author cesar
 */
public class ProfesionalEspecialidad {

    private int id;
    private int idProfesional;
    private int idEspecialidad;

    public ProfesionalEspecialidad(int id, int idProfesional, int idEspecialidad) {
        this.id = id;
        this.idProfesional = idProfesional;
        this.idEspecialidad = idEspecialidad;
    }

    public ProfesionalEspecialidad() {

        this.setId(0);
        this.setIdProfesional(0);
        this.setIdEspecialidad(0);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(int idProfesional) {
        this.idProfesional = idProfesional;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Object[] toObject() {
        Object[] info = new Object[]{getId(), getIdProfesional(), getIdEspecialidad()};
        return info;
    }

}
