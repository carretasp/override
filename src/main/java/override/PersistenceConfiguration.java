package override;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;

@Configuration
@EnableJpaRepositories
public class PersistenceConfiguration extends JpaRepositoryConfigExtension {
    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    private void addPersons() {
        Account john = new Account("John", 1000, 300);
        Account mary = new Account("Mary", 1000, 500);
        accountRepository.save(asList(john, mary));
    }
}
