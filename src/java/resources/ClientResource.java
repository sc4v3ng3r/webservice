/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dao.ClientDAO;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Client;

/**
 * REST Web Service
 *
 * @author scavenger
 */
@Path("clients")
public class ClientResource {

    @Context
    private UriInfo context;
    private ClientDAO m_dao;

    /**
     * Creates a new instance of ClientResource
     */
    public ClientResource() {
        m_dao = new ClientDAO();
    }

    /**
     * Retrieves representation of an instance of resources.ClientResource
     * @param start Valor do indice registro inicial
     * @param size valor do tamanho da pagina
     * @param info
     * @return an instance of model.Client
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getAllClients(
        @QueryParam("start") int start,
        @QueryParam("size") int size) {
        //System.out.println("CLIENS START " + start + " CLIENTS SIZE: " + size);
        List<Client> list= null;
        if (start >=0 && size > 0)
            list = m_dao.getAllWithPaging(start, size);
        
        else list = m_dao.getAllWithPaging(0,0);
        return list;
    }

    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClientById(@PathParam("clientId") Long id){
        return m_dao.getById( id );    
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClient(Client client, @Context UriInfo uriInfo){
        client = m_dao.insert(client);
        String id = String.valueOf( client.getId() );
        URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
    
        return Response.created(uri)
                .entity(client)
                .build();
    }
    
    /**
     * PUT method for updating or creating an instance of ClientResource
     * @param id
     * @param content representation for the resource
     * @param info
     * @return 
     */
    @PUT
    @Path("{clientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client updateClient(@PathParam("clientId") long id, Client content) {
       content.setId(id);
       m_dao.update(content);
       return content;
    }
    
    @DELETE
    @Path("{clientId}")
    public void delete( @PathParam("clientId") long Id/*, Client content*/){
        m_dao.remove(Id);
    }
    
}
