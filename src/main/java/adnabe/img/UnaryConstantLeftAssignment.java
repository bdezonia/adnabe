//Adnabe: a library derived from Imglib2's original imglib-ops project

/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2013 Stephan Preibisch, Tobias Pietzsch, Barry DeZonia,
 * Stephan Saalfeld, Albert Cardona, Curtis Rueden, Christian Dietz, Jean-Yves
 * Tinevez, Johannes Schindelin, Lee Kamentsky, Larry Lindsey, Grant Harris,
 * Mark Hiner, Aivar Grislis, Martin Horn, Nick Perry, Michael Zinsmaier,
 * Steffen Jaensch, Jan Funke, Mark Longair, and Dimiter Prodanov.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package adnabe.img;

import adnabe.operation.BinaryOperation;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.type.numeric.RealType;

/**
 * @author Christian Dietz (University of Konstanz)
 */
public class UnaryConstantLeftAssignment< V extends RealType< V >, T extends RealType< T >, O extends RealType< O >> implements BinaryOperation< V, IterableInterval< T >, IterableInterval< O >>
{

	private BinaryOperation< V, T, O > m_op;

	public UnaryConstantLeftAssignment( BinaryOperation< V, T, O > op )
	{
		m_op = op;
	}

	public IterableInterval< O > compute( V constant, IterableInterval< T > input, IterableInterval< O > output )
	{

		if ( input.iterationOrder().equals( output.iterationOrder() ) ) { throw new IllegalArgumentException( "Intervals are not compatible" ); }

		Cursor< T > inCursor = input.cursor();
		Cursor< O > outCursor = output.cursor();

		while ( inCursor.hasNext() && outCursor.hasNext() )
		{
			inCursor.fwd();
			outCursor.fwd();
			m_op.compute( constant, inCursor.get(), outCursor.get() );
		}

		return output;
	}

	public BinaryOperation< V, IterableInterval< T >, IterableInterval< O >> copy()
	{
		return new UnaryConstantLeftAssignment< V, T, O >( m_op );
	}

}
