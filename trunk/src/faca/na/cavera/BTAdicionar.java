/*
 *
 * @author Wellvolks
 */
package faca.na.cavera;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class BTAdicionar implements ActionListener{
    private boolean actived = false;
    private JFrame jSalvar;
    
    public void actionPerformed(ActionEvent e) {
        if(!actived){
            actived = true;
            Thread t = new Thread(new Runnable(){
                public void run() {
                    salvar();
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null,"Fatal Error!", "Salvar Site",JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            jSalvar.requestFocus();
        }
    }
    private void naoSalvoRss(){
        JOptionPane.showMessageDialog(null,"Não foi possivel salvar o site, tente novamente!", "Salvar Site",JOptionPane.ERROR_MESSAGE);
    }
    private void naoSalvoRss(String motivo) {
        JOptionPane.showMessageDialog(null, motivo + "\nNão foi possivel salvar o site, tente novamente!", "Salvar Site", JOptionPane.ERROR_MESSAGE);
    }
    private void salvoRss(){
        JOptionPane.showMessageDialog(null,"O site foi salvo com sucesso!", "Salvar Site",JOptionPane.INFORMATION_MESSAGE);
    }
    private void salvar(){
        jSalvar = new JFrame("Salvar Site");
        jSalvar.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jSalvar.setSize(300,120);
        jSalvar.setResizable(false);
        jSalvar.setLocationRelativeTo(null);
        jSalvar.setLayout(new FlowLayout());
        
        //Criando os componentes
        
        /*JLabel*/
        JLabel lSiteNome = new JLabel("Nome do site:");
        JLabel lRssNome = new JLabel("Link do site:");
        
        /*JTextField*/
        final JTextField tSiteNome = new JTextField(17);
        final JTextField tRssNome = new JTextField(18);
        
        /*Botões salvar*/
        JButton bSalvar = new JButton("Salvar", new ImageIcon("imagens/saveicon.png"));
        bSalvar.setToolTipText("Salvar site");
        JButton bCancelar = new JButton("Cancelar", new ImageIcon("imagens/cancelicon.png"));        
        bCancelar.setToolTipText("Cancelar");
        
        /*Eventos dos botões*/
        bSalvar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //Clickar em salvar
                try {
                    //Verifica se a url já é cadastrada
                    if(Configuration.pesquisaURL(tRssNome.getText(), tSiteNome.getText())){
                        naoSalvoRss("Nome ou URL já cadastrada!");
                        jSalvar.setVisible(false);
                        actived=false;
                    }
                    else{
                        //Gera Hash do arquivo xml
                        String chave = "temp/" + MD5.gerar(tSiteNome.getText()) + ".xml";
                        DownloadRSS dRss;
                        try{
                            //Baixa o Arquivo xml
                            dRss = new DownloadRSS(tRssNome.getText());
                        }catch(Exception ex){
                            naoSalvoRss("Erro no download!");
                            return;
                        }
                        //Salva o Xml na pasta chave
                        ProcessaXML.salvaXML(dRss.getXmlRss(), chave);
                        Configuration.addConfig(tRssNome.getText(), chave, tSiteNome.getText(), 0);
                        salvoRss();
                        //Adiciona o Site na Lista
                        VariaveisModais.listaXml.adicionarElementoLista(tSiteNome.getText());
                        jSalvar.setVisible(false);
                        actived=false;
                    }
                } catch (Exception ex) {
                    naoSalvoRss("Fatal Error!");
                    jSalvar.setVisible(false);
                    actived=false;
                } 
            }
        });
        //------------------
        bCancelar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //Clickar em cancelar
                jSalvar.setVisible(false);
                actived=false;
            }
        });

        //Adicinar Componentes
        jSalvar.add(lSiteNome);
        jSalvar.add(tSiteNome);
        jSalvar.add(lRssNome);
        jSalvar.add(tRssNome);
        jSalvar.add(bSalvar);
        jSalvar.add(bCancelar);
        
        jSalvar.setVisible(true);
        
    }
    
}
