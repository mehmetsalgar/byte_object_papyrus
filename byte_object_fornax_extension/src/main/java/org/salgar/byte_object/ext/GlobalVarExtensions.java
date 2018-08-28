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

/**
 * Java helper class for Stdlib extension <tt>org::eclipse::xtend::util::stdlib::globalvar</tt>.
 */
public class GlobalVarExtensions {
	
	private static Map<String,Object> vars = new HashMap<String,Object>();
	
	public static void storeGlobalVar(String s, Object o) {
		if (o != null) {
		vars.put(s, o);
		} else {
			vars.remove(s);
		}
	}
	
	public static Object getGlobalVar(String s) {
		return vars.get(s);
	}

	public static Object removeGlobalVar(String s) {
		return vars.remove(s);
	}
	
	public static void clearGlobalVars () {
		vars.clear();
	}
	
}