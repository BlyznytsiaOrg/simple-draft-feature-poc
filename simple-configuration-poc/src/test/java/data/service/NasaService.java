package data.service;

import com.fasterxml.jackson.databind.JsonNode;
import data.client.RestClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NasaService {
    
    private final RestClient nasaRestClient;
    
    public JsonNode getNasaContent(final Integer sol) {
        return null;
    }
    
}
