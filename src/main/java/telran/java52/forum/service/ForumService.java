package telran.java52.forum.service;

import java.util.List;
import java.util.Set;

import telran.java52.forum.dto.CommentDto;
import telran.java52.forum.dto.PeriodDto;
import telran.java52.forum.dto.PostAddDto;
import telran.java52.forum.dto.PostDto;

public interface ForumService {
	PostDto addPost(String user, PostAddDto postAddDto);
	
	PostDto findPostById(Long id);
	
	void addlike(Long id);
	
	List<PostDto> findPostsByAuthor(String user);
	
	PostDto addComment(Long id, String user, CommentDto commentDto);
	
	PostDto deletePost(Long id);
	
	List<PostDto> findPostsByTags(Set<String> tags);
	
	List<PostDto> findPostsByPeriod(PeriodDto period);
	
	PostDto updatePost(Long id, PostAddDto postAddDto);
	
	
}
