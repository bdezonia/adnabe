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

package adnabe.function.real;

import adnabe.function.Function;
import net.imglib2.type.numeric.RealType;

/**
 * This class facilitates the computation of a trimmed mean of another function
 * over a region. A trimmed mean is a mean calculated from a sorted distribution
 * where the outermost (perhaps outlier) values are not included in the
 * calculation. The number of values to trim from each end is specified in the
 * constructor.
 * 
 * @author Barry DeZonia
 */
public class RealTrimmedMeanFunction<T extends RealType<T>> extends
	AbstractRealStatFunction<T>
{
	// -- instance variables --
	
	private final int halfTrimSize;
	
	// -- constructor --
	
	/**
	 * Constructor
	 * 
	 * @param otherFunc The other function to pull data values from
	 * @param halfTrimSize The number of values to trim from each end when
	 *          calculating the final value.
	 */
	public RealTrimmedMeanFunction(Function<long[],T> otherFunc, int halfTrimSize)
	{
		super(otherFunc);
		this.halfTrimSize = halfTrimSize;
	}

	// -- abstract method overrides

	@Override
	protected double value(StatCalculator<T> calc) {
		return calc.trimmedMean(halfTrimSize);
	}
	
	// -- Function methods --
	
	public RealTrimmedMeanFunction<T> copy() {
		return new RealTrimmedMeanFunction<T>(otherFunc.copy(), halfTrimSize);
	}

}
