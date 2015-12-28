/**
 *
 * @author Bruno Gabriel
 */
package faca.na.cavera;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BTSair implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        confirmarSaida();
    }
    private void confirmarSaida(){
        switch(JOptionPane.showConfirmDialog(null, "Deseja realmente sair?","Atenção",JOptionPane.YES_NO_CANCEL_OPTION)){
            case 0:
                System.exit(0);
                break;
            case 1:
                //Miniminiza pro systemtray
                if(VariaveisModais.suportaSystemTray){
                      VariaveisModais.feedMe.setExtendedState(JFrame.ICONIFIED);
                      VariaveisModais.feedMe.setVisible(false);
                }else{
                      return;
                }
                break;
            case 2:
                return;
        }
        
    }
}
