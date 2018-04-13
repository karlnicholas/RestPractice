package com.elderbyte.spring.cloud.bootstrap.feign;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Translate Http Error codes to Spring HttpClientErrorException / HttpServerErrorException
 * Note that
 */
public class SpringWebClientErrorDecoder implements ErrorDecoder {

    private ErrorDecoder delegate = new Default();
    private HttpStatusExceptionBuilder exceptionBuilder = new HttpStatusExceptionBuilder();

    @Override
    public Exception decode(String methodKey, Response response) {
        return exceptionBuilder.buildException(response)
                .map(e -> (Exception)e)
                .orElse(delegate.decode(methodKey, response));
    }

}