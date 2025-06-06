package miselaneos;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import static sistema_clinica_obregon.JFrame_Sistema.jDeskTopSis;

/**
 *
 * @author OBREGON
 */
public class FrmIntern extends javax.swing.JInternalFrame {
    
    private boolean isMem = false;

    /**
     * Creates new form FrmIntern
     */
    public FrmIntern() {
        initComponents();
    }
    
    public void _show(){
        Dimension deskTopSis = jDeskTopSis.size();
        Dimension formInterno = getSize();
        
        if(!isMem){
            isMem = true;
        
            setSize(this.getWidth(), this.getHeight());
            
            setLocation((deskTopSis.width - formInterno.width) / 2,
                    (deskTopSis.height - formInterno.height) / 2);
        }
        try{
            if(!isMinimumSizeSet()){
                setSelected(true);
                setIcon(false);
            }
            setVisible(true);
            moveToFront();
            toFront();
        }catch(PropertyVetoException ex){
            System.out.println("Error ventana interna " + '\n' + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setTitle("DEMO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
