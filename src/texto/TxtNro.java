package texto;


import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class TxtNro extends JTextField{
    
    public TxtNro(){
       super(new Integer(0));
       setText("0");
       
        //Agregar Oyente para que solo ingrese Numeros
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                if(!Character.isDigit(e.getKeyChar()) && !Character.isISOControl(e.getKeyChar())){
                    Toolkit.getDefaultToolkit().beep();
                    e.consume();
                }
            }
        });
    }
    
    public Integer toEntero(){
        Integer varEntero = 0;
        try{
            varEntero = Integer.parseInt(getText().trim());
        }catch(NumberFormatException ex){
            varEntero = 0;
        }finally{
            return varEntero;
        }
    }
}
