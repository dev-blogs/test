package test;

public class OrderedArray {
	private int[] array = new int[10];
	
	public void fill() {
		array[0] = 23;
		array[1] = 34;
		array[2] = 44;
		array[3] = 55;
		array[4] = 66;
		array[5] = 77;
		array[6] = 88;
		array[7] = 99;
		array[8] = 101;
		array[9] = 105;
	}
	
	public boolean find(int toBeSearched) {
		int start = 0;
		int end = array.length - 1;
		int index = (end + start)/2;
		boolean found = false;
		while (!found && (start <= end)) {
			if (toBeSearched > array[index]) {
				start = index + 1;
			} else if (toBeSearched < array[index]) {
				end = index - 1;
			} else {
				found = true;
			}
			index = (end + start)/2;
		}
		return found;
	}
	
	public static void main(String [] args) {
		OrderedArray orderedArray = new OrderedArray();
		orderedArray.fill();
		System.out.println(orderedArray.find(88));
	}
}