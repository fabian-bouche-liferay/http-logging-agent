package com.liferay.samples.fbo.http.logging.agent;

import java.lang.reflect.Method;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.bytebuddy.asm.Advice;

public class HTTPResponseAdvice {

    @Advice.OnMethodEnter
    static void onEnter(@Advice.This Object request, @Advice.Local("logBuffer") StringBuffer logBuffer) {

    	Logger logger = LogManager.getLogger(HTTPResponseAdvice.class);
    	
    	logBuffer = new StringBuffer();

        try {
        	
            // Log the HTTP Request Method
            Method getRequestMethodMethod = request.getClass().getMethod("getMethod");
            String requestMethod = getRequestMethodMethod.invoke(request).toString();
            logBuffer.append(requestMethod).append(" ");
        	
            // Log the HTTP Request URI
            Method getURIMethod = request.getClass().getMethod("getURI");
            URI uri = (URI) getURIMethod.invoke(request);
            logBuffer.append(uri.toString()).append(System.lineSeparator());

            // Use reflection to call the getHeaderMap() method on the request object
            Method getHeadersMethod = request.getClass().getMethod("getHeaderMap");
            Object headers = getHeadersMethod.invoke(request);
            logBuffer.append("Headers: ").append(headers.toString()).append(System.lineSeparator());

            // Use reflection to call the getQuery() method on the request object
            Method getQueryMethod = request.getClass().getMethod("getQuery");
            String query = (String) getQueryMethod.invoke(request);
            logBuffer.append("Query: ").append(query).append(System.lineSeparator());

            
        } catch (Exception e) {
            logger.error("Error in onEnter", e);
        }
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    static void onExit(@Advice.Return Object response, @Advice.Thrown Throwable throwable, @Advice.Local("logBuffer") StringBuffer logBuffer) {
    	
    	Logger logger = LogManager.getLogger(HTTPResponseAdvice.class);
        
    	try {
            if (throwable != null) {
                logBuffer.append("Request failed with exception: ").append(throwable.getMessage()).append(System.lineSeparator());
                logger.error("Request failed", throwable);
                return;
            }

            // Use reflection to call the getStatusCode() method on the response object
            Method getStatusCodeMethod = response.getClass().getMethod("getStatusCode");
            int statusCode = (int) getStatusCodeMethod.invoke(response);
            logBuffer.append("Response Status Code: ").append(statusCode).append(System.lineSeparator());

            // Use reflection to call the getHeaderMap() method on the response object
            Method getHeadersMethod = response.getClass().getMethod("getHeaderMap");
            Object headers = getHeadersMethod.invoke(response);
            logBuffer.append("Headers: ").append(headers.toString()).append(System.lineSeparator());

            // Use reflection to call the getBody() method on the response object
            Method getBodyMethod = response.getClass().getMethod("getContent");
            String body = (String) getBodyMethod.invoke(response);
            logBuffer.append("Response: ").append(body);
            
        } catch (Exception e) {
            logger.error("Error in onExit", e);
        } finally {
            logger.info(logBuffer.toString());
        }
    }
}
