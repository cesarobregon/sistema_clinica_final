package texto;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class TxtImporte extends JFormattedTextField{
    
    public TxtImporte(){
       super(new Double(0.00d));
       setValue(new Double(0.00d));
       setText("0,00");
       setHorizontalAlignment(javax.swing.JTextField.RIGHT);
       
        // Formato de visualización
        NumberFormat dispFormat = NumberFormat.getCurrencyInstance();
        dispFormat.setMinimumFractionDigits(2);

        // Formato de edición: inglés (separador decimal: el punto)
        NumberFormat editFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        editFormat.setMinimumFractionDigits(2);

        // Para la edición, no queremos separadores de millares
        //editFormat.setGroupingUsed(false);
        // Creamos los formateadores de números
        NumberFormatter dnFormat = new NumberFormatter(dispFormat);
        NumberFormatter enFormat = new NumberFormatter(editFormat);

        // Creamos la factoría de formateadores especificando los
        // formateadores por defecto, de visualización y de edición
        DefaultFormatterFactory currFactory = new DefaultFormatterFactory(dnFormat, dnFormat, enFormat);

        // El formateador de edición admite caracteres incorrectos
        enFormat.setAllowsInvalid(false); //en este caso no(false)
       
        // Asignamos la factoría al campo
        setFormatterFactory(currFactory);
    }
    
    public double todouble(){
        double varDouble = 0.0d;
        try{
            varDouble = Double.parseDouble(getValue().toString());
        }catch(NumberFormatException ex){
            varDouble = 0.0d;
        }finally{
            return varDouble;
        }
    }
    
    
}
