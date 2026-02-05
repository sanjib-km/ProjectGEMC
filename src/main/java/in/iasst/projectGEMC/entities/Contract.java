package in.iasst.projectGEMC.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
@Entity
public class Contract extends Timestamp{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contractId;
	private String gemNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate contractDate;
	@ManyToOne
	private User buyerName;
	private String sellerName;
	private String accountDetails;
	private float amount;
	private int quantity;
	private String itemDetails;
	private String indenter;
	private String sourceOfFund;
	private String projectName;
	private String eFileNo;
	private String pur_req_putupBy;
	private String orderStatus;
	@ManyToOne
	private User consigneeName;
	@ManyToOne
	private User paymentAuth;
	@OneToOne(cascade = CascadeType.ALL)
	private ConsigneeDetails consigneeDetails;
	@OneToOne(cascade = CascadeType.ALL)
	private PaymentDetails paymentDetails;

	public Contract() {
		
	}

	public Contract(int contractId, String gemNo, LocalDate contractDate, User buyerName, String sellerName,
			String accountDetails, float amount, int quantity, String itemDetails, String indenter, String sourceOfFund,
			String projectName, String eFileNo, String pur_req_putupBy, String orderStatus, User consigneeName,
			User paymentAuth, ConsigneeDetails consigneeDetails, PaymentDetails paymentDetails) {
		super();
		this.contractId = contractId;
		this.gemNo = gemNo;
		this.contractDate = contractDate;
		this.buyerName = buyerName;
		this.sellerName = sellerName;
		this.accountDetails = accountDetails;
		this.amount = amount;
		this.quantity = quantity;
		this.itemDetails = itemDetails;
		this.indenter = indenter;
		this.sourceOfFund = sourceOfFund;
		this.projectName = projectName;
		this.eFileNo = eFileNo;
		this.pur_req_putupBy = pur_req_putupBy;
		this.orderStatus = orderStatus;
		this.consigneeName = consigneeName;
		this.paymentAuth = paymentAuth;
		this.consigneeDetails = consigneeDetails;
		this.paymentDetails = paymentDetails;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public String getGemNo() {
		return gemNo;
	}

	public void setGemNo(String gemNo) {
		this.gemNo = gemNo;
	}

	public LocalDate getContractDate() {
		return contractDate;
	}

	public void setContractDate(LocalDate contractDate) {
		this.contractDate = contractDate;
	}

	public User getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(User buyerName) {
		this.buyerName = buyerName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(String iteamDetails) {
		this.itemDetails = iteamDetails;
	}

	public String getIndenter() {
		return indenter;
	}

	public void setIndenter(String indenter) {
		this.indenter = indenter;
	}

	public String getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String geteFileNo() {
		return eFileNo;
	}

	public void seteFileNo(String eFileNo) {
		this.eFileNo = eFileNo;
	}

	public String getPur_req_putupBy() {
		return pur_req_putupBy;
	}

	public void setPur_req_putupBy(String pur_req_putupBy) {
		this.pur_req_putupBy = pur_req_putupBy;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public ConsigneeDetails getConsigneeDetails() {
		return consigneeDetails;
	}

	public void setConsigneeDetails(ConsigneeDetails consigneeDetails) {
		this.consigneeDetails = consigneeDetails;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}


	public User getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(User consigneeName) {
		this.consigneeName = consigneeName;
	}

	public User getPaymentAuth() {
		return paymentAuth;
	}

	public void setPaymentAuth(User paymentAuth) {
		this.paymentAuth = paymentAuth;
	}

	@Override
	public String toString() {
		return "Contract [contractId=" + contractId + ", gemNo=" + gemNo + ", contractDate=" + contractDate
				+ ", buyerName=" + buyerName + ", sellerName=" + sellerName + ", accountDetails=" + accountDetails
				+ ", amount=" + amount + ", quantity=" + quantity + ", itemDetails=" + itemDetails + ", indenter="
				+ indenter + ", sourceOfFund=" + sourceOfFund + ", projectName=" + projectName + ", eFileNo=" + eFileNo
				+ ", pur_req_putupBy=" + pur_req_putupBy + ", orderStatus=" + orderStatus + ", consigneeName="
				+ consigneeName + ", paymentAuth=" + paymentAuth + ", consigneeDetails=" + consigneeDetails
				+ ", paymentDetails=" + paymentDetails + "]";
	}

	
}
	