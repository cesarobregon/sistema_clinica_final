/*
 *Clase para trabajar con imagenes para los iconos que voy a necesitar 
 *luego en la clase JButtons
 */
package miselaneos;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author cesar
 */
public class Imagenes {
    
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

    public Image getBackGround(){
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/background.jpg"));
        return icono.getImage();
    }
    
    public Image getBackGroundIntern(){
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/backgroundIntern.png"));
        return icono.getImage();
    }
  
}
