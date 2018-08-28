/*MIT License
 *
 *Copyright (c) 2013-present, , GmbH.
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in all
 *copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 */
package org.salgar.byte_object.ext;


/**
 * A Counter (integer) associated to any object.
 */
public class CounterExtensions extends AbstractStatefulExtensions<Integer> {

	/**
	 * resets the counter to '0'.
	 * 
	 * @param o The context object for this counter.  
	 * @return 0 
	 */
	public int counterReset(Object o) {
		return counterSet(o,0);
	}

	/**
	 * Increments the counter.
	 * 
	 * @param o The context object for this counter.  
	 * @return the incremented counter. 
	 */
	public int counterInc(Object o) {
		return counterSet(o,counterGet(o)+1);
	}

	/**
	 * Decrements the counter
	 * 
	 * @param o - context object  
	 * @return the decremented counter. 
	 */
	public int counterDec(Object o) {
		return counterSet(o,counterGet(o)-1);
	}
	
	/**
	 * Sets the counter
	 * 
	 * @param o - context object 
	 * @param value - the counter value to be set
	 */
	public int counterSet(Object o, int value) {
		set(o, value);
		return value;
	}

	/**
	 * Returns the counter. 
	 * 
	 * @param o - context object 
	 */
	public int counterGet(Object o) {
		return get(o);
	}
	
	@Override
	protected Integer getDefault(Object o) {
		return 0;
	}


}