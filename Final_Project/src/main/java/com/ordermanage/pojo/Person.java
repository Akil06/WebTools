package com.ordermanage.pojo;

import javax.persistence.Column;
//table per subclass
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="person_table")
@Inheritance(strategy=InheritanceType.JOINED)
public class Person {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  //similar to NATIVE akin to xml def - AI in DB
	@Column(name="personID", unique=true, nullable=false)
	private long personID;
	
	@Column(name="firstName") //name is not mandatory
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	public Person(){
		
	}
	
	public long getPersonID() {
		return personID;
	}

	public void setPersonID(long personID) {
		this.personID = personID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
}
