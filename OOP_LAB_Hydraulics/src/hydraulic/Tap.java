package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends ElementExt {

	private Boolean open = true;
	public Tap(String name) {
		super(name);
		//TODO: complete
	}
	
	/**
	 * Defines whether the tap is open or closed.
	 * 
	 * @param open  opening level
	 */
	public void setOpen(boolean open){
		this.open = open;
	}
	public Boolean getOpen(){
		return this.open;
	}
	
	public void Rsimulate(SimulationObserver observer, double flow){
		if(this.open) {
			observer.notifyFlow("Tap", this.getName(), flow, flow);
			this.getOutput().Rsimulate(observer, flow);
			return;
		}
		observer.notifyFlow("Tap", this.getName(), flow, 0.0);
		this.getOutput().Rsimulate(observer, 0.0);
	}
	
	public void Rsimulate(SimulationObserverExt observer, double flow, boolean enableMaxFlowCheck){
		if(enableMaxFlowCheck && this.max_flow != -1 && this.max_flow <flow) {
			observer.notifyFlowError("Tap", this.getName(), flow, this.max_flow);
		}
		if(this.open) {
			observer.notifyFlow("Tap", this.getName(), flow, flow);
			this.getOutput().Rsimulate(observer, flow, enableMaxFlowCheck);
			return;
		}
		observer.notifyFlow("Tap", this.getName(), flow, 0.0);
		this.getOutput().Rsimulate(observer, 0.0, enableMaxFlowCheck);
	}
	
	public String layout(String disposizione){
		disposizione += " -> [" + this.getName() + "]Tap";
		disposizione = this.getOutput().layout(disposizione);
		return disposizione;
	}

}
