package com.example.waes.test.config;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JettyConfig implements
        WebServerFactoryCustomizer<JettyServletWebServerFactory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JettyConfig.class);

    @Value("${server.port}") int port;
    @Value("${server.minThreads}") int minThreads;
    @Value("${server.maxThreads}") int maxThreads;
    @Value("${server.keepAliveTime}") int keepAliveTime;

    @Override
    public void customize(JettyServletWebServerFactory factory) {
        factory.setPort(port);

        factory.addServerCustomizers((JettyServerCustomizer) server -> {
            LOGGER.info("Container Min Threads={}", minThreads);
            LOGGER.info("Container Max Threads={}", maxThreads);
            LOGGER.info("Container Idle Timeout={}", keepAliveTime);
            final QueuedThreadPool queuedThreadPool = server.getBean(QueuedThreadPool.class);
            queuedThreadPool.setMinThreads(minThreads);
            queuedThreadPool.setMaxThreads(maxThreads);
            queuedThreadPool.setIdleTimeout(keepAliveTime);

            GzipHandler gzipHandler = new GzipHandler();
            gzipHandler.setHandler(server.getHandler());
            server.setHandler(gzipHandler);

            for (Connector connector : server.getConnectors()) {
                if (connector instanceof ServerConnector) {
                    HttpConnectionFactory connectionFactory = ((ServerConnector) connector)
                            .getConnectionFactory(HttpConnectionFactory.class);
                    LOGGER.info("HttpConfiguration RequestHeaderSize={}", 16 * 1024);
                    connectionFactory.getHttpConfiguration()
                            .setRequestHeaderSize(16 * 1024);
                }
            }
        });
    }
}