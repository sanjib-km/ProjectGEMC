package in.iasst.projectGEMC.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
@Entity
public class ConsigneeDetails extends Timestamp{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deliveryDate;
	private boolean goodAcceptanceStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate cracGenDate;
	private int recieptQuantity;
	private int aceptedQuantity;
	private int rejectedQuantity;
	private String invoiceNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate invoiceDate;
	private float billAmount;
	@OneToOne
	private Contract contract;

	public ConsigneeDetails() {
		
	}

	public ConsigneeDetails(int id, LocalDate deliveryDate, boolean goodAcceptanceStatus,
			LocalDate cracGenDate, int recieptQuantity, int aceptedQuantity, int rejectedQuantity, String invoiceNo,
			LocalDate invoiceDate, float billAmount, Contract contract) {
		super();
		this.id = id;
		this.deliveryDate = deliveryDate;
		this.goodAcceptanceStatus = goodAcceptanceStatus;
		this.cracGenDate = cracGenDate;
		this.recieptQuantity = recieptQuantity;
		this.aceptedQuantity = aceptedQuantity;
		this.rejectedQuantity = rejectedQuantity;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.billAmount = billAmount;
		this.contract = contract;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public boolean isGoodAcceptanceStatus() {
		return goodAcceptanceStatus;
	}

	public void setGoodAcceptanceStatus(boolean goodAcceptanceStatus) {
		this.goodAcceptanceStatus = goodAcceptanceStatus;
	}

	public LocalDate getCracGenDate() {
		return cracGenDate;
	}

	public void setCracGenDate(LocalDate cracGenDate) {
		this.cracGenDate = cracGenDate;
	}

	public int getRecieptQuantity() {
		return recieptQuantity;
	}

	public void setRecieptQuantity(int recieptQuantity) {
		this.recieptQuantity = recieptQuantity;
	}

	public int getAceptedQuantity() {
		return aceptedQuantity;
	}

	public void setAceptedQuantity(int aceptedQuantity) {
		this.aceptedQuantity = aceptedQuantity;
	}

	public int getRejectedQuantity() {
		return rejectedQuantity;
	}

	public void setRejectedQuantity(int rejectedQuantity) {
		this.rejectedQuantity = rejectedQuantity;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public float getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@Override
	public String toString() {
		return "ConsigneeDetails [id=" + id +", deliveryDate="
				+ deliveryDate + ", goodAcceptanceStatus=" + goodAcceptanceStatus + ", cracGenDate=" + cracGenDate
				+ ", recieptQuantity=" + recieptQuantity + ", aceptedQuantity=" + aceptedQuantity
				+ ", rejectedQuantity=" + rejectedQuantity + ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate
				+ ", billAmount=" + billAmount + ", contract=" + contract + "]";
	}

	
}
