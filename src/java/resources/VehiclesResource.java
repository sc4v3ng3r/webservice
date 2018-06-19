/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dao.VehicleDAO;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import model.Vehicle;

/**
 * REST Web Service
 *
 * @author scavenger
 */
@Path("vehicles")
public class VehiclesResource {

    @Context
    private UriInfo context;
    private VehicleDAO m_dao;
    
    private Sse m_sse;
    private SseBroadcaster m_broadcaster;

    /**
     * Creates a new instance of VehiclesResource
     */
    public VehiclesResource() {
        m_dao = new VehicleDAO();
    }

    /**
     * Retrieves representation of an instance of resources.VehiclesResource
     * @param start 
     * @param size
     * @param licensePlate
     * @return an instance of model.Vehicle
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehicle> getVehicles(
            @DefaultValue("0") @QueryParam("start") int start,
            @DefaultValue("0") @QueryParam("size") int size,
            @QueryParam("licensePlate") String licensePlate){
            
        if (start >=0 && size > 0){
            return m_dao.getAllWithPaging(start, size );
        }
        
        if (licensePlate != null){
            
        }
        return m_dao.getAllWithPaging(0,0);
    }

    @GET
    @Path("{vehicleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vehicle getVehicleById(@PathParam("vehicleId") Long id){
        return m_dao.getById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVehicle(Vehicle vehicle, @Context UriInfo info){
        vehicle = m_dao.insert(vehicle);
        String id = String.valueOf( vehicle.getId());
        URI uri = info.getAbsolutePathBuilder().path(id).build();
        
        return Response.created(uri).entity( vehicle).build();
    }
    /**
     * PUT method for updating or creating an instance of VehiclesResource
     * @param id
     * @param content representation for the resource
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{vehicleId}")
    public Vehicle putJson(@PathParam("vehicleId") long id, Vehicle content) {
        content.setId(id);
        m_dao.update(content);
        return content;
    }
    
    @DELETE
    @Path("{vehicleId}")
    public void delete(@PathParam("vehicleId") long id){
        m_dao.remove(id);
    }
    
}
