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
        try {
            System.out.println("Logical Handler : " + message.getPayload().toString());
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
