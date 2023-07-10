package org.fabianmarroquin.controller;

import com.jfoenix.controls.JFXTimePicker;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.fabianmarroquin.bean.Cita;
import org.fabianmarroquin.bean.Doctor;
import org.fabianmarroquin.bean.Paciente;
import org.fabianmarroquin.db.Conexion;
import org.fabianmarroquin.system.Principal;

public class CitaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO,GUARDAR,ACTUALIZAR,ELIMINAR,CANCELAR,EDITAR,NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Cita> listaCita;
    private ObservableList<Paciente> listaPaciente;
    private ObservableList<Doctor> listaDoctor;
    private DatePicker fCita;
   
    @FXML private JFXTimePicker jfxHora;
    @FXML private TextField txtCodigoCita;
    @FXML private TextField txtTratamiento;
    @FXML private TextField txtDescripcionActual;
    @FXML private GridPane grpFecha;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private ComboBox cmbCodigoPaciente;
    @FXML private ComboBox cmbNumeroColegiado;
    @FXML private TableView tblCitas;
    @FXML private TableColumn colCodigoCita;
    @FXML private TableColumn colFechaCita;
    @FXML private TableColumn colHoraCita;
    @FXML private TableColumn colTratamiento;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colCodigoPaciente;
    @FXML private TableColumn colNumeroColegiado;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPaciente.setItems(getPaciente());
        cmbNumeroColegiado.setItems(getDoctor());
        cmbCodigoPaciente.setDisable(true);
        cmbNumeroColegiado.setDisable(true);
        fCita = new DatePicker(Locale.ENGLISH);
        fCita.setDateFormat(new SimpleDateFormat("yy-MM-dd"));
        fCita.getCalendarView().todayButtonTextProperty().set("Today");
        fCita.getCalendarView().setShowWeeks(false);
        grpFecha.add(fCita, 1, 1);
        fCita.getStylesheets().add("/org/fabianmarroquin/resource/DatePicker.css");
        fCita.setDisable(true);
        jfxHora.setDisable(true);
    }
    
    public void cargarDatos(){
        tblCitas.setItems(getCita());
        colCodigoCita.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("codigoCita"));
        colFechaCita.setCellValueFactory(new PropertyValueFactory<Cita, Date>("fechaCita"));
        colHoraCita.setCellValueFactory(new PropertyValueFactory<Cita, Time>("horaCita"));
        colTratamiento.setCellValueFactory(new PropertyValueFactory<Cita, String>("tratamiento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Cita, String>("descripCondActual"));
        colCodigoPaciente.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("codigoPaciente"));
        colNumeroColegiado.setCellValueFactory(new PropertyValueFactory<Cita, Integer>("numeroColegiado"));
    }
    
    public Paciente buscarPaciente(int codigoPaciente){
        Paciente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPacientes(?)}");
            procedimiento.setInt(1, codigoPaciente);
            ResultSet result = procedimiento.executeQuery();
            while(result.next()){
                resultado = new Paciente(result.getInt("codigoPaciente"),
                                            result.getString("nombresPaciente"),
                                            result.getString("apellidosPaciente"),
                                            result.getString("sexo"),
                                            result.getDate("fechaNacimiento"),
                                            result.getString("direccionPaciente"),
                                            result.getString("telefonoPersonal"),
                                            result.getDate("fechaPrimeraVisita"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Doctor buscarDoctor(int numeroColegiado){
        Doctor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarDoctor(?)}");
            procedimiento.setInt(1, numeroColegiado);
            ResultSet result = procedimiento.executeQuery();
            while(result.next()){
                resultado = new Doctor(result.getInt("numeroColegiado"),
                                        result.getString("nombresDoctor"),
                                        result.getString("apellidosDoctor"),
                                        result.getString("telefonoContacto"),
                                        result.getInt("codigoEspecialidad"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void seleccionarElemento(){
        if(tblCitas.getSelectionModel().getSelectedItem() != null){
        txtCodigoCita.setText(String.valueOf(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoCita()));
        fCita.selectedDateProperty().set(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getFechaCita());
        jfxHora.setValue(((((Cita)tblCitas.getSelectionModel().getSelectedItem()).getHoraCita())).toLocalTime());
        txtTratamiento.setText(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getTratamiento());
        txtDescripcionActual.setText(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getDescripCondActual());
        cmbCodigoPaciente.getSelectionModel().select(buscarPaciente(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoPaciente()));
        cmbNumeroColegiado.getSelectionModel().select(buscarDoctor(((Cita)tblCitas.getSelectionModel().getSelectedItem()).getNumeroColegiado()));
        
        }else
            JOptionPane.showMessageDialog(null, "No seleccionaste ningun dato");
        
    }
    
    public ObservableList<Cita> getCita(){
        ArrayList<Cita> lista = new ArrayList<Cita>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCitas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cita(resultado.getInt("codigoCita"),
                                resultado.getDate("fechaCita"),
                                resultado.getTime("horaCita"),
                                resultado.getString("tratamiento"),
                                resultado.getString("descripCondActual"),
                                resultado.getInt("codigoPaciente"),
                                resultado.getInt("numeroColegiado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCita = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Paciente> getPaciente(){
        ArrayList<Paciente> lista = new ArrayList<Paciente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPacientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Paciente(resultado.getInt("codigoPaciente"),
                                        resultado.getString("nombresPaciente"),
                                        resultado.getString("apellidosPaciente"),
                                        resultado.getString("sexo"),
                                        resultado.getDate("fechaNacimiento"),
                                        resultado.getString("direccionPaciente"),
                                        resultado.getString("telefonoPersonal"),
                                        resultado.getDate("fechaPrimeraVisita")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPaciente = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Doctor> getDoctor(){
        ArrayList<Doctor> lista = new ArrayList<Doctor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDoctores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Doctor(resultado.getInt("numeroColegiado"),
                                    resultado.getString("nombresDoctor"),
                                    resultado.getString("apellidosDoctor"),
                                    resultado.getString("telefonoContacto"),
                                    resultado.getInt("codigoEspecialidad")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDoctor = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/fabianmarroquin/image/guardar.png")); //poner el nombrede la imagen y el tipo de imagen igua para el cancelar
                imgEliminar.setImage(new Image("/org/fabianmarroquin/image/cancelar.png"));
                tipoDeOperaciones = operaciones.GUARDAR;
                break;
            
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/fabianmarroquin/image/agregar.png"));
                imgEliminar.setImage(new Image("/org/fabianmarroquin/image/eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void eliminar(){
        switch (tipoDeOperaciones){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/fabianmarroquin/image/agregar.png"));
                imgEliminar.setImage(new Image("/org/fabianmarroquin/image/eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
                
            default:
                if(tblCitas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este registro?", "Eliminar Cita", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCita(?)}");
                            procedimiento.setInt(1,((Cita)tblCitas.getSelectionModel().getSelectedItem()).getCodigoPaciente());
                            procedimiento.execute();
                            listaCita.remove(tblCitas.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null,"Seleccione un elemento");
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblCitas.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/fabianmarroquin/image/update.png"));
                    imgReporte.setImage(new Image("/org/fabianmarroquin/image/cancelar.png"));
                    activarControles();
                    cmbCodigoPaciente.setDisable(true);
                    cmbNumeroColegiado.setDisable(true);
                    txtCodigoCita.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Tiene que seleccionar un elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/fabianmarroquin/image/update.png"));
                imgReporte.setImage(new Image("/org/fabianmarroquin/image/listar.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_updateCita(?,?,?,?,?,?,?)}");
            Cita registro = (Cita)tblCitas.getSelectionModel().getSelectedItem();
            registro.setFechaCita(fCita.getSelectedDate());
            registro.setHoraCita(java.sql.Time.valueOf(jfxHora.getValue()));
            registro.setTratamiento(txtTratamiento.getText());
            registro.setDescripCondActual(txtDescripcionActual.getText());
            registro.setCodigoPaciente(((Paciente)cmbCodigoPaciente.getSelectionModel().getSelectedItem()).getCodigoPaciente());
            registro.setNumeroColegiado(((Doctor)cmbNumeroColegiado.getSelectionModel().getSelectedItem()).getNumeroColegiado());
            procedimiento.setInt(1, registro.getCodigoCita());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaCita().getTime()));
            procedimiento.setTime(3, registro.getHoraCita());
            procedimiento.setString(4, registro.getTratamiento());
            procedimiento.setString(5, registro.getDescripCondActual());
            procedimiento.setInt(6, registro.getCodigoPaciente());
            procedimiento.setInt(7, registro.getNumeroColegiado());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/fabianmarroquin/image/update.png"));
                imgReporte.setImage(new Image("/org/fabianmarroquin/image/listar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    
    
    
    public void guardar(){
        Cita registro = new Cita();
        registro.setFechaCita(fCita.getSelectedDate());
        registro.setHoraCita(java.sql.Time.valueOf(jfxHora.getValue()));
        registro.setTratamiento(txtTratamiento.getText());
        registro.setDescripCondActual(txtDescripcionActual.getText());
        registro.setCodigoPaciente(((Paciente)cmbCodigoPaciente.getSelectionModel().getSelectedItem()).getCodigoPaciente());
        registro.setNumeroColegiado(((Doctor)cmbNumeroColegiado.getSelectionModel().getSelectedItem()).getNumeroColegiado());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_AgregarCita(?,?,?,?,?,?)}");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaCita().getTime()));
            procedimiento.setTime(2, registro.getHoraCita());
            procedimiento.setString(3, registro.getTratamiento());
            procedimiento.setString(4, registro.getDescripCondActual());
            procedimiento.setInt(5, registro.getCodigoPaciente());
            procedimiento.setInt(6, registro.getNumeroColegiado());
            procedimiento.execute();
            listaCita.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    public void limpiarControles(){
        txtCodigoCita.clear();
        txtTratamiento.clear();
        txtDescripcionActual.clear();
        cmbCodigoPaciente.getSelectionModel().clearSelection();
        cmbNumeroColegiado.getSelectionModel().clearSelection();
        cmbCodigoPaciente.setValue(null);
        cmbNumeroColegiado.setValue(null);
        fCita.selectedDateProperty().set(null);
        jfxHora.setValue(null);
        
        tblCitas.getSelectionModel().clearSelection();
    }
    
    public void activarControles(){
        txtCodigoCita.setEditable(false);
        txtTratamiento.setEditable(true);
        txtDescripcionActual.setEditable(true);
        cmbCodigoPaciente.setDisable(false);
        cmbNumeroColegiado.setDisable(false);
        fCita.setDisable(false);
        jfxHora.setDisable(false);
    }
    
    public void desactivarControles(){
        txtCodigoCita.setEditable(false);
        txtTratamiento.setEditable(false);
        txtDescripcionActual.setEditable(false);
        cmbCodigoPaciente.setDisable(true);
        cmbNumeroColegiado.setDisable(true); 
        jfxHora.setDisable(true);
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}