package datos;

import com.mysql.jdbc.Statement;
import entidades.Personal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import miselaneos.Conexion;
import static sistema_clinica_obregon.JFrame_Sistema.fe;

/**
 *
 * @author cesar
 */
public class Personales extends Conexion {

    public Personales() {
        setTabla("personales");
    }

    /**
     * Metodo que recibe como parametro un Objeto tipo Personal y lo REGISTRA en
     * la base de datos en la tabla personales
     *
     * @param c
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isNew(Object o) {
        Personal per = (Personal) o;
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
            rs.updateString("usuario", per.getUsuario());
            rs.updateString("clave", per.getClave());
            rs.updateString("apellido", per.getApellido());
            rs.updateString("nombre", per.getNombre());
            rs.updateInt("cargo", per.getCargo_id());
            if (per.getFecha_inicio() == null) {
                rs.updateDate("fecha_inicio", null);
            } else {
                rs.updateString("fecha_inicio", fe.getFechaMySQL(per.getFecha_inicio()));
            }
            if (per.getFecha_fin() == null) {
                rs.updateDate("fecha_fin", null);
            } else {
                rs.updateString("fecha_fin", fe.getFechaMySQL(per.getFecha_fin()));
            }
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
     * Metodo que recibe como parametro un Objeto tipo personal y lo ACTUALIZA
     * en la base de datos en la tabla personales
     *
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isUpdate(Object o) {
        Personal per = (Personal) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + per.getId());

            //Metodo next() que indica si puedo ir al nuevo resultado del ResultSet, indicando que tengo un registro.
            //Pueden investigar si funciona tambien con el metodo rs.first()
            if (rs.next()) {
                //Me ubico en el resultado y actualizo los campos
                rs.updateString("usuario", per.getUsuario());
                rs.updateString("clave", per.getClave());
                rs.updateString("apellido", per.getApellido());
                rs.updateString("nombre", per.getNombre());
                rs.updateInt("cargo", per.getCargo_id());
                if (per.getFecha_inicio() == null) {
                    rs.updateDate("fecha_inicio", null);
                } else {
                    rs.updateString("fecha_inicio", fe.getFechaMySQL(per.getFecha_inicio()));
                }
                if (per.getFecha_fin() == null) {
                    rs.updateDate("fecha_fin", null);
                } else {
                    rs.updateString("fecha_fin", fe.getFechaMySQL(per.getFecha_fin()));
                }

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
     * Metodo que recibe como parametro un Objeto tipo personal y lo BORRA en la
     * base de datos en la tabla personales
     *
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isDelete(Object o) {
        Personal per = (Personal) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + per.getId());

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
     * Metodo que BORRA TODOS los personales en la base de datos
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
     * personal
     *
     * @param query
     * @return ArrayList
     */
    @Override
    public ArrayList list(String query) {
        ArrayList<Personal> list = new ArrayList<Personal>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Personal personal = new Personal(rs.getInt("id"), rs.getString("usuario"), rs.getString("clave"), rs.getString("apellido"), rs.getString("nombre"),
                        rs.getInt("cargo_id"), fe.getFechaACalendar(rs.getDate("fecha_inicio")), fe.getFechaACalendar(rs.getDate("fecha_fin")));
                list.add(personal);
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
     * Metodo que recibo codigo de personal y retorna un Objeto de tipo
     * Personal, sino encuentra el personal el Objeto que retorna es nulo (null)
     *
     * @param id
     * @return Paciente
     */
    public Personal getPersonal(int id) {
        Personal per = null;
        //Saber si me puedo Conectar
        if (isOkConexion()) {
            //Genero consulta SQL
            String query = "SELECT * FROM " + getTabla() + " WHERE id=" + id;
            //Reutilizo metodo de personalesList con SQL en busqueda de personales
            ArrayList<Personal> personalesList = list(query);
            isCloseConexion();//Cierro Conexion

            //Pregunto si el ArrayList en respuesta del metodo personalesList(***) tiene un Objeto
            if (personalesList.size() == 1) {
                //Si tiene acceso a la primer posicion
                Personal perList = personalesList.get(0);
                //hago una nueva instancia de per(Personal) con el valor de la posicion 0
                per = new Personal(perList.getId(), perList.getUsuario(), perList.getClave(), perList.getApellido(), perList.getNombre(), 
                perList.getCargo_id(), perList.getFecha_inicio(), perList.getFecha_fin());
            }
        }
        //Retorno el valor del Objeto per
        return per;
    }
}
