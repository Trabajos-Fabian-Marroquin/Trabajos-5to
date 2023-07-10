package org.fabianmarroquin.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.fabianmarroquin.bean.Usuario;
import org.fabianmarroquin.db.Conexion;
import org.fabianmarroquin.system.Principal;

public class UsuarioController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NINGUNO,GUARDAR};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    @FXML private TextField txtCodigoUsuario;
    @FXML private TextField txtContrasena;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtApellidoUsuario;
    @FXML private TextField txtUsuario;
    @FXML private Button btnCancelar;
    @FXML private Button btnNuevo;
    @FXML private ImageView imgCancelar;
    @FXML private ImageView imgNuevo;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCancelar.setDisable(true);
        desactivarControles();
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnCancelar.setText("Cancelar");
                imgNuevo.setImage(new Image("/org/fabianmarroquin/image/guardar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnCancelar.setText("Cacnelar");
                imgNuevo.setImage(new Image("/org/fabianmarroquin/image/agregar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuario.getText());
        registro.setContrasena(txtContrasena.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarUsuario(?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidoUsuario());
            procedimiento.setString(3, registro.getUsuarioLogin());
            procedimiento.setString(4, registro.getContrasena());
            procedimiento.execute();
            JOptionPane.showMessageDialog(null,"Los Datos se guardaron");
            ventanaLogin();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
    
    
    
    
    
    
    

    public void desactivarControles(){
        txtCodigoUsuario.setDisable(true);
        txtNombreUsuario.setDisable(true);
        txtApellidoUsuario.setDisable(true);
        txtUsuario.setDisable(true);
        txtContrasena.setDisable(true);
        btnCancelar.setDisable(false);
    }
    
    public void activarControles(){
        txtCodigoUsuario.setDisable(true);
        txtNombreUsuario.setDisable(false);
        txtApellidoUsuario.setDisable(false);
        txtUsuario.setDisable(false);
        txtContrasena.setDisable(false);
        btnCancelar.setDisable(true);
    }
    
    public void limpiarControles(){
        txtCodigoUsuario.clear();
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtContrasena.clear();
    }
    
    
    
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
