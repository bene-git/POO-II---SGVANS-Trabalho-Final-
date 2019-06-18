package Servidor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenteAc_Vans {
    
    static ConexaoBD conexaoBanco = new ConexaoBD();
    static ResultSet resultSet = null;
    static Statement statement = null;
    static PreparedStatement remove = null;
    static Calendar c = Calendar.getInstance();
    
    public static synchronized String cadastrarVan(String tudo){
        
        int mes = 0, dia = 0;
        
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH)+1;
        
        
        int p_mes = mes + 2;
        int p_dia = dia;
        if(p_mes>12){
            p_mes -= 12;
        }
        if(p_mes==2){
            if(dia>28){
                dia = 28;
            }
        }else{
            if(dia>30){
                dia = 30;
            }
        }
        
        String troca_ant = String.valueOf(dia)+"/"+String.valueOf(mes);
        String troca_pro = String.valueOf(p_dia)+"/"+String.valueOf(p_mes);
        
        String retorno = "false";
        String gamb = "";
        String [] vet = new String [6];
        
        String id = gerarId();
        
        int cont = 0;
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
            String sqlinsert = "INSERT INTO transportes ("
            + "id,"
            + "placa,"
            + "motorista,"
            + "tipo,"
            + "senha,"
            + "ano,"
            + "combustivel,"
            + "troca_ant,"
            + "troca_pro,"
            + "entrada,"
            + "saida"
            + ") VALUES(?,?,?,?,?,?,?,?,?,?,?)"
            + ";";
        
            PreparedStatement inserir = conexaoBanco.inserirTabela(sqlinsert);

            try{
                inserir.setString(1, id);
                inserir.setString(2, vet[0].substring(0, vet[0].length()-1));
                inserir.setString(3, vet[1].substring(0, vet[1].length()-1));
                inserir.setString(4, vet[2].substring(0, vet[2].length()-1));
                inserir.setString(5, vet[3].substring(0, vet[3].length()-1));
                inserir.setString(6, vet[4].substring(0, vet[4].length()-1));
                inserir.setString(7, vet[5].substring(0, vet[5].length()));
                inserir.setString(8, troca_ant);
                inserir.setString(9, troca_pro);
                inserir.setDouble(10, 0.0);
                inserir.setDouble(11, 0.0);

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
    
    public static synchronized String alterarVan(String tudo){

        String retorno = "false";
        String gamb = "";
        String [] vet = new String [7];
        
        int cont = 0;
        int p = 0;
        
        for(int i=0;i<tudo.length();i++){
            gamb = gamb + tudo.substring(i, i+1);
            if(tudo.substring(i, i+1).equals("|")||i==tudo.length()-1){
                vet[p] = gamb;
                gamb = "";
                p++;
            }
        }
        
        String sqlalterar = "UPDATE transportes" 
            + " SET "
            + " placa = ?,"
            + " motorista = ?,"
            + " tipo = ?,"
            + " senha = ?,"
            + " ano = ?,"
            + " combustivel = ?"
            + " WHERE id = ?";
        
        PreparedStatement inserir = null;
        
        try{
            inserir = conexaoBanco.inserirTabela(sqlalterar);
            inserir.setString(1, vet[1].substring(0, vet[1].length()-1));
            inserir.setString(2, vet[2].substring(0, vet[2].length()-1));
            inserir.setString(3, vet[3].substring(0, vet[3].length()-1));
            inserir.setString(4, vet[4].substring(0, vet[4].length()-1));
            inserir.setString(5, vet[5].substring(0, vet[5].length()-1));
            inserir.setString(6, vet[6].substring(0, vet[6].length()));
            inserir.setString(7, vet[0].substring(0, vet[0].length()-1));
            
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
        
        return retorno;
    }
    
    public static String alterarTrocaOleo(String tudo){

        String retorno = "false";
        String gamb = "";
        String [] vet = new String [4];
        double saida = 0;
        
        int cont = 0;
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
                    saida = resultSet.getDouble("saida");
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
        
        saida = saida + (Double.parseDouble(vet[3]));
        
        String sqlalterar = "UPDATE transportes" 
            + " SET "
            + " troca_ant = ?,"
            + " troca_pro = ?,"
            + " saida = ?"
            + " WHERE placa = ?";
        
        PreparedStatement inserir = null;
        
        try{
            inserir = conexaoBanco.inserirTabela(sqlalterar);
            inserir.setString(1, vet[1].substring(0, vet[1].length()-1));
            inserir.setString(2, vet[2].substring(0, vet[2].length()-1));
            inserir.setDouble(3, saida);
            inserir.setString(4, vet[0].substring(0, vet[0].length()-1));
            
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
        
        return retorno;
    }
    
    public static String recuperaTudo(String placa){
        String retorno = "false";
        int cont = 0;
        
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();
            
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(placa)){
                    retorno = resultSet.getString("id")+"|"+resultSet.getString("placa")+"|"+
                            resultSet.getString("motorista")+"|"+resultSet.getString("tipo")+"|"+
                            resultSet.getString("senha")+"|"+resultSet.getString("ano")+"|"+
                            resultSet.getString("combustivel")+"|"+
                            resultSet.getString("troca_ant")+"|"+resultSet.getString("troca_pro")+"|"+
                            String.valueOf(resultSet.getDouble("entrada"))+"|"+
                            String.valueOf(resultSet.getDouble("saida"));
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
        
        return retorno;
    }
    
    public static String excluirVan(String placa){
        String retorno = "false";
        
        String sqlremove = "DELETE FROM transportes"
            + " WHERE placa = ?;";

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
    
    public static String recuperaPlacas(){
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();
        
        String placas = "";
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                placas = placas+(resultSet.getString("placa"))+"|";             
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
        return placas;
    }
    
    public static String recuperarTrocaOleo(){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                retorno = resultSet.getString("placa")+"|"+resultSet.getString("motorista")+"|"+
                        resultSet.getString("troca_ant")+"|"+resultSet.getString("troca_pro")+"|";
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
    
    public static String recuperarRelatorio(){
        String retorno = "";
        String retornar = "";
        
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();
        
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                String ent, sai;
                double e, s;
                retorno = resultSet.getString("placa")+"|"+resultSet.getString("motorista")+"|"+
                        resultSet.getString("entrada")+"|"+resultSet.getString("saida")+"|";
                ent = resultSet.getString("entrada");
                sai = resultSet.getString("saida");
                e = Double.parseDouble(ent);
                s = Double.parseDouble(sai);
                retornar = retornar + retorno + String.valueOf(e-s)+"|=";
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
    
    public static synchronized String fecharCarro(String placa){

        String retorno = "false";
        
        String sqlalterar = "UPDATE transportes" 
            + " SET "
            + " entrada = ?,"
            + " saida = ?"
            + " WHERE placa = ?";
        
        PreparedStatement inserir = null;
        
        try{
            inserir = conexaoBanco.inserirTabela(sqlalterar);
            inserir.setDouble(1, 0.0);
            inserir.setDouble(2, 0.0);
            inserir.setString(3, placa);
            
            int result = inserir.executeUpdate();
            
            if(result == 1){
                retorno = "true";
                Frete_Viagem.excluirFreteViagemCarro(placa);
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
    
    public static synchronized String fecharCarroTodos(){

        String retorno = "false";
        
        String sqlalterar = "UPDATE transportes" 
            + " SET "
            + " entrada = ?,"
            + " saida = ?";
        
        PreparedStatement inserir = null;
        
        try{
            inserir = conexaoBanco.inserirTabela(sqlalterar);
            inserir.setDouble(1, 0.0);
            inserir.setDouble(2, 0.0);
            
            int result = inserir.executeUpdate();
            Frete_Viagem.excluirFreteViagem();
            retorno = "true"; 
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
        
        return retorno;
    }
    
    public static String registrarFrete(String tudo){
        
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
            inserir.setDouble(1, Operacoes.somaValor(et, en));
            inserir.setDouble(2, (st+sn));
            inserir.setString(3, vet[0].substring(0, vet[0].length()-1));
            
            int result = inserir.executeUpdate();
            
            if(result == 1){
                retorno = "true";
                Frete_Viagem.cadastraFrete(vet[0].substring(0, vet[0].length()-1), en, sn);
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
    
    public static String registrarEntrada(String tudo){
        
        String ent = "";
        
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
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();   
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(vet[0].substring(0, vet[0].length()-1))){
                    ent = resultSet.getString("entrada");
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
        
        //entrada do banco
        double et = Double.parseDouble(ent);
        
        //entrada da transacao
        double en = Double.parseDouble(vet[1].substring(0, vet[1].length()));
        
        String retorno = "false";
        
        String sqlalterar = "UPDATE transportes" 
            + " SET "
            + " entrada = ?"
            + " WHERE placa = ?";
        
        PreparedStatement inserir = null;
        
        try{
            inserir = conexaoBanco.inserirTabela(sqlalterar);
            inserir.setDouble(1, (et+en));
            inserir.setString(2, vet[0].substring(0, vet[0].length()-1));
            
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
        
        return retorno;
    }
    
    public static String registrarSaida(String tudo){
        
        String sai = "";
        
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
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();   
        try{
            resultSet = statement.executeQuery(sqlselect);
            while(resultSet.next()){
                if(resultSet.getString("placa").equals(vet[0].substring(0, vet[0].length()-1))){
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
        
        //saida do banco
        double st = Double.parseDouble(sai);
        
        //entrada da transacao
        double sn = Double.parseDouble(vet[1].substring(0, vet[1].length()));
        
        String retorno = "false";
        
        String sqlalterar = "UPDATE transportes" 
            + " SET "
            + " saida = ?"
            + " WHERE placa = ?";
        
        PreparedStatement inserir = null;
        
        try{
            inserir = conexaoBanco.inserirTabela(sqlalterar);
            inserir.setDouble(1, (st-sn));
            inserir.setString(2, vet[0].substring(0, vet[0].length()-1));
            
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
        
        return retorno;
    }
    
    public static synchronized String gerarId(){
        String id = "";
        int cont = 0;
        int n;
        
        String sqlselect = "SELECT * FROM transportes";
        statement = conexaoBanco.criarTabela();
        
        do{
            cont = 0;
            Random rand = new Random();
            n = rand.nextInt(9);
            id = String.valueOf(n);
            
            try{
                resultSet = statement.executeQuery(sqlselect);
                while(resultSet.next()){
                    if(resultSet.getString("id").equals(id)){
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
        }while(cont!=0);
        
        return id;
    }
    
    public static String validaLoginGerente(String tudo){
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
        
        String login = (vet[0].substring(0, vet[0].length()-1));
        String senha = (vet[1].substring(0, vet[1].length()));
        
        if(login.equals("admin")&&senha.equals("usuario")){
            retorno = "true";
        }
        
        return retorno;
    }
}
