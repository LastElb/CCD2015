package de.mki.jchess.server.filter;

import de.mki.jchess.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Testing CORS-Filter
 * Created by Igor on 04.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SimpleCORSFilterTest {

    @Autowired
    SimpleCORSFilter simpleCORSFilter;

    /**
     * Test if we can apply the CORS Headers
     * @throws Exception
     */
    @Test
    public void testDoFilter() throws Exception {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        simpleCORSFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    /**
     * Test if we can initialize the filter
     * @throws Exception
     */
    @Test
    public void testInit() throws Exception {
        SimpleCORSFilter simpleCORSFilter = new SimpleCORSFilter();
        FilterConfig filterConfig = mock(FilterConfig.class);
        simpleCORSFilter.init(filterConfig);
    }

    /**
     * Test if we can destroy the filter
     * @throws Exception
     */
    @Test
    public void testDestroy() throws Exception {
        SimpleCORSFilter simpleCORSFilter = new SimpleCORSFilter();
        simpleCORSFilter.destroy();
    }
}