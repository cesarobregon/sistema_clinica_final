/*
 * Clase base o super clase para la creacion de mis propios botones
 */
package botones;

import javax.swing.JButton;

/**
 *
 * @author cesar
 */
public class Btn extends JButton{
    
    public Btn(){
        setFont(new java.awt.Font("Thaoma", 1, 10));
        //Modificando la super clase, se van a modificar todos los demas botones
    }
}