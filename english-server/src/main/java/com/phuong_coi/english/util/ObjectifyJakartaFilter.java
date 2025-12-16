package com.phuong_coi.english.util;
import jakarta.servlet.*;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;

public class ObjectifyJakartaFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        ObjectifyService.run(() -> {
            try {
                chain.doFilter(request, response);
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void destroy() {
    }
}