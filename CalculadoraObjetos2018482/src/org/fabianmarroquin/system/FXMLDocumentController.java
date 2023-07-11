/*
Nombre: Fabian Angel Samuel Marroquin Chali
Codigo Tecnico: IN5AV
Carne: 2018482
Fecha de creacion: 23-03-2022
Fecha de modificacion: 1-04-2022
 */
package org.fabianmarroquin.system;

import static java.lang.Math.sqrt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author informatica
 */
    public class FXMLDocumentController implements Initializable {
    double dato1, dato2, result = 0;
    String nombreOpe = "";
    double acumulador;
    int menos = 1; 
    
    @FXML private Button btnMasMenos;
    @FXML private Button btnCero;
    @FXML private Button btnPunto;
    @FXML private Button btnIgual;
    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;
    @FXML private Button btnMas;
    @FXML private Button btnMenos;
    @FXML private Button btnMulti;
    @FXML private Button btn1X;
    @FXML private Button btnCuadrado;
    @FXML private Button btnRaiz;
    @FXML private Button btnDiv;
    @FXML private Button btnPorciento;
    @FXML private Button btnCE;
    @FXML private Button btnC;
    @FXML private TextField txtPantalla;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
       if (event.getSource() == btnCero){
           txtPantalla.setText(txtPantalla.getText()+"0");
       }
       
       else if(event.getSource() == btn1){
           txtPantalla.setText(txtPantalla.getText()+"1");
       }
       
       else if (event.getSource() == btn2){
           txtPantalla.setText(txtPantalla.getText()+"2");
       }
       
       else if (event.getSource() == btn3){
           txtPantalla.setText(txtPantalla.getText()+"3");
       }
       
       else if (event.getSource() == btn4){
           txtPantalla.setText(txtPantalla.getText()+"4");
       }
       
       else if(event.getSource() == btn5){
           txtPantalla.setText(txtPantalla.getText()+"5");
       }
       
       else if(event.getSource() == btn6){
           txtPantalla.setText(txtPantalla.getText()+"6");
       }
       
       else if(event.getSource() == btn7){
           txtPantalla.setText(txtPantalla.getText()+"7");
       }
       
       else if(event.getSource() == btn8){
           txtPantalla.setText(txtPantalla.getText()+"8");
       }
       
       else if(event.getSource() == btn9){
           txtPantalla.setText(txtPantalla.getText()+"9"); 
       }
       
       else if(event.getSource() == btnPunto){
           if(txtPantalla.getText().length() == 0){
               txtPantalla.setText(txtPantalla.getText() +"0.");
           }
           else if(txtPantalla.getText().length() > 0){
               txtPantalla.setText(txtPantalla.getText()+".");
           }
       }
       
       else if (event.getSource() == btnMas){
           dato1 = Double.parseDouble(txtPantalla.getText());
           txtPantalla.clear();
           acumulador = acumulador + dato1;
           nombreOpe = "mas";
       }
       
       else if(event.getSource() == btnMenos){
           dato1 = Double.parseDouble(txtPantalla.getText());
           txtPantalla.clear();
           if (menos == 1){
                acumulador = dato1;
            }else {
                acumulador = acumulador - dato1;
            }
            menos = 2;
           nombreOpe = "menos";
       }
       
       else if(event.getSource() == btnMulti){
           dato1 = Double.parseDouble(txtPantalla.getText());
           txtPantalla.clear();
           nombreOpe = "multi";
       }
      
       else if(event.getSource() == btnDiv){
           dato1 = Double.parseDouble(txtPantalla.getText());
           txtPantalla.clear();
           nombreOpe = "div";
       }
       
           else if(event.getSource() == btnIgual){
                if(nombreOpe == "mas"){
//              esto es la suma
                dato2 = Double.parseDouble(txtPantalla.getText());
                txtPantalla.clear();
                result = (acumulador + dato2);
                acumulador = 0;
                txtPantalla.setText(String.valueOf(result));
                
//                dato2 = Double.parseDouble(txtPantalla.getText());
//                txtPantalla.clear();
//                result = dato1 + dato2;
//                txtPantalla.setText(String.valueOf(result));
           }
           
           else if(nombreOpe == "menos"){
//              esto es la resta
                dato2 = Double.parseDouble(txtPantalla.getText());
                txtPantalla.clear();
                result = (acumulador - dato2);
                    acumulador = 0;
                    menos = 1;
                txtPantalla.setText(String.valueOf(result));
           }
           
           else if(nombreOpe == "multi"){
//              esto es la multiplicacion
                dato2 = Double.parseDouble(txtPantalla.getText());
                txtPantalla.clear();
                result = dato1 * dato2;
                txtPantalla.setText(String.valueOf(result));
           }
           
           else if(nombreOpe == "div"){
               
//               esto es la division
                dato2 = Double.parseDouble(txtPantalla.getText());
                txtPantalla.clear();
                result = dato1 / dato2;
                txtPantalla.setText(String.valueOf(result));
                } 
            }
       
           else if(event.getSource() == btnC){
               txtPantalla.clear();
           }
       
           else if(event.getSource() == btnCE){
               dato2 = Double.parseDouble(txtPantalla.getText());
               txtPantalla.clear();
               txtPantalla.setText(txtPantalla.getText()+ " ");
               
           }
       
           else if(event.getSource() == btnMasMenos){
                  dato1 = Double.parseDouble(txtPantalla.getText());
                  result = dato1 * -1;
                  txtPantalla.setText(String.valueOf(result));
           }
           
           else if(event.getSource() == btnCuadrado){
               dato1 = Double.parseDouble(txtPantalla.getText());
               result = dato1 * dato1;
               txtPantalla.setText(String.valueOf(result));
           }
           
           else if(event.getSource() == btnRaiz){
               dato1 = Double.parseDouble(txtPantalla.getText());
               result = (sqrt(dato1));
               txtPantalla.setText(String.valueOf(result));
           }
           
           else if(event.getSource() == btnPorciento){
               double valPantalla, valPorcentaje;
               valPantalla = Double.parseDouble(txtPantalla.getText());
               txtPantalla.clear();
               valPorcentaje = dato1 * valPantalla / 100;
               txtPantalla.setText(String.valueOf(valPorcentaje));
           }
       
           else if(event.getSource() == btn1X){
               dato1 = Double.parseDouble(txtPantalla.getText());
               result = (1 / dato1);
               txtPantalla.setText(String.valueOf(result));
           }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

//tener resta y suma suseciva