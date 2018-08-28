
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

import org.eclipse.emf.common.util.EList;

public class ByteObjectExtensions {
	public static int findOffset(String type) {
		if("java.lang.Integer".equals(type)) {
			return 4;
		} else if("java.lang.Long".equals(type)) {
			return 8;
		} else if("java.util.Date".equals(type)) {
			return 8;
		} else if("java.lang.Boolean".equals(type)) {
			return 1;
		} else if("java.lang.String".equals(type)) {
			return 4;
		} else if("java.util.Map".equals(type)) {
			return 4;
		} else if("java.util.Set".equals(type)) {
			return 4;
		}  
		else {
			return 4;
		}
	}
	
	public static Boolean isEnum(Object type) {
		if(type instanceof org.eclipse.uml2.uml.Enumeration) {
			return true;
		}
		return false;
	}
	
	public static void checkQualified(org.eclipse.uml2.uml.Association association) {
		EList<org.eclipse.uml2.uml.Classifier> qualifiers = association.getRedefinedClassifiers();
		
		if(qualifiers.size() > 0) {
			System.out.println("What we have here!");
		}
	}
	
	public static org.eclipse.uml2.uml.Property getMapKey(org.eclipse.uml2.uml.Property mapKey) {
		EList<org.eclipse.uml2.uml.Property> properties = mapKey.getRedefinedProperties();
		
		if(properties.size() > 0) {
			return properties.get(0);
		}
		
		return null;
	}
}