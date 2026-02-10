package in.iasst.projectGEMC.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PaymentDetails extends Timestamp{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate paymentDate;
	private String paymentInfo;
	@ManyToOne
	private Contract contract;

	public PaymentDetails() {
		
	}

	public PaymentDetails(int id, LocalDate paymentDate, String paymentInfo,
			Contract contract) {
		super();
		this.id = id;
		this.paymentDate = paymentDate;
		this.paymentInfo = paymentInfo;
		this.contract = contract;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@Override
	public String toString() {
		return "PaymentDetails [id=" + id + ", paymentDate="
				+ paymentDate + ", paymentInfo=" + paymentInfo + ", contract=" + contract + "]";
	}

	
}
