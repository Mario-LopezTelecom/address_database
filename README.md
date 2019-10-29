# Address Database important notes 
This source code corresponds to an assignment for a job interview. It consists of a contact database with a web and XML interfaces, validating the input data. Below is a report describing the development process.

### Run/Build instructions
The application is contained in the executable JAR file target "address-database-1.0.jar". It can be run from the project root by typing in CMD: ``java -jar target/address-database-1.0.jar``.

In addition, the Project Object Model (POM) for Maven is attached (pom.xml).
The application can also be run by typing in CMD ``mvn spring-boot:run`.

### Important mappings
As indicated in the assignment, my listing in the browser is mapped to http://localhost:8080/Lopez_Mario .
The database is expected at http://localhost:3066/exercise. This mapping can be changed by modifying "application.properties" in the source code.

The script for creating the database (and the "java" user, for the application to access the database) is "create_db.sql".
The "input.xml" file is provided as an example to test the application. It contains several valid and invalid contacts . It can be sent in a POST request with cURL as follows:

``curl -H "Content-Type:application/xml" -d @input.xml -X POST http://localhost:8080/Lopez_Mario`.

---

# Work report
Author: Mario López Martínez. <br/>
Date: 05/11/15.<br/>
Completion time: 6 hours.<br/>

First, I identified the following milestones in the exercise: 1) getting input from a web form, 2 ) validating the input data, 3) getting input from an XML file for single contact, 4) getting input from an XML file for multiple contacts, and 5) persisting contacts to a database. There are multiple ways to accomplish these milestones. Given the time constraint to do the exercise and that there are no other explicit design / operational requirements in the assignment, the criteria to select one implementation over another was simplicity and my previous knowledge. Since I have some basic notions of Spring, my solution is based on Spring Boot. In a real project, at the very least, an overview of the different solutions with their advantages / disadvantages should be conducted.

With regard to testing, , only manual testing has been performed, trying to cover all the possible corner cases of the inputs, specifically: blank fields, bad-formed e-mails, and not-present fields in the input XMLs. Again, in a real-life project, automatized tests (e.g. using JUnit) would be the best practice.

###### Getting input from a webform
I used as baseline the project documented in [1], which makes use of Thymeleaf for the view layer. Spring Boot provides auto-configuration support for the Thymeleaf template engine, as opposed to JSPs. In addition, JSPs do not work well with embedded containers [2, ch. 26.3.4], which I planned to use for simplicity.

###### Input data validation
For the validation of the form data, I decided to use Hibernate Validator instead of the Bean Validator API, simply because the former provides the @E-mail annotation, which validates well-formed e-mails without the need of defining any regular expression pattern.

###### Getting input from XML file (single contact)
To achieve this objective, it was enough to use JAXB annotations. Unlike the web form input, the object ResponseBinding is not available and I had to find an alternative way to validate a Contact object. After some research, I found the solution by obtaining an instance of the Validator class [3].
In addition, I had to map both the previous method for getting the form data and this method to the same URL. These overloaded methods get differentiated by the Content-Type of the POST requests.

###### Getting input from XML file (multiple contacts)
I could not find the way to bind a list of contacts in XML to a Java collection (if it is possible at all). Thus, I created a class representing a List of Contacts, and annotated it with JAXB. I iterated over that list, validating each Contact and saving to the DB.

###### Persistence to DB
I choose to manage persistency with the Java Persistence API (JPA). The challenge to solve here was the coexistence of JAXB and JPA: the contact_id attribute is needed for the contact to be persisted to the database, but no element in the XMLs should be binded to it. The solution was simply to annotate contact_id with @XmlTransient, and it will get ignored in XML binding.

Regarding the datatypes in the MySQL database:
* Since I expected a small number of contacts in the database, contact_id is selected as SMALLINT.
* The rest of the fields are defined as VARCHAR, since the length of the entires is likely to vary widely, even in the case of the telephone numbers (e.g., they may contain spaces, international prefixes, etc.).

### References
[1] Pivotal Software, “Validating Form Input”, Spring Guide. Available online: <http://spring.io/guides/gs/validating-form-input/> <br/>
[2] P. Webb, D. Syer, J. Long, S. Nicoll, R. Winch, A. Wilkinson, M. Overdijk, C. Dupuis, and S. Deleuze, “Spring Boot Reference Guide”, 1.2.7 Release. Available online: <http://docs.spring.io/spring-boot/docs/current/reference/pdf/spring-boot-reference.pdf> <br/>
[3] H. Ferentschik, and G. Morling, “Hibernate Validator. JSR 303 Reference Implementation. Reference Guide.”. 4.1.0.Final. Available online: <http://docs.jboss.org/hibernate/validator/4.1/reference/en-US/pdf/hibernate_validator_reference.pdf><br/>


