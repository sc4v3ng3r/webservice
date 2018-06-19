/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dao.ParkingDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Parking;

/**
 * REST Web Service
 *
 * @author scavenger
 */
@Path("parkings")
public class ParkingResource {

    @Context
    private UriInfo context;
    private ParkingDAO m_dao;
    
    /**
     * Creates a new instance of ParkingResource
     */
    public ParkingResource() {
        m_dao = new ParkingDAO();
        System.out.println("ParkingResource::ParkingResource()");
    }

    
    @GET
    @Path("{parkingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Parking getParkingById(@PathParam("parkingId") Long id){
        return m_dao.getById(id);
    }
    
    /**
     * Retrieves representation of an instance of resources.ParkingResource
     * @param vacancyId
     * @param start Valor do indice registro inicial
     * @param size valor do tamanho da pagina
     * @param id
     * @return an instance of model.Parking
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Parking> getParkingByVacancyId( 
            @PathParam("vacancyId") long vacancyId,
            @DefaultValue("0") @QueryParam("start") int start,
            @DefaultValue("0") @QueryParam("offset") int size){
            
            if (start >= 0 && size > 0){
                return m_dao.getByVacancyId(vacancyId,start, size);
            }
            
            return m_dao.getByVacancyId(vacancyId,0,0);
    }
    /**
     * PUT method for updating or creating an instance of ParkingResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Parking content) {
    }
    
}
