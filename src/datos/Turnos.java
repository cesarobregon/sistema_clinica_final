package datos;

import com.mysql.jdbc.Statement;
import entidades.Turno;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import miselaneos.Conexion;
import static sistema_clinica_obregon.JFrame_Sistema.fe;

/**
 *
 * @author cesar
 */
public class Turnos extends Conexion {

    public Turnos() {
        setTabla("turnos");
    }

    /**
     * @param o
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isNew(Object o) {
        Turno t = (Turno) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=-1");

            rs.moveToInsertRow();

            rs.updateInt("profesional_id", t.getIdProfesional());
            rs.updateInt("especialidad_id", t.getIdEspecialidad());
            //Pregunto si la información de fecha es null actualizo el campo de fecha con null y uso el 
            //metodo updateDate, sino pasa la información de tiempo a un valor String y uso el metodo updateString.
            if (t.getFecha() == null) {
                rs.updateDate("fecha", null);
            } else {
                rs.updateString("fecha", fe.getFechaMySQL(t.getFecha()));
            }
            rs.updateString("apellido_paciente", t.getApellidoPaciente());
            rs.updateString("nombre_paciente", t.getNombrePaciente());
            rs.updateString("dni_paciente", t.getNroDocumentoPaciente());
            rs.updateInt("obra_social", t.getObraSocial());
            rs.updateString("nro_credencial", t.getNroCredencial());
            rs.updateInt("atencion_particular", t.getAtencionPart());
            rs.updateString("motivo_turno", t.getMotivoTurno());
            rs.updateInt("estado", t.getEstado());
            rs.updateString("diagnostico", t.getDiagnostico());

            rs.insertRow();
            rs.close();
            st.close();
            isOk = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return isOk;
        }
    }

    /**
     * @param o
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isUpdate(Object o) {
        Turno t = (Turno) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + t.getId());
            if (rs.next()) {
                rs.updateInt("profesional_id", t.getIdProfesional());
                rs.updateInt("especialidad_id", t.getIdEspecialidad());
                //Pregunto si la información de fecha es null actualizo el campo de fecha con null y uso el 
                //metodo updateDate, sino pasa la información de tiempo a un valor String y uso el metodo updateString.
                if (t.getFecha() == null) {
                    rs.updateDate("fecha", null);
                } else {
                    rs.updateString("fecha", fe.getFechaMySQL(t.getFecha()));
                }
                rs.updateString("apellido_paciente", t.getApellidoPaciente());
                rs.updateString("nombre_paciente", t.getNombrePaciente());
                rs.updateString("dni_paciente", t.getNroDocumentoPaciente());
                rs.updateInt("obra_social", t.getObraSocial());
                rs.updateString("nro_credencial", t.getNroCredencial());
                rs.updateInt("atencion_particular", t.getAtencionPart());
                rs.updateString("motivo_turno", t.getMotivoTurno());
                rs.updateInt("estado", t.getEstado());
                rs.updateString("diagnostico", t.getDiagnostico());
                rs.updateRow();
                isOk = true;
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        } finally {
            return isOk;
        }
    }

    /**
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isDelete(Object o) {
        Turno t = (Turno) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + t.getId());
            if (rs.next()) {
                rs.deleteRow();
                isOk = true;
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return isOk;
        }
    }

    /**
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isDeleteAll() {
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            isOk = st.executeUpdate("DELETE FROM " + getTabla()) > 0;

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return isOk;
        }
    }

    /**
     * @param query
     * @return ArrayList
     */
    @Override
    public ArrayList list(String query) {
        ArrayList<Turno> list = new ArrayList<Turno>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Turno registroTurno = new Turno(rs.getInt("id"), rs.getInt("profesional_id"), rs.getInt("especialidad_id"), fe.getFechaACalendar(rs.getDate("fecha")), 
                        rs.getString("apellido_paciente"), rs.getString("nombre_paciente"), rs.getString("dni_paciente"), rs.getInt("obra_social"), 
                        rs.getString("nro_credencial"), rs.getInt("atencion_particular"), rs.getString("motivo_turno"), rs.getInt("estado"), rs.getString("diagnostico"));
                list.add(registroTurno);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return list;
        }
    }

    /**
     * @param id
     * @return Location
     */
    public Turno getTurno(int id) {
        Turno t = null;
        if (isOkConexion()) {
            String query = "SELECT * FROM " + getTabla() + " WHERE id=" + id;
            ArrayList<Turno> turnosList = list(query);
            isCloseConexion();
            if (turnosList.size() == 1) {
                Turno tList = turnosList.get(0);
                t = new Turno(tList.getId(), tList.getIdProfesional(), tList.getIdEspecialidad(), tList.getFecha(), 
                        tList.getApellidoPaciente(), tList.getNombrePaciente(), tList.getNroDocumentoPaciente(),
                        tList.getObraSocial(), tList.getNroCredencial(), tList.getAtencionPart(), tList.getMotivoTurno(),
                        tList.getEstado(), tList.getDiagnostico());
            }
        }
        return t;
    }
}
