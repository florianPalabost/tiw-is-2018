package fr.univlyon1.tiw.tiw1.banque.metier;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fr.univlyon1.tiw.tiw1.banque.data.AutorisationRepository;
import fr.univlyon1.tiw.tiw1.banque.data.CompteInconnuException;
import fr.univlyon1.tiw.tiw1.banque.data.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@CrossOrigin(origins = "*", methods = RequestMethod.OPTIONS)
@Component
public class CompteOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompteOperations.class);
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private AutorisationRepository autorisationRepository;

    private final static String QUEUE_NAME = "cinema-11301169";

    @Transactional
    public void prelevement(long idCompte, long destinataire, double valeur) throws CompteInconnuException, OperationImpossibleException {
        Compte compteParent = compteRepository.findByIdOrFail(idCompte);
        Compte compteDestinataire = compteRepository.findByIdOrFail(destinataire);
        if (compteParent.getMaximumAutorisation(compteDestinataire) >= valeur) {
            compteParent.debit(valeur);
            compteDestinataire.credit(valeur);
        } else {
            LOGGER.info("Prélèvement non autorisé: {} supérieur au maximum de {}",
                    valeur,
                    compteParent.getMaximumAutorisation(compteDestinataire));
            throw new OperationImpossibleException("Prélèvement non autorisé");
        }
    }

    @Transactional
    public void prelevementWithREF(long idCompte, long destinataire, double valeur, String ref) throws CompteInconnuException, IOException, OperationImpossibleException, TimeoutException {
        Compte compteParent = compteRepository.findByIdOrFail(idCompte);
        Compte compteDestinataire = compteRepository.findByIdOrFail(destinataire);
        if (compteParent.getMaximumAutorisation(compteDestinataire) >= valeur) {
            try {
                compteParent.debit(valeur);
                compteDestinataire.credit(valeur);
                LOGGER.info("On peut prelever");
                prelevementProducer(idCompte,destinataire,valeur,ref,true);
            } catch (OperationImpossibleException e) {
                prelevementProducer(idCompte,destinataire,valeur,ref,false);
            }
        } else {
            LOGGER.info("Prélèvement non autorisé: {} supérieur au maximum de {}",
                    valeur,
                    compteParent.getMaximumAutorisation(compteDestinataire));
            prelevementProducer(idCompte,destinataire,valeur,ref,false);
            throw new OperationImpossibleException("Prélèvement non autorisé");
        }
    }
    @Transactional
    public void autoriser(long idCompte, long destinataire, double maximum) throws CompteInconnuException {
        Compte compteParent = compteRepository.findByIdOrFail(idCompte);
        Compte compteDestinataire = compteRepository.findByIdOrFail(destinataire);
        Autorisation autorisation = compteParent.autoriser(compteDestinataire, maximum);
        LOGGER.debug("Saving autorisation from {} to {}: {}",
                autorisation.getParent().getId(), autorisation.getDestinataire().getId(), autorisation.getMaximum());
        autorisationRepository.save(autorisation);
    }

    public void prelevementProducer(long idCompte, long destinataire, double valeur, String ref, boolean success) throws IOException, TimeoutException, CompteInconnuException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        factory.setPort(5672);
        LOGGER.info("prelevement Producer start");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String message = "[ " +
                    "{\n" +
                    "\t\"source\":" + idCompte + ",\n" +
                    "\t\"destinataire\":" + destinataire + ",\n" +
                    "\t\"montant\": \""+ valeur +"\",\n" +
                    "\t\"ref\": \""+ ref +"\",\n" +
                    "\t\"success\": \""+ success +"\"\n" +
                    "}\n" +
                    "]\n";
            // Compte compte = compteRepository.findByIdOrFail(idCompte);
            LOGGER.info("Before banque publish compte info");
            // banqueEventHandler.handleCompteSave(compte);
             channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
           LOGGER.info(" --> Sent '" +message + "'");
        }
    }
}
