package org.fabianmarroquin.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.fabianmarroquin.bean.DetalleReceta;
import org.fabianmarroquin.bean.Medicamento;
import org.fabianmarroquin.bean.Receta;
import org.fabianmarroquin.db.Conexion;
import org.fabianmarroquin.system.Principal;


public class DetalleRecetaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ELIMINAR, EDITAR, GUARDAR,ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleReceta> listaDetalleReceta;
    private ObservableList<Receta> listaReceta;
    private ObservableList<Medicamento> listaMedicamento;
    
    @FXML private TextField txtCodigoDetalleReceta;
    @FXML private TextField txtDosis;
    @FXML private ComboBox cmbCodigoMedicamento;
    @FXML private ComboBox cmbCodigoReceta;
    @FXML private TableView tblDetalleReceta;
    @FXML private TableColumn colCodigoDetalleReceta;
    @FXML private TableColumn colCodigoMedicamento;
    @FXML private TableColumn colCodigoReceta;
    @FXML private TableColumn colDosis;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnNuevo;
    @FXML private Button btnReporte;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoMedicamento.setItems(getMedicamento());
        cmbCodigoReceta.setItems(getReceta());
        cmbCodigoMedicamento.setDisable(true);
        cmbCodigoReceta.setDisable(true);
    }
    
    public ObservableList<DetalleReceta>getDetalleReceta(){
        ArrayList<DetalleReceta> lista = new ArrayList<DetalleReceta>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetalleReceta()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetalleReceta(resultado.getInt("codigoDetalleReceta"),
                                            resultado.getString("dosis"),
                                            resultado.getInt("codigoReceta"),
                                            resultado.getInt("codigoMedicamento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDetalleReceta = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Medicamento>getMedicamento(){
        ArrayList<Medicamento> lista = new ArrayList<Medicamento>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarMedicamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Medicamento(resultado.getInt("codigoMedicamento"),
                                        resultado.getString("nombreMedicamento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaMedicamento = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Receta>getReceta(){
        ArrayList <Receta> lista = new ArrayList<Receta>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarRecetas}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Receta(resultado.getInt("codigoReceta"),
                                    resultado.getDate("fechaReceta"),
                                    resultado.getInt("numeroColegiado")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaReceta = FXCollections.observableArrayList(lista);
    }
    
    public void cargarDatos(){
        tblDetalleReceta.setItems(getDetalleReceta());
        colCodigoDetalleReceta.setCellValueFactory(new PropertyValueFactory<DetalleReceta, Integer>("codigoDetalleReceta"));
        colDosis.setCellValueFactory(new PropertyValueFactory<DetalleReceta, String>("dosis"));
        colCodigoMedicamento.setCellValueFactory(new PropertyValueFactory<DetalleReceta, Integer>("codigoMedicamento"));
        colCodigoReceta.setCellValueFactory(new PropertyValueFactory<DetalleReceta, Integer>("codigoReceta"));
    }
    
    public Medicamento buscarMedicamento(int codigoMedicamento){
        Medicamento resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_BuscarMedicamento(?)}");
            procedimiento.setInt(1, codigoMedicamento);
            ResultSet result = procedimiento.executeQuery();
            while(result.next()){
                resultado = new Medicamento(result.getInt("codigoMedicamento"),
                                            result.getString("nombreMedicamento"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Receta buscarReceta(int codigoReceta){
        Receta resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_BuscarReceta(?)}");
            procedimiento.setInt(1, codigoReceta);
            ResultSet result = procedimiento.executeQuery();
            while(result.next()){
                resultado = new Receta(result.getInt("codigoReceta"),
                                        result.getDate("fechaReceta"),
                                        result.getInt("NumeroColegiado"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void seleccionarElemento(){
        if(tblDetalleReceta.getSelectionModel().getSelectedItem() != null){
            txtCodigoDetalleReceta.setText(String.valueOf(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoDetalleReceta()));
            txtDosis.setText(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getDosis());
            cmbCodigoMedicamento.getSelectionModel().select(buscarMedicamento(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoMedicamento()));
            cmbCodigoReceta.getSelectionModel().select(buscarReceta(((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoReceta()));
        }else
            JOptionPane.showMessageDialog(null, "Seleccione un elemento");
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
   
    public void guardar(){
        DetalleReceta registro = new DetalleReceta();
        registro.setDosis(txtDosis.getText());
        registro.setCodigoReceta(((Receta)cmbCodigoReceta.getSelectionModel().getSelectedItem()).getCodigoReceta());
        registro.setCodigoMedicamento(((Medicamento)cmbCodigoMedicamento.getSelectionModel().getSelectedItem()).getCodigoMedicamento());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarDetalleReceta(?,?,?)}");
            procedimiento.setString(1, registro.getDosis());
            procedimiento.setInt(2, registro.getCodigoReceta());
            procedimiento.setInt(3, registro.getCodigoMedicamento());
            procedimiento.execute();
            listaDetalleReceta.add(registro);
        }catch(Exception e){
            e.printStackTrace();
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
                if(tblDetalleReceta.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este registro?", "Eliminar Detalle Receta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleReceta(?)}");
                            procedimiento.setInt(1,((DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem()).getCodigoDetalleReceta());
                            procedimiento.execute();
                            listaDetalleReceta.remove(tblDetalleReceta.getSelectionModel().getSelectedIndex());
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
                if(tblDetalleReceta.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/fabianmarroquin/image/update.png"));
                    imgReporte.setImage(new Image("/org/fabianmarroquin/image/cancelar.png"));
                    activarControles();
                    txtCodigoDetalleReceta.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_updateDetalleReceta(?,?,?,?)}");
            DetalleReceta registro = (DetalleReceta)tblDetalleReceta.getSelectionModel().getSelectedItem();
            registro.setDosis(txtDosis.getText());
            registro.setCodigoDetalleReceta(((Receta)cmbCodigoReceta.getSelectionModel().getSelectedItem()).getCodigoReceta());
            registro.setCodigoMedicamento(((Medicamento)cmbCodigoMedicamento.getSelectionModel().getSelectedItem()).getCodigoMedicamento());
            procedimiento.setInt(1, registro.getCodigoDetalleReceta());
            procedimiento.setString(2, registro.getDosis());
            procedimiento.setInt(3, registro.getCodigoReceta());
            procedimiento.setInt(4, registro.getCodigoMedicamento());
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
    
    
    
    
     public void activarControles(){
        txtCodigoDetalleReceta.setEditable(true);
        txtDosis.setEditable(true);
        cmbCodigoMedicamento.setDisable(false);
        cmbCodigoReceta.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoDetalleReceta.clear();
        txtDosis.clear();
//        cmbCodigoMedicamento.getSelectionModel().clearSelection();
//        cmbCodigoReceta.getSelectionModel().clearSelection();
        tblDetalleReceta.getSelectionModel().clearSelection();
        cmbCodigoMedicamento.setValue(null);
        cmbCodigoReceta.setValue(null);
    }
    
    public void desactivarControles(){
        txtCodigoDetalleReceta.setEditable(false);
        txtDosis.setEditable(false);
        cmbCodigoMedicamento.setDisable(true);
        cmbCodigoReceta.setDisable(true);
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
