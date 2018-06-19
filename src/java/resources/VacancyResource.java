/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dao.VacancyDAO;
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
import model.Vacancy;

/**
 * REST Web Service
 *
 * @author scavenger
 */
@Path("vacancys")
public class VacancyResource {

    @Context
    private UriInfo context;
    private VacancyDAO m_dao;
    /**
     * Creates a new instance of VacancyResource
     */
    public VacancyResource() {
        m_dao = new VacancyDAO();
    }

    /**
     * Retrieves representation of an instance of resources.VacancyResource
     * @param id
     * @return an instance of model.Vacancy
     */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{vacancyId}")
    public Vacancy getVacancyById( @PathParam("vacancyId") Long id) {
        return m_dao.getById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vacancy> getVacancyByPerfilId(@PathParam("perfilId") long id){
            return m_dao.getByPerfilId(id);
    }
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vacancy> getAll(){
        return m_dao.getAllWithPaging(0,0);
    }*/
    /**
     * Esse é um método DELEGADOR. Qulquer método HTTP que for chamando
     * por cima da URL {vacancyId}/parkings o método getParkingResource é
     * chamado e o método HTTP requisitado será tratado pelo novo recurso
     * retornado.
     * @param id vacancy id
     * @return a instance of resources.ParkingResource
     */
    
    @Path("{vacancyId}/parkings")
    public ParkingResource getParkingResource( @PathParam("vacancyId") Long id) {
        return new ParkingResource();
    }
    
    /**
     * PUT method for updating or creating an instance of VacancyResource
     * @param id
     * @param content representation for the resource
     */
    @PUT
    @Path("{vacancyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("vacancyId") long id, Vacancy content) {
        content.setId(id);
        m_dao.update(content);
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{vacancyId}")
    public void delete( @PathParam("vacancyId") long id){
        m_dao.remove(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVacancy(Vacancy vacancy, @Context UriInfo info){
        vacancy = m_dao.insert(vacancy);
        String id = String.valueOf( vacancy.getId());
        
        URI uri = info.getAbsolutePathBuilder().path(id).build();
        return Response.created(uri)
                .entity(vacancy)
                .build();
    }
    
}
