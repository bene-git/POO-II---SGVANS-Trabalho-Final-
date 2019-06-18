package Servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread{
    
    ConnectionServer conexao;
    
    public Servidor(Socket cliente) throws IOException{
        this.conexao = new ConnectionServer(cliente);
    }
    
    @Override
    public void run() {
       
        while(true){
            try {
                String op;
                String volta;
                op = conexao.receber();
                switch(op.substring(0, 2)){
                    //login do gerente
                    case "lg":
                        volta = GerenteAc_Vans.validaLoginGerente(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //login motorista
                    case "lm":
                        volta = MotoristaAc.validaLoginMotorista(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //cadastrar van
                    case "cv":
                        volta = GerenteAc_Vans.cadastrarVan(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //pedir dados
                    case "pd":
                        volta = GerenteAc_Vans.recuperaTudo(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //pedir placas
                    case "pp":
                        volta = GerenteAc_Vans.recuperaPlacas();
                        conexao.enviar(volta);
                        break;
                    
                    //alterar van
                    case "av":
                        volta = GerenteAc_Vans.alterarVan(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //excluir vans
                    case "ev":
                        volta = GerenteAc_Vans.excluirVan(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //registrar troca de oleo
                    case "rt":
                        volta = GerenteAc_Vans.alterarTrocaOleo(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //pedir troca de oleo
                    case "pt":
                        volta = GerenteAc_Vans.recuperarTrocaOleo();
                        conexao.enviar(volta);
                        break;
                    
                    //pedir relatorio completo
                    case "pr":
                        volta = GerenteAc_Vans.recuperarRelatorio();
                        conexao.enviar(volta);
                        break;
                    
                    //fechar mes de um carro
                    case "fc":
                        volta = GerenteAc_Vans.fecharCarro(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //fechar mes todos os carros
                    case "ft":
                        volta = GerenteAc_Vans.fecharCarroTodos();
                        conexao.enviar(volta);
                        break;
                    
                    //registrar frete
                    case "rf":
                        volta = GerenteAc_Vans.registrarFrete(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //registrar viagem
                    case "rv":
                        volta = MotoristaAc.registrarViagem(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //registrar entrada
                    case "re":
                        volta = GerenteAc_Vans.registrarEntrada(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //registrar saida
                    case "rs":
                        volta = GerenteAc_Vans.registrarSaida(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //recuperar fretes em geral
                    case "tf":
                        volta = Frete_Viagem.recuperarFretes();
                        conexao.enviar(volta);
                        break;
                    
                    //recuperar viagens em geral
                    case "tv":
                        volta = Frete_Viagem.recuperarViagens();
                        conexao.enviar(volta);
                        break;
                    
                    //recuperar fretes carro
                    case "tc":
                        volta = Frete_Viagem.recuperarFretesCarro(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //recuperar viagem carro
                    case "tl":
                        volta = Frete_Viagem.recuperarViagensCarro(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //cadastrar Ticket
                    case "ui":
                        volta = PassageiroAc.cadastrarPassagem(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //confere assento
                    case "os":
                        volta = PassageiroAc.verAssento(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //recupera todos os tickes
                    case "qq":
                        volta = PassageiroAc.recuperarPassagens();
                        conexao.enviar(volta);
                        break;
                    
                    //recupera tickets de um carro
                    case "ww":
                        volta = PassageiroAc.recuperarPassagensCarro(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                    
                    //excluir todos os tickets
                    case "kk":
                        volta = PassageiroAc.excluirTickets();
                        conexao.enviar(volta);
                        break;
                    
                    //excluir todos os tickets
                    case "zz":
                        volta = PassageiroAc.excluirTicketsCarro(op.substring(2, op.length()));
                        conexao.enviar(volta);
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
}