package miselaneos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author cesar
 */
public abstract class Conexion {

    //Información necesaria para realizar la conexion
    private FileConexion fileConect;
    static final String DRIVER = "com.mysql.jdbc.Driver"; // Nombre del driver de MySQL 
    private String servidor = "localhost"; // Servidor donde se ejecuta el servicio de MySQL. Puede ser un nombre o un numero de IP
    private String DB = ""; // Nombre de la base de datos donde se va a conectar
    private String puerto = "3306"; // Puerto de escucha en el servidor de base de datos
    private String usuario = ""; // Usuario que tiene acceso a la base de datos
    private String pws = ""; // Contraseña de usuario
    private String URL = "jdbc:mysql://" + servidor + ":" + puerto + "/" + DB + "?" + "user=" + usuario + "&password=" + pws; // Direccion que se pasa
    // al driver de conexion para crear una nueva conexion
    private Connection cn; // Objeto conexion que guarda toda la informacion necesaria para conectarse con la base de datos
    private String tabla;
    private Encripta encry;

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Encripta getEncry() {
        return encry;
    }

    public void setEncry(Encripta encry) {
        this.encry = encry;
    }

    public Conexion() {
        fileConect = new FileConexion();
        setEncry(new Encripta());
    }

    public FileConexion getFileConect() {
        return fileConect;
    }

    public void setFileConect(FileConexion fileConect) {
        this.fileConect = fileConect;
    }

    public boolean isLoadFileCofig() {
        //para cargar la informacion del archivo
        boolean isOk = getFileConect().loadFile();
        if (!isOk) {
            //sino puede cargar intenta crear el archivo
            isOk = getFileConect().createFile();
        }
        if (isOk) {
            isOk = false;
            if (getFileConect().loadFile()) {
                //si puede cargar el archivo, isOk lo pongo en false para continuar
                //evaluado el metodo y saber si va a ser exitoso

                //Accedo y cargo la informacion del archivo con el metodo getValorPropiedad
                this.servidor = getFileConect().getValorPropiedad("hostname");
                this.DB = getFileConect().getValorPropiedad("nameDB");
                this.puerto = getFileConect().getValorPropiedad("port");
                this.usuario = getFileConect().getValorPropiedad("user");
                this.pws = this.getEncry().desencrypt(getFileConect().getValorPropiedad("pws").trim());
                this.URL = "jdbc:mysql://" + servidor + ":" + puerto + "/" + DB + "?" + "user=" + usuario + "&password=" + pws; // Direccion que se para
            }
        }
        return isOk;
    }

    /**
     * Metodo que devuelve verdadero si se puede crear un Objeto Connection
     *
     * @return
     */
    public boolean isOkConexion() {
        Connection cn = null;
        boolean isOk = isLoadFileCofig();
        try {
            Class.forName(DRIVER).newInstance(); // para saber si el driver esta y se puede cargar
            cn = DriverManager.getConnection(URL); // Genera un objeto connexion
            setCn(cn); // se setea guarda la un Objeto connexion en la clase para luego ser usado
            isOk = true; // bandera para poner en true y el metodo isOkConexion() me retorne verdadero
        } catch (Exception e) {
            isOk = false;
            System.out.println(e.getMessage());
        }
        return isOk;
    }

    /**
     * Metodo para deconectar la base de datos
     *
     * @return
     */
    public boolean isCloseConexion() {
        boolean isOk = false;
        try {
            if (getCn() != null && !getCn().isClosed()) {
                getCn().close();
                setCn(null);
                isOk = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return isOk;
        }
    }

    public boolean isCancelConexion() {
        boolean isOk = false;
        try {
            if (getCn() != null && !getCn().isClosed()) {
                getCn().rollback();
                getCn().close();
                setCn(null);
                isOk = true;
            }
        } catch (SQLException e) {
            setCn(null);
            e.printStackTrace();
        } finally {
            return isOk;
        }
    }

    /**
     * Metodo para evaluar si el archivo de configuracion esta creado y tiene la
     * informacion correcta para conectarse a la base de datos. Sino es así, por
     * otro medio se debe cargar la informacion correcta. ESTE METODO SE USARIA
     * INICIO DEL SISTEMA PARA CONTROLAR LA INFORMACION.-
     *
     * @return true si esta todo en codiciones para usar la configuracion del
     * archivo
     */
    public boolean isOkFileCofigConexion() {
        //para cargar la informacion del archivo
        boolean isOk = getFileConect().loadFile();
        if (!isOk) {
            //sino puede cargar intenta crear el archivo
            isOk = getFileConect().createFile();
        }
        if (isOk) {
            isOk = false;
            if (getFileConect().loadFile()) {
                //si puede cargar el archivo, isOk lo pongo en false para continuar
                //evaluado el metodo y saber si va a ser exitoso

                //Accedo y cargo la informacion del archivo con el metodo getValorPropiedad
                this.servidor = getFileConect().getValorPropiedad("hostname");
                this.DB = getFileConect().getValorPropiedad("nameDB");
                this.puerto = getFileConect().getValorPropiedad("port");
                this.usuario = getFileConect().getValorPropiedad("user");
                this.pws = this.getEncry().desencrypt(getFileConect().getValorPropiedad("pws").trim());
                this.URL = "jdbc:mysql://" + servidor + ":" + puerto + "/" + DB + "?" + "user=" + usuario + "&password=" + pws; // Direccion que se para
                //Compruebo que se puede conectar con la informacion del archivo de configuracion
                isOk = this.isOkConexion();
                if (isOk) {
                    //Si se puede cierro la conexion
                    this.isCloseConexion();
                }//Si no se puede, debo ver la forma de cargar en algun momento la informacion
                //correcta para la base de datos
            }
        }
        return isOk;
    }

    public abstract boolean isNew(Object o);

    public abstract boolean isUpdate(Object o);

    public abstract boolean isDelete(Object o);

    public abstract boolean isDeleteAll();

    public abstract ArrayList list(String query);

}
