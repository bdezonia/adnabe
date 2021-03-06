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

package adnabe.pointset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import adnabe.pointset.HyperVolumePointSet;
import adnabe.pointset.PointSet;
import adnabe.pointset.PointSetUnion;

public class PointSetUnionTest {

	@Test
	public void test() {
		PointSet ps1 = new HyperVolumePointSet(new long[] { 0 }, new long[] { 1 });
		PointSet ps2 = new HyperVolumePointSet(new long[] { 7 }, new long[] { 8 });
		PointSet ps = new PointSetUnion(ps1, ps2);

		assertEquals(4, ps.size());
		assertEquals(0, ps.min(0));
		assertEquals(8, ps.max(0));
		assertEquals(0, ps.realMin(0), 0);
		assertEquals(8, ps.realMax(0), 0);
		assertEquals(9, ps.dimension(0));
		assertTrue(ps.includes(new long[]{0}));
		assertTrue(ps.includes(new long[]{1}));
		assertTrue(ps.includes(new long[]{7}));
		assertTrue(ps.includes(new long[]{8}));
		assertFalse(ps.includes(new long[]{-1}));
		assertFalse(ps.includes(new long[]{2}));
		assertFalse(ps.includes(new long[]{3}));
		assertFalse(ps.includes(new long[]{4}));
		assertFalse(ps.includes(new long[]{5}));
		assertFalse(ps.includes(new long[]{6}));
		assertFalse(ps.includes(new long[]{9}));
		
		ps.translate(new long[]{2});

		assertEquals(4, ps.size());
		assertEquals(2, ps.min(0));
		assertEquals(10, ps.max(0));
		assertEquals(2, ps.realMin(0), 0);
		assertEquals(10, ps.realMax(0), 0);
		assertEquals(9, ps.dimension(0));
		assertTrue(ps.includes(new long[]{2}));
		assertTrue(ps.includes(new long[]{3}));
		assertTrue(ps.includes(new long[]{9}));
		assertTrue(ps.includes(new long[]{10}));
		assertFalse(ps.includes(new long[]{1}));
		assertFalse(ps.includes(new long[]{4}));
		assertFalse(ps.includes(new long[]{5}));
		assertFalse(ps.includes(new long[]{6}));
		assertFalse(ps.includes(new long[]{7}));
		assertFalse(ps.includes(new long[]{8}));
		assertFalse(ps.includes(new long[]{11}));
	}

}
