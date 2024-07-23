package pl.thinkdata.b2bbase.pricelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.pricelist.model.PriceList;

import java.util.Date;
import java.util.List;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    List<PriceList> findByProductNameAndStartDateBeforeAndEndDateAfter(String productName, Date startDate, Date endDate);
}
