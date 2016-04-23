package sort;

import java.util.Random;

public abstract class AbstractSort implements Sort {
	protected int[] a;
	private int size = 10;
	protected int count;
	
	public AbstractSort() {
		count = 0;
		/*Random randomGenerator = new Random();
		a = new int[size];		
		for (int i = 0; i < size; i++) {
			int randomInt = randomGenerator.nextInt(1000);
			a[i] = randomInt;
		}*/
		a = new int[size];
		a[0] = 159;
		a[1] = 17;
		a[2] = 124;
		a[3] = 663;
		a[4] = 32;
		a[5] = 566;
		a[6] = 173;
		a[7] = 716;
		a[8] = 573;
		a[9] = 634;
	}
	
	@Override
	public void print() {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.print("count = " + count);
		System.out.println();
	}
}