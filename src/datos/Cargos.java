package datos;

import com.mysql.jdbc.Statement;
import entidades.Cargo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import miselaneos.Conexion;

/**
 *
 * @author cesar
 */
public class Cargos extends Conexion {

    public Cargos() {
        setTabla("cargos");
    }

    /**
     * Metodo que recibe un parametro tipo Object y lo REGISTRA en la base de
     * datos en la tabla cargos
     *
     * @param o
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isNew(Object o) {
        Cargo car = (Cargo) o;
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

            //Actualizo los campos del nuevo registro con los datos que recibo del Objeto Especialidad 
            rs.updateString("descripcion", car.getDescripcion());

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
     * Metodo que recibe como parametro un Objeto tipo Cargo y lo
     * ACTUALIZA en la base de datos en la tabla cargos
     *
     * @param o
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isUpdate(Object o) {
        Cargo car = (Cargo) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + car.getId());

            //Metodo next() que indica si puedo ir al nuevo resultado del ResultSet, indicando que tengo un registro.
            if (rs.next()) {
                //Me ubico en el resultado y actualizo los campos
                rs.updateString("descripcion", car.getDescripcion());

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
     * Metodo que recibe como parametro un Objeto tipo Cargo y lo BORRA
     * en la base de datos en la tabla cargos
     *
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isDelete(Object o) {
        Cargo car = (Cargo) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + car.getId());

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
     * Metodo que BORRA TODos los Cargos en la base de datos
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
     * Cargos
     *
     * @param query
     * @return ArrayList
     */
    @Override
    public ArrayList list(String query) {
        ArrayList<Cargo> list = new ArrayList<Cargo>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Cargo cargo = new Cargo(rs.getInt("id"), rs.getString("descripcion"));
                list.add(cargo);
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
     * Metodo que recibe un id de Cargo y retorna un Objeto de tipo
     * Cargo, si no encuentra el cargo el Objeto que retorna es
     * nulo (null)
     *
     * @param id
     * @return Location
     */
    public Cargo getCargo(int id) {
        Cargo car = null;
        //Saber si me puedo Conectar
        if (isOkConexion()) {
            //Genero consulta SQL
            String query = "SELECT * FROM " + getTabla() + " WHERE id=" + id;
            //Reutilizo metodo de cargosList con SQL en busqueda de cargos
            ArrayList<Cargo> cargosList = list(query);
            isCloseConexion();//Cierro Conexion

            //Pregunto si el ArrayList en respuesta del metodo list(***) tiene un Objeto
            if (cargosList.size() == 1) {
                //Si tiene acceso a la primer posicion
                Cargo cList = cargosList.get(0);
                //hago una nueva instancia de car(Cargo) con el valor de la posicion 0
                car = new Cargo(cList.getId(), cList.getDescripcion());
            }
        }
        //Retorno el valor del Objeto car
        return car;
    }
}
