package contactBook;

import org.springframework.data.repository.CrudRepository;

/**
 * An interface for a repository that can store Contact objects
 * 
 * @author mlopmart
 *
 */
public interface ContactRepository extends CrudRepository<Contact, Integer> {
 
}
