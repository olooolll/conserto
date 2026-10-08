import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;

@SpringBootConfiguration
@EnableAutoConfiguration(excludeName = "org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration")
@Import({ConsertoController.class, ConsertoRepository.class})
public class ApiConsertosApplication {


    // Benjamin Silva   - SC3052125
    // Guilherme Araujo - SC3051862
    public static void main(String[] args) {
        SpringApplication.run(ApiConsertosApplication.class, args);
    }

    @Bean
    PersistenceManagedTypes persistenceManagedTypes() {
        return PersistenceManagedTypes.of(Conserto.class.getName());
    }
}
