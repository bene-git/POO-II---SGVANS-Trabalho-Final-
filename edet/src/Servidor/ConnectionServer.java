/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Benedito Rodrigo
 */
public class ConnectionServer {
    
    private final DataOutputStream saida;
    private final DataInputStream entrada;

    public ConnectionServer(Socket cliente) throws IOException {
        this.saida = new DataOutputStream(cliente.getOutputStream());
        this.entrada = new DataInputStream(cliente.getInputStream());
    }

    public void enviar(String str) throws IOException {
        saida.writeUTF(str);
    }

    public String receber() throws IOException {
        return entrada.readUTF();
    }

    public void fechar() throws IOException {
        this.saida.close();
        this.entrada.close();
    }
}
