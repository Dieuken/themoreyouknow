/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import Domein.Message;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
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
public class messages 
{
    @PersistenceContext
    private EntityManager em;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages(@QueryParam("first") @DefaultValue("0") int first, @QueryParam("results") @DefaultValue("10") int results)
    {
        TypedQuery<Message> queryFindAll = em.createNamedQuery("Message.findAll", Message.class);
        queryFindAll.setFirstResult(first);
        queryFindAll.setMaxResults(results);
        return queryFindAll.getResultList();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addMessage(Message message)
    {
        
        
        message.setFromUser(message.getFromUser());
        message.setToUser(message.getToUser());
        
        
        
        if (message.getMessage() == null) {
            throw new BadRequestException("Message is leeg");
        }
        
       message.setMessage(message.getMessage());
        
        em.persist(message);
        
       
    }
    
    @Path("{messageId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessage(@PathParam("messageId") int messageId)
    {
        Message message = em.find(Message.class, messageId);
        
        if (message == null) {
            throw new NotFoundException("Message niet gevonden");
        }
        
        return message;
    }
}
