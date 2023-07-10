/*
Nombre: Fabian Angel Samuel Marroquin Chali
Codigo Técnico: IN5AV
Carné: 2018482
Fecha de Creación: 05/04/2022
Modificaciones: 18/05/2022
 */
package org.fabianmarroquin.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fabianmarroquin.controller.CitaController;
import org.fabianmarroquin.controller.DetalleRecetaController;
import org.fabianmarroquin.controller.DoctorController;
import org.fabianmarroquin.controller.EspecialidadController;
import org.fabianmarroquin.controller.LoginController;
import org.fabianmarroquin.controller.MedicamentoController;
import org.fabianmarroquin.controller.MenuPrincipalController;
import org.fabianmarroquin.controller.PacienteController;
import org.fabianmarroquin.controller.PlantillaController;
import org.fabianmarroquin.controller.ProgramadorController;
import org.fabianmarroquin.controller.RecetaController;
import org.fabianmarroquin.controller.UsuarioController;


public class Principal extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String PAQUETE_VISTA = "/org/fabianmarroquin/view/";
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
            
       this.escenarioPrincipal = escenarioPrincipal;
       this.escenarioPrincipal.setTitle("Shylily 2022");
       escenarioPrincipal.getIcons().add(new Image("/org/fabianmarroquin/image/LogotipoIcono.png"));
//       Parent root = FXMLLoader.load(getClass().getResource("/org/fabianmarroquin/view/MenuPrincipalView.fxml"));
//       Scene escena = new Scene(root);
//       escenarioPrincipal.setScene(escena);
       ventanaLogin();
       escenarioPrincipal.show();
    }
    
    public void ventanaLogin(){
        try{
            LoginController login = (LoginController)cambiarEscena("LoginView.fxml",711,500);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try{
            UsuarioController usuario = (UsuarioController) cambiarEscena("UsuarioView.fxml",711,500);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController ventanaMenu = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",600,400);
            ventanaMenu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController vistaProgramador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml",600,400);
            vistaProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPacientes(){
        try{
            PacienteController vistaPaciente = (PacienteController) cambiarEscena("PacientesView.fxml",1070,500);
            vistaPaciente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaMedicamentos(){
        try{
            MedicamentoController vistaMedicamento = (MedicamentoController) cambiarEscena("MedicamentosView.fxml",775,500);
            vistaMedicamento.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEspecialidades(){
        try{
            EspecialidadController vistaEspecialidad = (EspecialidadController) cambiarEscena("EspecialidadesView.fxml",775,500);
            vistaEspecialidad.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPlantilla(){
        try{
            PlantillaController vistaPlantilla = (PlantillaController) cambiarEscena("Plantilla.fxml",781,400);
            vistaPlantilla.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDoctor(){
        try{
            DoctorController vistaDoctor = (DoctorController) cambiarEscena("DoctoresView.fxml",961,500);
            vistaDoctor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaReceta(){
        try{
            RecetaController vistaReceta = (RecetaController) cambiarEscena("RecetaView.fxml",818,500);
            vistaReceta.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDetalleReceta(){
        try{
            DetalleRecetaController vistaDetalleReceta = (DetalleRecetaController) cambiarEscena("DetalleRecetaView.fxml",961,500);
            vistaDetalleReceta.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCita(){
        try{
            CitaController vistaCita = (CitaController) cambiarEscena("CitasView.fxml",1070,500);
            vistaCita.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml,int ancho,int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
}
