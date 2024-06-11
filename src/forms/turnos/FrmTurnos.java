package forms.turnos;

import datos.Pacientes;
import datos.Turnos;
import entidades.Paciente;
import entidades.Turno;
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
 * @author OBREGON
 */
public class FrmTurnos extends FrmIntern {
    
    public static int id = 16;
    private DefaultTableModel modeloTurnos;
    private DefaultTableModel modeloPacientes;
    private TableRowSorter<TableModel> orden;

    /**
     * Creates new form FrmTurnos
     */
    public FrmTurnos() {
        initComponents();
        _loadTurnos();
        _loadPacientes();
    }
    
    
    private void _initTableTurnos() {
        modeloTurnos = new DefaultTableModel();
        modeloTurnos.addColumn("#");
        modeloTurnos.addColumn("Especialidad");
        modeloTurnos.addColumn("Apellido");
        modeloTurnos.addColumn("Nombre");
        modeloTurnos.addColumn("DNI");
        modeloTurnos.addColumn("Estado");
        modeloTurnos.addColumn("Motivo Turno");
        modeloTurnos.addColumn("Diagnostico");
        jTableTurnos.setRowSorter(null); //Elimino Filtro
        jTableTurnos.setModel(modeloTurnos);
    }
    
    private void _initTablePacientes() {
        modeloPacientes = new DefaultTableModel();
        modeloPacientes.addColumn("Id");
        modeloPacientes.addColumn("Apellido");
        modeloPacientes.addColumn("Nombre");
        modeloPacientes.addColumn("Domicilio");
        modeloPacientes.addColumn("Dni");
        modeloPacientes.addColumn("Localidad");
        modeloPacientes.addColumn("Provincia");
        modeloPacientes.addColumn("Celular");
        modeloPacientes.addColumn("Telefono");
        modeloPacientes.addColumn("Email");
        
        jTablePacientes.setRowSorter(null); //Elimino Filtro
        jTablePacientes.setModel(modeloPacientes);
    }
    
