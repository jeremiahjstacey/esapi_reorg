package org.owasp.esapi.codecs.canonicalization;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.LogFactory;
import org.owasp.esapi.Logger;
import org.owasp.esapi.codecs.canonicalization.LoggingEncodingFailureHandler;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * FIXME:  Document intent of class.  General Function, purpose of creation, intended feature, etc.
 *  Why do people care this exists? 
 * @author Jeremiah
 * @since Jan 3, 2018
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest (ESAPI.class)
public class LoggingEncodingFailureHandlerTest {

	@Before
    public void setup() throws Exception {
    	Logger logger = Mockito.mock(Logger.class);
    	LogFactory factory = Mockito.mock(LogFactory.class);
    	Mockito.when(factory.getLogger(Matchers.anyString())).thenReturn(logger);
    	Mockito.when(factory.getLogger(Matchers.any(Class.class))).thenReturn(logger);
    	PowerMockito.mockStatic(ESAPI.class);
    	PowerMockito.when(ESAPI.class, "logFactory").thenReturn(factory);
    	PowerMockito.when(ESAPI.class, "getLogger", Matchers.anyString()).thenReturn(logger);
    	PowerMockito.when(ESAPI.class, "getLogger", Matchers.any(Class.class)).thenReturn(logger);
    }
	
    @Test
    public void testEventIsLogged() {
        String message = "testEvent";
        LoggingEncodingFailureHandler handler = new LoggingEncodingFailureHandler();
        
        Logger mockLogger = Mockito.mock(Logger.class);
        Whitebox.setInternalState(handler, "logger", mockLogger);
        
        handler.onFailure(message);
        
        Mockito.verify(mockLogger).warning(Logger.SECURITY_FAILURE, message);
    }
}
