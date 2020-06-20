package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	protected final Integer MAX_ELEMENT = 100;
	protected Element[] elements = new Element[MAX_ELEMENT];
	protected Integer n_elements = 0;
	
	public void addElement(Element elem){
		if(n_elements >= 100) {
			System.out.print("Non è possibile aggiungere nuovi elementi");
			return;
		}
		elements[n_elements++] = elem;
	}
	
	
	public Element[] getElements(){
		Element[] e = new Element[n_elements];
		System.arraycopy(this.elements, 0, e, 0, n_elements); 
		return e;
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for(Element e: elements) {
			if(e instanceof Source) {
				((Source)e).Rsimulate(observer);
			}
		}
	}
	
}
