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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtend.expression.ExecutionContext;
import org.eclipse.xtend.expression.IExecutionContextAware;
import org.eclipse.xtend.expression.Variable;

public class AbstractStatefulExtensions<T> implements IExecutionContextAware {

	protected T set(Object o, T value) {
		T previous = get(o);
		getVars().put(o, value);
		return previous;
	}
	
	protected T get(Object o) {
		Map<Object, T> vars = getVars();
		T value = vars.get(o);
		if (value == null) {
			return getDefault(o);
		}
		return value;
	}
	
	protected T getDefault(Object o) {
		return null;
	}

	@SuppressWarnings("unchecked")
	protected Map<Object, T> getVars() {
		Map<String, Variable> vars = exeCtx.getGlobalVariables();
		Variable variable = vars.get(getClass().getName());
		if (variable == null) {
			variable = new Variable(getClass().getName(), newMap());
			vars.put(getKey(), variable);
		}
		return (Map<Object, T>) variable.getValue();
	}

	protected Map<Object, T> newMap() {
		return new HashMap<Object, T>();
	}

	protected String getKey() {
		return getClass().getName();
	}
	
	protected ExecutionContext exeCtx;

	public void setExecutionContext(ExecutionContext ctx) {
		this.exeCtx = ctx;
	}
	
}