package test;

import com.bring.context.AnnotationConfigApplicationContext;
import data.configuration.TestConfiguration;
import data.service.NasaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest {
    
    @DisplayName("All beans from configuration class registered in Application Context")
    @Test
    void testConfigurationBeansRegistration() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        context.register();

        NasaService nasaService = context.getBean(NasaService.class);
        
        assertThat(nasaService).isNotNull();
        assertThat(nasaService.getNasaRestClient()).isNotNull();
        assertThat(nasaService.getNasaRestClient().getKey()).isNotEmpty();
        assertThat(nasaService.getNasaRestClient().getUrl()).isNotEmpty();
    }
    
}
