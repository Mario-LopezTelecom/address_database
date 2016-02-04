package contactBook;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This WebController allows clients to input contact information 
 * either by a web form or by an XML file
 * 
 * @author mlopmart
 *
 */
@Controller
public class WebController extends WebMvcConfigurerAdapter {
	@Autowired
	private ContactRepository contactRepository;
	
	// Obtaining a Validator instance for validation of contacts obtained by XML 
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	// Registers a simple automated controller returning the "results" view/template
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("Lopez_Mario/results").setViewName("results");
    }

    // Receives GET request to /Lopez_Mario and returns an input form
    @RequestMapping(value="/Lopez_Mario", method=RequestMethod.GET)
    public String showForm(Contact contact) {
        return "form";
    }
    
    // Receives the POST request made when the user pushes the "Submit" button in the web form.
    // If any field is invalid, the user is informed and the rest of the input is not deleted.
    // If all fields are valid, the contact is stored in the database.
    @RequestMapping(value="/Lopez_Mario", method=RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public String checkPersonInfo(@Valid Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        contactRepository.save(contact);
        return "redirect:/Lopez_Mario/results";
    }
    
    // Receives the POST requests containing an XML file in the HTTP body. 
    // For each retrieved contact, if any field is invalid, the user is informed.
    // If all fields are valid for a given contact, the contact is stored in the database.
    // In any case, the user is informed in plain/text.
    @RequestMapping(value="/Lopez_Mario", method=RequestMethod.POST, consumes = {"application/xml"})
    @ResponseBody
    public String checkPersonInfoXML(@RequestBody CollectionOfContacts collectionOfContacts) {
    	List<Contact> listOfContacts = collectionOfContacts.getCollectionOfContacts(); 
    	StringBuilder response = new StringBuilder(); 
    	int numContacts = 0;
    	
    	if (listOfContacts == null) {	
    		return("Empty list");
    	}
    	
    	for (Contact contact : listOfContacts) {
    		numContacts++;
    		response.append("----------------------------- \n");
    		response.append("CONTACT #" + numContacts + "\n");
    		Set<ConstraintViolation<Contact>> constraintViolations = validator.validate(contact);			
    		
    		if (constraintViolations.size() == 0) {
    			contactRepository.save(contact);
    			response.append("Contact " + contact.getFirstname() + " " + contact.getLastname() + " saved \n");
    		}
    		
    		else {
    			response.append("Invalid contact \n");
    			// Get all the invalid properties and the cause of invalidation
    			for (Iterator<ConstraintViolation<Contact>> iterator = constraintViolations.iterator(); iterator.hasNext();) {
        			ConstraintViolation<Contact> constraintViolation = (ConstraintViolation<Contact>) iterator.next();
    				response.append(constraintViolation.getPropertyPath().toString() + " " + 
    								constraintViolation.getMessage() + "\n");
    			}
    		}
    	}
    	return(response.toString());
    }
}
