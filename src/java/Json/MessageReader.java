/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Json;

import Domein.Message;
import Domein.User;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Stef
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MessageReader implements MessageBodyReader<Message>
{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public Message readFrom(Class<Message> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try (JsonReader in = Json.createReader(entityStream)) {
            
            JsonObject jsonMessage = in.readObject();
            Message message = new Message();
            User user = new User();
            
            message.setMessageId(jsonMessage.getInt("messageId", 0));
            user.setEmail(jsonMessage.getString("fromUser", null));
            message.setFromUser(user);
            user.setEmail(jsonMessage.getString("toUser", null));
            message.setToUser(user);
            message.setMessage(jsonMessage.getString("message", null));
            
            
            
            return message;
            
        } catch (JsonException | ClassCastException ex) {
            throw new BadRequestException("Ongeldige JSON invoer");
        }
    }
    
}
