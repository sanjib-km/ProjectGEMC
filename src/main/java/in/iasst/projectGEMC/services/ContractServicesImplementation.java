package in.iasst.projectGEMC.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.repositories.ContractRepository;

@Service
public class ContractServicesImplementation implements ContractServices {
	@Autowired
	ContractRepository contract_repo;
	
	@Override
	public String saveContract(Contract contract) {
		contract_repo.save(contract);
		return "contract added";
		
	}

	@Override
	public boolean existsByGemNo(String gemNo) {
		
		return contract_repo.existsByGemNo(gemNo);
	}

	@Override
	public Optional<Contract> getContractById(int id) {
		
		return contract_repo.findById(id);
	}

	@Override
	public List<Contract> getAllContracts() {
		
		return contract_repo.findAll();
	}

//	@Override
//	public List<Contract> findByGemNoOrDateRange(String searchGemNo, LocalDate startDate, LocalDate endDate) {
//		
//		return contract_repo.findByGemNoOrDateRange(searchGemNo, startDate, endDate);
//	}
	public Page<Contract> findByGemNoOrDateRange(String gemNo, LocalDate start, LocalDate end, Pageable pageable) {
	    return contract_repo.findByGemNoOrDateRange(gemNo, start, end, pageable);
	}

	@Override
	public String deleteContractById(int id) {
		contract_repo.deleteById(id);
		return "success";
	}

	@Override
	public boolean isContractExist(int id) {
		return contract_repo.existsById(id);
	}

	@Override
	public Page<Contract> contractPage(Pageable pageable) {
		return contract_repo.findAll(pageable);
	}

	@Override
	public Page<Contract> getContractsByBuyer(User buyer, Pageable pageable) {
		
		return contract_repo.findByBuyerName(buyer, pageable);
	}

	@Override
	public Page<Contract> getContractsByPaymentAuth(User paymentAuth, Pageable pageable) {
	
		return contract_repo.findByPaymentAuth(paymentAuth, pageable);
	}

	@Override
	public Page<Contract> getContractsByConsigneeWithDetails(User consignee, Pageable pageable) {
		
		return contract_repo.findByConsigneeNameAndConsigneeDetailsIsNotNull(consignee, pageable);
	}

	@Override
	public Page<Contract> getConsigneePendingContracts(User user, Pageable pageable) {
		return contract_repo.findByConsigneeNameAndConsigneeDetailsIsNull(user, pageable);
	}

	@Override
	public Page<Contract> getPaymentAuthPendingContracts(User user, Pageable pageable) {
		return contract_repo.findByPaymentDetailsIsNullAndConsigneeDetailsIsNotNull(user, pageable);
	}

}
