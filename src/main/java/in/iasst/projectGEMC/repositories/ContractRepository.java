package in.iasst.projectGEMC.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.entities.User;

public interface ContractRepository extends JpaRepository<Contract, Integer>{
	
	boolean existsByGemNo(String gemNo);
	
	@Query("SELECT c FROM Contract c WHERE " +
		       "(:gemNo IS NOT NULL AND c.gemNo = :gemNo) OR " +
		       "(:gemNo IS NULL AND c.contractDate BETWEEN :startDate AND :endDate)")
	Page<Contract> findByGemNoOrDateRange(@Param("gemNo") String gemNo, 
		                                      @Param("startDate") LocalDate startDate, 
		                                      @Param("endDate") LocalDate endDate,
		                                      Pageable pageable);
	
	Page<Contract> findAll(Pageable pageable);
	//For Buyer
	Page<Contract> findByBuyerName(User buyer, Pageable pageable);

    // For Payment Authority
    Page<Contract> findByPaymentAuth(User paymentAuth, Pageable pageable);

    //For Consginee
    Page<Contract> findByConsigneeNameAndConsigneeDetailsIsNotNull(User consignee, Pageable pageable);
    
 // For Consignee: Pending where details are NULL
    Page<Contract> findByConsigneeNameAndConsigneeDetailsIsNull(User user, Pageable pageable);
 // For PaymentAuth: Pending where payment is NULL but consignee details are NOT NULL
    Page<Contract> findByPaymentDetailsIsNullAndConsigneeDetailsIsNotNull(User user, Pageable pageable);

    List<Contract> findBycontractDateBetween(LocalDate start, LocalDate end);
    
}