    private void _loadTurnos() {
        if (!btnNewAgendar.isEnabled()) {
            return;
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                
                _initTableTurnos();

                ArrayList<Turno> turnosList = new ArrayList<Turno>();
                Turnos cnx = new Turnos();
                
                if (cnx.isOkConexion()) {
                    turnosList = cnx.list("SELECT * FROM " + cnx.getTabla());
                    cnx.isCloseConexion();
                }

                modeloTurnos.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < turnosList.size(); index++) {
                    Turno t = turnosList.get(index);
                    
                    modeloTurnos.addRow(t.toObject());
                }
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });
        t.start();
    }
    
    private void _loadPacientes() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNewAgendar.setEnabled(false);
                
                _initTablePacientes();

                ArrayList<Paciente> pacientesList = new ArrayList<Paciente>();
                Pacientes cnx = new Pacientes();
                
                if (cnx.isOkConexion()) {
                    pacientesList = cnx.list("SELECT * FROM " + cnx.getTabla());
                    cnx.isCloseConexion();
                }
                
                modeloPacientes.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < pacientesList.size(); index++) {
                    Paciente p = pacientesList.get(index);
                    modeloPacientes.addRow(p.toObject());
                }
                
                btnNewAgendar.setEnabled(true);
            }
        });
        t.start();
    }
    
    private boolean _isValidate(Turno t) {
        boolean isOk = false;
        if(t.getIdProfesional() == 0) {
            JOptionPane.showMessageDialog(PanelPrincipal, "No Ha Ingresado Profesional.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(t.getIdEspecialidad() == 0){
            JOptionPane.showMessageDialog(PanelPrincipal, "No Ha Ingresado Especialidad.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(t.getFecha() == null){
            JOptionPane.showMessageDialog(PanelPrincipal, "No Ha Ingresado Fecha.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(t.getApellidoPaciente().trim().isEmpty()) {
            JOptionPane.showMessageDialog(PanelPrincipal, "No Ha Ingresado Apellido.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(t.getNombrePaciente().trim().isEmpty()){
            JOptionPane.showMessageDialog(PanelPrincipal, "No Ha Ingresado Nombre.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(t.getNroDocumentoPaciente().trim().isEmpty()){
            JOptionPane.showMessageDialog(PanelPrincipal, "No Ha Ingresado DNI.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(t.getEstado()== 0){
            JOptionPane.showMessageDialog(PanelPrincipal, "No Ha Ingresado Estado.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        isOk = true;
        return isOk;
    }
    
    //
    private Turno _getTurnoForm() {
        Turno t = new Turno(0, 0, 0, null, "", "", "", 0, "", 0, "", 0, "");
        t.setId(txtId1.toEntero());
        t.setIdProfesional(pnlComboProf_Esp1._getProfesionalSelected().getId());
        t.setIdEspecialidad(pnlComboProf_Esp1._getEspecialidadSelected().getId());
        t.setFecha(fe.getFechaACalendar(txtFecha1.getDate()));
        t.setApellidoPaciente(txtApellido1.getText().trim());
        t.setNombrePaciente(txtNombre1.getText().trim());
        t.setNroDocumentoPaciente(txtDni1.getText().trim());
        t.setObraSocial(pnlComboObrasSociales1._getObraSelected().getId());
        if(jCheckAtencion1.isSelected()){
            t.setAtencionPart(1);
            t.setObraSocial(0);
        }else{
            t.setAtencionPart(0);
        }
        t.setNroCredencial(txtCredencial1.getText().trim());
        t.setEstado(intEstado1.toEntero());
        t.setMotivoTurno(txtMotivo1.getText().trim());
        t.setDiagnostico(txtDiagnostico1.getText().trim());
        return t;
    }
    
    private Turno _getTurnoFormAgendar() {
        Turno t = new Turno(0, 0, 0, null, "", "", "", 0, "", 0, "", 0, "");
        t.setId(txtId3.toEntero());
        t.setIdProfesional(pnlComboProf_Esp3._getProfesionalSelected().getId());
        t.setIdEspecialidad(pnlComboProf_Esp3._getEspecialidadSelected().getId());
        t.setFecha(fe.getFechaACalendar(txtFecha3.getDate()));
        t.setApellidoPaciente(txtApellido3.getText().trim());
        t.setNombrePaciente(txtNombre3.getText().trim());
        t.setNroDocumentoPaciente(txtDni3.getText().trim());
        t.setObraSocial(pnlComboObrasSociales3._getObraSelected().getId());
        if(jCheckAtencion3.isSelected()){
            t.setAtencionPart(1);
            t.setObraSocial(0);
        }else{
            t.setAtencionPart(0);
        }
        t.setNroCredencial(txtCredencial3.getText().trim());
        t.setEstado(intEstado3.toEntero());
        t.setMotivoTurno(txtMotivo3.getText().trim());
        t.setDiagnostico(txtDiagnostico3.getText().trim());
        return t;
    }
    
    //formulario de pacientes
    private void _setFormAgendar(Paciente t) {
        txtApellido3.setText(t.getApellido().trim());
        txtNombre3.setText(t.getNombre().trim());
        txtDni3.setText(t.getNroDocumento().trim());
    }
    
    //formulario de turnos
    private void _setForm(Turno t) {
        txtId1.setText(String.valueOf(t.getId()));
        pnlComboProf_Esp1._setProfesionalSelected(t.getIdProfesional());
        pnlComboProf_Esp1._setEspecialidadSelected(t.getIdEspecialidad());
        if(t.getFecha() == null){
            t.setFecha(Calendar.getInstance());
        }
        txtFecha1.setDate(t.getFecha().getTime());
        txtApellido1.setText(t.getApellidoPaciente().trim());
        txtNombre1.setText(t.getNombrePaciente().trim());
        txtDni1.setText(t.getNroDocumentoPaciente().trim());
        pnlComboObrasSociales1._setObraSelected(t.getObraSocial() + 1);
        txtCredencial1.setText(t.getNroCredencial());
        if(t.getAtencionPart() == 1){
            jCheckAtencion1.setSelected(true);
        }
        intEstado1.setText(String.valueOf(t.getEstado()));
        txtMotivo1.setText(t.getMotivoTurno());
        txtDiagnostico1.setText(t.getDiagnostico());
        
        txtId1.requestFocus();
        txtApellido1.requestFocus();
        txtNombre1.requestFocus();
        txtDni1.requestFocus();
    }
    
    private void _selectionRowPacientes() {
        try {
            int indexRow = jTablePacientes.getSelectedRow();
            int indexModel = jTablePacientes.convertRowIndexToModel(indexRow);
            String dni =  (String) modeloPacientes.getValueAt(indexModel, 4);

            Paciente p = new Paciente();
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                p = cnx.getPaciente(dni);
                cnx.isCloseConexion();
            }
            _setFormAgendar(p);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void _selectionRowTurnos() {
        try {
            int indexRow = jTableTurnos.getSelectedRow();
            int indexModel = jTableTurnos.convertRowIndexToModel(indexRow);
            int id = (int) modeloTurnos.getValueAt(indexModel, 0);

            Turno t = new Turno();
            Turnos cnx = new Turnos();
            if (cnx.isOkConexion()) {
                t = cnx.getTurno(id);
                cnx.isCloseConexion();
            }
            _setForm(t);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void _filter(String texto){
        try{
            orden = new TableRowSorter<TableModel>(modeloTurnos);
            this.jTableTurnos.setRowSorter(orden);
            RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(texto.trim());
            orden.setRowFilter(filtro);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }    
    }
    
    private void _filterAgendar(String texto){
        try{
            orden = new TableRowSorter<TableModel>(modeloPacientes);
            this.jTablePacientes.setRowSorter(orden);
            RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(texto.trim());
            orden.setRowFilter(filtro);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }    
    }
    
    private boolean _isNewAgendar() {
        boolean isOk = false;
        Turno t = _getTurnoFormAgendar();
        if (_isValidate(t)) {
            //Esta en codiciones
            Turnos cnx = new Turnos();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(t);
                if (isOk) {
                    cnx.isCloseConexion();
                    JOptionPane.showMessageDialog(pnlDetalleTurnos, "Turno Agendado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
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
                    JOptionPane.showMessageDialog(pnlFicha1, "Turno Modificado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    private boolean _isDelete() {
        boolean isOk = false;
        Turno t = _getTurnoForm();
        if (_isValidate(t)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlFicha1, "¿Desea Eliminar el Turno?.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Turnos cnx = new Turnos();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(t);
                if (isOk) {
                    cnx.isCloseConexion();
                    JOptionPane.showMessageDialog(pnlFicha1, "Turno Eliminado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE);
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

        PanelPrincipal = new javax.swing.JTabbedPane();
        pnlListaTurnos = new javax.swing.JPanel();
        pnlFiltroTurnos = new javax.swing.JPanel();
        lblFilter3 = new etiquetas.LblFilter();
        txtFilterTurnos = new texto.TxtMayusculas();
        btnUpdate = new botones.BtnUpdate();
        BtnAyuda = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTurnos = new javax.swing.JTable();
        pnlFichaTurnos = new javax.swing.JPanel();
        pnlFicha1 = new javax.swing.JPanel();
        id2 = new etiquetas.Id();
        lblCheck2 = new etiquetas.LblCheck();
        lblCheck14 = new etiquetas.LblCheck();
        txtApellido1 = new texto.TxtMayusculas();
        txtNombre1 = new texto.TxtMayusculas();
        txtId1 = new texto.TxtNro();
        lblCheck15 = new etiquetas.LblCheck();
        lblCheck16 = new etiquetas.LblCheck();
        lblCheck17 = new etiquetas.LblCheck();
        txtFecha1 = new com.toedter.calendar.JDateChooser();
        lblCheck18 = new etiquetas.LblCheck();
        lblCheck19 = new etiquetas.LblCheck();
        txtDni1 = new texto.TxtMayusculas();
        lblCheck20 = new etiquetas.LblCheck();
        txtCredencial1 = new texto.TxtMayusculas();
        lblCheck22 = new etiquetas.LblCheck();
        lblCheck23 = new etiquetas.LblCheck();
        lblCheck24 = new etiquetas.LblCheck();
        intEstado1 = new texto.TxtNro();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDiagnostico1 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtMotivo1 = new javax.swing.JTextArea();
        pnlComboObrasSociales1 = new forms.turnos.pnlComboObrasSociales();
        jCheckAtencion1 = new javax.swing.JCheckBox();
        pnlComboProf_Esp1 = new forms.turnos.pnlComboProf_Esp();
        pnlButtons1 = new javax.swing.JPanel();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();
        PanelAgendar = new javax.swing.JTabbedPane();
        pnlListaPacientes = new javax.swing.JPanel();
        pnlFiltroPacientes = new javax.swing.JPanel();
        lblFilter2 = new etiquetas.LblFilter();
        txtFilterAgendar = new texto.TxtMayusculas();
        BtnAyuda1 = new javax.swing.JButton();
        pnlList3 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTablePacientes = new javax.swing.JTable();
        pnlDetalleTurnos = new javax.swing.JPanel();
        pnlFicha3 = new javax.swing.JPanel();
        id4 = new etiquetas.Id();
        lblCheck37 = new etiquetas.LblCheck();
        lblCheck38 = new etiquetas.LblCheck();
        txtApellido3 = new texto.TxtMayusculas();
        txtNombre3 = new texto.TxtMayusculas();
        txtId3 = new texto.TxtNro();
        lblCheck39 = new etiquetas.LblCheck();
        lblCheck40 = new etiquetas.LblCheck();
        lblCheck41 = new etiquetas.LblCheck();
        txtFecha3 = new com.toedter.calendar.JDateChooser();
        lblCheck42 = new etiquetas.LblCheck();
        lblCheck43 = new etiquetas.LblCheck();
        txtDni3 = new texto.TxtMayusculas();
        lblCheck44 = new etiquetas.LblCheck();
        txtCredencial3 = new texto.TxtMayusculas();
        lblCheck46 = new etiquetas.LblCheck();
        lblCheck47 = new etiquetas.LblCheck();
        lblCheck48 = new etiquetas.LblCheck();
        intEstado3 = new texto.TxtNro();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtDiagnostico3 = new javax.swing.JTextArea();
        jScrollPane12 = new javax.swing.JScrollPane();
        txtMotivo3 = new javax.swing.JTextArea();
        pnlComboObrasSociales3 = new forms.turnos.pnlComboObrasSociales();
        jCheckAtencion3 = new javax.swing.JCheckBox();
        pnlComboProf_Esp3 = new forms.turnos.pnlComboProf_Esp();
        pnlButtons3 = new javax.swing.JPanel();
        btnNewAgendar = new botones.BtnNew();

        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        pnlListaTurnos.setLayout(new java.awt.BorderLayout());

        pnlFiltroTurnos.setPreferredSize(new java.awt.Dimension(762, 40));

        txtFilterTurnos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterTurnosKeyReleased(evt);
            }
        });

        btnUpdate.setText("");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        BtnAyuda.setText("Ayuda");
        BtnAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAyudaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFiltroTurnosLayout = new javax.swing.GroupLayout(pnlFiltroTurnos);
        pnlFiltroTurnos.setLayout(pnlFiltroTurnosLayout);
        pnlFiltroTurnosLayout.setHorizontalGroup(
            pnlFiltroTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroTurnosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilterTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 514, Short.MAX_VALUE)
                .addComponent(BtnAyuda)
                .addContainerGap())
        );
        pnlFiltroTurnosLayout.setVerticalGroup(
            pnlFiltroTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroTurnosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltroTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnAyuda, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFilterTurnos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFilter3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlListaTurnos.add(pnlFiltroTurnos, java.awt.BorderLayout.NORTH);

        pnlList.setLayout(new java.awt.BorderLayout());

        jTableTurnos.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableTurnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableTurnosMouseClicked(evt);
            }
        });
        jTableTurnos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableTurnosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableTurnos);

        pnlList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlListaTurnos.add(pnlList, java.awt.BorderLayout.CENTER);

        PanelPrincipal.addTab("Turnos", iconos.getList(16)
            , pnlListaTurnos);

        pnlFichaTurnos.setLayout(new java.awt.BorderLayout());

        id2.setText("ID:");

        lblCheck2.setText("Apellido:");

        lblCheck14.setText("Obra Social");

        txtApellido1.setLenghtText(25);

        txtNombre1.setLenghtText(30);

        txtId1.setEditable(false);
        txtId1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblCheck15.setText("Fecha");

        lblCheck16.setText("Especialidad");

        lblCheck17.setText("Profesional");

        lblCheck18.setText("Nombre(s):");

        lblCheck19.setText("DNI");

        txtDni1.setLenghtText(30);

        lblCheck20.setText("Nro Credencial");

        txtCredencial1.setLenghtText(25);

        lblCheck22.setText("Motivo Turno");

        lblCheck23.setText("Diagnostico");

        lblCheck24.setText("Estado");

        intEstado1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtDiagnostico1.setColumns(20);
        txtDiagnostico1.setRows(5);
        jScrollPane5.setViewportView(txtDiagnostico1);

        txtMotivo1.setColumns(20);
        txtMotivo1.setRows(5);
        jScrollPane6.setViewportView(txtMotivo1);

        jCheckAtencion1.setText("Atencion Particular");

        javax.swing.GroupLayout pnlFicha1Layout = new javax.swing.GroupLayout(pnlFicha1);
        pnlFicha1.setLayout(pnlFicha1Layout);
        pnlFicha1Layout.setHorizontalGroup(
            pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFicha1Layout.createSequentialGroup()
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCheck17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(id2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlFicha1Layout.createSequentialGroup()
                                .addComponent(lblCheck24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(29, 29, 29))
                            .addComponent(lblCheck14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFicha1Layout.createSequentialGroup()
                                .addComponent(pnlComboObrasSociales1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckAtencion1)
                                .addGap(118, 118, 118))
                            .addComponent(txtDni1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtApellido1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(intEstado1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombre1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtId1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCredencial1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFecha1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlComboProf_Esp1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFicha1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane6))
                            .addGroup(pnlFicha1Layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(lblCheck22, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                .addGap(149, 149, 149)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(pnlFicha1Layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(lblCheck23, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .addGap(107, 107, 107)))))
                .addGap(30, 30, 30))
        );
        pnlFicha1Layout.setVerticalGroup(
            pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFicha1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(txtId1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(lblCheck16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCheck17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(pnlComboProf_Esp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(lblCheck15, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(txtFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(lblCheck2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)))
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(lblCheck18, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addGap(13, 13, 13))
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCheck19, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addGroup(pnlFicha1Layout.createSequentialGroup()
                        .addComponent(txtDni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCheck14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlComboObrasSociales1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jCheckAtencion1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCredencial1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCheck20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intEstado1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCheck24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCheck22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCheck23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFicha1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane5))
                .addGap(23, 23, 23))
        );

        pnlFichaTurnos.add(pnlFicha1, java.awt.BorderLayout.CENTER);

        pnlButtons1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnlButtons1.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, -1));

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButtons1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, -1));

        pnlFichaTurnos.add(pnlButtons1, java.awt.BorderLayout.EAST);

        PanelPrincipal.addTab("Detalle del Turno", pnlFichaTurnos);

        pnlListaPacientes.setLayout(new java.awt.BorderLayout());

        pnlFiltroPacientes.setPreferredSize(new java.awt.Dimension(762, 40));

        txtFilterAgendar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterAgendarKeyReleased(evt);
            }
        });

        BtnAyuda1.setText("Ayuda");
        BtnAyuda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAyuda1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFiltroPacientesLayout = new javax.swing.GroupLayout(pnlFiltroPacientes);
        pnlFiltroPacientes.setLayout(pnlFiltroPacientesLayout);
        pnlFiltroPacientesLayout.setHorizontalGroup(
            pnlFiltroPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilterAgendar, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 556, Short.MAX_VALUE)
                .addComponent(BtnAyuda1)
                .addContainerGap())
        );
        pnlFiltroPacientesLayout.setVerticalGroup(
            pnlFiltroPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiltroPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltroPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnAyuda1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addGroup(pnlFiltroPacientesLayout.createSequentialGroup()
                        .addGroup(pnlFiltroPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFilter2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFilterAgendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(2, 2, 2)))
                .addContainerGap())
        );

        pnlListaPacientes.add(pnlFiltroPacientes, java.awt.BorderLayout.NORTH);

        pnlList3.setLayout(new java.awt.BorderLayout());

        jTablePacientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jTablePacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePacientesMouseClicked(evt);
            }
        });
        jTablePacientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablePacientesKeyReleased(evt);
            }
        });
        jScrollPane10.setViewportView(jTablePacientes);

        pnlList3.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        pnlListaPacientes.add(pnlList3, java.awt.BorderLayout.CENTER);

        PanelAgendar.addTab("Pacientes", iconos.getList(16)
            , pnlListaPacientes);

        pnlDetalleTurnos.setLayout(new java.awt.BorderLayout());

        id4.setText("ID:");

        lblCheck37.setText("Apellido:");

        lblCheck38.setText("Obra Social");

        txtApellido3.setLenghtText(25);

        txtNombre3.setLenghtText(30);

        txtId3.setEditable(false);
        txtId3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblCheck39.setText("Fecha");

        lblCheck40.setText("Especialidad");

        lblCheck41.setText("Profesional");

        lblCheck42.setText("Nombre(s):");

        lblCheck43.setText("DNI");

        txtDni3.setLenghtText(30);

        lblCheck44.setText("Nro Credencial");

        txtCredencial3.setLenghtText(25);

        lblCheck46.setText("Motivo Turno");

        lblCheck47.setText("Diagnostico");

        lblCheck48.setText("Estado");

        intEstado3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtDiagnostico3.setColumns(20);
        txtDiagnostico3.setRows(5);
        jScrollPane11.setViewportView(txtDiagnostico3);

        txtMotivo3.setColumns(20);
        txtMotivo3.setRows(5);
        jScrollPane12.setViewportView(txtMotivo3);

        jCheckAtencion3.setText("Atencion Particular");

        javax.swing.GroupLayout pnlFicha3Layout = new javax.swing.GroupLayout(pnlFicha3);
        pnlFicha3.setLayout(pnlFicha3Layout);
        pnlFicha3Layout.setHorizontalGroup(
            pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFicha3Layout.createSequentialGroup()
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCheck39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(id4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCheck38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlFicha3Layout.createSequentialGroup()
                                .addComponent(lblCheck48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(29, 29, 29))
                            .addGroup(pnlFicha3Layout.createSequentialGroup()
                                .addComponent(lblCheck40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFecha3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDni3, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                            .addComponent(txtApellido3, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                            .addComponent(intEstado3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombre3, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                            .addComponent(txtId3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCredencial3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlFicha3Layout.createSequentialGroup()
                                .addComponent(pnlComboObrasSociales3, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(jCheckAtencion3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(pnlComboProf_Esp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFicha3Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane12))
                            .addGroup(pnlFicha3Layout.createSequentialGroup()
                                .addGap(111, 111, 111)
                                .addComponent(lblCheck46, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                .addGap(149, 149, 149)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane11)
                            .addGroup(pnlFicha3Layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(lblCheck47, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                .addGap(107, 107, 107)))))
                .addGap(30, 30, 30))
        );
        pnlFicha3Layout.setVerticalGroup(
            pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFicha3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addComponent(id4, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(txtId3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblCheck40, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCheck41, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlComboProf_Esp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addComponent(lblCheck39, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(txtFecha3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addComponent(lblCheck37, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(txtApellido3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addComponent(lblCheck42, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(txtNombre3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addComponent(lblCheck43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addComponent(txtDni3, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckAtencion3)
                            .addComponent(pnlComboObrasSociales3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFicha3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblCheck38, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCheck44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCredencial3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFicha3Layout.createSequentialGroup()
                        .addComponent(lblCheck48, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(intEstado3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCheck46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCheck47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFicha3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12)
                    .addComponent(jScrollPane11))
                .addGap(23, 23, 23))
        );

        pnlDetalleTurnos.add(pnlFicha3, java.awt.BorderLayout.CENTER);

        pnlButtons3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNewAgendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewAgendarActionPerformed(evt);
            }
        });
        pnlButtons3.add(btnNewAgendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        pnlDetalleTurnos.add(pnlButtons3, java.awt.BorderLayout.EAST);

        PanelAgendar.addTab("Detalle del Turno", pnlDetalleTurnos);

        PanelPrincipal.addTab("Agendar Turnos", PanelAgendar);

        getContentPane().add(PanelPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFilterTurnosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterTurnosKeyReleased
        _filter(txtFilterTurnos.getText().trim());
    }//GEN-LAST:event_txtFilterTurnosKeyReleased

    private void jTableTurnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTurnosMouseClicked
        _selectionRowTurnos();
    }//GEN-LAST:event_jTableTurnosMouseClicked

    private void jTableTurnosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableTurnosKeyReleased
        _selectionRowTurnos();
    }//GEN-LAST:event_jTableTurnosKeyReleased

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        _isUpdate();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        _isDelete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtFilterAgendarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterAgendarKeyReleased
        _filterAgendar(txtFilterAgendar.getText().trim());
    }//GEN-LAST:event_txtFilterAgendarKeyReleased

    private void jTablePacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePacientesMouseClicked
        _selectionRowPacientes();
    }//GEN-LAST:event_jTablePacientesMouseClicked

    private void jTablePacientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePacientesKeyReleased
        _selectionRowPacientes();
    }//GEN-LAST:event_jTablePacientesKeyReleased

    private void btnNewAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewAgendarActionPerformed
        _isNewAgendar();
    }//GEN-LAST:event_btnNewAgendarActionPerformed

    private void BtnAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAyudaActionPerformed
        JOptionPane.showMessageDialog(PanelPrincipal, "Seleccione un Turno de la tabla para ver mas informacion en el panel 'Detalle del Turno'", "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_BtnAyudaActionPerformed

    private void BtnAyuda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAyuda1ActionPerformed
        JOptionPane.showMessageDialog(PanelPrincipal, "Seleccione un Paciente de la tabla para poder agendar el Turno en el panel 'Detalle del Turno'", "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_BtnAyuda1ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        _loadTurnos();
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAyuda;
    private javax.swing.JButton BtnAyuda1;
    private javax.swing.JTabbedPane PanelAgendar;
    private javax.swing.JTabbedPane PanelPrincipal;
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNewAgendar;
    private botones.BtnUpdate btnUpdate;
    private etiquetas.Id id2;
    private etiquetas.Id id4;
    private texto.TxtNro intEstado1;
    private texto.TxtNro intEstado3;
    private javax.swing.JCheckBox jCheckAtencion1;
    private javax.swing.JCheckBox jCheckAtencion3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTablePacientes;
    private javax.swing.JTable jTableTurnos;
    private etiquetas.LblCheck lblCheck14;
    private etiquetas.LblCheck lblCheck15;
    private etiquetas.LblCheck lblCheck16;
    private etiquetas.LblCheck lblCheck17;
    private etiquetas.LblCheck lblCheck18;
    private etiquetas.LblCheck lblCheck19;
    private etiquetas.LblCheck lblCheck2;
    private etiquetas.LblCheck lblCheck20;
    private etiquetas.LblCheck lblCheck22;
    private etiquetas.LblCheck lblCheck23;
    private etiquetas.LblCheck lblCheck24;
    private etiquetas.LblCheck lblCheck37;
    private etiquetas.LblCheck lblCheck38;
    private etiquetas.LblCheck lblCheck39;
    private etiquetas.LblCheck lblCheck40;
    private etiquetas.LblCheck lblCheck41;
    private etiquetas.LblCheck lblCheck42;
    private etiquetas.LblCheck lblCheck43;
    private etiquetas.LblCheck lblCheck44;
    private etiquetas.LblCheck lblCheck46;
    private etiquetas.LblCheck lblCheck47;
    private etiquetas.LblCheck lblCheck48;
    private etiquetas.LblFilter lblFilter2;
    private etiquetas.LblFilter lblFilter3;
    private javax.swing.JPanel pnlButtons1;
    private javax.swing.JPanel pnlButtons3;
    private forms.turnos.pnlComboObrasSociales pnlComboObrasSociales1;
    private forms.turnos.pnlComboObrasSociales pnlComboObrasSociales3;
    private forms.turnos.pnlComboProf_Esp pnlComboProf_Esp1;
    private forms.turnos.pnlComboProf_Esp pnlComboProf_Esp3;
    private javax.swing.JPanel pnlDetalleTurnos;
    private javax.swing.JPanel pnlFicha1;
    private javax.swing.JPanel pnlFicha3;
    private javax.swing.JPanel pnlFichaTurnos;
    private javax.swing.JPanel pnlFiltroPacientes;
    private javax.swing.JPanel pnlFiltroTurnos;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlList3;
    private javax.swing.JPanel pnlListaPacientes;
    private javax.swing.JPanel pnlListaTurnos;
    private texto.TxtMayusculas txtApellido1;
    private texto.TxtMayusculas txtApellido3;
    private texto.TxtMayusculas txtCredencial1;
    private texto.TxtMayusculas txtCredencial3;
    private javax.swing.JTextArea txtDiagnostico1;
    private javax.swing.JTextArea txtDiagnostico3;
    private texto.TxtMayusculas txtDni1;
    private texto.TxtMayusculas txtDni3;
    private com.toedter.calendar.JDateChooser txtFecha1;
    private com.toedter.calendar.JDateChooser txtFecha3;
    private texto.TxtMayusculas txtFilterAgendar;
    private texto.TxtMayusculas txtFilterTurnos;
    private texto.TxtNro txtId1;
    private texto.TxtNro txtId3;
    private javax.swing.JTextArea txtMotivo1;
    private javax.swing.JTextArea txtMotivo3;
    private texto.TxtMayusculas txtNombre1;
    private texto.TxtMayusculas txtNombre3;
    // End of variables declaration//GEN-END:variables
}
