package datos;

import com.mysql.jdbc.Statement;
import entidades.Paciente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import miselaneos.Conexion;

/**
 *
 * @author OBREGON
 */
public class Pacientes extends Conexion{

    public Pacientes() {
        setTabla("pacientes");
    }

    /**
     * Metodo que recibe como parametro un Objeto tipo Paciente y lo REGISTRA en
     * la base de datos en la tabla pacientes
     *
     * @param c
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isNew(Object o) {
        Paciente p = (Paciente) o;
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
            rs.updateString("apellido", p.getApellido());
            rs.updateString("nombre", p.getNombre());
            rs.updateString("domicilio", p.getDomicilio());
            rs.updateString("dni_paciente", p.getNroDocumento());
            rs.updateString("localidad", p.getLocalidad());
            rs.updateString("provincia", p.getProvincia());
            rs.updateString("celulares", p.getCelular());
            rs.updateString("telefonos", p.getTelefono());
            rs.updateString("emails", p.getEmail());

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
     * Metodo que recibe como parametro un Objeto tipo Paciente y lo ACTUALIZA
     * en la base de datos en la tabla pacientes
     *
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isUpdate(Object o) {
        Paciente p = (Paciente) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + p.getId());

            //Metodo next() que indica si puedo ir al nuevo resultado del ResultSet, indicando que tengo un registro.
            //Pueden investigar si funciona tambien con el metodo rs.first()
            if (rs.next()) {
                //Me ubico en el resultado y actualizo los campos
                rs.updateString("apellido", p.getApellido());
                rs.updateString("nombre", p.getNombre());
                rs.updateString("domicilio", p.getDomicilio());
                rs.updateString("dni_paciente", p.getNroDocumento());
                rs.updateString("localidad", p.getLocalidad());
                rs.updateString("provincia", p.getProvincia());
                rs.updateString("celulares", p.getCelular());
                rs.updateString("telefonos", p.getTelefono());
                rs.updateString("emails", p.getEmail());

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
     * Metodo que recibe como parametro un Objeto tipo Paciente y lo BORRA en la
     * base de datos en la tabla pacientes
     *
     * @param l
     * @return true=registra, false=presencia de error
     */
    @Override
    public boolean isDelete(Object o) {
        Paciente p = (Paciente) o;
        boolean isOk = false;
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM " + getTabla() + " WHERE id=" + p.getId());

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
     * Metodo que BORRA TODOS los pacientes en la base de datos
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
     * Paciente
     *
     * @param query
     * @return ArrayList
     */
    @Override
    public ArrayList list(String query) {
        ArrayList<Paciente> list = new ArrayList<Paciente>();
        try {
            Statement st = (Statement) this.getCn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Paciente paciente = new Paciente(rs.getInt("id"), rs.getString("apellido"),
                        rs.getString("nombre"), rs.getString("domicilio"), rs.getString("dni_paciente"),
                        rs.getString("localidad"), rs.getString("provincia"), rs.getString("celulares"),
                        rs.getString("telefonos"), rs.getString("emails"));

                list.add(paciente);
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
     * Metodo que recibo codigo de Paciente y retorna un Objeto de tipo
     * Paciente, sino encuentra el paciente el Objeto que retorna es nulo (null)
     *
     * @param id
     * @return Paciente
     */
    public Paciente getPaciente(String dni) {
        Paciente p = null;
        //Saber si me puedo Conectar
        if (isOkConexion()) {
            //Genero consulta SQL
            String query = "SELECT * FROM " + getTabla() + " WHERE dni_paciente =" + dni;
            //Reutilizo metodo de listPacientes con SQL en busqueda de client
            ArrayList<Paciente> pacientesList = list(query);
            isCloseConexion();//Cierro Conexion

            //Pregunto si el ArrayList en respuesta del metodo listPacientes(***) tiene un Objeto
            if (pacientesList.size() == 1) {
                //Si tiene acceso a la primer posicion
                Paciente pList = pacientesList.get(0);
                //hago una nueva instancia de p(Pacientes) con el valor de la posicion 0
                p = new Paciente(pList.getId(), pList.getApellido(), pList.getNombre(), pList.getDomicilio(),
                        pList.getNroDocumento(), pList.getLocalidad(), pList.getProvincia(), pList.getCelular(),
                        pList.getTelefono(), pList.getEmail());
            }
        }
        //Retorno el valor del Objeto p
        return p;
    }
}
