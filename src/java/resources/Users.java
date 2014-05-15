/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import Domein.User;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Stef
 */
@Path("users")
@Transactional(dontRollbackOn = {BadRequestException.class, NotFoundException.class})
public class Users 
{
    @PersistenceContext
    private EntityManager em;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers(@QueryParam("first") @DefaultValue("0") int first, @QueryParam("results") @DefaultValue("10") int results)
    {
        TypedQuery<User> queryFindAll = em.createNamedQuery("User.findAll", User.class);
        queryFindAll.setFirstResult(first);
        queryFindAll.setMaxResults(results);
        return queryFindAll.getResultList();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user)
    {
        
        em.persist(user);
        
        return Response.created(URI.create("/" + user.getEmail())).build();
    }
    
    @Path("{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email)
    {
        User user = em.find(User.class, email);
        
        if (user == null) {
            throw new NotFoundException("Gebruiker niet gevonden");
        }
        
        return user;
    } 
    
    @Path("{email}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam("email") String email, InputStream input)
    {
        User user = em.find(User.class, email);
        
        if (user == null) {
            throw new NotFoundException("Gebruiker niet gevonden");
        }
        
        try (JsonReader jsonInput = Json.createReader(input)) {
            JsonObject jsonUser = jsonInput.readObject();

            // Ter illustratie ondersteunen we hier enkel het wijzigen van het paswoord en de
            // fullName. Hoe je een volledige update kan ondersteunen, is te vinden in het grote
            // voorbeeld 'Reminders'.
            
            int lat = jsonUser.getInt("lat", 0);
            if (lat != 0) {
                
                    user.setLat(lat);
     
            }

            int lng = jsonUser.getInt("lng", 0);
            if (lng != 0) {
                
                    user.setLng(lng);
     
            }

        } catch (JsonException | ClassCastException ex) {
            throw new BadRequestException("Ongeldige JSON invoer");
        }
    }
    
    @Path("{email}")
    @DELETE
    public void removeUser(@PathParam("email") String email)
    {
        User user = em.find(User.class, email);
        
        if (user == null) {
            throw new NotFoundException("Gebruiker niet gevonden");
        }
        
        em.remove(user);
    }
}
