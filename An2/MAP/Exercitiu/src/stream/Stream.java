package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Stream {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("asf", "bcd", "asd", "bed", "bbb");

        System.out.println("a)");
        String res = list.stream()
                .filter(x -> x.startsWith("b"))
                .map(String::toUpperCase)
                .reduce("", (x, y) -> x + y);
        System.out.println(res);
        System.out.println();

        System.out.println("b)");
        list.stream()
                .filter(x -> x.startsWith("b"))
                .map(String::toUpperCase)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("c)");
        Optional<String> res2 = list.stream()
                .filter(x -> x.startsWith("b"))
                .map(String::toUpperCase)
                .reduce((x, y) -> x + y);

        if(!res2.isEmpty()){
            System.out.println(res2.get());
        }

        res2.ifPresent(System.out::println);
    }
}
