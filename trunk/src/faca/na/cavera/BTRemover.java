/**
 *
 * @author Bruno Gabriel
 */
package faca.na.cavera;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class BTRemover implements ActionListener {
    private void remover(){
        
        String nome = (String) VariaveisModais.listaXml.getSelectedValue();
        String chave = "temp/" + MD5.gerar(nome) + ".xml";
        chave.trim();
        try{
            Configuration.removeNoSite(nome);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Não foi possivel remover o site, tente novamente!", "Remover", JOptionPane.ERROR_MESSAGE);
            return;
        }
        VariaveisModais.listaXml.removerElementoLista(nome);
        //Que bruxaria é essa?
        Configuration.deletarArquivoXml(chave);
        JOptionPane.showMessageDialog(null, "Site removido com sucesso!", "Remover", JOptionPane.INFORMATION_MESSAGE);
        Configuration.deletarArquivoXml(chave);
        
          //  JOptionPane.showMessageDialog(null, "Não foi possivel remover o site, tente novamente!", "Remover", JOptionPane.ERROR_MESSAGE);
        
        
    }
    public void actionPerformed(ActionEvent e) {
        if(VariaveisModais.listaXml.getSelectedIndex()!=-1)
        switch(JOptionPane.showConfirmDialog(null, "Deseja realmente remover?","Remover",JOptionPane.OK_CANCEL_OPTION)){
            case 0:
                remover();
                break;
            case 1:
                break;
        }
    }
    
}
