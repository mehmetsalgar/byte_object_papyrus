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
package org.salgar.byte_object.uml2;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.uml2.uml.UMLPackage;

public class Setup
	extends org.eclipse.xtend.typesystem.uml2.Setup {
	private static final String UML2_500_NS_URI = "http://www.eclipse.org/uml2/5.0.0/UML";

	@Override
	public void setStandardUML2Setup(boolean b) {
		super.setStandardUML2Setup(b);
		EPackage.Registry.INSTANCE.put(UML2_500_NS_URI, EPackage.Registry.INSTANCE.get(UMLPackage.eINSTANCE.getNsURI()));
	}
}
