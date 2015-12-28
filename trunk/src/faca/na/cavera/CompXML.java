/*
 * 
 * @author  Carlos Alexandre & Victor Martin
 * @date 2 de dezembro 2011
 * 
 */
package faca.na.cavera;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* CompXML
 * funcao : compara o Xml antigo com o Novo obtido do servidor e verifica o numero de atualizacoes
 * retorna um int com o numero de atualizacoes
 * 
    */
public class CompXML {

    public int compareXml(String oldXml, Document downloadedXml) {
        int contador = 0; // guarda o numero de atualizacoes 
      
        try {
     
            File file = new File(oldXml);   // carrega o arquivo da pasta para o programa
         // File novo = new File(downloadedXml);
            
          
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
            dbf.setIgnoringElementContentWhitespace(true);                     // Transformando o arquivo XML
            DocumentBuilder db = dbf.newDocumentBuilder();                     // para Document
            Document doc = db.parse(file);
         // Document Adoc = db.parse(novo);
            
            doc.getDocumentElement().normalize();
            downloadedXml.getDocumentElement().normalize();
         
            NodeList nodeLst = doc.getElementsByTagName("item");            //Pegando a Tag Item no XML
            NodeList AnodeLst = downloadedXml.getElementsByTagName("item"); //Pegando a Tag Item no XML
            Node fstNode = nodeLst.item(0);                              
            Element fstElmnt = (Element) fstNode;
            NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("title");
            Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
            NodeList fstNm = fstNmElmnt.getChildNodes();
           
            
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node AfstNode = AnodeLst.item(s);
                if (AfstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element AfstElmnt = (Element) AfstNode;
                    NodeList AfstNmElmntLst = AfstElmnt.getElementsByTagName("title");
                    Element AfstNmElmnt = (Element) AfstNmElmntLst.item(0);
                    NodeList AfstNm = AfstNmElmnt.getChildNodes();
                    if (!(((Node) fstNm.item(0)).getNodeValue()).equals((((Node) AfstNm.item(0)).getNodeValue()))) {
                        contador++;
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contador;
    }
}