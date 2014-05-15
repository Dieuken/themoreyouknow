/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Json;

import Domein.Message;
import Domein.User;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Stef
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MessageWriter implements MessageBodyWriter<Message>
{
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return -1;
    }

    @Override
    public void writeTo(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
    {
        JsonObjectBuilder jsonMessage = Json.createObjectBuilder();
        User user = new User();
        user = message.getFromUser();
        jsonMessage.add("fromUser", user.getEmail());
        user = message.getToUser();
        jsonMessage.add("toUser", user.getEmail());
        jsonMessage.add("message", message.getMessage());
            
        
        
        try (JsonWriter out = Json.createWriter(entityStream)) {
            out.writeObject(jsonMessage.build());
        }
    }
}
