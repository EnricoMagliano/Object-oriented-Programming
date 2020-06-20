package clinic;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {

	protected Map<String, Patient> patients = new HashMap<>();
	protected Map<Integer, Doctor> doctors = new HashMap<>();
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		Patient p = new Patient(ssn, first, last);
		this.patients.put(ssn, p);
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if(!this.patients.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		Patient p = this.patients.get(ssn);
		return p.surname + " " + p.name + " (" + p.ssn + ")";
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		this.doctors.put(docID, new Doctor(ssn, first, last, docID,specialization));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if(!this.doctors.containsKey(docID)) {
			throw new NoSuchDoctor();
		}
		Doctor d = this.doctors.get(docID);
		return d.surname + " " + d.name + " (" + d.ssn + ") [" + d.badge + "] " + d.specialization;
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if(!this.doctors.containsKey(docID)) {
			throw new NoSuchDoctor();
		}
		if(!this.patients.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		if(this.patients.get(ssn).doctor != null) {
			return;
		}
		this.patients.get(ssn).doctor = this.doctors.get(docID);
		this.doctors.get(docID).patients.put(ssn, this.patients.get(ssn));
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if(!this.patients.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		if(this.patients.get(ssn).doctor == null) {
			throw new NoSuchDoctor();
		}
		return this.patients.get(ssn).doctor.badge;
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if(!this.doctors.containsKey(id)) {
			throw new NoSuchDoctor();
		}
		return this.doctors.get(id).patients.keySet();
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		try (BufferedReader in = new BufferedReader(reader)) {
			List<String> lines = in.lines().collect(toList());
			for(String s : lines) {
				Scanner scan = new Scanner(s);
				scan.useDelimiter(";");
				try {
					if(scan.next() == "M") {
						int badge = scan.nextInt();
						String name = scan.next();
						String surname = scan.next();
						String ssn = scan.next();
						String spec = scan.next();
						Doctor d = new Doctor(ssn.trim(), name.trim(), surname.trim(), badge, spec.trim());
						this.doctors.put(badge, d);
					}else {
						String name = scan.next();
						String surname = scan.next();
						String ssn = scan.next();
						Patient p = new Patient(ssn.trim(), name.trim(), surname.trim());
						this.patients.put(ssn, p);
					}
				}catch(Exception e) {
					System.out.print(e.getMessage());
				}finally {
					scan.close();
				}
				
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}


	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		return this.doctors.values().stream().filter(d->d.patients.isEmpty()).sorted((d1, d2)->{
			if(d1.surname.compareTo(d2.surname) == 0) {
				return d1.name.compareTo(d2.name);
			}
			return d1.surname.compareTo(d2.surname);
		}).collect(mapping(Doctor::getBadge, toList()));
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Double avgPatient() {
		return this.doctors.values().stream().mapToInt(d->d.patients.size()).average().getAsDouble();
	}
	
	public Collection<Integer> busyDoctors(){
		return this.doctors.values().stream().filter(d->d.patients.size() > this.avgPatient()).collect(mapping(Doctor::getBadge, toList()));
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		return this.doctors.values().stream().sorted((d1, d2)->{
			if(d1.patients.size() == d2.patients.size()) {
				return 0;
			}else {
			     if(d1.patients.size()< d2.patients.size())
			    	 return -1;
			}
			return 1;
		}).collect(mapping(d->String.format("%3d : %d %s %s", d.patients.size(), d.badge, d.surname, d.name), toList()));
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
	    return  this.doctors.values().stream().collect(toMap(Doctor::getSpecialization, d->d.patients.size(), (v1,v2)->v1+v2, HashMap::new)).entrySet().stream().sorted((e1,e2)->{
			if(e1.getValue() == e2.getValue()) {
				return e1.getKey().compareTo(e2.getKey());
			}
			return e2.getValue().compareTo(e1.getValue());
		}).collect(mapping(e->String.format("%3d - %s", ((Entry<String, Integer>) e).getValue(), ((Entry<String, Integer>) e).getKey()), toList()));
		
	    //Senza ordinamento sul numero di pazienti per specializzazione
		/*Map<String, Integer> m = this.doctors.values().stream().collect(toMap(Doctor::getSpecialization, d->d.patients.size(), (v1,v2)->v1+v2, TreeMap::new));
	    Collection<String> col = new LinkedList<>();
		for(String s : m.keySet()) {
			col.add(String.format("%3d - %s", m.get(s), s));
		}
		return col;*/
	}
	
}
