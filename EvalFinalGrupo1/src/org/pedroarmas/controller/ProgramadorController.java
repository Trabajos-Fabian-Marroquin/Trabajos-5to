package org.pedroarmas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.pedroarmas.system.Principal;


public class ProgramadorController implements Initializable{
    private Principal escenarioPrincipal;
    @FXML Button btnProgramador;
    @FXML Button btnKinal;
    @FXML Label lblNombres;
    @FXML Label lblApellidos;
    @FXML Label lblTitulo;
    @FXML AnchorPane pnlStatus;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    private void opciones(ActionEvent event){
        if (event.getSource() == btnProgramador){
            lblNombres.setText("Pedro Manuel");
            lblApellidos.setText("Armas Chang");
            lblTitulo.setText("Instructor");
        }else if(event.getSource() == btnKinal){
            //pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(249, 228, 134),CornerRadii.EMPTY,Insets.EMPTY)));
            lblNombres.setText("Fundación");
            lblApellidos.setText("®KINAL");
            lblTitulo.setText("2021");
            
        }
        
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
