/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM - Rational Software and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v0.5 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v05.html
 * 
 * Contributors:
 *     IBM - Rational Software - initial implementation
 ******************************************************************************/
package org.eclipse.cdt.internal.core.parser.scanner;


public class ScannerContextMacro implements IScannerContext
{
    private int macroOffset;
    private int macroLength;
    int position=0;
    int length;
    char [] reader;
    String macroName;
				
    /* (non-Javadoc)
     * @see org.eclipse.cdt.internal.core.parser.IScannerContext#initialize(Reader, String, int, IASTInclusion, int, int, int)
     */
	public ScannerContextMacro(String in, String macro, int mO, int mL)
	{
		macroName = macro;
		reader = in.toCharArray();
		length = in.length();
        macroOffset = mO;
        macroLength = mL;
	}
    
	public final String getContextName() {
		return macroName;
	}
	public int getChar() {
		if (position < length)
			return reader[position++];
		return -1;
	}
	
	public void close() {
		
	}
	
	public final void ungetChar(int c)
	{
		position--;
		// may want to assert that reader.charAt(position) == c
	}

    /* (non-Javadoc)
     * @see org.eclipse.cdt.internal.core.parser.IScannerContext#getMacroLength()
     */
    public final int getMacroLength() 
    {
        return macroLength;
    }

	/* (non-Javadoc)
     * @see org.eclipse.cdt.internal.core.parser.IScannerContext#getOffset()
     */
	public int getOffset()
	{
        // All the tokens generated by the macro expansion 
        // will have dimensions (offset and length) equal to the expanding symbol.
		return macroOffset;
	}

	/**
	 * Returns the kind.
	 * @return int
	 */
	public int getKind() {
		return IScannerContext.ContextKind.MACROEXPANSION;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append( "Macro "); //$NON-NLS-1$
		buffer.append( getContextName() );
		return buffer.toString();
	}
}
