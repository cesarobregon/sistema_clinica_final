/*
 * SuperClase para etiquetas o JLabel
 */
package etiquetas;

import javax.swing.JLabel;

import static sistema_clinica_obregon.JFrame_Sistema.iconos;

/**
 *
 * @author pablo
 */
public class LblFilter extends JLabel{
    
    public LblFilter(){
        setText("Filrar");
        setIcon(iconos.getFilter(16));
    }
    
}
