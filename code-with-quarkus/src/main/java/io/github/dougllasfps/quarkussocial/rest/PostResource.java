package io.github.dougllasfps.quarkussocial.rest;

import io.github.dougllasfps.quarkussocial.domain.model.Post;
import io.github.dougllasfps.quarkussocial.domain.model.User;
import io.github.dougllasfps.quarkussocial.domain.repository.UserRepository;
import io.github.dougllasfps.quarkussocial.rest.dto.CreatePostRequest;
import io.github.dougllasfps.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private User user;
    private Post post;

    @Inject
    public PostResource(User user, Post post) {
        this.user = user;
        this.post = post;

    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request) {
        User user1 = user.findById(userId);
        if(user1 == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user1);

        post.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts( @PathParam("userId") Long userId) {
        User user1 = user.findById(userId);
        if(user1 == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PanacheQuery<Post> query = post.find("user", user1);

        //PanacheQuery<Post> query = post.find(
        //               "user", Sort.by("dateTime", Sort.Direction.Descending), user);

        List<Post> list = query.list();

        //list.sort(Comparator.comparing(Post::"dateTime").reversed());
        //falta ordenar o post pelo dateTime em ordem decrescente..

        var postResponseList = list.stream()
                .map(post -> PostResponse.fromEntity(post))
                //.map(PostResponse::fromEntity) mesma função da linha acima
                .collect(Collectors.toList());

        return Response.ok(postResponseList).build();
    }
}
