package sort;

public class BubbleSort extends AbstractSort {
	@Override
	public void sort() {
		for (int i = a.length - 1; i >= 0; i--) {
			for (int j = 0; j <= i - 1; j++) {
				if (a[j] > a[j + 1]) {
					count++;
					int buffer = a[j];
					a[j] = a[j + 1];
					a[j + 1] = buffer;
				}
			}
		}
	}
}