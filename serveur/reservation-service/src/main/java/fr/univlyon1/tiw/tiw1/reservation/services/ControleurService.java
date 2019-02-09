package fr.univlyon1.tiw.tiw1.reservation.services;

import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.ws.Provider;

@Component
public class ControleurService implements Provider<Source> {
    @Override
    public Source invoke(Source source) {
        DOMResult dom = new DOMResult();
        Transformer trans = null;

        try {
            trans = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(source);
            trans.transform(source, dom);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("je suis dans invoke");
        Node node = dom.getNode();
        Node root = node.getFirstChild();
        System.out.println(root.getNodeName());

        if (root.getNodeName().equals("res:annuler-reservation")) {
            System.out.println("je suis dans annuler reservation");
            System.out.println(root.getFirstChild().getNodeValue());
        } else if (root.getNodeName().equals("res:reserver")) {
            System.out.println("je suis dans reserver");
            System.out.println(root.getFirstChild().getNodeValue());
            System.out.println("\n** ChildNodes **\n" + root.getChildNodes() + "\n** ChildNodes **\n");
            System.out.println(root.getFirstChild().getNextSibling().getNodeValue());
            // todo : appeller la bonne méthode de ServiceReservationComponent
        }

        return null;
    }
}
