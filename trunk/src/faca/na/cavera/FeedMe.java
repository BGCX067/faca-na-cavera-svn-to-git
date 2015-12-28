/**
 * @author Bruno Gabriel
 */

package faca.na.cavera;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.TrayIcon;
import java.awt.SystemTray;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class FeedMe extends JFrame {
   private PopupMenu trayPop = null;
   private TrayIcon trayIcon = null;
   private MenuItem tiSair = null;
   private MenuItem tiAtualiza = null;
   private MenuItem tiRestaura = null;
   private MenuItem tiExecuta = null;
  
   private Relogio relogio = new Relogio();
   
    public FeedMe(){
        
        super("FeedMe");
        
        
        final FeedMe me = this;
        VariaveisModais.feedMe = this;
        
        //------------Configura o Sistem tray----------------------
        
        if(SystemTray.isSupported()){
            
            VariaveisModais.suportaSystemTray=true;
            
            trayPop = new PopupMenu();
            tiSair = new MenuItem("Sair");
            
            //Ação do sair
            tiSair.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            
            });
            
            tiAtualiza = new MenuItem("Atualiza");
            
            //Ação do Atualiza
            tiAtualiza.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //chama atualiza
                    
                }
            
            });
            
            tiRestaura = new MenuItem("Restaura");
            
            //Ação do Atualiza
            tiRestaura.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //Restaura
                    me.setExtendedState(JFrame.NORMAL);
                    VariaveisModais.feedMe.setVisible(true);
                }
            
            });
            
            tiExecuta = new MenuItem("Iniciar");
            
            //Ação do Atualiza
            tiExecuta.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //chama função de procurar atualizações no tempo
                    if(!VariaveisModais.executa){
                        VariaveisModais.executa = true;
                        trayIcon.getPopupMenu().getItem(0).setLabel("Parar");
                        //Começa o atualizador 
                        relogio.comecar();
                        
                    }else{
                        VariaveisModais.executa = false;
                        trayIcon.getPopupMenu().getItem(0).setLabel("Iniciar");
                        //para o loop do atualizador
                        relogio.parar();
                        
                    }
                    
                }
            
            });
            
            trayPop.add(tiExecuta);
            trayPop.add(tiAtualiza);
            trayPop.addSeparator();
            trayPop.add(tiRestaura);   
            trayPop.add(tiSair);
            
            trayIcon = new TrayIcon(new ImageIcon("imagens/systrayicon.png").getImage(),"FeedMe",trayPop);
                      
            SystemTray sysTray = SystemTray.getSystemTray();
            try {
                sysTray.add(trayIcon);
            } catch (AWTException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possivel minimizar a janela!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.setIconImage(new ImageIcon("imagens/winicon.png").getImage());
            
        }else{
            
            VariaveisModais.suportaSystemTray=false;
            
        }
        
        this.setLayout(new FlowLayout());
        
        //Criar a ListaXML que já vem com a barra de Rolagem vertical
        ListaXML listaXml = new ListaXML();
        VariaveisModais.listaXml = listaXml;
        String[] sites = null;
        try{
            sites = Configuration.lista("site");
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Não foi possivel abrir os arquivos de configuração!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        for(int i =0; i<sites.length; i++){
            listaXml.adicionarElementoLista(sites[i]);
        }
        
        //Criar o toolBar
        ToolMenu toolMenu = new ToolMenu();
        add(toolMenu,BorderLayout.PAGE_START);
               
       //Cria e adiciona a ListaXMl a um JPanel
        JPanel panelListaXml = new JPanel(new BorderLayout());
        panelListaXml.setBorder(BorderFactory.createTitledBorder("Lista de Sites"));
        panelListaXml.setPreferredSize(new Dimension(200, 390));
        panelListaXml.add(listaXml);
        panelListaXml.setOpaque(true);
        
        //Cria o segundo JPanel onde ficará as demais configurações
        JPanel sobreFeedMe = new JPanel(new BorderLayout());
        sobreFeedMe.setLayout(new FlowLayout());
        sobreFeedMe.setBorder(BorderFactory.createTitledBorder("FeedMe"));
        sobreFeedMe.setPreferredSize(new Dimension(350, 390));
        sobreFeedMe.add(new JLabel(new ImageIcon("imagens/lycans.png")));
        
        
        //Cria um terceiro JPanel dentro do segunda par colocar as configurações
        JPanel config = new JPanel(new BorderLayout());
        config.setLayout(new FlowLayout());
        config.setBorder(BorderFactory.createTitledBorder("Configurações"));
        config.setPreferredSize(new Dimension(300, 60));
        
        //Cria uma Label
        JLabel lTempo = new JLabel("Tempo em minuto para atualizar: ");
        config.add(lTempo);
        
        //Cria o ComboBox imutavel
        String[] minutos = {"1 Minuto","2 Minutos","5 Minutos","10 Minutos","30 Minutos","1 Hora","2 Horas"};
        final JComboBox cbListaTempo = new JComboBox(minutos);
        cbListaTempo.setSelectedIndex(0);
        
        cbListaTempo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                switch(cbListaTempo.getSelectedIndex()){
                    case 0:
                        VariaveisModais.tempoParaAtualizar = 1*VariaveisModais.MINUTO;
                        break;
                    case 1:
                        VariaveisModais.tempoParaAtualizar = 2*VariaveisModais.MINUTO;
                        break;
                    case 2:
                        VariaveisModais.tempoParaAtualizar = 5*VariaveisModais.MINUTO;
                        break;
                    case 3:
                        VariaveisModais.tempoParaAtualizar = 30*VariaveisModais.MINUTO;
                        break;
                    case 4:
                        VariaveisModais.tempoParaAtualizar = 60*VariaveisModais.MINUTO;
                        break;
                    case 5:
                        VariaveisModais.tempoParaAtualizar = 120*VariaveisModais.MINUTO;
                        break;    
                }
            }
        
        });
        
        
        //Adicionando os componentes no config
        config.add(lTempo);
        config.add(cbListaTempo);
        sobreFeedMe.add(config);
        
        //Criando o botao para Começar
        
        JButton bComecar = new JButton("Começar",new ImageIcon("imagens/Select.png"));
        bComecar.setToolTipText("Começar");
        
        bComecar.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                
                    VariaveisModais.executa = true;
                    trayIcon.getPopupMenu().getItem(0).setLabel("Parar");
                    //Começa o atualizador 
                    relogio.comecar();
            }
        });
        
        //Cria o botão para atualizar agora
        JButton bAtualizar = new JButton("Atualizar",new ImageIcon("imagens/update.png"));
        bAtualizar.setToolTipText("Atualizar Agora");
        
        bAtualizar.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                //Evento que dispara quando o botao começar for clicado

            }
        
        });
        
        sobreFeedMe.add(bComecar);
        sobreFeedMe.add(bAtualizar);
        sobreFeedMe.setOpaque(true);
        add(panelListaXml);
        add(sobreFeedMe);
        
        //Ultimas configurações
        super.setSize(580,510);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
    }
    
    public static void main(String[] args){
        File conf = new File("configuration.xml");
        File temp = new File("temp");
        if(!conf.exists())
        try {
            Configuration.criaConfig();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Não foi possivel criar o arquivo de configuração do programa","Erro ao abrir",JOptionPane.INFORMATION_MESSAGE);
        }
        if(!temp.exists())
            temp.mkdir();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FeedMe app = new FeedMe();
                app.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                app.setVisible(true);
                app.addWindowListener( new WindowAdapter(){
                    public void windowClosing(WindowEvent w) {
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
                });
                
            }
        });
    }
}
