package guru.springframework.msscbeerservice.config.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApacheHttpClient implements RestTemplateCustomizer {
    private final int MAX_CONNECTION;
    private final int MAX_PER_ROUTE;
    private final int SOCKET_TIMEOUT;
    private final int REQUEST_TIMEOUT;

    @Autowired
    public ApacheHttpClient(@Value("${maxConnection}") int MAX_CONNECTION,
                            @Value("${maxConnectionPerRoute}") int MAX_PER_ROUTE,
                            @Value("${socketTimeout}") int SOCKET_TIMEOUT,
                            @Value("${requestTimeout}") int REQUEST_TIMEOUT) {
        this.MAX_CONNECTION = MAX_CONNECTION;
        this.MAX_PER_ROUTE = MAX_PER_ROUTE;
        this.SOCKET_TIMEOUT = SOCKET_TIMEOUT;
        this.REQUEST_TIMEOUT = REQUEST_TIMEOUT;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(MAX_CONNECTION);
        manager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .build();

        CloseableHttpClient closeableHttpClient = HttpClients
                .custom()
                .setConnectionManager(manager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(clientHttpRequestFactory());
    }
}
