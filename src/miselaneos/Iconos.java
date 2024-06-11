package miselaneos;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author OBREGON
 */
public class Iconos {

    /**
     * Metodo que recibe un valor entero usado para el ancho, otro para el alto
     * y por ultimo una clase ImageIcon. Estos valores son usados para lograr
     * una imagen redimensionada.-
     *
     * @param x
     * @param y
     * @param icono
     * @return
     */
    public ImageIcon getSizeIcon(int x, int y, ImageIcon icono) {
        ImageIcon ImagenIconizable = icono;
        Image imgTrabajar = ImagenIconizable.getImage();
        Image imagenIconizable = imgTrabajar.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenIconizable);
    }

    /**
     * Metodo que recibe el tamaño que se va a usar tanto para ancho y alto de
     * la imagen
     *
     * @param size
     * @return
     */
    public ImageIcon getSys(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/sys.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getAdd(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/add.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getEdit(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/edit.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getDelete(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/delete.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getList(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/list.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getFilter(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/filter.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getUser(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/user.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getCargos(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Cargos.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getHorario(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Horario.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getProfesionales(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Profesional.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getProfEsp(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/prof_esp.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getAdmin(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Admin.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getAdministrador(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Administrador.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getSistema(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Sistema.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getDatabase(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Database.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getHistoriaClinica(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/HistoriaClinica.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getClient(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/client.png"));
        return getSizeIcon(size, size, icono);
    }
    public ImageIcon getPaciente(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Paciente.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getProfesional(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Profesional.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getPersonal(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Personal.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getEspecialidad(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Especialidad.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getTurnos(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Turnos.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getObrasocial(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/Obrasocial.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getExit(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/exit.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getKey(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/key.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getUpdate(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/update.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getCheck(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/check.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getClock(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/clock.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getAcept(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/acept.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getRevision(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/revision.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getSetting(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/setting.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getCancel(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/cancel.png"));
        return getSizeIcon(size, size, icono);
    }
    
}
