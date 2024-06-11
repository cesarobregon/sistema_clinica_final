/*
 * Boton Salir
 */
package botones;

import static sistema_clinica_obregon.JFrame_Sistema.iconos;


/**
 *
 * @author cesar
 */
public class BtnExit extends Btn{
    
    public BtnExit(){
        setText("Salir");
        setIcon(iconos.getExit(32));
    }
    
}
