/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cyrille.hibernate.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.ThreadLocalSessionContext;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class DisconnectedSessionOpenInviewFilter implements Filter {

    private final static Logger logger = Logger.getLogger(DisconnectedSessionOpenInviewFilter.class);

    protected SessionFactory sessionFactory;

    public void destroy() {
        // sessionFactory will be closed by its creator
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            doFilter(httpServletRequest, httpServletResponse, chain);
        }
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            logger.debug("No http session found");
        } else {
            Session session = (Session) httpSession.getAttribute(Session.class.getName());
            if (session == null) {
                logger.debug("No Hibernate session found in http session");
            } else {
                // no need to reconnect the session, automatically done by Hibernate

                if (logger.isDebugEnabled()) {
                    logger.debug("Bind " + session + " to ThreadLocalSessionContext");
                }
                ThreadLocalSessionContext.bind(session);
            }
        }

        try {
            // CHAIN
            chain.doFilter(request, response);

        } finally {
            // UNBIND HIBERNATE SESSION FROM HIBERNATE_THREAD_LOCAL_SESSION_CONTEXT, DISCONNECT AND BIND TO HTTP_SESSION

            Session session = ThreadLocalSessionContext.unbind(sessionFactory);
            if (session == null) {
                logger.debug("No Hibernate session unbound by ThreadLocalSessionContext");
            } else {
                if (session.isDirty()) {
                    try {
                        logger.warn("Hibernate session is dirty !");
                        // TODO what is the policy ? Session will be disconnected and thus modifications will come back during reconnect
                        if (session.getTransaction().isActive()) {
                            logger.warn("Rollback active transaction for dirty session");
                            session.getTransaction().rollback();
                        }
                        logger.warn("Close dirty session");
                        session.close();
                    } finally {
                        logger.warn("Remove Hibernate session from http session");
                        request.getSession().removeAttribute(Session.class.getName());

                    }
                } else {
                    logger.debug("Hibernate session unbound from ThreadLocalSessionContext, disconnected and bound to http session");

                    try {
                        session.disconnect();
                        // force creation of the HttpSession even if it did not exist at the beginning of the filter
                        request.getSession().setAttribute(Session.class.getName(), session);
                    } catch (RuntimeException e) {
                        logger.error("Exception disconnecting hibernate session. Remove Hibernate session from http session", e);
                        request.getSession().removeAttribute(Session.class.getName());
                        throw e;
                    }
                }

            }
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

}
