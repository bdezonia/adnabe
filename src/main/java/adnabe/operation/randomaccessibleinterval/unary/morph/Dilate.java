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

package adnabe.operation.randomaccessibleinterval.unary.morph;

import adnabe.operation.UnaryOperation;
import adnabe.types.ConnectedType;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.type.logic.BitType;

/**
 * @author Felix Schonenberger (University of Konstanz)
 * 
 * @param <I>
 */
public final class Dilate implements UnaryOperation< RandomAccessibleInterval< BitType >, RandomAccessibleInterval< BitType > >
{

	private final int m_neighbourhoodCount;

	private final ConnectedType m_type;

	private final BinaryOps m_binOps;

	private final OutOfBoundsFactory< BitType, RandomAccessibleInterval< BitType >> m_factory;

	public Dilate( ConnectedType type, OutOfBoundsFactory< BitType, RandomAccessibleInterval< BitType > > factory, final int neighbourhoodCount )
	{
		m_neighbourhoodCount = neighbourhoodCount;
		m_type = type;
		m_binOps = new BinaryOps( m_factory =  factory );
	}

	public RandomAccessibleInterval< BitType > compute( RandomAccessibleInterval< BitType > op, RandomAccessibleInterval< BitType > r )
	{
		m_binOps.dilate( m_type, r, op, m_neighbourhoodCount );
		return r;

	}

	public UnaryOperation< RandomAccessibleInterval< BitType >, RandomAccessibleInterval< BitType > > copy()
	{
		return new Dilate( m_type, m_factory, m_neighbourhoodCount );
	}
}
