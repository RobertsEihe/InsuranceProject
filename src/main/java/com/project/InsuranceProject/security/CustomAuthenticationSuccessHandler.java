package com.project.InsuranceProject.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(Roles.ADMIN)) {
                response.sendRedirect("/admin/user-management");
                return;
            } else if (authority.getAuthority().equals(Roles.AGENT)) {
                response.sendRedirect("/agent");
                return;
            }else if (authority.getAuthority().equals(Roles.CUSTOMER)) {
            response.sendRedirect("/customer/policy");
            return;
        }
        }

        response.sendRedirect("/");
    }
}