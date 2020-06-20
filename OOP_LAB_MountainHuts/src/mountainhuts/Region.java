package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collector.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */

public class Region {

	public class AltitudeRange{
		protected int min;
		protected int max;
		
		public AltitudeRange(int min, int max) {
			this.min = min;
			this.max = max;
		}
	}
	
	protected String name;
	protected HashSet<AltitudeRange> altiduteRanges = new HashSet<>();
	protected TreeMap<String, Municipality> municipality = new TreeMap<>();
	protected TreeMap<String, MountainHut> mountainhuts = new TreeMap<>();
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for(String s : ranges) {
			String[] ss = s.split("-");
			this.altiduteRanges.add(new AltitudeRange(Integer.parseInt(ss[0]), Integer.parseInt(ss[1])));
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for(AltitudeRange ar: this.altiduteRanges) {
			if(ar.min <= altitude && ar.max >= altitude) {
				return ar.min + "-" + ar.max;
			}
		}
		return "0-INF";
	}
	
	public Collection<AltitudeRange> altitudeRanges(){
		return this.altiduteRanges;
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if(this.municipality.containsKey(name)) {
			return this.municipality.get(name);
		}
		Municipality m = new Municipality(name, province, altitude);
		this.municipality.put(name, m);
		return m;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return this.municipality.values();
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber, Municipality municipality) {
		if(this.mountainhuts.containsKey(name)) {
			return this.mountainhuts.get(name);
		}
		MountainHut m = new MountainHut(name, null, category, bedsNumber, municipality);
		this.mountainhuts.put(name, m);
		return m;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		if(this.mountainhuts.containsKey(name)) {
			return this.mountainhuts.get(name);
		}
		MountainHut m = new MountainHut(name, altitude, category, bedsNumber, municipality);
		this.mountainhuts.put(name, m);
		return m;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.mountainhuts.values();
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region r = new Region(name);
		List<String> data = readData(file);
		data.remove(0);
		for(String s : data) {
			Scanner scan = new Scanner(s);
			scan.useDelimiter(";");
			String province = scan.next();
			String municipality = scan.next();
			int munAlt = scan.nextInt();
			Municipality m = r.createOrGetMunicipality(municipality, province, munAlt);
			String nameMH = scan.next();
			Integer altitudeMH = null;
			try {
		        altitudeMH = scan.nextInt();
		    }catch( Exception e ) {}
			String catMH = scan.next();
			Integer bedNumMH = scan.nextInt();
			if(altitudeMH == null) {
				r.createOrGetMountainHut(nameMH, catMH, bedNumMH, m);
			}else {
				r.createOrGetMountainHut(nameMH, altitudeMH, catMH, bedNumMH, m);
			}
			scan.close();
		}
		return r;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return this.getMunicipalities().stream().collect(groupingBy(m->m.getProvince(), counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return this.getMunicipalities().stream().collect(groupingBy(Municipality::getProvince, groupingBy(Municipality::getName, counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return this.getMountainHuts().stream().collect(groupingBy(m->{
			if(m.getAltitude().isPresent()) {
				return (String)this.getAltitudeRange(m.getAltitude().get());
			}
			return (String)this.getAltitudeRange(m.municipality.altitude);
		}, counting()));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return this.getMountainHuts().stream().collect(groupingBy(mh->mh.municipality.province, summingInt(mh->mh.getBedsNumber())));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		/*Map<String, Optional<MountainHut>> sp = this.getMountainHuts().stream().collect(groupingBy(m->{
			if(m.getAltitude().isPresent()) {
				return (String)this.getAltitudeRange(m.getAltitude().get());
			}
			return (String)this.getAltitudeRange(m.municipality.altitude);
		}, maxBy((r1, r2)->r1.getBedsNumber().compareTo(r2.getBedsNumber()))));
		HashMap <String, Optional<Integer>> str = new HashMap<>();
		for(String s: sp.keySet()) {
			if(sp.get(s).isPresent()) {
				str.put(s, Optional.ofNullable(sp.get(s).get().getBedsNumber()));
			}else {
				str.put(s, Optional.ofNullable(null) );
			}
		}
		return str;*/
		return this.getMountainHuts().stream().collect(groupingBy(m->{
			if(m.getAltitude().isPresent()) {
				return (String)this.getAltitudeRange(m.getAltitude().get());
			}
			return (String)this.getAltitudeRange(m.municipality.altitude);
		}, mapping(MountainHut::getBedsNumber, maxBy(Comparator.naturalOrder()))));
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		return this.getMunicipalities().stream().collect(groupingBy(m->{			//Con Ordinamento sulla lista dei municipality
			long l = 0;
			for(MountainHut mh: mountainhuts.values()) {
				if(mh.municipality == m) {
					l++;
				}
			}
			return l;
		}, of(ArrayList<String>::new, (a, m)->a.add(m.getName()), (a, b)->{
				a.addAll(b);
				return a;
			}, a->{a.sort((b,c)->b.compareTo(c)); return a;})));
		
		//senza ordinamento sulla lista dei municipality
		//return this.getMountainHuts().stream().map(x->x.municipality.name).collect(groupingBy(x->x, counting())).entrySet().stream().collect(groupingBy(x->(long)x.getValue(), mapping(x->(String)x.getKey(), toList())));
	}

}
