package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	protected int num_uscite;
	protected double[] prop_uscite;
	
	public Multisplit(String name, int numOutput) {
		super(name);
		this.uscite = new Element[numOutput];
		this.prop_uscite = new double[numOutput];
		this.num_uscite = numOutput;
	}  
	
    public Element[] getOutputs(){
    	return super.getOutputs();
    	//return this.uscite;
    }

   
	public void connect(Element elem, int noutput){
		if(noutput < 0 || noutput >= this.num_uscite) {
			System.out.print("Numero Uscite non valida");
			return;
		}
		this.uscite[noutput] = elem;
	}
	
	public void setProportions(double... proportions) {
		int cont = 0;
		for(int i = 0; i < this.num_uscite; i++) {
			if(this.uscite[i] != null) {
				this.prop_uscite[i] = proportions[cont++];
			}
		}
	}
	
	public String layout(String disposizione){
		disposizione += " -> [" + this.getName() + "]Multisplit";
		int n = disposizione.length();
		disposizione += " +";
		disposizione = this.uscite[0].layout(disposizione);
		for(int i = 1; i < num_uscite; i++) {
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
	public void Rsimulate(SimulationObserver observer, double flow){
		observer.notifyFlow("Multisplit", this.getName(), flow, SimulationObserver.NO_FLOW);
		for(int i = 0; i < this.num_uscite; i++) {
			if(this.uscite[i] != null)
				this.uscite[i].Rsimulate(observer, flow*this.prop_uscite[i]);
		}
	}
	
	public void Rsimulate(SimulationObserverExt observer, double flow,  boolean enableMaxFlowCheck){
		if(enableMaxFlowCheck && this.max_flow != -1 && this.max_flow < flow) {
			observer.notifyFlowError("Multisplit", this.getName(), flow, this.max_flow);
		}
		observer.notifyFlow("Split", this.getName(), flow, SimulationObserverExt.NO_FLOW);
		for(int i = 0; i < this.num_uscite; i++) {
			if(this.uscite[i] != null)
			this.uscite[i].Rsimulate(observer, flow*this.prop_uscite[i], enableMaxFlowCheck);
		}
		
	}
}
