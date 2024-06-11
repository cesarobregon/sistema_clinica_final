/*
 * Clase utilizada para saber si una ventana interna ya esta cargada
 */
package miselaneos;

import forms.Profesionales.FrmProfesionales;
import forms.admin.FrmPanelAdmin;
import forms.admin.FrmLoginAdmin;
import forms.cargos.FrmCargos;
import forms.especialidades.FrmEspecialidades;
import forms.horarios.FrmHorarios;
import forms.obrasocial.FrmObraSocial;
import forms.pacientes.FrmPacientes;
import forms.personal.FrmLoginPersonal;
import forms.personal.FrmPersonal;
import forms.personales.FrmPersonales;
import forms.prof_esp.FrmProfEsp;
import forms.Profesional.FrmLoginProfesional;
import forms.Profesional.FrmPacientesProfesional;
import forms.Profesional.FrmProfesional;
import forms.Profesional.FrmTurnosProfesional;
import forms.admin.FrmSetAdmin;
import forms.settings.FrmSetting;
import forms.turnos.FrmTurnos;
import java.awt.BorderLayout;
import java.util.HashMap;
import static sistema_clinica_obregon.JFrame_Sistema.jDeskTopSis;
/**
 * @author OBREGON
 */
public class GUI {
    private HashMap<Integer,FrmIntern> frmList;
    
    public GUI(){
        frmList = new HashMap<Integer, FrmIntern>();
    }
    
    /**
     * Metodo que recibe un valor int que seria el identificador de la ventana
     * y tambien si voy a querer que se muestre
     * @param id
     * @param isView 
     */
    
    public FrmEspecialidades loadEspecialidad(int id, boolean isView){
        FrmEspecialidades frm = null;
        if(frmList.containsKey(id)){
            //si el id esta en la lista solo accedo al espacio/ubicacion de memoria del objeto
            frm = (FrmEspecialidades) frmList.get(id);
        }else{
            //sino creo el objeto
            frm = new FrmEspecialidades();
            //ingreso en la lista
            frmList.put(id, frm);
            //ingreso en el objeto JDeskTopSis
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            //quiere decir que voy a mostrar el objeto FrmLocation
            frm._show();
        }
        return frm; //hago que el metodo retorne el objeto solicitado por si necesito en el futuro
    }
   
    /*
        MISMO PROCESO PARA TODOS LOS DEMAS
    */
    
    public FrmPacientes loadPacientes(int id, boolean isView){
        FrmPacientes frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmPacientes) frmList.get(id);
        }else{
            frm = new FrmPacientes();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm; 
    }
    
    public FrmPacientesProfesional loadPacientesProfesional(int id, boolean isView){
        FrmPacientesProfesional frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmPacientesProfesional) frmList.get(id);
        }else{
            frm = new FrmPacientesProfesional();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm; 
    }
    
    public FrmProfesionales loadProfesionales(int id, boolean isView){
        FrmProfesionales frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmProfesionales) frmList.get(id);
        }else{
            frm = new FrmProfesionales();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmTurnosProfesional loadTurnosProfesional(int id, boolean isView, int idprof){
        FrmTurnosProfesional frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmTurnosProfesional) frmList.get(id);
            frm.setIdProf(idprof);
        }else{
            frm = new FrmTurnosProfesional();
            frmList.put(id, frm);
            frm.setIdProf(idprof);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmProfesional loadProfesional(int id, boolean isView, int idprof){
        FrmProfesional frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmProfesional) frmList.get(id);
            frm.setIdProf(idprof);
        }else{
            frm = new FrmProfesional();
            frmList.put(id, frm);
            frm.setIdProf(idprof);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmLoginProfesional loadLoginProfesional(int id, boolean isView){
        FrmLoginProfesional frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmLoginProfesional) frmList.get(id);
        }else{
            frm = new FrmLoginProfesional();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmPersonal loadPersonal(int id, boolean isView){
        FrmPersonal frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmPersonal) frmList.get(id);
        }else{
            frm = new FrmPersonal();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmLoginPersonal loadLoginPersonal(int id, boolean isView){
        FrmLoginPersonal frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmLoginPersonal) frmList.get(id);
        }else{
            frm = new FrmLoginPersonal();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmObraSocial loadObraSocial(int id, boolean isView){
        FrmObraSocial frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmObraSocial) frmList.get(id);
        }else{
            frm = new FrmObraSocial();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmSetting loadSetting(int id, boolean isView){
        FrmSetting frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmSetting) frmList.get(id);
        }else{
            frm = new FrmSetting();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmPanelAdmin loadAdmin(int id, boolean isView){
        FrmPanelAdmin frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmPanelAdmin) frmList.get(id);
        }else{
            frm = new FrmPanelAdmin();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmLoginAdmin loadLoginAdmin(int id, boolean isView){
        FrmLoginAdmin frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmLoginAdmin) frmList.get(id);
        }else{
            frm = new FrmLoginAdmin();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmSetAdmin SetAdmin(int id, boolean isView){
        FrmSetAdmin frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmSetAdmin) frmList.get(id);
        }else{
            frm = new FrmSetAdmin();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmCargos loadCargos(int id, boolean isView){
        FrmCargos frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmCargos) frmList.get(id);
        }else{
            frm = new FrmCargos();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm; 
    }
    
    public FrmHorarios loadHorarios(int id, boolean isView){
        FrmHorarios frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmHorarios) frmList.get(id);
        }else{
            frm = new FrmHorarios();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmProfEsp loadProf_Esp(int id, boolean isView){
        FrmProfEsp frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmProfEsp) frmList.get(id);
        }else{
            frm = new FrmProfEsp();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmPersonales loadPersonales(int id, boolean isView){
        FrmPersonales frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmPersonales) frmList.get(id);
        }else{
            frm = new FrmPersonales();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
    
    public FrmTurnos loadTurno(int id, boolean isView){
        FrmTurnos frm = null;
        if(frmList.containsKey(id)){
            frm = (FrmTurnos) frmList.get(id);
        }else{
            frm = new FrmTurnos();
            frmList.put(id, frm);
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            frm._show();
        }
        return frm;
    }
}