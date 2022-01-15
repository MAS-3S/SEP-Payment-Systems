package acquirer.bank.acquirerbankbackend.config;

import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class SSLConfig {

    @Value("${server.ssl.trust-store}")
    private Resource trustStore;
    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;

    @Bean
    @SneakyThrows
    public RestTemplate restTemplate() throws Exception {
        final SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(this.trustStore.getURL(), this.trustStorePassword.toCharArray())
                .build();
        final SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        final HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        final HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
