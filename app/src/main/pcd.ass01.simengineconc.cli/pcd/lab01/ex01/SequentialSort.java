package pcd.lab01.ex01;

import java.util.*;

public class SequentialSort {

	static final int VECTOR_SIZE = 200000000;
	static final int NUM_THREAD = 16;
	public static void main(String[] args) {
	
		log("Generating array...");
		long[] v = genArray(VECTOR_SIZE);
		
		log("Array generated.");
		log("Sorting (" + VECTOR_SIZE + " elements)...");
	
		long t0 = System.nanoTime();
		
		List<Thread> li = new ArrayList<>();

		for(int i=0; i<NUM_THREAD; i++){
			final int index=i;
			Thread th=new Thread(new Runnable() {
				@Override
				public void run(){
					Arrays.sort(v, index*(v.length/NUM_THREAD), (index+1)*(v.length/NUM_THREAD) - 1);
				}
			});
			li.add(th);
		}

		li.forEach(th -> th.start());
		
		li.forEach(th -> {
			try {
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		if(NUM_THREAD>1){
			Arrays.sort(v, 0, v.length);
		}

		long t1 = System.nanoTime();
		log("Done. Time elapsed: " + ((t1 - t0) / 1000000) + " ms");
		
		// dumpArray(v);
	}


	private static long[] genArray(int n) {
		Random gen = new Random(System.currentTimeMillis());
		long v[] = new long[n];
		for (int i = 0; i < v.length; i++) {
			v[i] = gen.nextLong();
		}
		return v;
	}

	private static void dumpArray(long[] v) {
		for (long l:  v) {
			System.out.print(l + " ");
		}
	}

	private static void log(String msg) {
		System.out.println(msg);
	}
}
