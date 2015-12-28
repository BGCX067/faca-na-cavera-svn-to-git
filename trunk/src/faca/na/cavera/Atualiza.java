/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faca.na.cavera;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Atualiza {
    
     private String[] urls = null;
     private String[] caminhos = null;

     public Atualiza() throws ParserConfigurationException, SAXException, IOException
     {            
         urls = Configuration.lista("url");
         caminhos = Configuration.lista("caminho");
     }
     
     /*
      * Essa funcao retorna um vetor de int  , q na ordem mostra o numero de atualizacoes em kda site.
      */
  public int[] atualizaX() throws MalformedURLException, IOException, ParserConfigurationException, SAXException, FileNotFoundException, TransformerConfigurationException, TransformerException
   {
         int[] atualizacoes = new int[urls.length]; // vetor de atualizacoes
         
         for(int i = 0; i < urls.length ; i++){
         Document doc = new DownloadRSS(urls[i]).getXmlRss(); //fazendo download do novo arquivo
         atualizacoes[i] =  new CompXML().compareXml(caminhos[i],doc);// comparando os arquivos
         ProcessaXML.salvaXML(doc, caminhos[i]); // salvando o  novo arquivo
         
         }
         
         return atualizacoes;
         
    }
     /*
      * Escolhe o tipo de atributo pra retornar , se for title o 1 eh o nome do site , o resto 
      * eh as atualizacoes , a string de retorno tem o tamanho de atualizacao + 1
      */
    public String[] pegaAtualizacoes(String atributo,String caminho ,int atualizacao) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, FileNotFoundException, TransformerConfigurationException, TransformerException
    {
            int i = 0 ;
            Document doc = ProcessaXML.abreXML(caminho);
            NodeList nodeLst = doc.getElementsByTagName("item"); // pega a lista de item
            String[] atributos = new String[atualizacao+1]; 
            
            if(atributo.compareTo("title") ==0 ){
             NodeList sTitle = doc.getElementsByTagName("title"); //pega lista de titles 
             Node  fstTitle = sTitle.item(0);                     // pega o 1 da lista q eh nome do site
             Node  name = fstTitle.getFirstChild();               // pega o 1 no abaixo do q voce quer para poder pegar a informacao
             atributos[0] = name.getNodeValue(); 
             i++;
            }
            
            for( ;i < atualizacao ; i++){
            
                Node fstNode = nodeLst.item(i);           //pega o 1 no da lista item
                Element fstElmnt = (Element) fstNode;     // transforma o 1 no em element pra poder proucura outros nos dentro dele
                NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(atributo); // proucura os nos link dentro do 1 item q nesse caso eh sempre 1
                Node fstNmElmnt = fstNmElmntLst.item(0); // pega o 1 no title
                Node fstNm = fstNmElmnt.getFirstChild(); // pega o filho do no title q eh onde fik as informacoes
          
                atributos[i] = fstNm.getNodeValue(); // adiciona as informacoes no vetor
            
            }
         return atributos;
    }
    
    public void MostraAtualizacao() throws MalformedURLException, IOException, ParserConfigurationException, SAXException, FileNotFoundException, TransformerConfigurationException, TransformerException{
      
         int[] update = atualizaX();
         String[] caminho = Configuration.lista("caminho");
      
        for(int i =0 ; i<caminho.length;i++){ 
       
           String[] titles =  pegaAtualizacoes("titles",caminho[i],update[i]);
           String[] links = pegaAtualizacoes ("link",caminho[i],update[i]);
       
           
       }
    
    }
}


