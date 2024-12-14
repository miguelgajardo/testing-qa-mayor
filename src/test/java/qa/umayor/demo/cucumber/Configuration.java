package qa.umayor.demo.cucumber;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import io.cucumber.spring.CucumberContextConfiguration;
import qa.umayor.demo.repository.IClaveRepository;


public class Configuration {
	
	 @Bean
	    public IClaveRepository myBean() {
	        return Mockito.mock(IClaveRepository.class);
	    }

}
