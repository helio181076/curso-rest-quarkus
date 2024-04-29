package io.github.dougllasfps.quarkussocial.rest;

import io.github.dougllasfps.quarkussocial.domain.model.User;
import io.github.dougllasfps.quarkussocial.rest.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.validator.HibernateValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;

//utilizando panache entity usa o próprio objeto: user.findAll() , user.persist()
//utilizando repositório cria classes separadas e específicas, logica fica no
//repositório e entidade é só representação da tabela banco de dados

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {


    //private final UserResource repository;

    //@Inject
    //public UserResource(UserResource repository){
     //   this.repository = repository;
    //}
    @POST
    @Transactional
    public Response createUser(@Valid CreateUserRequest userRequest){
        //validator.validate(userRequest);

        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        user.persist();
        //repository.persist(user);
        //return Response.ok(user).build();
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }

    //não precisa @Transaction porque é só leitura
    @GET
    public Response listAllUsers(){
        PanacheQuery<User> query = User.findAll();
        //PanacheQuery<User> query = repository.listAllUsers();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}") //exemplo /users/1 ... /users/2
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        User user = User.findById(id);
        //User user = repository.findById(id);
        //verifica se o usuario existe antes do delete
        if(user != null){
            user.delete();
            //repository.delete(user);
            //return Response.ok().build();
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT //sempre a mesma resposta
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData ){
        User user = User.findById(id);
        //User user = repository.finById(id);


        if(user != null) {
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            //return Response.ok().build();
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
