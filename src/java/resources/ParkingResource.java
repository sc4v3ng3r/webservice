/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dao.ParkingDAO;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
            
        List<Parking> list = null; 
            
        if (start >= 0 && size > 0){
            list = m_dao.getByVacancyId(vacancyId,start, size);
        }
        else 
          list =  m_dao.getByVacancyId(vacancyId,0,0);
        
        return list;
    }
    
    /**
     * PUT method for updating or creating an instance of ParkingResource
     * @param content representation for the resource
     */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addParking(Parking parking, @Context UriInfo info){
        parking = m_dao.insert(parking);
        URI uri = info.getAbsolutePathBuilder().path( String.valueOf( parking.getId())).build();
        
        return Response.created(uri).entity(parking).build();
    }
            
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Parking putJson(Parking content,
            @PathParam("vacancyId") long idVacancy,
            @PathParam("parkingId")long idParking) {
        
        content.setId(idParking);
        m_dao.update(content);
        return content;
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(Parking content, @PathParam("parkingId") long id){
        m_dao.remove(id);
    }
}
