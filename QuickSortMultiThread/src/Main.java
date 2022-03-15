import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void fillArray(int[] arr) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(101);
        }
    }
    private static void testMultiThread(int[] arr) {
        ExecutorService es = Executors.newFixedThreadPool(15);
        es.submit(new Runnable() {
            @Override
            public void run() {
                QuickSortSingleThread.quickSort(arr, 0, arr.length - 1);
            }
        });
        es.shutdown();
    }

    private static void testSingleThread(int[] arr) {
        QuickSortSingleThread.quickSort(arr, 0, arr.length - 1);
    }

    private static void test() {
        int[] arr1 = new int[20000];
        int[] arr2 = new int[20000];

        fillArray(arr1);
        arr2 = arr1.clone();
        Date beginDate = null;
        Date endDate = new Date();

        beginDate = new Date();
        testSingleThread(arr1);
        endDate = new Date();
        System.out.print("SingleThread - " + (endDate.getTime() - beginDate.getTime()));

        beginDate = new Date();
        testMultiThread(arr2);
        endDate = new Date();
        System.out.println("  MultiThread - " + (endDate.getTime() - beginDate.getTime()));
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            test();
            Thread.sleep(3000);
            System.gc();
        }
    }
}

class QuickSortSingleThread {

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static int partition(int[] arr, int low, int high) {
        int pivote = arr[high];
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivote) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {

            int partion_index = partition(arr, low, high);
            quickSort(arr, low, partion_index - 1);
            quickSort(arr, partion_index + 1, high);

        }
    }
}