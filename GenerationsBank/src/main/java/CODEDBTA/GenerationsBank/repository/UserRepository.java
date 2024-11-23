package CODEDBTA.GenerationsBank.repository;

import CODEDBTA.GenerationsBank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNumber(String number);
    UserEntity findByEmail(String email);
    Optional<UserEntity> findByName(String username);

}
