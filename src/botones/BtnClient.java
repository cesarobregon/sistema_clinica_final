/*
 * Boton Cliente
 */
package botones;

import static sistema_clinica_obregon.JFrame_Sistema.iconos;

/**
 *
 * @author cesar
 */
public class BtnClient extends Btn{
    
    public BtnClient(){
        setText("Cliente");
        setIcon(iconos.getClient(16));
    }
    
}
