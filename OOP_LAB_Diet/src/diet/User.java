package diet;

/**
 * Represent a take-away system user
 *  
 */
public class User implements Comparable<User>{
	
	protected String name;
	protected String surname;
	protected String email;
	protected String phone;
	
	public User(String name, String surname, String email, String phone) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
	}
	
	/**
	 * get user's last name
	 * @return last name
	 */
	public String getLastName() {
		return this.surname;
	}
	
	/**
	 * get user's first name
	 * @return first name
	 */
	public String getFirstName() {
		return this.name;
	}
	
	/**
	 * get user's email
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * get user's phone number
	 * @return  phone number
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * change user's email
	 * @param email new email
	 */
	public void SetEmail(String email) {
		this.email = email;
	}
	
	/**
	 * change user's phone number
	 * @param phone new phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int compareTo(User o) {
		int r = this.surname.compareTo(o.surname);
		if(r == 0) {
			r = this.name.compareTo(o.name);
		}
		return r;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.surname;
	}
	
}
