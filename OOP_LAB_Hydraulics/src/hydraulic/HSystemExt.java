package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		String disposizione = "";
		for(Element e: elements) {
			if(e instanceof Source) {
				disposizione += e.layout(disposizione);
			}
		}
		return disposizione;
	}
	
	
	public void deleteElement(String name) {
		for(Element e : elements) {
			if(e.getName().equalsIgnoreCase(name)) {
				if(e instanceof Split || e instanceof Multisplit){
					e = null;
					return;
				}
				for(Element e1:elements) {
						if(e1.getOutput() == e) {
							e1.connect(e.getOutput());
							e = null;
							return;
						}
					
				}
				
			}
		}
		System.out.print("Non ci sono stringhe di nome " + name);
		return;
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		for(Element e: elements) {
			if(e instanceof Source) {
				((Source)e).Rsimulate(observer, enableMaxFlowCheck);
			}
		}
	}
	
}
