package diet;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import static java.util.stream.Collectors.*;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {
	
	protected SortedMap<String, Restaurant> restaurants = new TreeMap<>();
	protected ArrayList<User> users = new ArrayList<>();
	protected ArrayList<Order> orders = new ArrayList<>(); 
	
	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		this.restaurants.put(r.getName(), r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		return this.restaurants.keySet();
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User u = new User(firstName, lastName, email, phoneNumber);
		this.users.add(u);
		return u;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users(){
		//return this.users().stream().sorted().collect(toList());
		Collections.sort(this.users);
		return this.users;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Order o = new Order(user, restaurantName, h + ":" + m, this);
		this.orders.add(o);
		this.restaurants.get(restaurantName).addOrder(o);
		return o;
	}
	
	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		Collection<Restaurant> res_ope = new ArrayList<>();
		boolean cont = false;
		for(Restaurant res : this.restaurants.values()) {
			Iterator<String> iter = res.hours.iterator();
			while(iter.hasNext() && !cont) {
				String oraA = iter.next();
				String oraC;
				if(iter.hasNext()) {
					oraC = iter.next();
				}
				else {
					break;
				}
				if(oraA.compareTo(time) <= 0 && oraC.compareTo(time) > 0) {
					cont = true;
				}
			}
			if(cont) {
				res_ope.add(res);
			}
			cont = false;
		}
		return res_ope;
	}

	
	
}
