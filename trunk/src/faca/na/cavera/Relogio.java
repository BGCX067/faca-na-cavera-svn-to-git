/**
 *
 * @author Bruno Gabriel
 */
package faca.na.cavera;

import javax.swing.JOptionPane;


public class Relogio {
    private Atualizar atualizaJanela = null;
    private Thread relogio = null;
    public Relogio(){
         //Janela de atualizações
         atualizaJanela = new Atualizar();
    }
    public  void parar(){
        relogio.interrupt();
    }
    public void comecar(){
       
    relogio = new Thread(new Runnable(){
             public void run() {
                   //atualiza site
                   while(VariaveisModais.executa){
                        Thread t = new Thread(new Runnable(){
                              public void run() {
                                        //Executa atualizar
                                  atualizaJanela.mostrarJanela();
                              }
                        });
                        try {
                              t.sleep(VariaveisModais.tempoParaAtualizar);
                        } catch (InterruptedException ex) {
                              t.interrupt();
                              VariaveisModais.executa=false;
                              JOptionPane.showMessageDialog(null, "As atualizações foram paradas","Parar",JOptionPane.INFORMATION_MESSAGE);
                              atualizaJanela.esconderJanela();
                        }
                        t.start();
                   }
               }
           });
        relogio.start();
    }
    
}
