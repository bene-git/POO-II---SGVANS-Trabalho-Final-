/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mandar;

import edet.telaInicial;
import java.io.IOException;

public class MainCliente {
    
    public static void main(String[] args) throws IOException{
        
        new telaInicial(new Cliente()).setVisible(true);
            
    }
}
