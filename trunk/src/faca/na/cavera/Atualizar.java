/**
 *
 * @author Bruno Gabriel
 */
package faca.na.cavera;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Atualizar {
    
    private JFrame jAtualiza = null;
    private JPanel jPAtualiza =null;
    private static  int largura = 0;
    private static int altura = 0;
    private ImageIcon imgIcon =null;
    private Container con =null;
    
    private int totolAtualizacoes =0;
    private String[] sSites=null;
    private String[] sTitulos=null;
    private String[] sLinks=null;
    private int contador =0;
    
    public Atualizar(){
        
        final Atualizar me = this;
        jAtualiza = new JFrame();
        con = jAtualiza.getContentPane();
        con.setLayout(null);     
        imgIcon = new ImageIcon("imagens/lycanatualiza.png");
        
        Atualizar.largura = imgIcon.getIconWidth();
        Atualizar.altura = imgIcon.getIconHeight();
        
        jAtualiza.setSize(Atualizar.largura,Atualizar.altura);
        
        jPAtualiza = new JPanel(){
            public void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                Image img = imgIcon.getImage();
                Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
                setPreferredSize(size);
                setMinimumSize(size);
                setMaximumSize(size);
                setSize(size);
                setLayout(null);
                g.drawImage(img, 0, 0, null);
            } 
        };
        
        con.add(jPAtualiza);
        jPAtualiza.setBounds(0, 0, imgIcon.getIconWidth(), imgIcon.getIconHeight());      
        
         //Cria as Labels
        final JLabel lNomeSite = new JLabel("Anitube");
        lNomeSite.setForeground(Color.white);

        
        //Cria Titulo Mensagem
        final JLabel ltituloSite = new JLabel("O site foi atualizado muito coisa ficarar aki");
        ltituloSite.setForeground(Color.white);
        
        
        jAtualiza.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getSource() == lNomeSite){
                    abrirUrlSite(sLinks[contador]);
                }else if(e.getSource() == ltituloSite){
                    abrirUrlSite(sLinks[contador]);
                } 
            }
            public void mouseEntered(MouseEvent e){
                if (e.getSource() == lNomeSite){
                    lNomeSite.setText("<html><u>"+sSites[contador]+"</u></html>");
                }else if(e.getSource() == ltituloSite){
                     ltituloSite.setText("<html><u>"+sTitulos[contador]+"</u></html>");
                } 
            }
            @Override
            public void mouseExited(MouseEvent e){
                if (e.getSource() == lNomeSite){
                    lNomeSite.setText(sSites[contador]);
                }else if(e.getSource() == ltituloSite){
                     ltituloSite.setText(sTitulos[contador]);
                } 
            }
        });
         
        //Cria o botao proximo
        JButton bProx =new JButton(">");
        bProx.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(contador<totolAtualizacoes){
                    me.esconderJanela();
                }else{
                    contador++;
                    lNomeSite.setText(sSites[contador-1]);
                    ltituloSite.setText(sTitulos[contador-1]);
                    
                }
            }
        });
        JButton bFec =new JButton("X");
        bFec.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //quando for clicado.
                jAtualiza.setVisible(false);
            }
        });
        
        
        //Ajustar o jPAtualiza   
        jPAtualiza.add(lNomeSite);
        jPAtualiza.add(bProx);
        jPAtualiza.add(bFec);
        jPAtualiza.add(ltituloSite);
        
        
        jAtualiza.setUndecorated(true);
        jAtualiza.setAlwaysOnTop(true);
        jAtualiza.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jAtualiza.setResizable(false);
        jAtualiza.setLocationRelativeTo(null);
        
        
        //Pego a altura do taskbar
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize(); //pega só a area de trabalho
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();//Pega a area util do sistema
        int taskBarHeight = scrnSize.height - winSize.height;//area do sitema - area de trabalho = taskbar
        jAtualiza.setLocation(scrnSize.width-Atualizar.largura, scrnSize.height-taskBarHeight-Atualizar.altura);//Sempre no canto da tela na parte superiro
       
        
    }
    public void mostrarJanela(){
        atualizarTudo();
        if(VariaveisModais.executa)
            jAtualiza.setVisible(true);
    }
    public void esconderJanela(){
        jAtualiza.setVisible(false);
    }
    public void atualizarTudo(){
        totolAtualizacoes =0; //6
        String[] sSites=null;//{Punch,Punch,Punch,Anitube,Anitube,Anitube}
        String[] sTitulos=null;//{Naruto 500,titulo2,titulo3,titulo1,}
        String[] sLinks=null;
        
        //totolAtualizacoes = Configuration.atualizaN(sSites, sTitulos,sLinks);
        
        /*
         * Punch
         *  -Naruto 500 , www.punch/naruto500.html
         *  -titulo2 , link2
         *  -titulo3, link3
         *Anitube
         *  -titulo1 , link1
         *  -titulo2 , link2
         *  -titulo3, link3
         */
    }
    
    public void abrirUrlSite(String url){
        if( !java.awt.Desktop.isDesktopSupported() ) {
            JOptionPane.showMessageDialog(null, "Não é possivel abrir o site!", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
            JOptionPane.showMessageDialog(null, "Não é possivel abrir o site!", "Error", JOptionPane.ERROR_MESSAGE);
        }
 
        try {

            java.net.URI uri = new java.net.URI( url );
            desktop.browse( uri );
       }
       catch ( Exception e ) {
           JOptionPane.showMessageDialog(null, "Não é possivel abrir o site!", "Error", JOptionPane.ERROR_MESSAGE);
       }
    }
}
