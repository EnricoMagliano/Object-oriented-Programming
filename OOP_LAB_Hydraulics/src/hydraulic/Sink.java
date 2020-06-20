package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {

	
	public Sink(String name) {
		super(name);
		//TODO: complete
	}
	
	public void connect(Element elem){
		System.out.print("Non è possibile collegare un elemento in uscita ad un'oggetto di tipo sink");
	}
	
	public void Rsimulate(SimulationObserver observer, double flow){
		observer.notifyFlow("Sink", this.getName(), flow, SimulationObserver.NO_FLOW);
	}
	
	public String layout(String disposizione){
		disposizione += " -> [" + this.getName() + "]Sink *\n";
		return disposizione;
	}
	
	public void Rsimulate(SimulationObserverExt observer, double flow, boolean enableMaxFlowCheck){
		observer.notifyFlow("Sink", this.getName(), flow, SimulationObserverExt.NO_FLOW);
		if(enableMaxFlowCheck && this.max_flow != -1 && this.max_flow < flow) {
			observer.notifyFlowError("Sink", this.getName(), flow, this.max_flow);
		}
	}
}
