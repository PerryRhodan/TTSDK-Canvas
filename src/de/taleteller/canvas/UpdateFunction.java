/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   UpdateFunction
 * 
 * Summary:
 *   
 * History:
 * 
 * Ideas:
 * 
 * Stephan Hogrefe, Edinburgh, 2018
 */
package de.taleteller.canvas;


public interface UpdateFunction {
	
	public abstract Integer apply(Integer t);
	
	public abstract Double apply(Double t);
}
