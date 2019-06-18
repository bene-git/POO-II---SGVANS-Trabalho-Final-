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
import java.util.Calendar;

/**
 *
 * @author Benedito Rodrigo
 */
public class Frete_Viagem {
    
    static ConexaoBD conexaoBanco = new ConexaoBD();
    static ResultSet resultSet = null;
    static Statement statement = null;
    static PreparedStatement remove = null;
    static Calendar c = Calendar.getInstance();
    
    public static String cadastraFrete(String placa, double ent, double sai){
        
        String retorno = "false";
        
        int mes = 0, dia = 0;
        
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH)+1;
        
        String data = String.valueOf(dia)+"/"+String.valueOf(mes);
        
        String sqlinsert = "INSERT INTO frete ("
            + "placa,"
            + "data,"
            + "entrada,"
            + "saida,"
            + "lucro"
            + ") VALUES(?,?,?,?,?)"
            + ";";
        
        PreparedStatement inserir = conexaoBanco.inserirTabela(sqlinsert);

        try{
            inserir.setString(1, placa);
            inserir.setString(2, data);
            inserir.setDouble(3, ent);
            inserir.setDouble(4, sai);
            inserir.setDouble(5, ent-sai);

            int result = inserir.executeUpdate();

            if(result == 1){
                retorno = "true";
            } 
        }catch(SQLException e){
            //System.out.println("ERRO!");
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
    
    public static String cadastraViagem(String placa, double ent, double sai){
        
        String retorno = "false";
        
        int mes = 0, dia = 0;
        
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH)+1;
        
        String data = String.valueOf(dia)+"/"+String.valueOf(mes);
        
        String sqlinsert = "INSERT INTO viagem ("
            + "placa,"
            + "data,"
            + "entrada,"
            + "saida,"
            + "lucro"
            + ") VALUES(?,?,?,?,?)"
            + ";";
        
        PreparedStatement inserir = conexaoBanco.inserirTabela(sqlinsert);

        try{
            inserir.setString(1, placa);
            inserir.setString(2, data);
            inserir.setDouble(3, ent);
            inserir.setDouble(4, sai);
            inserir.setDouble(5, (ent-sai));

            int result = inserir.executeUpdate();

            if(result == 1){
                retorno = "true";
            } 
        }catch(SQLException e){
            //System.out.println("ERRO!");
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
    
    public static String recuperarFretes(){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM frete";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                retorno = resultSet.getString("placa")+"|"+resultSet.getString("data")+"|"+
                        resultSet.getString("entrada")+"|"+resultSet.getString("saida")+"|"+
                        resultSet.getString("lucro");
                retornar = retornar + retorno +"|=";
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
        return retornar;
    }
    
    public static String recuperarViagens(){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM viagem";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                retorno = resultSet.getString("placa")+"|"+resultSet.getString("data")+"|"+
                        resultSet.getString("entrada")+"|"+resultSet.getString("saida")+"|"+
                        resultSet.getString("lucro");
                retornar = retornar + retorno +"|=";
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
        return retornar;
    }
    
    public static String recuperarFretesCarro(String placa){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM frete";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(placa)){
                    retorno = resultSet.getString("placa")+"|"+resultSet.getString("data")+"|"+
                            resultSet.getString("entrada")+"|"+resultSet.getString("saida")+"|"+
                            resultSet.getString("lucro");
                    retornar = retornar + retorno +"|=";
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
        return retornar;
    }
    
    public static String recuperarViagensCarro(String placa){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM viagem";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(placa)){
                    retorno = resultSet.getString("placa")+"|"+resultSet.getString("data")+"|"+
                            resultSet.getString("entrada")+"|"+resultSet.getString("saida")+"|"+
                            resultSet.getString("lucro");
                    retornar = retornar + retorno +"|=";
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
        return retornar;
    }

    public static void excluirFreteViagem(){
        
        String sqlremove = "DELETE FROM frete;";

        try{
            remove = conexaoBanco.inserirTabela(sqlremove);
            remove.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                remove.close();
                conexaoBanco.desconectar();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        
        sqlremove = "DELETE FROM viagem;";

        try{
            remove = conexaoBanco.inserirTabela(sqlremove);
            remove.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                remove.close();
                conexaoBanco.desconectar();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
    public static void excluirFreteViagemCarro(String placa){
        String sqlremove = "DELETE FROM frete"
            + " WHERE placa = ?;";

        try{
            remove = conexaoBanco.inserirTabela(sqlremove);
            remove.setString(1, placa);

            remove.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                remove.close();
                conexaoBanco.desconectar();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        
        sqlremove = "DELETE FROM viagem"
            + " WHERE placa = ?;";

        try{
            remove = conexaoBanco.inserirTabela(sqlremove);
            remove.setString(1, placa);

            remove.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            try{
                remove.close();
                conexaoBanco.desconectar();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
