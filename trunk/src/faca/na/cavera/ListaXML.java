/**
 *
 * @author Bruno Gabriel
 */
package faca.na.cavera;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;


public class ListaXML extends JList {
    DefaultListModel listModel = new DefaultListModel();
    JScrollPane scrollListaXml;
    public ListaXML(){
        super();
        this.setVisibleRowCount(-1);
        this.setLayoutOrientation(JList.VERTICAL_WRAP);
        this.setModel(listModel);
        scrollListaXml = new JScrollPane(this);
    }
    public void adicionarElementoLista(String elemento){
        listModel.addElement(elemento);
    }
    public void removerElementoLista(String elemento){
        listModel.removeElement(elemento);
    }
}
