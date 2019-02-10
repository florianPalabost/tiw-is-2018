package fr.univlyon1.tiw.tiw1.reservation.services;

import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SoapMessageHandler implements SOAPHandler<SOAPMessageContext> {
    private static final Logger LOG = LoggerFactory.getLogger(SoapMessageHandler.class);

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if ((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            return true;
        }
        SOAPMessage soapMsg = context.getMessage();
        SOAPEnvelope soapEnv = null;
        try {
            soapEnv = soapMsg.getSOAPPart().getEnvelope();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        try {
            SOAPHeader soapHeader = soapEnv.getHeader();
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        Message message = ((WrappedMessageContext) context).getWrappedMessage();
        List<Header> headers = CastUtils.cast((List<?>) message.get(Header.HEADER_LIST));

        for (Header h : headers) {
            Element n = (Element) h.getObject();
            if (n.getLocalName().equals("TestHeader")) {
                context.put(n.getLocalName(), n.getTextContent());
            }
        }
        LOG.info("HANDLEMESSAGE M1:::"+message.get(Header.HEADER_LIST).toString());
        LOG.info("HANDLEMESSAGE M2:::"+context.get(MessageContext.HTTP_REQUEST_HEADERS).toString());
        String contentApiKey = ((Map<String, Object>) context.get(MessageContext.HTTP_REQUEST_HEADERS)).get("api-key").toString();

        if (contentApiKey != null) {
            context.put("api-key", contentApiKey);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }
}
