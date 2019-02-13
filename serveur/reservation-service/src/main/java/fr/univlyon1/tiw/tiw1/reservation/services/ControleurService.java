package fr.univlyon1.tiw.tiw1.reservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Node;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.ws.Provider;

@CrossOrigin(origins = "*", methods = RequestMethod.OPTIONS)
@Component
public class ControleurService implements Provider<Source> {

    private static final Logger LOG = LoggerFactory.getLogger(ControleurService.class);
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
            LOG.info("SOURCE::::"+source);
            trans.transform(source, dom);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        LOG.info("invoke");
        Node node = dom.getNode();
        Node root = node.getFirstChild();
        LOG.info("root node:::"+root.getNodeName());

        if (root.getNodeName().equals("res:annuler-reservation")) {
            LOG.info("annuler reservation");
            LOG.info(root.getFirstChild().getNodeValue());

        } else if (root.getNodeName().equals("res:reserver")) {
            LOG.info("reserver");
            LOG.info(root.getFirstChild().getNodeValue());
            LOG.info("\n** ChildNodes **\n" + root.getChildNodes() + "\n** ChildNodes **\n");
            LOG.info(root.getFirstChild().getNextSibling().getNodeValue());
            // todo : appeller la bonne méthode de ServiceReservationComponent
        }

        return null;
    }
}
