package Servidor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class PassageiroAc {
    
    static ConexaoBD conexaoBanco = new ConexaoBD();
    static ResultSet resultSet = null;
    static Statement statement = null;
    static PreparedStatement remove = null;
    
    static Calendar c = Calendar.getInstance();
    
    public static synchronized String cadastrarPassagem(String tudo){
        String retorno = "";
        
        int mes = 0, dia = 0;
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH)+1;
        String data = String.valueOf(dia)+"/"+String.valueOf(mes);
        
        String gamb = "";
        String [] vet = new String [5];
        int p = 0;
        
        for(int i=0;i<tudo.length();i++){
            gamb = gamb + tudo.substring(i, i+1);
            if(tudo.substring(i, i+1).equals("|")||i==tudo.length()-1){
                vet[p] = gamb;
                gamb = "";
                p++;
            }
        }
        int cont = 0;
        String sqlselect = "SELECT * FROM passagem";
        statement = conexaoBanco.criarTabela();
            
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(vet[0].substring(0, vet[0].length()-1))&&
                        resultSet.getString("assento").equals(vet[1].substring(0, vet[1].length()-1))){
                    cont++;
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
        
        if(cont!=0){
            retorno = "false";
        } else{
            String sqlinsert = "INSERT INTO passagem ("
            + "placa,"
            + "assento,"
            + "saida,"
            + "destino,"
            + "valor"
            + ") VALUES(?,?,?,?,?)"
            + ";";
        
            PreparedStatement inserir = conexaoBanco.inserirTabela(sqlinsert);

            try{
                inserir.setString(1, vet[0].substring(0, vet[0].length()-1));
                inserir.setString(2, vet[1].substring(0, vet[1].length()-1));
                inserir.setString(3, vet[2].substring(0, vet[2].length()-1));
                inserir.setString(4, vet[3].substring(0, vet[3].length()-1));
                inserir.setDouble(5, Double.parseDouble(vet[4].substring(0, vet[4].length())));

                int result = inserir.executeUpdate();

                if(result == 1){
                    retorno = "true";
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
        }
        return retorno;
    }
    
    public static String verAssento(String tudo){
        String retorno = "true";
        
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
        int cont = 0;
        String sqlselect = "SELECT * FROM passagem";
        statement = conexaoBanco.criarTabela();
            
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(vet[0].substring(0, vet[0].length()-1))&&
                        resultSet.getString("assento").equals(vet[1].substring(0, vet[1].length()))){
                    retorno = "false";
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
    
    public static String recuperarPassagens(){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM passagem";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                retorno = resultSet.getString("placa")+"|"+resultSet.getString("assento")+"|"+
                        resultSet.getString("saida")+"|"+resultSet.getString("destino")+"|"+
                        String.valueOf(resultSet.getString("valor"));
                retornar = retornar + retorno +"=";
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
    
    public static String recuperarPassagensCarro(String placa){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM passagem";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(placa)){
                    retorno = resultSet.getString("placa")+"|"+resultSet.getString("assento")+"|"+
                            resultSet.getString("saida")+"|"+resultSet.getString("destino")+"|"+
                            String.valueOf(resultSet.getString("valor"));
                    retornar = retornar + retorno +"=";
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
    
    public static String excluirTickets(){
        
        String retorno = "false";
        
        String sqlremove = "DELETE FROM passagem;";

        try{
            remove = conexaoBanco.inserirTabela(sqlremove);
            remove.executeUpdate();
            retorno = "true";

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
        return retorno;
    }
    
    public static String excluirTicketsCarro(String placa){
        
        String retorno = "false";
        
        String sqlremove = "DELETE FROM passagem WHERE placa = ?;";

        try{
            remove = conexaoBanco.inserirTabela(sqlremove);
            remove.setString(1, placa);
            remove.executeUpdate();
            retorno = "true";

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
        return retorno;
    }
}
