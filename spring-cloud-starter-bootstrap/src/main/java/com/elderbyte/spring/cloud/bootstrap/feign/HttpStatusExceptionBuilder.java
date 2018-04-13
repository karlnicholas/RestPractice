package com.elderbyte.spring.cloud.bootstrap.feign;

import com.elderbyte.commons.exceptions.ExceptionUtil;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

public final class HttpStatusExceptionBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Builds a HttpStatusCodeException if the response indicates an error,
     * otherwise, returns empty.
     */
    public Optional<HttpStatusCodeException> buildException(Response response){

        HttpStatusCodeException exception = null;

        HttpHeaders responseHeaders = new HttpHeaders();
        response.headers().entrySet()
                .forEach(entry -> responseHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue())));

        HttpStatus statusCode = HttpStatus.valueOf(response.status());

        byte[] responseBody = readResponseBody(response);
        String errorInfo = getErrorText(response, responseBody);

        if (response.status() >= 400 && response.status() <= 499) {
            exception = new HttpClientErrorException(statusCode, errorInfo, responseHeaders, responseBody, null);
        }else  if (response.status() >= 500 && response.status() <= 599) {
            exception = new HttpServerErrorException(statusCode, errorInfo, responseHeaders, responseBody, null);
        }
        return Optional.ofNullable(exception);
    }


    /**
     * Attempts to get a detailed error message from the response
     * @param response The response
     * @param responseBody The extracted response body
     * @return Detail error information.
     */
    private String getErrorText(Response response, byte[] responseBody){

        // INFO: The response 'reason' is often not populated, and Http2 will drop it
        // So the primary source for information is the response body

        // WARN: Since the default HttpClient used in spring / feign drops the response-body
        // on errors like 401, we might have no change of retrieving it here.

        String errorInfo;
        if(responseBody != null && responseBody.length > 0){
            // The response 'reason' is often not populated, and Http2 will drop it
            // Thus we assume additional information might be in the body as text
            errorInfo = new String(responseBody);
        }else if(response.reason() != null && response.reason().length() > 0){
            // If there was no response body we might have luck with the response reason property
            errorInfo = response.reason();
        }else{
            errorInfo = "(No detailed error info was found in the response!)";
        }
        return errorInfo;
    }



    private byte[] readResponseBody(Response response){
        Response.Body body = response.body();
        byte[] responseBody;
        if(body != null){
            try {
                responseBody = getBytesFromInputStream(body.asInputStream());
            } catch (Exception e) {
                logger.warn("Failed to read response body of error message! -> " + ExceptionUtil.aggregateMessages(e));
                responseBody = new byte[0];
            }
        }else{
            responseBody = new byte[0];
        }
        return responseBody;
    }


    private static byte[] getBytesFromInputStream(InputStream is) throws IOException
    {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream())
        {
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);
            os.flush();
            return os.toByteArray();
        }
    }

}
