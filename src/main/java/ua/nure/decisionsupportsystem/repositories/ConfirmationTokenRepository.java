package ua.nure.decisionsupportsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.decisionsupportsystem.entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken>  findByConfirmationToken(String confirmationToken);
}
