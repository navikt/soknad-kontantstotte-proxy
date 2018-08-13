package no.nav.servlet.callid;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static no.nav.servlet.callid.CallId.DEFAULT_CALL_ID_KEY;


/**
 * @deprecated Ta i bruk PUS sitt filter istedet
 */
@Deprecated
public class CallIdFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = HttpServletRequest.class.cast(servletRequest);

        Optional.ofNullable(req.getHeader(DEFAULT_CALL_ID_KEY))
                .map(CallId::set)
                .orElseGet(CallId::create);

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
