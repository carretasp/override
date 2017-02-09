package override;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Override
    Page<Account> findAll(Pageable pageable);

    @Override
    Account findOne(Long aLong);

    @RestResource(exported = false)
    Account findByNameEquals(String name);
}
