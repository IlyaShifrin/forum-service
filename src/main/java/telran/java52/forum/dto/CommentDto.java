package telran.java52.forum.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class CommentDto {
    String user;
    String message;
    LocalDate dateCreated;
    int likes;
}
