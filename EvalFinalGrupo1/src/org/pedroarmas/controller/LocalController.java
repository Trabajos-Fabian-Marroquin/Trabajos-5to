package org.pedroarmas.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.pedroarmas.bean.Local;
import org.pedroarmas.db.Conexion;
import org.pedroarmas.system.Principal;


public class LocalController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR,NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Local> listaLocal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoLocal;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtSaldoContra;
    @FXML private TextField txtMesesPendientes;
    @FXML private TextField txtDisponibilidad;    
    @FXML private TextField txtValorLocal;
    @FXML private TextField txtValorAdministracion;
    @FXML private TableView tblLocales;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colSaldoFavor;
    @FXML private TableColumn colSaldoContra;
    @FXML private TableColumn colMesesPendientes;
    @FXML private TableColumn colDisponibilidad;
    @FXML private TableColumn colValorLocal;
    @FXML private TableColumn colValorAdministracion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblLocales.setItems(getLocal());
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Local,Integer>("codigoLocal"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Local,Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Local,Double>("saldoContra"));
        colMesesPendientes.setCellValueFactory(new PropertyValueFactory<Local,Integer>("mesesPendientes"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Local,Boolean>("disponibilidad"));
        colValorLocal.setCellValueFactory(new PropertyValueFactory<Local,Double>("valorLocal"));
        colValorAdministracion.setCellValueFactory(new PropertyValueFactory<Local,Double>("valorAdministracion"));
    }
    
    public ObservableList<Local> getLocal(){
        ArrayList<Local> lista = new ArrayList<Local>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Local(resultado.getInt("codigoLocal"),
                                     resultado.getDouble("saldoFavor"),
                                     resultado.getDouble("saldoContra"),
                                     resultado.getInt("mesesPendientes"),
                                     resultado.getBoolean("disponibilidad"),
                                     resultado.getDouble("valorLocal"),
                                     resultado.getDouble("valorAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaLocal = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblLocales.getSelectionModel().getSelectedItem() !=null){
            txtCodigoLocal.setText(String.valueOf(((Local)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal()));
            txtSaldoFavor.setText(String.valueOf(((Local)tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Local)tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra()));
            txtMesesPendientes.setText(String.valueOf(((Local)tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes()));
            txtDisponibilidad.setText(String.valueOf(((Local)tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad()));
            txtValorLocal.setText(String.valueOf(((Local)tblLocales.getSelectionModel().getSelectedItem()).getValorLocal()));
            txtValorAdministracion.setText(String.valueOf(((Local)tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion()));
        }
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                txtDisponibilidad.setEditable(true);
                txtValorLocal.setEditable(true);
                txtValorAdministracion.setEditable(true);
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/pedroarmas/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/pedroarmas/images/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/pedroarmas/images/nuevo.png"));
                imgEliminar.setImage(new Image("/org/pedroarmas/images/eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Local registro = new Local();
        registro.setDisponibilidad(Boolean.valueOf(txtDisponibilidad.getText()));
        registro.setValorLocal(Double.valueOf(txtValorLocal.getText()));
        registro.setValorAdministracion(Double.valueOf(txtValorAdministracion.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarLocal(?,?,?)}");
            procedimiento.setBoolean(1, registro.isDisponibilidad());
            procedimiento.setDouble(2, registro.getValorLocal());
            procedimiento.setDouble(3, registro.getValorAdministracion());

            procedimiento.execute();
            listaLocal.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/pedroarmas/images/nuevo.png"));
                imgEliminar.setImage(new Image("/org/pedroarmas/images/eliminar.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblLocales.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar este registro?", "Eliminar Local ", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarLocal(?)}");
                            procedimiento.setInt(1, ((Local)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
                            procedimiento.execute();
                            listaLocal.remove(tblLocales.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        }
                }else
                    JOptionPane.showMessageDialog(null, "Tiene que seleccionar un elemento");
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblLocales.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/pedroarmas/images/actualizar.png"));
                    imgReporte.setImage(new Image("/org/pedroarmas/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/pedroarmas/images/editar.png"));
                imgReporte.setImage(new Image("/org/pedroarmas/images/reporte.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();                
                break;
            
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarAdministracion(?,?,?)}");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case NINGUNO:
                
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/pedroarmas/images/editar.png"));
                imgReporte.setImage(new Image("/org/pedroarmas/images/reporte.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;                
                break;
            
        }
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
     public void activarControles(){
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        txtMesesPendientes.setEditable(true);
        txtDisponibilidad.setEditable(true);
        txtValorLocal.setEditable(true);
        txtValorAdministracion.setEditable(true);
    }
    
    public void desactivarControles(){
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(false);
        txtValorLocal.setEditable(false);
        txtValorAdministracion.setEditable(false);
    }
    
    public void limpiarControles(){
        txtCodigoLocal.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        txtMesesPendientes.clear();
        txtDisponibilidad.clear();
        txtValorLocal.clear();
        txtValorAdministracion.clear();
    }
    
}
