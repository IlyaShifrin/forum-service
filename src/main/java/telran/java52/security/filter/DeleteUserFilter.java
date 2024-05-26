package telran.java52.security.filter;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import telran.java52.accounting.model.Role;
import telran.java52.security.model.User;

@Component
@RequiredArgsConstructor
@Order(40)
public class DeleteUserFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		if (checkEndpoint(request.getMethod(), request.getServletPath())) {
			String principal = request.getUserPrincipal().getName();
			Set<String> roles = ((User) request.getUserPrincipal()).getRoles();
			String[] parts = request.getServletPath().split("/");
			String owner = parts[parts.length - 1];
			
			if (!(roles.contains(Role.ADMINISTRATOR.name()) || principal.equalsIgnoreCase(owner))) { 
				response.sendError(403, "Permission denied");
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	private boolean checkEndpoint(String method, String path) {
		return (HttpMethod.DELETE.matches(method) && path.matches("/account/user/\\w+"));
	}

}
