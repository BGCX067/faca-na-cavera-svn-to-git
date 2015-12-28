/**
 *
 * @author Bruno Gabriel
 */
package faca.na.cavera;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;


public class ToolMenu extends JToolBar{
    
    private JButton btAdicionar = new JButton(new ImageIcon("imagens/Download.png"));
    private JButton btRemover = new JButton(new ImageIcon("imagens/Delete.png"));
    private JButton btAjuda = new JButton(new ImageIcon("imagens/Bookmark.png"));
    private JButton btSair = new JButton(new ImageIcon("imagens/Home.png"));   
   
            
    public ToolMenu(){
        
        super();
        this.setPreferredSize(new Dimension(570, 70));
        //Adicionar os botões
        this.addSeparator();
        this.add(btAdicionar);
        this.addSeparator();
        this.add(btRemover);
        this.addSeparator();
        this.add(btAjuda);
        this.addSeparator();
        this.add(btSair);
        this.setFloatable(false);
        
        //Adicioar os eventos
        btAdicionar.addActionListener(new BTAdicionar());
        btRemover.addActionListener(new BTRemover());
        btAjuda.addActionListener(new BTAjuda());
        btSair.addActionListener(new BTSair()); 
        
        //Adicionar as dicas
        btAdicionar.setToolTipText("Adicioar Site RSS");
        btRemover.setToolTipText("Remover RSS selecionado");
        btAjuda.setToolTipText("Ajuda");
        btSair.setToolTipText("Sair do Programa");
    }
}
