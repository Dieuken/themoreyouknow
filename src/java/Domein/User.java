package Domein;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stef
 */
@Entity
@Table(name = "TBL_USER")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User 
{
    @Id
    private String email;
    private int lat;
    private int lng;

    public User() {
    }

    public User(String email, int lat, int lng) {
        this.email = email;
        this.lat = lat;
        this.lng = lng;
    }

    public String getEmail() {
        return email;
    }

    public int getLat() {
        return lat;
    }

    public int getLng() {
        return lng;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }
    
    
}
