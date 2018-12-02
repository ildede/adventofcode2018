import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Day2part1 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        final URL input = Day2part1.class.getClassLoader().getResource("day2/input");
        Stream<String> lines = (input != null) ? Files.lines(Paths.get(input.toURI())) : Stream.empty();

        AtomicLong twoChars = new AtomicLong(0);
        AtomicLong threeChars = new AtomicLong(0);

        lines.forEach(s -> {
            HashMap<Character, Integer> map = new HashMap<>();
            var value = 0;

            for (int i=0; i < s.length(); i++){
                if (map.containsKey(s.charAt(i))) {
                    value = map.get(s.charAt(i));
                    value ++;
                    map.put(s.charAt(i),value);
                } else {
                    map.put(s.charAt(i),1);
                }
            }

            final Collection<Integer> values = map.values();
            if (values.contains(2)) {
                twoChars.incrementAndGet();
            }
            if (values.contains(3)) {
                threeChars.incrementAndGet();
            }
        });

        System.out.println("result = " + twoChars.get() * threeChars.get());
    }

}
