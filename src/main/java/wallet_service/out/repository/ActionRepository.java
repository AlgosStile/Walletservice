package wallet_service.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Action;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {


    List<Action> findByUsername(String username);
}
