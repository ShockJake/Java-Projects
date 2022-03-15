import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collection;

public class App {
    public static void initializeCollection(Collection<Integer> c) {
        Date init_date_begin = new Date();
        for (int i = 0; i < 1000000; i++) {
            c.add((int) (Math.random() * 2000000000));
        }
        Date init_date_end = new Date();

        System.out.println("Initialized in time:   " + (init_date_end.getTime() - init_date_begin.getTime()) + " [ms]");
    }

    static String g = "===============================\n";

    public static void testCollection(Collection<Integer> c) {
        Date add_date_begin = new Date();
        c.add(123);
        Date add_date_end = new Date();

        Date remove_date_begin = new Date();
        c.remove(123);
        Date remove_date_end = new Date();

        Date contains_date_begin = new Date();
        c.contains((int) (Math.random() * 2000000000));
        Date contains_date_end = new Date();

        Date toArray_date_begin = new Date();
        c.toArray();
        Date toArray_date_end = new Date();

        System.out.println(
                "Time of add():\t\t" + (add_date_end.getTime() - add_date_begin.getTime()) + " [ms]\n");
        System.out.println(
                "Time of remove():\t" + (remove_date_end.getTime() - remove_date_begin.getTime()) + " [ms]\n");
        System.out.println(
                "Time of contains():\t" + (contains_date_end.getTime() - contains_date_begin.getTime()) + " [ms]\n");
        System.out.println(
                "Time of toAray():\t" + (toArray_date_end.getTime() - toArray_date_begin.getTime()) + " [ms]\n" + g);
    }

    public static void test(Collection<Integer> c) {
        App.initializeCollection(c);
        App.testCollection(c);
    }

    public static void main(String[] args) throws Exception {

        ArrayList<Integer> al = new ArrayList<>();
        HashSet<Integer> hs = new HashSet<>();
        LinkedList<Integer> ll = new LinkedList<>();
        Stack<Integer> st = new Stack<>();
        Vector<Integer> v = new Vector<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        TreeSet<Integer> ts = new TreeSet<>();

        Thread.sleep(15000);

        System.out.println("*** Test ArrayList ***");
        App.test(al);
        System.gc();
        Thread.sleep(15000);

        System.out.println("*** Test HashSet ***");
        App.test(hs);
        System.gc();
        Thread.sleep(15000);

        System.out.println("*** Test LinkedList ***");
        App.test(ll);
        System.gc();
        Thread.sleep(15000);

        System.out.println("*** Test Stack ***");
        App.test(st);
        System.gc();
        Thread.sleep(15000);

        System.out.println("*** Test Vector ***");
        App.test(v);
        System.gc();
        Thread.sleep(15000);

        System.out.println("*** Test Priority Queue ***");
        App.test(pq);
        System.gc();
        Thread.sleep(15000);

        System.out.println("*** Test Tree Set ***");
        App.test(ts);
        System.gc();
        Thread.sleep(15000);
    }
}
