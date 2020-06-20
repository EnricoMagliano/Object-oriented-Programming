package clinic;

import java.util.HashMap;
import java.util.Map;

public class Doctor {
	
	protected String ssn;
	protected String name;
	protected String surname;
	protected int badge;
	protected String specialization;
	
	protected Map<String, Patient> patients = new HashMap<>();
	
	public Doctor(String ssn, String name, String surname, int badge, String specialization) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.surname = surname;
		this.badge = badge;
		this.specialization = specialization;
	}

	public String getSsn() {
		return ssn;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getBadge() {
		return badge;
	}

	public String getSpecialization() {
		return specialization;
	}

	public Map<String, Patient> getPatients() {
		return patients;
	}
	
}
