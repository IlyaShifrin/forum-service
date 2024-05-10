package telran.java52.forum.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.java52.forum.dto.CommentDto;
import telran.java52.forum.dto.PeriodDto;
import telran.java52.forum.dto.PostAddDto;
import telran.java52.forum.dto.PostDto;
import telran.java52.forum.service.ForumService;


@RestController
public class ForumController {
	
	final ForumService forumService;

	@PostMapping("/forum/post/{user}")
	public PostDto addPost(@PathVariable String user, @RequestBody PostAddDto postAddDto) {
		return forumService.addPost(user, postAddDto);
	}

	@GetMapping("/forum/post/{postId}")
	public PostDto findPostById(@PathVariable("postId") Long id) {
		return forumService.findPostById(id);
	}

	@PutMapping("/forum/post/{postId}/like")
	public void addlike(@PathVariable("postId") Long id) {
		forumService.addlike(id);
	}

	@GetMapping("/forum/posts/author/{user}")
	public List<PostDto> findPostsByAuthor(@PathVariable String user) {
		return forumService.findPostsByAuthor(user);
	}

	@PutMapping("/forum/post/{postId}/comment/{user}")
	public PostDto addComment(@PathVariable("postId") Long id, @PathVariable String user, @RequestBody CommentDto commentDto) {
		return forumService.addComment(id, user, commentDto);
	}

	@DeleteMapping("/forum/post/{postId}")
	public PostDto deletePost(@PathVariable("postId") Long id) {
		return forumService.deletePost(id);
	}

	@PostMapping("/forum/posts/tags")
	public List<PostDto> findPostsByTags(Set<String> tags) {
		return forumService.findPostsByTags(tags);
	}

	@PostMapping("/forum/posts/period")
	public List<PostDto> findPostsByPeriod(@RequestBody PeriodDto period) {
		return forumService.findPostsByPeriod(period);
	}

	@PutMapping("/forum/post/{postId}")
	public PostDto updatePost(@PathVariable("postId") Long id, @RequestBody PostAddDto postAddDto) {
		return forumService.updatePost(id, postAddDto);
	}

	
	
}
