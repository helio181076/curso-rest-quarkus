package io.github.dougllasfps.quarkussocial.rest.dto;

import io.github.dougllasfps.quarkussocial.domain.model.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime dateTime;

    public static PostResponse fromEntity(Post post){
        var response = new PostResponse();
        response.setText(post.getText());
        response.setDateTime(post.getDataTime());
        return response;
    }
}
