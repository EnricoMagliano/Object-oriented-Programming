package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends ElementExt {

	private final Integer MAX_USCITE = 2;
	protected Element[] uscite = new Element[MAX_USCITE];
	
	public Split(String name) {
		super(name);
		//TODO: complete
	}
    
	
    public Element[] getOutputs(){
        return uscite;
    }

    
	public void connect(Element elem, int noutput){
		if(noutput < 0 || noutput > 1) {
			System.out.print("Numero Uscite non valida");
			return;
		}
		this.uscite[noutput] = elem;
	}
	
	public void Rsimulate(SimulationObserver observer, double flow){
		observer.notifyFlow("Split", this.getName(), flow, flow/MAX_USCITE);
		for(Element e: uscite) {
			e.Rsimulate(observer, flow/MAX_USCITE);
		}
		
	}
	
	public void Rsimulate(SimulationObserverExt observer, double flow,  boolean enableMaxFlowCheck){
		if(enableMaxFlowCheck && this.max_flow != -1 && this.max_flow <flow) {
			observer.notifyFlowError("Split", this.getName(), flow, this.max_flow);
		}
		observer.notifyFlow("Split", this.getName(), flow, flow/MAX_USCITE);
		for(Element e: uscite) {
			e.Rsimulate(observer, flow/MAX_USCITE, enableMaxFlowCheck);
		}
		
	}
	
	public String layout(String disposizione){
		disposizione += " -> [" + this.getName() + "]Split";
		int n = disposizione.length();
		disposizione += " +";
		disposizione = this.uscite[0].layout(disposizione);
		for(int i = 1; i < MAX_USCITE; i++) {
			for(int j = 0; j < n; j++)
				disposizione += " ";
			disposizione += " |\n";
			for(int j = 0; j < n; j++)
				disposizione += " ";		
			disposizione += " +";
			disposizione = this.uscite[i].layout(disposizione);
		}
		return disposizione;
	}
}
