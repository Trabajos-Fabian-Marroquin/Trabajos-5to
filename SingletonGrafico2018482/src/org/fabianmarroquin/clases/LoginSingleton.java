/*
    paso 1: crear un constructor de clase
    paso 2: declarar una variable privada y estatica del formulario que se requiere utilizar
    paso 3: agregar la libreria
    paso 4: crear un metodo para instancia del objeto
    paso 5: agregar el paquete login con su clase
*/
package org.fabianmarroquin.clases;

import javax.swing.JFrame;
import org.fabianmarroquin.system.Login;

public class LoginSingleton {
    private static JFrame log;
    
    private LoginSingleton(){
    }
    
    public static JFrame getInsatance(){
        if(log == null)
            log = new Login();
        return log;
    }
    
}
