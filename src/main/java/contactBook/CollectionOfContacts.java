package contactBook;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * A wrapper object in order to parse a collection 
 * of contacts by using JAXB annotations
 * 
 * @author mlopmart
 */

@XmlRootElement(name = "collectionOfContacts")
@XmlAccessorType (XmlAccessType.FIELD)
public class CollectionOfContacts {
	@XmlElement(name = "contact")
	private List<Contact> collectionOfContacts;
	
	public List<Contact> getCollectionOfContacts() {
		return collectionOfContacts;
	}
	public void setCollectionOfContacts(List<Contact> collectionOfContacts) {
		this.collectionOfContacts = collectionOfContacts;
	}
}
