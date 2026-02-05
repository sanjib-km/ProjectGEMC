package in.iasst.projectGEMC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.iasst.projectGEMC.entities.PaymentDetails;
import in.iasst.projectGEMC.repositories.PaymentDetailsRepository;

@Service
public class PaymentDetailsServicesImplementation implements PaymentDetailsServices{

	public PaymentDetailsServicesImplementation() {
		
	}
	@Autowired
	PaymentDetailsRepository paymentRepo;
	
	@Override
	public String saveDetails(PaymentDetails details) {
		paymentRepo.save(details);
		return "saved";
	}
	
	

}
