package sort;

public class InsertSort extends AbstractSort {
	@Override
	public void sort() {
		int temp = 0;
		for (int i = 1; i < a.length; i++) {
			temp = a[i];
			int j = i;
			while (j > 0 && a[j - 1] > temp) {	
				count++;
				a[j] = a[j - 1];				
				j--;
			}
			a[j] = temp;
		}		
	}
}