package task02;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Integer[] src = {1, 5, 25, -4, 5, 1, 9, 25, 900, 25, 25, 900, 5, -67, 1000, 25};
        List<Integer> numbers = new ArrayList<>(Arrays.asList(src));

        HashMap<Integer, Integer> res = new HashMap<>();

        numbers.forEach (number -> {
            if (!res.containsKey(number)) {
                res.put(number, 1);
            } else {
                res.put(number, res.get(number) + 1);
            }
        });
        res.forEach((key, value) -> System.out.format("Number: %-7d Repeated: %d%n", key, value));
    }
}
