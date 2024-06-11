package entidades;

import java.util.Calendar;

/**
 *
 * @author OBREGON
 */
public class Turno {

    private int id;
    private int idProfesional;
    private int idEspecialidad;
    private Calendar fecha;
    private String apellidoPaciente;
    private String nombrePaciente;
    private String nroDocumentoPaciente;
    private int obraSocial;
    private String nroCredencial;
    private int atencionPart;
    private String motivoTurno;
    private int estado;
    private String diagnostico;

    public Turno(int id, int idProfesional, int idEspecialidad, Calendar fecha,
            String apellidoPaciente, String nombrePaciente, String nroDocumentoPaciente, int obraSocial, 
            String nroCredencial, int atencionPart, String motivoTurno, int estado, String diagnostico) {
        this.id = id;
        this.idProfesional = idProfesional;
        this.idEspecialidad = idEspecialidad;
        this.fecha = fecha;
        this.apellidoPaciente = apellidoPaciente;
        this.nombrePaciente = nombrePaciente;
        this.nroDocumentoPaciente = nroDocumentoPaciente;
        this.obraSocial = obraSocial;
        this.nroCredencial = nroCredencial;
        this.atencionPart = atencionPart;
        this.motivoTurno = motivoTurno;
        this.estado = estado;
        this.diagnostico = diagnostico;
    }

    

    public Turno() {

        this.setId(0);
        this.setIdProfesional(0);
        this.setIdEspecialidad(0);
        this.setFecha(null);
        this.setApellidoPaciente("");
        this.setNombrePaciente("");
        this.setNroDocumentoPaciente("");
        this.setObraSocial(0);
        this.setNroCredencial("");
        this.setAtencionPart(0);
        this.setMotivoTurno("");
        this.setEstado(0);
        this.setDiagnostico("");
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

    public Calendar getFecha() {
        return fecha;
    }


    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNroDocumentoPaciente() {
        return nroDocumentoPaciente;
    }

    public void setNroDocumentoPaciente(String nroDocumentoPaciente) {
        this.nroDocumentoPaciente = nroDocumentoPaciente;
    }

    public int getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(int obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getNroCredencial() {
        return nroCredencial;
    }

    public void setNroCredencial(String nroCredencial) {
        this.nroCredencial = nroCredencial;
    }

    public int getAtencionPart() {
        return atencionPart;
    }

    public void setAtencionPart(int atencionPart) {
        this.atencionPart = atencionPart;
    }

    public String getMotivoTurno() {
        return motivoTurno;
    }

    public void setMotivoTurno(String motivoTurno) {
        this.motivoTurno = motivoTurno;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Object[] toObject() {
        Object[] info = new Object[]{getId(), getIdEspecialidad(),getApellidoPaciente(), getNombrePaciente(), 
            getNroDocumentoPaciente(), getEstado(), getMotivoTurno(),getDiagnostico()};
        return info;
    }
}