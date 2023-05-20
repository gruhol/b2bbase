package pl.thinkdata.b2bbase.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
