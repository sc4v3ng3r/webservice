/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dao.ParkPerfilDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.ParkPerfil;

/**
 * REST Web Service
 *
 * @author scavenger
 */
@Path("perfis")
public class ParkPerfisResource {

    @Context
    private UriInfo context;
    private ParkPerfilDAO m_dao;
    /**
     * Creates a new instance of ParkPerfisResource
     */
    public ParkPerfisResource() {
        m_dao = new ParkPerfilDAO();
    }

    /**
     * Retrieves representation of an instance of resources.ParkPerfisResource
     * @param start
     * @param size
     * @return an instance of model.ParkPerfil
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParkPerfil> getJson(
            @DefaultValue("0") @QueryParam("start") int start, 
            @DefaultValue("0") @QueryParam("size") int size ) {
        //TODO return proper representation object
        if (start >=0 && size > 0)
            return m_dao.getAllWithPaging(start, size);
        
        return m_dao.getAllWithPaging(0, 0);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{perfilId}")
    public ParkPerfil getPerfil( @PathParam("perfilId") long perfilId ){
        return m_dao.getById(perfilId);
    }
    
    /*SUB - RECURSO!*/
    @Path("{perfilId}/vacancys")
    public VacancyResource getVacancyResource(@PathParam("perfilId") long perfilId){
        return new VacancyResource();
    }
    /**
     * PUT method for updating or creating an instance of ParkPerfisResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(ParkPerfil content) {
    }
}
