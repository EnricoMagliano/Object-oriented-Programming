package mountainhuts;

/**
 * Represents a municipality
 *
 */
public class Municipality {

	protected String name;
	protected String province;
	protected int altitude;
	
	
	public Municipality(String name, String province, int altitude) {
		this.name = name;
		this.province = province;
		this.altitude = altitude;
	}

	/**
	 * Name of the municipality.
	 * 
	 * Within a region the name of a municipality is unique
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Province of the municipality
	 * 
	 * @return province
	 */
	public String getProvince() {
		return this.province;
	}

	/**
	 * Altitude of the municipality
	 * 
	 * @return altitude
	 */
	public Integer getAltitude() {
		return this.altitude;
	}

}
