package diet;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents an order in the take-away system
 */
public class Order {
 
	protected User user;
	protected String restaurant;
	protected String ora_consegna;
	protected Takeaway takeaway;
	protected OrderStatus status;
	protected PaymentMethod payment;
	protected SortedMap<String, Integer> menus = new TreeMap<>();
	
	public Order(User user, String restaurant, String ora_consegna, Takeaway takeaway) {
		super();
		Iterator<String> iter = takeaway.restaurants.get(restaurant).hours.iterator();
		while(iter.hasNext()) {
			String oraA = iter.next();
			String oraC;
			if(iter.hasNext()) {
				oraC = iter.next();
			}
			else {
				break;
			}
			if(oraA.compareTo(ora_consegna) > 0) {
				ora_consegna = oraA;
			}
		}
		this.user = user;
		this.restaurant = restaurant;
		this.ora_consegna = ora_consegna;
		this.takeaway = takeaway;
		this.status = OrderStatus.ORDERED;
		this.payment = PaymentMethod.CASH;
	}
	
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
		
	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		this.payment = method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.payment;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		this.status = newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return this.status;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
		if(this.menus.get(menu) == null) {
			this.menus.put(menu, quantity);
		}else{
			this.menus.replace(menu, this.menus.get(menu)+ quantity);
		}
		return this;
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		String s = this.restaurant + ", " + this.user.name + " " + this.user.surname + " : DELIVERY(" + this.ora_consegna + "):\n";
		for(String m : this.menus.keySet()) {
			s += "\t" + m + "->" + this.menus.get(m);
		}
		return s;
	}
	
}
