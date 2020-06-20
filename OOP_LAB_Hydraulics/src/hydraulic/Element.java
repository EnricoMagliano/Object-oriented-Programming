package hydraulic;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public class Element {
	
	private String name;
	private Element uscita;
	
	public Element(String name){
		this.name = name;
	}

	
	public String getName(){
		return this.name;
	}
	
	
	public void connect(Element elem){
		uscita = elem;
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput(){
		
		return uscita;
	}
	
	public void Rsimulate(SimulationObserver observer, double flow){
		observer.notifyFlow("Element", this.getName(), flow, flow);
		this.getOutput().Rsimulate(observer, flow);
	}
	
	public void Rsimulate(SimulationObserverExt observer, double flow, boolean enableMaxFlowCheck){
		
	}
	
	public String layout(String disposizione){
		disposizione += " -> [" + this.getName() + "]Element";
		disposizione = this.getOutput().layout(disposizione);
		return disposizione;
	}
	
}
