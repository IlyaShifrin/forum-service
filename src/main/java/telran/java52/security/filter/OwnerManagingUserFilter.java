package telran.java52.security.filter;

import java.io.IOException;

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
import telran.java52.accounting.dao.UserAccountRepository;
import telran.java52.accounting.dto.exception.NoPermissionException;
import telran.java52.accounting.model.Role;
import telran.java52.accounting.model.UserAccount;

@Component
@RequiredArgsConstructor
@Order(30)
public class OwnerManagingUserFilter implements Filter {

	final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndpoint(request.getMethod(), request.getServletPath())) {
			
			try {
				UserAccount userAccount = userAccountRepository.findById(request.getUserPrincipal().getName())
						.orElseThrow(RuntimeException::new);
				
				boolean isNotAdministrator = !userAccount.getRoles().contains(Role.ADMINISTRATOR);
				boolean isNotOwner = !request.getUserPrincipal().getName().equals(request.getServletPath().split("/")[3]);
				
				if (HttpMethod.DELETE.matches(request.getMethod()) && isNotAdministrator && isNotOwner) {
					throw new NoPermissionException(); 
				}
				
				if (HttpMethod.PUT.matches(request.getMethod()) && isNotOwner) {
					throw new NoPermissionException();
				}
			} catch (NoPermissionException e) {
				response.sendError(409);
				return;
			}

		}
		
		chain.doFilter(request, response);
	}

	private boolean checkEndpoint(String method, String path) {
		return (HttpMethod.PUT.matches(method) || HttpMethod.DELETE.matches(method))
				&& path.matches("/account/user/\\w+");
	}

}
