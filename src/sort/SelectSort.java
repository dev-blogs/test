package sort;

public class SelectSort extends AbstractSort {
	@Override
	public void sort() {
		for (int i = 0; i < a.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < a[min]) {
					min = j;
				}
			}
			int buffer = a[i];
			a[i] = a[min];
			a[min] = buffer;
			count++;
		}
	}
}