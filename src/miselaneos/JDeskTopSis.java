package miselaneos;


import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JDesktopPane;
import static sistema_clinica_obregon.JFrame_Sistema.imgs;

/**
 *
 * @author cesar
 */
public class JDeskTopSis extends JDesktopPane {
    private Image backGround;

    public JDeskTopSis() {
        backGround = imgs.getBackGround();
    }
    
    
    //el metodo paintComponent es un metodo de JDesktopPane que sobre escribo para redimensionar la imagen de fondo
    //y actualiar en el objeto.
    //JDesktopPane extiendo de JLayeredPane y a su ves este, extiende de Component; en este objeto se encuentra el metodo paintComponent
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, (int) getWidth(), (int) getHeight(), null);
    }
}
