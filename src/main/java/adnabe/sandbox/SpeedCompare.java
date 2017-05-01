package adnabe.sandbox;

import adnabe.function.Function;
import adnabe.function.general.GeneralBinaryFunction;
import adnabe.function.real.RealImageFunction;
import adnabe.operation.real.binary.RealAdd;
import adnabe.pointset.HyperVolumePointSet;
import adnabe.pointset.PointSet;
import adnabe.pointset.PointSetIterator;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.BenchmarkHelper;

public class SpeedCompare {

	static final long[] DIMS = new long[]{5000,5000};
	
	static final Img< FloatType > imgA = ArrayImgs.floats( DIMS[0], DIMS[1] );
	static final Img< FloatType > imgB = ArrayImgs.floats( DIMS[0], DIMS[1] );
	static final Img< FloatType > imgC = ArrayImgs.floats( DIMS[0], DIMS[1] );

	private static void initImgs() {
		float i = 0;
		for (FloatType f : imgA) {
			f.set(i++);
		}
		i = 0;
		for (FloatType f : imgB) {
			f.set(i++);
		}
		for (FloatType f : imgC) {
			f.setZero();
		}
	}

	private static void print(int count) {
		Cursor<FloatType> cursor = imgC.cursor();
		for (int i = 0; i < count; i++) {
			System.out.print(" " + cursor.next().getRealFloat());
		}
		System.out.println();
	}
	private static void testCore() {
		Cursor<FloatType> cA = imgA.cursor();
		Cursor<FloatType> cB = imgB.cursor();
		Cursor<FloatType> cC = imgC.cursor();
		while (cA.hasNext()) {
			cA.fwd();
			cB.fwd();
			cC.fwd();
			float sum = cA.get().get() + cB.get().get();
			cC.get().set(sum);
		}
	}
	
	private static void testOps() {
		RealAdd<FloatType, FloatType, FloatType> op = new RealAdd<FloatType, FloatType, FloatType>();
		RealImageFunction<FloatType, FloatType> fA = new RealImageFunction<FloatType, FloatType>(imgA, new FloatType());
		RealImageFunction<FloatType, FloatType> fB = new RealImageFunction<FloatType, FloatType>(imgB, new FloatType());
		Function<long[],FloatType> func = new GeneralBinaryFunction<long[], FloatType, FloatType, FloatType>(fA, fB, op, new FloatType());
		RandomAccess<FloatType> accessor = imgC.randomAccess();
		FloatType val = new FloatType();
		PointSet ps = new HyperVolumePointSet(DIMS);
		PointSetIterator iter = ps.iterator();
		long[] pos;
		while (iter.hasNext()) {
			pos = iter.next();
			func.compute(pos, val);
			accessor.setPosition(pos);
			accessor.get().set(val);
		}
	}
	
	private static void testOpsExpr() {
		RealImageFunction<FloatType, FloatType> fA = new RealImageFunction<FloatType, FloatType>(imgA, new FloatType());
		RealImageFunction<FloatType, FloatType> fB = new RealImageFunction<FloatType, FloatType>(imgB, new FloatType());
		Function<long[],FloatType> func = new AddFunction(fA, fB);
		RandomAccess<FloatType> accessor = imgC.randomAccess();
		FloatType val = new FloatType();
		PointSet ps = new HyperVolumePointSet(DIMS);
		PointSetIterator iter = ps.iterator();
		long[] pos;
		// WOULD LOVE TO PROFILE THIS CODE AND SEE HOW MUCH TIME IS SPENT IN EACH LINE
		while (iter.hasNext()) {
			pos = iter.next();
			func.compute(pos, val);
			accessor.setPosition(pos);
			accessor.get().set(val);
		}
	}

	private static class AddFunction implements Function<long[],FloatType> {
		private final Function<long[],FloatType> f1;
		private final Function<long[],FloatType> f2;
		private final FloatType v1;
		private final FloatType v2;
		
		public AddFunction(Function<long[],FloatType> f1, Function<long[],FloatType> f2) {
			this.f1 = f1;
			this.f2 = f2;
			v1 = new FloatType();
			v2 = new FloatType();
		}
		
		public void compute(long[] input, FloatType output) {
			f1.compute(input, v1);
			f2.compute(input, v2);
			output.set(v1.getRealFloat() + v2.getRealFloat());
		}

		public FloatType createOutput() {
			return new FloatType();
		}

		public Function<long[], FloatType> copy() {
			return new AddFunction(f1.copy(), f2.copy());
		}
	}
	
	public static void main(String[] args) {
		initImgs();
		BenchmarkHelper.benchmarkAndPrint( 10, true, new Runnable()
		{
			public void run()
			{
				testCore();
			}
		});
		print(10);
		initImgs();
		BenchmarkHelper.benchmarkAndPrint( 10, true, new Runnable()
		{
			public void run()
			{
				testOps();
			}
		});
		print(10);
		initImgs();
		BenchmarkHelper.benchmarkAndPrint( 10, true, new Runnable()
		{
			public void run()
			{
				testOpsExpr();
			}
		});
		print(10);
		
	}
}
