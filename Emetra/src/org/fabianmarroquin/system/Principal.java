/* 
NOMBRE: Fabian Angel Samuel Marroquin Chali
CODIGO: IN5AV
FECHA DE CREACION: 27/09/2022
FECHA DE MODIFICACION: 28/09/2022
 */
package org.fabianmarroquin.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fabianmarroquin.controller.MenuPrincipalController;
import org.fabianmarroquin.controller.VecinosController;
import org.fabianmarroquin.controller.VehiculosController;

public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/fabianmarroquin/view/";
    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("PMT");
        escenarioPrincipal.getIcons().add(new Image("/org/fabianmarroquin/image/logo-pmt.png"));
        menuPrincipal();
        escenarioPrincipal.show();
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController ventanaMenu = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml",444,400);
            ventanaMenu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaVecinos(){
        try{
            VecinosController vistaVecinos = (VecinosController) cambiarEscena("VecinosView.fxml",1003,609);
            vistaVecinos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaVehiculos(){
        try{
            VehiculosController vistaVehiculos = (VehiculosController) cambiarEscena("VehiculosView.fxml",1003,609);
            vistaVehiculos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;
    }
    
        public static void main(String[] args) {
        launch(args);
    }
}
