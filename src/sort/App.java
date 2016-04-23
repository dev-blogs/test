package sort;

public class App {
	public static void main(String [] args) {
		Sort sort = new BubbleSort();
		sort.sort();
		sort.print();
		sort = new SelectSort();
		sort.sort();
		sort.print();
		sort = new InsertSort();
		sort.sort();
		sort.print();
	}
}