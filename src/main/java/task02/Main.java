package task02;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> test = new MyArrayList<>();
        test.add("test1");
        test.add("test2");
//        test.add(null); // produce NullPointerException
        test.add("excess");
        test.add("test2");

        System.out.println(test.lastIndexOf(null)); // 2
        Iterator<String> nameIterator = test.iterator();
        nameIterator.forEachRemaining(name -> {
            if (name.equals("test2")) {
                name += "1";
                System.out.println("Found " + name); // Found test21 > Found test21
            }
            System.out.println(name); // test1 > test21 > excess > test21
        });
        System.out.println(test); // [test1, test2, excess, test2]
        test.sort(Comparator.reverseOrder());
        System.out.println(test); // [test2, test2, test1, excess]

    }
}
