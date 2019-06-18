/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Benedito Rodrigo
 */
public class MotoristaAc {
    static ConexaoBD conexaoBanco = new ConexaoBD();
    static ResultSet resultSet = null;
    static Statement statement = null;
    static PreparedStatement remove = null;
    
    public static String validaLoginMotorista(String tudo){
        String retorno = "false";
        
        String gamb = "";
        String [] vet = new String [2];
        int p = 0;
        
        for(int i=0;i<tudo.length();i++){
            gamb = gamb + tudo.substring(i, i+1);
            if(tudo.substring(i, i+1).equals("|")||i==tudo.length()-1){
                vet[p] = gamb;
                gamb = "";
                p++;
            }
        }
        
        String placa = (vet[0].substring(0, vet[0].length()-1));
        String senha = (vet[1].substring(0, vet[1].length()));
        
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();
            
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(placa)&&resultSet.getString("senha").equals(senha)){
                    retorno = resultSet.getString("motorista");
                }                
            }
        }catch(SQLException e){
                
        }finally{
            try{
                resultSet.close();
                statement.close();
                conexaoBanco.desconectar();
            }catch(SQLException ex){
                    
            }
        }
        
        return retorno;
    }
    
    public static String registrarViagem(String tudo){
        
        String ent = "";
        String sai = "";
        
        String gamb = "";
        String [] vet = new String [3];
        
        int p = 0;
        
        for(int i=0;i<tudo.length();i++){
            gamb = gamb + tudo.substring(i, i+1);
            if(tudo.substring(i, i+1).equals("|")||i==tudo.length()-1){
                vet[p] = gamb;
                gamb = "";
                p++;
            }
        }
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();   
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(vet[0].substring(0, vet[0].length()-1))){
                    ent = resultSet.getString("entrada");
                    sai = resultSet.getString("saida");
                }                
            }
        }catch(SQLException e){    
        }finally{
            try{
                resultSet.close();
                statement.close();
                conexaoBanco.desconectar();
            }catch(SQLException ex){     
            }
        }
        
        //entrada e saida do banco
        double et = Double.parseDouble(ent);
        double st = Double.parseDouble(sai);
        
        //entrada e saida do frete
        double en = Double.parseDouble(vet[1].substring(0, vet[1].length()-1));
        double sn = Double.parseDouble(vet[2].substring(0, vet[2].length()));
        
        String retorno = "false";
        
        String sqlalterar = "UPDATE transportes" 
            + " SET "
            + " entrada = ?,"
            + " saida = ?"
            + " WHERE placa = ?";
        
        PreparedStatement inserir = null;
        
        try{
            inserir = conexaoBanco.inserirTabela(sqlalterar);
            inserir.setDouble(1, (et+en));
            inserir.setDouble(2, (st+sn));
            inserir.setString(3, vet[0].substring(0, vet[0].length()-1));
            
            int result = inserir.executeUpdate();
            
            if(result == 1){
                retorno = "true";
                Frete_Viagem.cadastraViagem(vet[0].substring(0, vet[0].length()-1), en, sn);
            } 
        }catch(SQLException e){
            System.out.println("ERRO!");
        } finally{
            if(inserir != null){
                try {
                    inserir.close();
                } catch (SQLException ex) {
                }
            }
            conexaoBanco.desconectar();
        }
        
        return retorno;
    }
}
