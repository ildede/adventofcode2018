import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day1 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        final URL input = Day1.class.getClassLoader().getResource("day1/input");
        Stream<String> lines = (input != null) ? Files.lines(Paths.get(input.toURI())) : Stream.empty();

        lines.map(Long::valueOf)
                .reduce(Long::sum)
                .ifPresent(System.out::println);
    }

}
