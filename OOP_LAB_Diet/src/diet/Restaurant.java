package diet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	
	public String name;
	public Food food;
	protected List<String> hours;
	protected Map<String, Menu> menus = new HashMap<>();
	protected ArrayList<Order> orders = new ArrayList<>();
	
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.name = name;
		this.food = food;
	}
	
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	public void addOrder(Order o) {
		this.orders.add(o);
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		this.hours = Arrays.asList(hm);
	}
	
	public Menu getMenu(String name) {
		return this.menus.get(name);
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu m = new Menu(name, this.food);
		this.menus.put(name, m);
		return m;
	}

	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		String s = "";
		Iterator<Order> iter = this.orders.stream().filter(o->o.status.equals(status)).sorted((u1, u2)->{
		if(u1.user.getFirstName().compareTo(u2.user.getFirstName()) > 0) {
			return 1;	
		}
		if(u1.user.getFirstName().compareTo(u2.user.getFirstName()) < 0) {
			return -1;	
		}
		return u1.ora_consegna.compareTo(u2.ora_consegna);
		}).collect(toList()).iterator();
		while(iter.hasNext()) {
			Order o = iter.next();
			s += o.restaurant + ", " + o.user.getFirstName() + " " + o.user.getLastName() + " : (" + o.ora_consegna + "):\n";
			for(String m : o.menus.keySet()) {
				s += "\t" + m + "->" + o.menus.get(m) + "\n";
			}
		}
		return s;
	}
}
