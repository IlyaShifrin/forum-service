package telran.java52.forum.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter
public class PostDto {
    Long id;
    String title;
    String content;
    String author;
    LocalDate dateCreated;
    List<String> tags;
    Integer likes;
    List<CommentDto> comments;
}
