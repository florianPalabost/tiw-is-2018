//package fr.univlyon1.tiw.tiw1.banque;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import fr.univlyon1.tiw.tiw1.banque.metier.Compte;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.helpers.CastUtils;
//import org.apache.cxf.jaxws.context.WrappedMessageContext;
//import org.apache.cxf.message.Message;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.xml.ws.handler.MessageContext;
//import javax.xml.ws.handler.Handler;
//import java.util.List;
//import java.util.logging.Logger;
//
//@Component
//public class BanqueEventHandler implements Handler {
//    private static final Logger LOGGER = Logger.getLogger(BanqueEventHandler.class.getName());
//    private RabbitTemplate rabbitTemplate;
//    private Queue candidateQueue;
//
//    @Autowired
//    public BanqueEventHandler(RabbitTemplate rabbitTemplate, Queue candidateQueue) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.candidateQueue = candidateQueue;
//    }
//
//
//    public void handleCompteSave(Compte compte) {
//        sendMessage(compte);
//    }
//
//    private void sendMessage(Compte compte) {
//        LOGGER.info("BANQUE SEND MSG FROM SEND MESSAGE");
//        rabbitTemplate.convertAndSend(
//                candidateQueue.getName(), serializeToJson(compte));
//    }
//
//    private String serializeToJson(Compte compte) {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = "";
//
//        try {
//            jsonInString = mapper.writeValueAsString(compte);
//        } catch (JsonProcessingException e) {
//            LOGGER.info(String.valueOf(e));
//        }
//
//        LOGGER.info("Serialized message payload: {}"+jsonInString);
//
//        return jsonInString;
//    }
//
//    @Override
//    public boolean handleMessage(MessageContext context) {
//        Message message = ((WrappedMessageContext) context).getWrappedMessage();
//        List<Header> headers = CastUtils.cast((List<?>) message.get(Header.HEADER_LIST));
//
//        String compteVal = (String) context.get("compte");
//        LOGGER.info("COMPTE:::"+compteVal);
//        try {
//            LOGGER.info("Logical Handler : " + message.toString());
//
//            if (compteVal != null) {
//                LOGGER.info("Send Msg from handle message: ");
//                 sendMessage( (Compte) context.get("compte"));
//                LOGGER.info("End Msg from handle message: ");
//
//            } else {
//                LOGGER.info("Pb handle Msg");
//            }
//        } catch (Exception e) {
//            LOGGER.info(e.getMessage());
//        }
//        return  false;
//    }
//
//
//
//    @Override
//    public boolean handleFault(MessageContext context) {
//        return false;
//    }
//
//    @Override
//    public void close(javax.xml.ws.handler.MessageContext context) {
//
//    }
//}
