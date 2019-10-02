package task02;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
//        List<String> list = new MyArrayList<>();
//        list.add("test1");
//        list.add("test4");
//        list.add(null);
//        list.add("kjb"); //3
//        list.add("test4");
//
//        //System.out.println(list.lastIndexOf(null));
//        Iterator<String> nameIterator = list.iterator();
//        nameIterator.forEachRemaining(name -> {
//            if (name.equals("test4")) {
//                name += "1";
//                System.out.println("Found " + name);
//                //return;
//            }
//            System.out.println(name);
//        });


        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.sort(null);
        //boolean b = list.containsAll(null);
        System.out.println(list);
    }
}
