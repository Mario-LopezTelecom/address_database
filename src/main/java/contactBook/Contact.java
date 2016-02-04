package contactBook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * A simple object representing a contact in the Address Database.
 * 
 * @author mlopmart
 *
 */
@Entity
@Table(name = "contact")
@XmlRootElement(name = "contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact {
	@Id
	@GeneratedValue
	@XmlTransient
	private int contact_id;
	@Column
	@NotBlank
	@NotNull
	private String firstname;
	@Column
	@NotBlank
	@NotNull
	private String lastname;
	@Column
	private String address;
	@Column
	@NotBlank
	@NotNull
	@Email
	private String email;
	@Column
	private String phone;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		// This method is only used for debugging purposes
		return "First name: " + this.firstname + "\n" +
			   "Last name: " + this.lastname + "\n" +
				"Address: " + this.address + "\n" +
				"E-mail: " + this.email + "\n" +
				"Phone: " + this.phone + "\n";
	}
}
