package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;

import java.util.List;

@Repository
public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, Long> {
    List<SubscriptionOrder> findAllByCompanyId(Long id);
}
