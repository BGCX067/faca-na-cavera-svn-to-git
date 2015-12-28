/**
 *
 * @author Victor Martin & Bruno Gabriel
 * @date 03 de dezembro de 2011
 */

package faca.na.cavera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Configuration {

    /*
     * Cria um novo congiguration XML  , precisa criar apenas 1 vez , ake soh cria um arquivo XML com 1 no
     */
    static private final String CONFIGURATION_PATH = "configuration.xml";
    
    
    static public void criaConfig() throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException
    {
        
           
            //Criando um novo documento
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();


            //cria o 1 elemento ( raiz ) do XML
            Element root = doc.createElement("configuration");
            doc.appendChild(root);

            //Salva o Arquivo na Pasta
            ProcessaXML.salvaXML(doc, Configuration.CONFIGURATION_PATH);  
            
    }
    /*
     * aqui adiciona os nos com as informacoes de cada RSS , e ja sobrescreve o outro Configuration
     */
    static public void addConfig(String url , String caminhoTotal,String site , int nAtualizacoes) throws TransformerConfigurationException, ParserConfigurationException, SAXException, IOException, FileNotFoundException, TransformerException
    {
        
        if(Configuration.pesquisaURL(url, site) == false){
            
        Document doc = ProcessaXML.abreXML( Configuration.CONFIGURATION_PATH); // abre o arquivo XML Configuration
        
        Element item = doc.createElement("item"); // cria os novos nos
        
        NodeList nodeLst = doc.getElementsByTagName("configuration");   // pega o no configuration 
        Node fstNode = nodeLst.item(0);
        
        String atual = Integer.toString(nAtualizacoes); // faz nAtualizacoes de int pra String
        
        fstNode.appendChild(item);  // adiciona o elemento(no) item no Configuration
        
        item.setAttribute("site",site); //adiciona atributos no item
        item.setAttribute("url",url);
        item.setAttribute("nAtual",atual);
        item.setAttribute("caminho",caminhoTotal);
        
        ProcessaXML.salvaXML(doc, Configuration.CONFIGURATION_PATH); // salva o arquivo denovo na pasta
        
        }
    }
    /*
     * Verifica se ja há alguma URL no Configuration igual a pesquisada retorna boolean
     */
    static public String[] lista(String atributo) throws ParserConfigurationException, SAXException, IOException {

        Document doc = ProcessaXML.abreXML(Configuration.CONFIGURATION_PATH); // Abre o arquivo XML

        NodeList nodeLst = doc.getElementsByTagName("item"); // Pega a Lista de Nos do nome item;
        String[] atributos = new String[nodeLst.getLength()];  // cria vetor de Strings para salvar as urls..

        for (int i = 0; i < nodeLst.getLength(); i++) {

            Element item = (Element) nodeLst.item(i); // pega cada NO da lisata de Nos de item
            String oldUrl = item.getAttribute(atributo); // pega o valor do atributo url do NO em questao
            atributos[i] = oldUrl;

        }

        return atributos;
    }
    
    static public boolean pesquisaURL(String url, String nome) throws ParserConfigurationException, SAXException, IOException
    {       
        Document doc = ProcessaXML.abreXML(Configuration.CONFIGURATION_PATH); // Abre o arquivo XML
        
        NodeList nodeLst = doc.getElementsByTagName("item"); // Pega a Lista de Nos do nome item;

        for(int i = 0; i < nodeLst.getLength(); i++ ){
         
            Element item = (Element)nodeLst.item(i); // pega cada NO da lisata de Nos de item
            String oldUrl = item.getAttribute("url"); // pega o valor do atributo url do NO em questao
            
            if(oldUrl.compareTo(url) == 0) // verifica se o no informado eh igual ao no encontrado .
                    return true;
            
            oldUrl = item.getAttribute("site");
            if(oldUrl.compareTo(nome) == 0)
                return true;
        }
        
             return false;
    }
    static public void deletarArquivoXml(String caminhoTotal){
        System.gc();
        File arquivo = new File(caminhoTotal);
        arquivo.delete();
    }
    static public void removeNoSite(String site) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException
    {
        Document doc = ProcessaXML.abreXML(Configuration.CONFIGURATION_PATH) ; // abre o XML Configuration
        
        NodeList nodeLst = doc.getElementsByTagName("item");  // cria uma lista de nos do tipo item
        NodeList rootLst = doc.getElementsByTagName("configuration");  // pega a lista de nos do tipo configuration
        Node root = rootLst.item(0); // pega 1 no configuration q eh a Raiz
       
        for(int i = 0; i < nodeLst.getLength(); i++ ){
         
            Element item = (Element)nodeLst.item(i); //pega um no da lista de nos
            String oldUrl = item.getAttribute("site"); // pega a url do no em questao
            
            if(oldUrl.compareTo(site) == 0) // verifica se o no informado eh igual ao no em questao
                    root.removeChild(item); // c for , remove o no da Raiz.
        }
        
        ProcessaXML.salvaXML(doc,Configuration.CONFIGURATION_PATH);    // salva o XML

    }
    static public void removeNoUrl(String url) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        Document doc = ProcessaXML.abreXML(Configuration.CONFIGURATION_PATH); // abre o XML Configuration

        NodeList nodeLst = doc.getElementsByTagName("item");  // cria uma lista de nos do tipo item
        NodeList rootLst = doc.getElementsByTagName("configuration");  // pega a lista de nos do tipo configuration
        Node root = rootLst.item(0); // pega 1 no configuration q eh a Raiz

        for (int i = 0; i < nodeLst.getLength(); i++) {

            Element item = (Element) nodeLst.item(i); //pega um no da lista de nos
            String oldUrl = item.getAttribute("url"); // pega a url do no em questao

            if (oldUrl.compareTo(url) == 0) // verifica se o no informado eh igual ao no em questao
            {
                root.removeChild(item); // c for , remove o no da Raiz.
            }
        }

        ProcessaXML.salvaXML(doc, Configuration.CONFIGURATION_PATH);    // salva o XML

    }
    
    static public void atualizaN(String site, int nAtualiza) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, TransformerConfigurationException, TransformerException {
        Document doc = ProcessaXML.abreXML(Configuration.CONFIGURATION_PATH); // Abre o arquivo XML

        NodeList nodeLst = doc.getElementsByTagName("item"); // Pega a Lista de Nos do nome item;
        
        for (int i = 0; i < nodeLst.getLength(); i++) {

            Element item = (Element) nodeLst.item(i); // pega cada NO da lisata de Nos de item
            String nomeSite = item.getAttribute("site");
            if (nomeSite.compareTo(site) == 0){
                item.setAttribute("nAtual", Integer.toString(nAtualiza));
            }
        }
        ProcessaXML.salvaXML(doc, Configuration.CONFIGURATION_PATH);
    }
    
    static public String procuraAtualizacao(String site) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        Document doc = ProcessaXML.abreXML(Configuration.CONFIGURATION_PATH); // abre o XML Configuration

        NodeList nodeLst = doc.getElementsByTagName("item");  // cria uma lista de nos do tipo item
        NodeList rootLst = doc.getElementsByTagName("configuration");  // pega a lista de nos do tipo configuration
        Node root = rootLst.item(0); // pega 1 no configuration q eh a Raiz

        for (int i = 0; i < nodeLst.getLength(); i++) {

            Element item = (Element) nodeLst.item(i); //pega um no da lista de nos
            String oldUrl = item.getAttribute("site"); // pega a url do no em questao

            if (oldUrl.compareTo(site) == 0) // verifica se o no informado eh igual ao no em questao
            {
                return item.getAttribute("nAtual");
            }
        }
        return "";
    }
}
    
