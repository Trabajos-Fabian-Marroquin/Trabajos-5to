package org.fabianmarroquin.controller;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.fabianmarroquin.bean.Vecinos;
import org.fabianmarroquin.bean.Vehiculos;
import org.fabianmarroquin.conexion.Conexion;
import org.fabianmarroquin.system.Principal;


public class VehiculosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCERLAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Vehiculos> listaVehiculos;
    private ObservableList<Vecinos> listaVecinos;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtPlaca;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtTipoDeVehiculo;
    @FXML private ComboBox cmbNIT;
    @FXML private TableView tblVehiculos;
    @FXML private TableColumn colPlaca;
    @FXML private TableColumn colMarca;
    @FXML private TableColumn colModelo;
    @FXML private TableColumn colTipoDeVehiculo;
    @FXML private TableColumn colNIT;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        desactivarControles();
        cmbNIT.setItems(getVecino());
    }
    
    public void cargarDatos(){
        tblVehiculos.setItems(getVehiculos());
        colPlaca.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("placa"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("modelo"));
        colTipoDeVehiculo.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("tipoDeVehiculo"));
        colNIT.setCellValueFactory(new PropertyValueFactory<Vehiculos,String>("NIT"));
    }
    
    public void seleccionarElemento(){
        if(tblVehiculos.getSelectionModel().getSelectedItem() !=null){
            txtPlaca.setText(((Vehiculos)tblVehiculos.getSelectionModel().getSelectedItem()).getPlaca());
            txtMarca.setText(((Vehiculos)tblVehiculos.getSelectionModel().getSelectedItem()).getMarca());
            txtModelo.setText(((Vehiculos)tblVehiculos.getSelectionModel().getSelectedItem()).getModelo()); 
            txtTipoDeVehiculo.setText(((Vehiculos)tblVehiculos.getSelectionModel().getSelectedItem()).getTipoDeVehiculo());
            cmbNIT.getSelectionModel().select(buscarVecino(((Vehiculos)tblVehiculos.getSelectionModel().getSelectedItem()).getNIT()));
        }else
            JOptionPane.showMessageDialog(null, "Seleccione un elemento");
    }
   
 
    public ObservableList <Vehiculos> getVehiculos(){
        ArrayList<Vehiculos> lista = new ArrayList<Vehiculos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarVehiculos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Vehiculos(resultado.getString("placa"),
                                        resultado.getString("marca"),
                                        resultado.getString("modelo"),
                                        resultado.getString("TipoDeVehiculo"),
                                        resultado.getString("NIT")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaVehiculos = FXCollections.observableArrayList(lista);
    }
    
    
    public ObservableList <Vecinos> getVecino(){
        ArrayList<Vecinos> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarVecinos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Vecinos(resultado.getString("NIT"),
                                        resultado.getLong("DPI"),
                                        resultado.getString("nombres"),
                                        resultado.getString("apellidos"),
                                        resultado.getString("direccion"),
                                        resultado.getString("municipalidad"),
                                        resultado.getInt("codigoPostal"),
                                        resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaVecinos = FXCollections.observableArrayList(lista);
    }
    
    public Vecinos buscarVecino(String NIT){
        Vecinos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarVecino(?)}");
            procedimiento.setString(1, NIT);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Vecinos(registro.getString("NIT"),
                                     registro.getLong("DPI"),
                                     registro.getString("nombres"),
                                     registro.getString("apellidos"),
                                     registro.getString("direccion"),
                                     registro.getString("municipalidad"),
                                     registro.getInt("codigoPostal"),
                                     registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public  void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = operaciones.GUARDAR;
                break;
                
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void eliminar(){
        switch (tipoDeOperaciones){
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;  
            default:
                if(tblVehiculos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este registro?", "Eliminar Vehiculo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarVehiculo(?)}");
                            procedimiento.setString(1,((Vehiculos)tblVehiculos.getSelectionModel().getSelectedItem()).getPlaca());
                            procedimiento.execute();
                            listaVehiculos.remove(tblVehiculos.getSelectionModel().getSelectedIndex());
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
                if(tblVehiculos.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtPlaca.setEditable(false);
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
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
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
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    
    
    public void guardar(){
        Vehiculos registro = new Vehiculos();
        registro.setPlaca(txtPlaca.getText());
        registro.setMarca(txtMarca.getText());
        registro.setModelo(txtModelo.getText());
        registro.setTipoDeVehiculo(txtTipoDeVehiculo.getText());
        registro.setNIT(((Vecinos)cmbNIT.getSelectionModel().getSelectedItem()).getNIT());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarVehiculo(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getPlaca());
            procedimiento.setString(2, registro.getMarca());
            procedimiento.setString(3, registro.getModelo());
            procedimiento.setString(4, registro.getTipoDeVehiculo());
            procedimiento.setString(5, registro.getNIT());
            procedimiento.execute();
            listaVehiculos.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_UpdateVehiculo(?,?,?,?,?)}");
            Vehiculos registro = (Vehiculos)tblVehiculos.getSelectionModel().getSelectedItem();
            registro.setPlaca(txtPlaca.getText());
            registro.setMarca(txtMarca.getText());
            registro.setModelo(txtModelo.getText());
            registro.setTipoDeVehiculo(txtTipoDeVehiculo.getText());
            registro.setNIT(((Vecinos)cmbNIT.getSelectionModel().getSelectedItem()).getNIT());
            procedimiento.setString(1, registro.getPlaca());
            procedimiento.setString(2, registro.getMarca());
            procedimiento.setString(3, registro.getModelo());
            procedimiento.setString(4, registro.getTipoDeVehiculo());
            procedimiento.setString(5, registro.getNIT());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void activarControles(){
        txtPlaca.setEditable(true);
        txtMarca.setEditable(true);
        txtModelo.setEditable(true);
        txtTipoDeVehiculo.setEditable(true);
        cmbNIT.setDisable(false);
    }
    
    public void desactivarControles(){
        txtPlaca.setEditable(false);
        txtMarca.setEditable(false);
        txtModelo.setEditable(false);
        txtTipoDeVehiculo.setEditable(false);
        cmbNIT.setDisable(true);
    }
    
    public void limpiarControles(){
        txtPlaca.clear();
        txtMarca.clear();
        txtModelo.clear();
        txtTipoDeVehiculo.clear();
        cmbNIT.setValue(null);
    }

    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
