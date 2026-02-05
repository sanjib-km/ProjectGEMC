package in.iasst.projectGEMC.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.entities.User;

public interface ContractServices {
	String saveContract(Contract contract);
	
	boolean existsByGemNo(String gemNo);
	
	Optional<Contract> getContractById(int id);
	
	List<Contract> getAllContracts();

	Page<Contract> findByGemNoOrDateRange(String searchGemNo, LocalDate startDate, LocalDate endDate, Pageable pageable);

	String deleteContractById(int id);

	boolean isContractExist(int id);
	
	Page<Contract> contractPage(Pageable pageable);
	
	Page<Contract> getContractsByBuyer(User buyer, Pageable pageable);
	
	Page<Contract> getContractsByPaymentAuth(User paymentAuth, Pageable pageable);
	
	Page<Contract> getContractsByConsigneeWithDetails(@Param("consignee") User consignee, Pageable pageable);
	
	Page<Contract> getConsigneePendingContracts(User user, Pageable pageable);
	
	Page<Contract> getPaymentAuthPendingContracts(User user, Pageable pageable);
}
