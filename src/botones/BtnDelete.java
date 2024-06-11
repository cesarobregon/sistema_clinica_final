/*
 * Boton Borrar
 */
package botones;

import static sistema_clinica_obregon.JFrame_Sistema.iconos;


/**
 *
 * @author cesar
 */
public class BtnDelete extends Btn{
    
    public BtnDelete(){
        setText("Borrar");
        setIcon(iconos.getDelete(16));
    }
    
}
