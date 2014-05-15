/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domein;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Stef
 */
@Entity
@Table(name = "TBL_MESSAGE")
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "Select m from Message m")
})
public class Message 
{
    @Id
    @GeneratedValue
    private int messageId;
    @OneToMany(fetch = FetchType.EAGER)
    private User fromUser;
    @OneToMany(fetch = FetchType.EAGER)
    private User toUser;
    private String message;

    public Message() {
    }

    public Message(int messageId, User fromUser, User toUser, String message) {
        this.messageId = messageId;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
   
}
