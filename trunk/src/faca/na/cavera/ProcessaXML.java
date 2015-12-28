/*
 *
 * @author Victor Martin & Bruno Gabriel
 * @date 02 de novembro de 2011
 * 
 * 
 */

package faca.na.cavera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/*
 * Salva o novo Arquvio XML , na pasta do programa 
 */
public class ProcessaXML {

    public ProcessaXML(){
        
    }
    /*Esta clasee recebe o Novo XML no formato Document , e o caminho onde vai ficar na pasta ja com o nome
     * salva o arquivo na Pasta e retorna null
     */
    static public void salvaXML(Document arquivo,String caminhoTotal) throws FileNotFoundException, TransformerConfigurationException, TransformerException{
                       
        DOMSource source = new DOMSource(arquivo);  // Pega o arquivo novo q esta no formato Document , dexa ele em Source
        StreamResult result = new StreamResult(new FileOutputStream(caminhoTotal));  // cria um arquivo XML com o nome que esta no Caminho total 
        TransformerFactory transFactory = TransformerFactory.newInstance();         // o caminho tbm ja esta no caminho total
        Transformer transformer = transFactory.newTransformer();  
        transformer.transform(source, result);     // salva as informacoes da Source( o XML) no arquivo XML q foi criado.
    }
    
        
      /*
        * Esta classe pega o caminho de um arquivo XML na pasta abre ele e retorna um Document
      */
    static public Document abreXML(String caminhoTotal) throws ParserConfigurationException, IOException, SAXException{
        File file = new File(caminhoTotal); // abre o arquivo XML
            
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
        dbf.setIgnoringElementContentWhitespace(true);                     // Transformando o arquivo XML
        DocumentBuilder db = dbf.newDocumentBuilder();                     // para Document
        Document doc = (Document) db.parse(file);
        
        return doc;
    }

}    

