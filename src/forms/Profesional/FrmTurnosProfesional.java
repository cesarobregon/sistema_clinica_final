package forms.Profesional;

import datos.Pacientes;
import datos.Turnos;
import entidades.Paciente;
import entidades.Turno;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import miselaneos.FrmIntern;
import static sistema_clinica_obregon.JFrame_Sistema.fe;
import static sistema_clinica_obregon.JFrame_Sistema.iconos;
/**
 *
 * @author cesar
 */
public class FrmTurnosProfesional extends FrmIntern {
    
    public static int id = 7;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;
    private int id_prof;// ID del Profesional
    private int id_esp;// ID del Profesional
    private int id_turno;// ID del Profesional
    
    /**
     * Creates new form FrmProfesional
     */
    public FrmTurnosProfesional() {
        initComponents();
        _loadTurnos();
    }
    
    private void _initTableTurnos() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Especialidad");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre");
        modelo.addColumn("DNI");
        modelo.addColumn("Estado");
        modelo.addColumn("Motivo Turno");
        modelo.addColumn("Diagnostico");
        jTable.setRowSorter(null); //Elimino Filtro
        jTable.setModel(modelo);
    }
    
    //metodo que recibe el id del profesional desde el login
    public void setIdProf(int id){
        this.id_prof = id;
    }
    
    //turnos
    private void _loadTurnos() {
        if (!btnEdit.isEnabled()) {
            return;
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnEdit.setEnabled(false);
                _initTableTurnos();
                ArrayList<Turno> turnosList = new ArrayList<Turno>();
                Turnos cnx = new Turnos();
                if (cnx.isOkConexion()) {
                    turnosList = cnx.list("SELECT * FROM " + cnx.getTabla() + " WHERE profesional_id=" + id_prof + ";");
                    cnx.isCloseConexion();
                }
                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)
                for (int index = 0; index < turnosList.size(); index++) {
                    Turno t = turnosList.get(index);
                    modelo.addRow(t.toObject());
                }
                btnEdit.setEnabled(true);
            }
        });
        t.start();
    }
    
    //esto se usaria para abrir la historia clinica del paciente seleccionado, falta importar las librerias necesarias
    private void _loadHistoriaClinica(){
        
        JOptionPane.showMessageDialog(pnlFicha, "Funcion en Desarrollo", "Aviso", JOptionPane.WARNING_MESSAGE);
        
        String RUTA = System.getProperty("user.dir") + "/src/historias_clinicas";
        String nombredocx = "HistoriaClinica_" + txtDoc + ".docx";
        String nombreodt = "HistoriaClinica_" + txtDoc + ".odt";
        File dir = new File(RUTA);
        
        if(dir.exists() && dir.isDirectory()){
            File[] archivos = dir.listFiles();
            boolean encontrado = false;
            if (archivos!=null){
                for(File archivo:archivos){
                    //si el archivo es .docx
                    if(archivo.isFile() && archivo.getName().equals(nombredocx)){
                        try {
                            FileInputStream fis = new FileInputStream(archivo.getName());
                            //XWPFDocument docx = new XWPFDocument(fis);
                        } catch (Exception e) {
                        }
                        encontrado = true;
                        break;
                    //si el archivo es .odt
                    }else if(archivo.getName().equals(nombreodt)){
                        encontrado = true;
                        break;
                    }
                }
            }
            if(!encontrado){
                //JOptionPane.showMessageDialog(pnlFicha, "No se ha encontrado la Historia Clinica del Paciente", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            System.out.println("El directorio no existe");
        }
    }
    
    private boolean _isValidate(Turno t) {
        boolean isOk = false;
        if(t.getFecha() == null){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Fecha.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(t.getEstado()== 0){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Estado.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        isOk = true;
        return isOk;
    }
    
    private Turno _getTurnoForm() {
        Turno t = new Turno(0, 0, 0, null, "", "", "", 0, "", 0, "", 0, "");
        t.setId(id_turno);
        t.setIdProfesional(id_prof);
        t.setIdEspecialidad(id_esp);
        t.setFecha(fe.getFechaACalendar(txtFecha.getDate()));
        t.setApellidoPaciente(txtApellido.getText().trim());
        t.setNombrePaciente(txtNombre.getText().trim());
        t.setNroDocumentoPaciente(txtDoc.getText().trim());
        t.setObraSocial(pnlComboObrasSociales._getObraSelected().getId());
        if(jCheckAtencion.isSelected()){
            t.setAtencionPart(1);
            t.setObraSocial(0);
        }else{
            t.setAtencionPart(0);
        }
        t.setNroCredencial(txtCredencial.getText().trim());
        t.setEstado(intEstado.toEntero());
        t.setMotivoTurno(txtMotivo.getText().trim());
        t.setDiagnostico(txtDiagnostico.getText().trim());
        return t;
    }
    

    private void _setForm(Turno t, Paciente p) {
        id_turno = t.getId();
        id_esp = t.getIdEspecialidad();
        if(t.getFecha() == null){
            t.setFecha(Calendar.getInstance());
        }
        txtFecha.setDate(t.getFecha().getTime());
        txtApellido.setText(t.getApellidoPaciente().trim());
        txtNombre.setText(t.getNombrePaciente().trim());
        txtDoc.setText(t.getNroDocumentoPaciente().trim());
        txtDomicilio.setText(p.getDomicilio());
        txtProvincia.setText(p.getProvincia());
        txtLocalidad.setText(p.getLocalidad());
        txtCel.setText(p.getCelular());
        txtTel.setText(p.getTelefono());
        txtEmail.setText(p.getEmail());
        pnlComboObrasSociales._setObraSelected(t.getObraSocial() + 1);
        txtCredencial.setText(t.getNroCredencial());
        intEstado.setText(String.valueOf(t.getEstado()));
        if(t.getAtencionPart() == 1){
            jCheckAtencion.setSelected(true);
        }
        txtMotivo.setText(t.getMotivoTurno());
        txtDiagnostico.setText(t.getDiagnostico());
        
        txtApellido.requestFocus();
        txtNombre.requestFocus();
        txtDoc.requestFocus();
    }
    
    private void _selectionRow() {
        try {
            int indexRow = jTable.getSelectedRow();
            int indexModel = jTable.convertRowIndexToModel(indexRow);
            int id = (int) modelo.getValueAt(indexModel, 0);
            String dni = (String) modelo.getValueAt(indexModel, 4);

            Turno t = new Turno();
            Paciente p = new Paciente();
            Turnos cnx_t = new Turnos();
            Pacientes cnx_p = new Pacientes();
            if (cnx_t.isOkConexion()) {
                t = cnx_t.getTurno(id);
                cnx_t.isCloseConexion();
            }
            if (cnx_p.isOkConexion()) {
                p = cnx_p.getPaciente(dni);
                cnx_p.isCloseConexion();
            }
            _setForm(t, p);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void _filter(String texto){
        try{
            orden = new TableRowSorter<TableModel>(modelo);
            this.jTable.setRowSorter(orden);
            RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(texto.trim());
            orden.setRowFilter(filtro);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }    
    }
    
    private boolean _isUpdate() {
        boolean isOk = false;
        Turno t = _getTurnoForm();
        if (_isValidate(t)) {
            //Esta en codiciones
            Turnos cnx = new Turnos();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(t);
                if (isOk) {
                    cnx.isCloseConexion();
                    JOptionPane.showMessageDialog(pnlFicha, "Turno Modificado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        pnlListaPacientes = new javax.swing.JPanel();
        pnlFilList1 = new javax.swing.JPanel();
        lblFilter2 = new etiquetas.LblFilter();
        txtFilter1 = new texto.TxtMayusculas();
        pnlList1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        progressPacientes = new javax.swing.JProgressBar();
        pnlFichaPaciente = new javax.swing.JPanel();
        pnlFicha = new javax.swing.JPanel();
        lblCheck1 = new etiquetas.LblCheck();
        lblCheck2 = new etiquetas.LblCheck();
        lblCheck3 = new etiquetas.LblCheck();
        lblCheck4 = new etiquetas.LblCheck();
        txtApellido = new texto.TxtMayusculas();
        txtNombre = new texto.TxtMayusculas();
        txtDomicilio = new texto.TxtMayusculas();
        lblCheck6 = new etiquetas.LblCheck();
        txtLocalidad = new texto.TxtMayusculas();
        txtDoc = new javax.swing.JTextField();
        lblCheck7 = new etiquetas.LblCheck();
        txtCel = new texto.TxtMayusculas();
        lblCheck8 = new etiquetas.LblCheck();
        txtTel = new texto.TxtMayusculas();
        lblCheck9 = new etiquetas.LblCheck();
        txtEmail = new texto.TxtMayusculas();
        lblCheck5 = new etiquetas.LblCheck();
        txtProvincia = new texto.TxtMayusculas();
        btnEdit = new botones.BtnEdit();
        lblCheck10 = new etiquetas.LblCheck();
        lblCheck13 = new etiquetas.LblCheck();
        lblCheck12 = new etiquetas.LblCheck();
        txtCredencial = new texto.TxtMayusculas();
        intEstado = new texto.TxtNro();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMotivo = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiagnostico = new javax.swing.JTextArea();
        btnHistoriaClinica1 = new botones.BtnHistoriaClinica();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        lblCheck14 = new etiquetas.LblCheck();
        pnlComboObrasSociales = new forms.turnos.pnlComboObrasSociales();
        jCheckAtencion = new javax.swing.JCheckBox();

        setTitle("Profesional");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        pnlListaPacientes.setLayout(new java.awt.BorderLayout());

        pnlFilList1.setPreferredSize(new java.awt.Dimension(762, 40));

        lblFilter2.setText("Filtrar:");

        txtFilter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilter1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlFilList1Layout = new javax.swing.GroupLayout(pnlFilList1);
        pnlFilList1.setLayout(pnlFilList1Layout);
        pnlFilList1Layout.setHorizontalGroup(
            pnlFilList1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilList1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(571, Short.MAX_VALUE))
        );
        pnlFilList1Layout.setVerticalGroup(
            pnlFilList1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFilList1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFilList1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFilter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlListaPacientes.add(pnlFilList1, java.awt.BorderLayout.NORTH);

        pnlList1.setLayout(new java.awt.BorderLayout());

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(jTable);

        pnlList1.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        progressPacientes.setVisible(false);
        pnlList1.add(progressPacientes, java.awt.BorderLayout.PAGE_START);

        pnlListaPacientes.add(pnlList1, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Listado de Turnos", iconos.getList(16)
            , pnlListaPacientes);

        pnlFichaPaciente.setLayout(new java.awt.BorderLayout());

        lblCheck1.setText("Apellido:");

        lblCheck2.setText("Nombre(s):");

        lblCheck3.setText("Documento:");

        lblCheck4.setText("Domicilio:");

        txtApellido.setEditable(false);
        txtApellido.setLenghtText(25);

        txtNombre.setEditable(false);
        txtNombre.setLenghtText(30);

        txtDomicilio.setEditable(false);
        txtDomicilio.setLenghtText(60);

        lblCheck6.setText("Localidad");

        txtLocalidad.setEditable(false);
        txtLocalidad.setLenghtText(60);

        txtDoc.setEditable(false);

        lblCheck7.setText("Nro. Celular");

        txtCel.setEditable(false);
        txtCel.setLenghtText(60);

        lblCheck8.setText("Nro. Telefono");

        txtTel.setEditable(false);
        txtTel.setLenghtText(60);

        lblCheck9.setText("Email");

        txtEmail.setEditable(false);
        txtEmail.setLenghtText(60);

        lblCheck5.setText("Provincia:");

        txtProvincia.setEditable(false);
        txtProvincia.setLenghtText(60);

        btnEdit.setText("Guardar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        lblCheck10.setText("Nro Credencial");

        lblCheck13.setText("Estado");

        lblCheck12.setText("Obra Social");

        txtCredencial.setEditable(false);
        txtCredencial.setLenghtText(25);

        intEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtMotivo.setColumns(20);
        txtMotivo.setRows(5);
        jScrollPane3.setViewportView(txtMotivo);

        txtDiagnostico.setColumns(20);
        txtDiagnostico.setRows(5);
        jScrollPane2.setViewportView(txtDiagnostico);

        btnHistoriaClinica1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoriaClinica1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Motivo del Turno");

        jLabel2.setText("Diagnostico");

        lblCheck14.setText("Fecha");

        jCheckAtencion.setText("ATENCION PARTICULAR");

        javax.swing.GroupLayout pnlFichaLayout = new javax.swing.GroupLayout(pnlFicha);
        pnlFicha.setLayout(pnlFichaLayout);
        pnlFichaLayout.setHorizontalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(330, 330, 330)
                .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(337, 337, 337))
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(lblCheck13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(659, 659, 659))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addGap(219, 219, 219)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addGap(184, 184, 184))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFichaLayout.createSequentialGroup()
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnHistoriaClinica1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlFichaLayout.createSequentialGroup()
                                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane3)
                                    .addGroup(pnlFichaLayout.createSequentialGroup()
                                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCheck7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(60, 60, 60)
                                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDoc)
                                            .addComponent(txtCel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(pnlComboObrasSociales, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(intEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFichaLayout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(jScrollPane2))
                                    .addGroup(pnlFichaLayout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCheck10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck14, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtTel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtCredencial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jCheckAtencion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addGap(40, 40, 40))))
        );
        pnlFichaLayout.setVerticalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblCheck2, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(lblCheck1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addGap(17, 17, 17)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblCheck3, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addComponent(txtDoc)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(lblCheck4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(txtDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addGap(17, 17, 17)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblCheck5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(7, 7, 7))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(txtProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblCheck6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(txtLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addGap(16, 16, 16)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(txtTel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCheck7, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                            .addComponent(lblCheck8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)))
                .addGap(17, 17, 17)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblCheck9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCheck14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblCheck12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblCheck13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlComboObrasSociales, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(jCheckAtencion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(intEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCredencial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(14, 14, 14)
                .addComponent(btnHistoriaClinica1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        pnlFichaPaciente.add(pnlFicha, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Ficha del Paciente", pnlFichaPaciente);

        getContentPane().add(jTabbedPane);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadTurnos();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void txtFilter1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilter1KeyReleased
        _filter(txtFilter1.getText().trim());
    }//GEN-LAST:event_txtFilter1KeyReleased

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        _selectionRow();
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased
        _selectionRow();
    }//GEN-LAST:event_jTableKeyReleased

    private void btnHistoriaClinica1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoriaClinica1ActionPerformed
        _loadHistoriaClinica();
    }//GEN-LAST:event_btnHistoriaClinica1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnEdit btnEdit;
    private botones.BtnHistoriaClinica btnHistoriaClinica1;
    private texto.TxtNro intEstado;
    private javax.swing.JCheckBox jCheckAtencion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable;
    private etiquetas.LblCheck lblCheck1;
    private etiquetas.LblCheck lblCheck10;
    private etiquetas.LblCheck lblCheck12;
    private etiquetas.LblCheck lblCheck13;
    private etiquetas.LblCheck lblCheck14;
    private etiquetas.LblCheck lblCheck2;
    private etiquetas.LblCheck lblCheck3;
    private etiquetas.LblCheck lblCheck4;
    private etiquetas.LblCheck lblCheck5;
    private etiquetas.LblCheck lblCheck6;
    private etiquetas.LblCheck lblCheck7;
    private etiquetas.LblCheck lblCheck8;
    private etiquetas.LblCheck lblCheck9;
    private etiquetas.LblFilter lblFilter2;
    private forms.turnos.pnlComboObrasSociales pnlComboObrasSociales;
    private javax.swing.JPanel pnlFicha;
    private javax.swing.JPanel pnlFichaPaciente;
    private javax.swing.JPanel pnlFilList1;
    private javax.swing.JPanel pnlList1;
    private javax.swing.JPanel pnlListaPacientes;
    private javax.swing.JProgressBar progressPacientes;
    private texto.TxtMayusculas txtApellido;
    private texto.TxtMayusculas txtCel;
    private texto.TxtMayusculas txtCredencial;
    private javax.swing.JTextArea txtDiagnostico;
    private javax.swing.JTextField txtDoc;
    private texto.TxtMayusculas txtDomicilio;
    private texto.TxtMayusculas txtEmail;
    private com.toedter.calendar.JDateChooser txtFecha;
    private texto.TxtMayusculas txtFilter1;
    private texto.TxtMayusculas txtLocalidad;
    private javax.swing.JTextArea txtMotivo;
    private texto.TxtMayusculas txtNombre;
    private texto.TxtMayusculas txtProvincia;
    private texto.TxtMayusculas txtTel;
    // End of variables declaration//GEN-END:variables
}
