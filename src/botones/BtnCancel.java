/*
 * Boton cancelar
 */
package botones;

import static sistema_clinica_obregon.JFrame_Sistema.iconos;


/**
 *
 * @author cesar
 */
public class BtnCancel extends Btn{
    
    public BtnCancel(){
        setText("Cancelar");
        setIcon(iconos.getCancel(16));
    }
    
}
