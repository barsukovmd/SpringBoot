package by.teachmeskills.soap.config;

import by.teachmeskills.soap.model.CountriesPort;
import by.teachmeskills.soap.model.CountriesPortService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfig {

    @Bean
    public CountriesPort countryClient() {
        CountriesPortService client = new CountriesPortService();
        return client.getCountriesPortSoap11();
    }
}
