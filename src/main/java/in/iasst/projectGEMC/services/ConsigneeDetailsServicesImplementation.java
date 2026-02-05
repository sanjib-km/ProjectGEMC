package in.iasst.projectGEMC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.iasst.projectGEMC.entities.ConsigneeDetails;
import in.iasst.projectGEMC.repositories.ConsigneeDetailsRepository;

@Service
public class ConsigneeDetailsServicesImplementation implements ConsigneeDetailsServices{
	@Autowired
	ConsigneeDetailsRepository consignee_repo;

	public ConsigneeDetailsServicesImplementation() {
		
	}

	@Override
	public String saveConsigneeDetails(ConsigneeDetails details) {
		consignee_repo.save(details);
		return "saved";
	}

}
