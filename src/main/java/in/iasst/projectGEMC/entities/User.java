package in.iasst.projectGEMC.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User extends Timestamp{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;
	private String userName;
	private String password;
	private String name;
	private String role;
	@OneToMany(mappedBy = "buyerName")
	private List<Contract> boughtContracts;
	@OneToMany(mappedBy = "consigneeName")
	private List<Contract> receivedContracts;
	@OneToMany(mappedBy = "paymentAuth")
	private List<Contract> paidContracts;

	public User() {
		super();
	}

	public User(int userid, String userName, String password, String name, String role, List<Contract> boughtContracts,
		List<Contract> receivedContracts, List<Contract> paidContracts) {
	super();
	this.userid = userid;
	this.userName = userName;
	this.password = password;
	this.name = name;
	this.role = role;
	this.boughtContracts = boughtContracts;
	this.receivedContracts = receivedContracts;
	this.paidContracts = paidContracts;
}



	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Contract> getBoughtContracts() {
		return boughtContracts;
	}

	public void setBoughtContracts(List<Contract> boughtContracts) {
		this.boughtContracts = boughtContracts;
	}

	public List<Contract> getReceivedContracts() {
		return receivedContracts;
	}

	public void setReceivedContracts(List<Contract> receivedContracts) {
		this.receivedContracts = receivedContracts;
	}

	public List<Contract> getPaidContracts() {
		return paidContracts;
	}

	public void setPaidContracts(List<Contract> paidContracts) {
		this.paidContracts = paidContracts;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", userName=" + userName + ", password=" + password + ", name=" + name
				+ ", role=" + role + ", boughtContracts=" + boughtContracts + ", receivedContracts=" + receivedContracts
				+ ", paidContracts=" + paidContracts + "]";
	}	

}
