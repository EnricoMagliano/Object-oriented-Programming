package clinic;

public class Patient {
	
	protected String ssn;
	protected String name;
	protected String surname;
	protected Doctor doctor;
	
	public Patient(String ssn, String name, String surname) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.surname = surname;
		this.doctor = null;
	}

	public String getSsn() {
		return this.ssn;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}
	
	public Doctor getDoctor() {
		return this.doctor;
	}

}
