package telran.java52.forum.service;

import java.util.List;
import java.util.Set;

import telran.java52.forum.dto.DatePeriodDto;
import telran.java52.forum.dto.NewCommentDto;
import telran.java52.forum.dto.NewPostDto;
import telran.java52.forum.dto.PostDto;

public interface ForumService {
	PostDto addNewPost(String author, NewPostDto newPostDto);
	
	PostDto findPostById(String id);
	
	PostDto removePost(String id);
	
	PostDto updatePost(String id, NewPostDto newPostDto);
	
	PostDto addComment(String id, String author, NewCommentDto newCommentDto);
	
	void addLike(String id);
	
	Iterable<PostDto> findPostsByAuthor(String author);
	
	Iterable<PostDto> findPostsByTags(List<String> tags);
	
	Iterable<PostDto> findPostsByPeriod(DatePeriodDto period);
	
}
