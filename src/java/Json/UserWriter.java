package json;


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

/*
 * Deze klasse is een JAX-RS provider die een User kan uitschrijven als JSON.
 */

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserWriter implements MessageBodyWriter<User>
{
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return User.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(User user, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return -1;
    }

    @Override
    public void writeTo(User user, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
    {
        JsonObjectBuilder jsonUser = Json.createObjectBuilder();

        jsonUser.add("email", user.getEmail());
        
        if (user.getLat() != null) {
            jsonUser.add("lat", user.getLat());
        }
        
        if (user.getLng() != null) {
            jsonUser.add("lng", user.getLng());
        }
        
        try (JsonWriter out = Json.createWriter(entityStream)) {
            out.writeObject(jsonUser.build());
        }
    }
}
