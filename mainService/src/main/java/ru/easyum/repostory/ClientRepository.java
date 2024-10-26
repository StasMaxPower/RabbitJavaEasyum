package ru.easyum.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.easyum.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
