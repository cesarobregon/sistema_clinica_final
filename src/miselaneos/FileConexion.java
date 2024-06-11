/*
 * Clase para crear archivo de configuracion para base de datos
 */
package miselaneos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author cesar
 */
public class FileConexion {

    static final String SEP = System.getProperty("file.separator");
    static final String PATHSYS = new File("").getAbsolutePath() + SEP;
    static final String FILECONFIG = "fileConfig.properties";

    private Properties propiedades;

    public Properties getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Properties propiedades) {
        this.propiedades = propiedades;
    }
    
    public FileConexion(){
        setPropiedades(new Properties());
    }

    public HashMap getEstructura() {
        HashMap map = new HashMap();
        map.put("hostname", "localhost");
        map.put("port", "3306");
        map.put("nameDB", "");
        map.put("user", "root");
        map.put("pws", "");
        return map;
    }

    public boolean createFile() {
        boolean isOk = false;
        FileWriter archivoConfig = null;
        FileInputStream fileConfig = null;
        Properties propConfig = new Properties();
        try {
            //Crear Archivo en disco
            archivoConfig = new FileWriter(PATHSYS + FILECONFIG);

            //Se carga el archivo a un File para poder usar el metodo load
            fileConfig = new FileInputStream(PATHSYS + FILECONFIG);

            //Con el metodo load cargamos el objeto properties
            propConfig.load(fileConfig);

            //Ahora agregamos informacion al properties
            //mediante un Hashmap
            propConfig.putAll(getEstructura());

            //Salvamos los datos en el properties y listo
            propConfig.store(new FileOutputStream(PATHSYS + FILECONFIG), FILECONFIG);

            archivoConfig.close();
            isOk = true;

        } catch (IOException ex) {

        }finally{
            return isOk;
        }
    }

    public Boolean loadFile() {
        Boolean ok = true;
        FileReader archivoConfig = null;
        FileInputStream fileConfig = null;
        try {
            String file = PATHSYS + FILECONFIG;
            //Crear Archivo en disco
            archivoConfig = new FileReader(file);

            //Se carga el archivo a un File para poder usar el metodo load
            fileConfig = new FileInputStream(file);

            //Con el metodo load cargamos el objeto properties
            propiedades.load(fileConfig);

            archivoConfig.close();

        } catch (IOException ex) {
            ok = false;
        } catch (NullPointerException ex) {
            ok = false;
        } finally {
            return ok;
        }
    }

    /**
     * Permite leer todas las propiedades almacenadas en el archivo mostrando su
     * respectivo valor
     */
    public void readPropiedades() {
        // Obtengo una enumeracion de llaves, cada llave permitira
        // obtener un valor dentro del properties
        Enumeration llaves = propiedades.keys();
        // Recorro llave por llave y obtengo su valor
        while (llaves.hasMoreElements()) {
            String llave = (String) llaves.nextElement();
            System.out.println(llave + "=" + propiedades.getProperty(llave));
        }
    }

    /**
     * Obtiene el valor de una propiedad determinada y que exista en el archivo
     * de propiedades, de lo contrario retornara null
     *
     * @param propiedad
     * @return
     */
    public String getValorPropiedad(String propiedad) {
        return propiedades.getProperty(propiedad);
    }

    public void setValorPropiedad(String key, String value) {
        FileReader archivoConfig = null;
        FileInputStream fileConfig = null;
        try {
            //Crear Archivo en disco
            archivoConfig = new FileReader(PATHSYS + FILECONFIG);

            //Se carga el archivo a un File para poder usar el metodo load
            fileConfig = new FileInputStream(PATHSYS + FILECONFIG);

            //Con el metodo load cargamos el objeto properties
            propiedades.load(fileConfig);

            propiedades.setProperty(key, value);

            propiedades.store(new FileOutputStream(PATHSYS + FILECONFIG), FILECONFIG);

            archivoConfig.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
