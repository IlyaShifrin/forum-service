package telran.java52.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java52.post.dao.PostRepository;
import telran.java52.post.model.Post;

@Component
@RequiredArgsConstructor
public class WebSecurity {
	
	final PostRepository postRepository;

	public boolean checkDeletePost(Authentication authentication, String postId) {
		String principal = authentication.getName();
		Post post = postRepository.findById(postId).orElse(null);
		
		return post != null &&
				(principal.equals(post.getAuthor()) || 
				 authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR")));
	}
	
	public boolean checkUpdatePost(Authentication authentication, String postId) {
		String principal = authentication.getName();
		Post post = postRepository.findById(postId).orElse(null);
		
		return post != null && principal.equals(post.getAuthor());
	}
}
