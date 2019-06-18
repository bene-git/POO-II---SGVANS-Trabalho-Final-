package JUnit;

import Servidor.Frete_Viagem;
import Servidor.GerenteAc_Vans;
import Servidor.MotoristaAc;
import Servidor.Operacoes;
import Servidor.PassageiroAc;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesteSGVans {
    
    public TesteSGVans() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void teste01Diferenca(){
        double n1 = 10.50;
        double n2 = 5.40;
        double resultado = 5.10;
        
        double obtido = Operacoes.diferenca(n1, n2);
        
        assertEquals(resultado, obtido, 0.01);
    }
    
    @Test
    public void teste01Soma(){
        double n1 = 10.50;
        double n2 = 5.40;
        double resultado = 15.90;
        
        double obtido = Operacoes.somaValor(n1, n2);
        
        assertEquals(resultado, obtido, 0.01);
    }
    
    @Test
    public void teste02CadFrete(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = Frete_Viagem.cadastraFrete("AAA2222", 0, 0);
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste03CadViagem(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = Frete_Viagem.cadastraFrete("AAA2222", 0, 0);
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste04CadVan(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.cadastrarVan("AZA1234|Justino|VAN|1234|2019|DIESEL COMUM");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste05AlterarVan(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.alterarVan("50|AZA1234|Justino|VAN|1234|2019|DIESEL COMUM");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste06TrocaDeOleo(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.alterarTrocaOleo("AZA1234|17/06|17/08|100");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste07ExcluirVan(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.excluirVan("AZA1234");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste08FecharCarro(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.fecharCarro("AZA1234");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    /*
    @Test
    public void teste09Entrada(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.registrarEntrada("AZA1234|200");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste10Saida(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.registrarSaida("AZA1234|200");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste11LoginGerente(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = GerenteAc_Vans.validaLoginGerente("admin|usuario");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    */
    @Test
    public void teste12LoginMotorista(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = MotoristaAc.validaLoginMotorista("AZA1234|usuario");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    /*
    @Test
    public void teste13RegistraViagem(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = MotoristaAc.registrarViagem("AZA1234|10|2");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }*/
    
    @Test
    public void teste13RegistraPassagem(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = PassageiroAc.cadastrarPassagem("QWE2233|02|VALENÇA|PICOS|20");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
    
    @Test
    public void teste13VerAssento(){
        String result01 = "true";
        String result02 = "false";
        
        String obtido = PassageiroAc.verAssento("QWE2233|02");
        
        if(obtido.equals("true")){
            assertEquals(result01, obtido);
        } else {
            assertEquals(result02, obtido);
        }
    }
}
