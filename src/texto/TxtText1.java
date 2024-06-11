package texto;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/*
 * Clase principal o superclase para uso en campos de texto
 */
/**
 *
 * @author cesar
 */
public class TxtText1 extends JTextField {

    private int lenghtText = 0;

    public TxtText1() {
        setText("");
        ollentes();
    }

    public int getLenghtText() {
        return lenghtText;
    }

    public void setLenghtText(int lenghtText) {
        this.lenghtText = lenghtText;
    }

    public void ollentes() {
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (caracter == '\b') {
                    return;
                }
                if (getLenghtText() != 0) {
                    if (getText().trim().length() == getLenghtText()) {
                        e.consume();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    transferFocus();
                }
            }
        });
    }

}
