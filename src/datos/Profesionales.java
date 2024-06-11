package datos;

import com.mysql.jdbc.Statement;
import entidades.Profesional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import miselaneos.Conexion;

/**
 *
 * @author cesar
 */
public class Profesionales extends Conexion {

    public Profesionales() {
        setTabla("profesionales");
    }

    /**
     * Metodo que recibe como parametro un Objeto tipo Profesonal y lo REGISTRA en
     * la base de datos en la tabla profesionales
     *
     * @param c
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isNew(Object o) {
        Profesional prof = (Profesional) o;
        boolean isOk = false;
        try {
            //Creo un Objeto tipo Statament (st), que es necesario para procesar sentencias SQL
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //Del Objeto Statement genero otro Objeto tipo ResultSet que almacena los resultados
            //de la consulta sql.
            //En este caso resultados en blanco porque en la consulta no hay registros con id=-1
            //Esto se hace para luego crear un nuevo registro en blanco
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=-1");

            //Creo un nuevo registro en blanco para luego ir actualizando cada campo del registro
            rs.moveToInsertRow();

            //rs.updateInt("id", p.getId()); // como el campo de id es autoincremental no se hace mención
            //Actualizo los campos del nuevo registro con los datos que recibo del Objeto ObraSocial 
            rs.updateString("usuario", prof.getUsuario());
            rs.updateString("clave", prof.getClave());
            rs.updateString("apellido", prof.getApellido());
            rs.updateString("nombre", prof.getNombre());
            rs.updateInt("matricula", prof.getMatricula());
            rs.updateString("titulo_profesional", prof.getTitulo());
            rs.updateString("email", prof.getEmail());
            rs.updateString("celular", prof.getCelular());
            rs.updateString("foto", prof.getFoto());
            rs.updateInt("estado", prof.getEstado());

            //Por ultimo para que se termine de crear un registro en la base de datos/tabla, ejecuto el metodo insertRow
            rs.insertRow();

            //Cierro ResultSet (rs) y Statament (st), es indispensable NO Olvidarse de cerrar
            //estos objetos. De no ser asi, NO se registrara que se creo un registro
            rs.close();
            st.close();

            //Por Ultimo pongo a la bandera del metodo en true para indicar que no ha ocurrido ningun error
            //y el metodo tubo exito
            //Nota: es importante definir a lo ultimo del codigo esta bandera indicando que todos los pasos
            //fueron realizados con exito
            isOk = true;

        } catch (SQLException e) {
            //Si hay alguna excepción en algun momento relacionado con algun metodo mal escrito o mal ejecutado, o bien
            //problemas en la red para ejecutar los metodos se sucede esta seccion de codigo.
            System.out.println(e.getMessage());
        } finally {
            return isOk;
        }
    }

    /**
     * Metodo que recibe como parametro un Objeto tipo Profesional y lo ACTUALIZA
     * en la base de datos en la tabla profesionales
     *
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isUpdate(Object o) {
        Profesional prof = (Profesional) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + prof.getId());

            //Metodo next() que indica si puedo ir al nuevo resultado del ResultSet, indicando que tengo un registro.
            if (rs.next()) {
                //Me ubico en el resultado y actualizo los campos
                rs.updateString("usuario", prof.getUsuario());
                rs.updateString("clave", prof.getClave());
                rs.updateString("apellido", prof.getApellido());
                rs.updateString("nombre", prof.getNombre());
                rs.updateInt("matricula", prof.getMatricula());
                rs.updateString("titulo_profesional", prof.getTitulo());
                rs.updateString("email", prof.getEmail());
                rs.updateString("celular", prof.getCelular());
                rs.updateString("foto", prof.getFoto());
                rs.updateInt("estado", prof.getEstado());

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
     * Metodo que recibe como parametro un Objeto tipo Profesional y lo BORRA en la
     * base de datos en la tabla profesionales
     *
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isDelete(Object o) {
        Profesional prof = (Profesional) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + prof.getId());

            if (rs.next()) {
                //Al ejecutar el metodo deleteRow() confirmo los campos en la base de datos/tabla
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
     * Metodo que BORRA TODOS los profesionales en la base de datos
     *
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
     * Metodo que recibo una consulta SQL en String y retorna un ArrayList de
     * profesional
     *
     * @param query
     * @return ArrayList
     */
    @Override
    public ArrayList list(String query) {
        ArrayList<Profesional> list = new ArrayList<Profesional>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Profesional profesional = new Profesional(rs.getInt("id"), rs.getString("usuario"), rs.getString("clave"), rs.getString("apellido"),
                        rs.getString("nombre"), rs.getInt("matricula"), rs.getString("titulo_profesional"),
                        rs.getString("email"), rs.getString("celular"), rs.getString("foto"), rs.getInt("estado"));
                list.add(profesional);
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
     * Metodo que recibo codigo de profesional y retorna un Objeto de tipo
     * Profesional, sino encuentra el profesional el Objeto que retorna es nulo (null)
     *
     * @param id
     * @return Paciente
     */
    public Profesional getProfesional(int id) {
        Profesional prof = null;
        //Saber si me puedo Conectar
        if (isOkConexion()) {
            //Genero consulta SQL
            String query = "SELECT * FROM " + getTabla() + " WHERE id=" + id;
            //Reutilizo metodo de profesionalesList con SQL en busqueda de profesionales
            ArrayList<Profesional> profesionalesList = list(query);
            isCloseConexion();//Cierro Conexion

            //Pregunto si el ArrayList en respuesta del metodo profesionalesList(***) tiene un Objeto
            if (profesionalesList.size() == 1) {
                //Si tiene acceso a la primer posicion
                Profesional profList = profesionalesList.get(0);
                //hago una nueva instancia de prof(Profesional) con el valor de la posicion 0
                prof = new Profesional(profList.getId(), profList.getUsuario(), profList.getClave(), profList.getApellido(), profList.getNombre(),profList.getMatricula(),
                        profList.getTitulo(),profList.getEmail(),profList.getCelular(), profList.getFoto(), profList.getEstado());
            }
        }
        //Retorno el valor del Objeto prof
        return prof;
    }
}
