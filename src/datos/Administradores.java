package datos;

import com.mysql.jdbc.Statement;
import entidades.Administrador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import miselaneos.Conexion;

/**
 *
 * @author cesar
 */
public class Administradores extends Conexion{
    public Administradores() {
        setTabla("administradores");
    }
    
    /**
     * Metodo que recibe como parametro un Objeto tipo Administrador y lo
     * ACTUALIZA en la base de datos en la tabla Administrador
     *
     * @param o
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isUpdate(Object o) {
        Administrador admin = (Administrador) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + admin.getId());

            //Metodo next() que indica si puedo ir al nuevo resultado del ResultSet, indicando que tengo un registro.
            if (rs.next()) {
                //Me ubico en el resultado y actualizo los campos
                rs.updateString("usuario", admin.getUsuario());
                rs.updateString("clave", admin.getClave());

                //Metodo para atualizar resultado(registro) en la base de datos/tabla
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
     * Metodo que recibe una consulta SQL en String y retorna un ArrayList de
     * Admin
     *
     * @param query
     * @return ArrayList
     */
    @Override
    public ArrayList list(String query) {
        ArrayList<Administrador> list = new ArrayList<Administrador>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Administrador admin = new Administrador(rs.getInt("id"), rs.getString("usuario"), rs.getString("clave"));
                list.add(admin);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            return list;
        }
    }

    @Override
    public boolean isNew(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public boolean isDelete(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public boolean isDeleteAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}