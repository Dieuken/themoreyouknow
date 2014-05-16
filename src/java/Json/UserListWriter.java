/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Json;

import Domein.User;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
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
import resources.Users;

/**
 *
 * @author Stef
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserListWriter implements MessageBodyWriter<List<User>> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) 
    {
        if(!List.class.isAssignableFrom(type)){
            return false;
        }
        
        if (genericType instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
            return arguments.length == 1 && arguments[0].equals(User.class);
        } else {
            return false;
        }
    }

    @Override
    public long getSize(List<User> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<User> users, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonArrayBuilder jsonUsers = Json.createArrayBuilder();
        
        for (User user : users) {
            JsonObjectBuilder jsonUser = Json.createObjectBuilder();
            
            jsonUser.add("email", user.getEmail());
            if (user.getLat() != null) {
                jsonUser.add("lat", user.getLat());
            }
            
            if (user.getLng() != null) {
                jsonUser.add("lng", user.getLng());
            }
            jsonUsers.add(jsonUser);
        }
        
        try (JsonWriter out = Json.createWriter(entityStream)) {
            out.writeArray(jsonUsers.build());
        }
    }
    
}
