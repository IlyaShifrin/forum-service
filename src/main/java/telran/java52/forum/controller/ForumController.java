package telran.java52.forum.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.forum.dto.DatePeriodDto;
import telran.java52.forum.dto.NewCommentDto;
import telran.java52.forum.dto.NewPostDto;
import telran.java52.forum.dto.PostDto;
import telran.java52.forum.service.ForumService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {
	
	final ForumService forumService;

	@PostMapping("/post/{author}")
	public PostDto addNewPost(@PathVariable String author, @RequestBody NewPostDto newPostDto) {
		return forumService.addNewPost(author, newPostDto);
	}

	@GetMapping("/post/{id}")
	public PostDto findPostById(@PathVariable String id) {
		return forumService.findPostById(id);
	}

	@DeleteMapping("/post/{id}")
	public PostDto removePost(@PathVariable String id) {
		return forumService.removePost(id);
	}
	
	@PutMapping("/post/{id}")
	public PostDto updatePost(@PathVariable String id, @RequestBody NewPostDto newPostDto) {
		return forumService.updatePost(id, newPostDto);
	}
	
	@PutMapping("/post/{id}/comment/{author}")
	public PostDto addComment(@PathVariable String id, @PathVariable String author, 
			@RequestBody NewCommentDto newCommentDto) {
		return forumService.addComment(id, author, newCommentDto);
	}
	
	@PutMapping("/post/{id}/like")
	public void addlike(@PathVariable String id) {
		forumService.addLike(id);
	}

	@GetMapping("/posts/author/{author}")
	public Iterable<PostDto> findPostsByAuthor(@PathVariable String author) {
		return forumService.findPostsByAuthor(author);
	}

	@PostMapping("/posts/tags")
	public Iterable<PostDto> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);
	}

	@PostMapping("/posts/period")
	public Iterable<PostDto> findPostsByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
		return forumService.findPostsByPeriod(datePeriodDto);
	}



	
	
}
