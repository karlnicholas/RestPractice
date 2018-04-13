package com.elderbyte.spring.cloud.bootstrap.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is referenced in META-INF/spring.factories, using the fully qualified name of this class as the key
 */
public class HystrixEnvironmentConfigurer implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "defaultWardenClientProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        Map<String, Object> defaultProperties = new HashMap<>();

        // Since the RedirectCurrentUserRequestInterceptor requires that the security-context is available in the
        // hystrix thread, we need to enable hystrix.shareSecurityContext
        defaultProperties.put("hystrix.shareSecurityContext", "true");


        addOrReplace(environment.getPropertySources(), defaultProperties);
    }


    private void addOrReplace(MutablePropertySources propertySources,
                              Map<String, Object> map) {

        MapPropertySource target = null;
        if (propertySources.contains(PROPERTY_SOURCE_NAME)) {
            PropertySource<?> source = propertySources.get(PROPERTY_SOURCE_NAME);
            if (source instanceof MapPropertySource) {
                target = (MapPropertySource) source;
                for (String key : map.keySet()) {
                    if (!target.containsProperty(key)) {
                        target.getSource().put(key, map.get(key));
                    }
                }
            }
        }
        if (target == null) {
            target = new MapPropertySource(PROPERTY_SOURCE_NAME, map);
        }
        if (!propertySources.contains(PROPERTY_SOURCE_NAME)) {
            propertySources.addLast(target);
        }
    }
}
