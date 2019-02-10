package fr.univlyon1.tiw.tiw1.reservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public class LogicalMessageHandler implements LogicalHandler<LogicalMessageContext> {
    private static final Logger LOG = LoggerFactory.getLogger(LogicalMessageHandler.class);

    @Override
    public boolean handleMessage(LogicalMessageContext context) {
        LogicalMessage message = context.getMessage();

        String apiKeyValue = (String) context.get("api-key");
        try {
            LOG.info("Logical Handler : " + message.getPayload().toString());

            if (apiKeyValue != null) {
                LOG.info("Accès via la clé d'API " + apiKeyValue);
            } else {
                LOG.info("Pas de clé d'API trouvé");
            }
        } catch (Exception e) {
            LOG.warn(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }
}
