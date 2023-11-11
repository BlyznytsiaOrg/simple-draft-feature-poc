package data.configuration;

import com.bring.annotation.Bean;
import com.bring.annotation.Configuration;
import data.client.RestClient;
import data.service.NasaService;

@Configuration
public class TestConfiguration {

    @Bean
    public RestClient nasaRestClient() {
        final RestClient restClient = new RestClient();
        restClient.setUrl("https://");
        restClient.setKey("KEY");
        
        return restClient;
    }

    @Bean
    public NasaService nasaService(final RestClient nasaRestClient) {
        return new NasaService(nasaRestClient);
    }
    
}
