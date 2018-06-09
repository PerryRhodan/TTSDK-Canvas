/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   UpdateFunction_Linear
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

public class UpdateFunction_Linear implements UpdateFunction {

	public UpdateFunction_Linear() {
		
	}
	
	@Override
	public Integer apply(Integer t) {
		// input t is difference between current
		// position value and target value
		
		int lin = (int) (t / 50.0);
		// if the above line rounds to 0, we still
		// want some non-zero value
		if (lin == 0) {
			if (t >= 0)
				return 1;
			else
				return -1;
		}

		return lin;
	}

	@Override
	public Double apply(Double t) {
		// input t is difference between current
		// position value and target value
		
		double lin = (t / 50.0);
		// if the above line rounds to 0, we still
		// want some non-zero value
		if (lin == 0.0) {
			if (t >= 0)
				return 0.1;
			else
				return -0.1;
		}

		return lin;
	}

}
