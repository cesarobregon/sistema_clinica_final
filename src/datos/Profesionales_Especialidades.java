package datos;

import com.mysql.jdbc.Statement;
import entidades.ProfesionalEspecialidad;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import miselaneos.Conexion;
/**
 *
 * @author cesar
 */
public class Profesionales_Especialidades extends Conexion {

    public Profesionales_Especialidades() {
        setTabla("profesionales_especialidades");
    }
    /**
     * @param o
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isNew(Object o) {
        ProfesionalEspecialidad pe = (ProfesionalEspecialidad) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=-1");
            rs.moveToInsertRow();
            rs.updateInt("profesional_id", pe.getIdProfesional());
            rs.updateInt("especialidad_id", pe.getIdEspecialidad());
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
        ProfesionalEspecialidad pe = (ProfesionalEspecialidad) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + pe.getId());
            if (rs.next()) {
                rs.updateInt("profesional_id", pe.getIdProfesional());
                rs.updateInt("especialidad_id", pe.getIdEspecialidad());
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
        ProfesionalEspecialidad pe = (ProfesionalEspecialidad) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + pe.getId());
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
        ArrayList<ProfesionalEspecialidad> list = new ArrayList<ProfesionalEspecialidad>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ProfesionalEspecialidad profesp = new ProfesionalEspecialidad(rs.getInt("id"), rs.getInt("profesional_id"), rs.getInt("especialidad_id"));
                list.add(profesp);
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
    public ProfesionalEspecialidad getProfEsp(int id) {
        ProfesionalEspecialidad pe = null;
        if (isOkConexion()) {
            String query = "SELECT * FROM " + getTabla() + " WHERE id=" + id;
            ArrayList<ProfesionalEspecialidad> profespList = list(query);
            isCloseConexion();
            if (profespList.size() == 1) {
                ProfesionalEspecialidad peList = profespList.get(0);
                pe = new ProfesionalEspecialidad(peList.getId(), peList.getIdProfesional(), peList.getIdEspecialidad());
            }
        }
        return pe;
    }
}