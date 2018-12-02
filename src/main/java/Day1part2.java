import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1part2 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        final URL input = Day1part2.class.getClassLoader().getResource("day1/input");
        Stream<String> lines = (input != null) ? Files.lines(Paths.get(input.toURI())) : Stream.empty();

        AtomicLong frequency = new AtomicLong(0L);
        ArrayList<Long> freqReached = new ArrayList<>();
        AtomicBoolean solved = new AtomicBoolean();

        final List<Long> longList = lines.map(Long::valueOf).collect(Collectors.toList());

        while (!solved.get()) {
            longList.forEach(l -> {
                        if (freqReached.contains(frequency.get())) {
                            solved.set(true);
                        } else {
                            freqReached.add(frequency.get());
                            frequency.addAndGet(l);
                        }
                    });
        }
        System.out.println("frequency = " + frequency);
    }

}
