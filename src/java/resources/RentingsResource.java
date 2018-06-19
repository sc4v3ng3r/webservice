/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dao.RentingDAO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Renting;

/**
 * REST Web Service
 *
 * @author scavenger
 */
@Path("rentings")
public class RentingsResource {

    @Context
    private UriInfo context;
    private RentingDAO m_dao;

    /**
     * Creates a new instance of RentingsResource
     */
    
    public RentingsResource() {
        m_dao = new RentingDAO();
    }

    /**
     * Retrieves representation of an instance of resources.RentingsResource
     * @return an instance of model.Renting
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Renting getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RentingsResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Renting content) {
    
    }
    
    @DELETE
    @Path("{rentingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("rentingId") long id){
        m_dao.remove(id);
    }
}
