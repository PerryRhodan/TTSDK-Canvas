/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   UpdateFunction_Quadratic
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

public class UpdateFunction_Quadratic implements UpdateFunction {

	
	public UpdateFunction_Quadratic() {
		
	}
	
	@Override
	public Integer apply(Integer t) {
		// input t is difference between current
		// position value and target value
		int qdr = (int) (Math.pow(t / 50.0, 2));
		if (qdr == 0) {
			if (t >= 0)
				return 1;
			else
				return -1;
		}
		if (qdr > 300) {
			if (t >= 0)
				qdr = 300;
			else
				qdr = -300;
		}
		if (t >= 0)
			return qdr;
		else
			return -qdr;
	}

	@Override
	public Double apply(Double t) {
		// input t is difference between current
		// position value and target value
		double qdr = (Math.pow(t / 50.0, 2));
		if (qdr == 0.0) {
			if (t >= 0.0)
				return 0.1;
			else
				return -0.1;
		}
		if (qdr > 300.0) {
			if (t >= 0.0)
				qdr = 300.0;
			else
				qdr = -300.0;
		}
		if (t >= 0.0)
			return qdr;
		else
			return -qdr;
	}

}
