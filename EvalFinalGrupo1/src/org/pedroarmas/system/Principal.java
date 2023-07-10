/* 
Nombre: Fabian Angel Samuel Marroquin Chali
carne: 2018482
codigo Tecnico: IN5AV
Fecha: 20/7/2022
Hora: 13:00 PM
 */
package org.pedroarmas.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pedroarmas.controller.AdministracionController;
import org.pedroarmas.controller.ClienteController;
import org.pedroarmas.controller.CuentaPorPagarController;
import org.pedroarmas.controller.LocalController;
import org.pedroarmas.controller.LoginController;
import org.pedroarmas.controller.MenuPrincipalController;
import org.pedroarmas.controller.ProgramadorController;
import org.pedroarmas.controller.ProveedorController;
import org.pedroarmas.controller.TipoClienteController;
import org.pedroarmas.controller.UsuarioController;

/**
 *
 * @author Pedro Armas
 */
public class Principal extends Application {    
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String PAQUETE_VISTA = "/org/pedroarmas/view/";
    
    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("KinalMall");
//        Parent root = FXMLLoader.load(getClass().getResource("/org/pedroarmas/view/ProgramadorView.fxml"));
//        Scene escena = new Scene(root);
       // escenarioPrincipal.setOpacity(0.7);
       // escenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
//        escenarioPrincipal.setScene(escena);
        ventanaLogin();
        escenarioPrincipal.show();
    }
    
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menu = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 514, 400);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",600,400);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaAdministracion(){
        try{
            AdministracionController admin =(AdministracionController)cambiarEscena("AdministracionView.fxml",803,400);            
            admin.setEscenariPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoCliente(){
        try{
            TipoClienteController tipoCliente = (TipoClienteController)cambiarEscena("TipoClienteView.fxml",803,400);
            tipoCliente.setEscenarioPrinpal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLocales(){
        try{
            LocalController localController = (LocalController)cambiarEscena("LocalView.fxml",972,400);
            localController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaClientes(){
        try{
            ClienteController cliente = (ClienteController)cambiarEscena("ClienteView.fxml",1200,400);
            cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProveedores(){
        try{
            ProveedorController proveedor = (ProveedorController)cambiarEscena("ProveedorView.fxml",1214,400);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentasPorPagar(){
        try{
            CuentaPorPagarController cuentas = (CuentaPorPagarController) cambiarEscena("CuentaPorPagarView.fxml",1165,400);
            cuentas.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try{
            UsuarioController usuarioController = (UsuarioController)cambiarEscena("UsuarioView.fxml",631,336);
            usuarioController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }   
    
    public void ventanaLogin(){
        try{
            LoginController login = (LoginController) cambiarEscena ("LoginView.fxml",600,400);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException{
       Initializable resultado = null; 
       FXMLLoader cargadorFXML = new FXMLLoader();
       InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml); // "/org/pedroarmas/view/MenuPrincipalView.fxml"
       cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
       cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
       escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
       escenarioPrincipal.setScene(escena);
       escenarioPrincipal.sizeToScene();
       resultado = (Initializable)cargadorFXML.getController();
       return resultado;
    }
            
    
}
