import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2part2 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        final URL input = Day2part2.class.getClassLoader().getResource("day2/input");
        Stream<String> lines = (input != null) ? Files.lines(Paths.get(input.toURI())) : Stream.empty();

        final List<String> lineList = lines.collect(Collectors.toList());

        String one = "";
        String two = "";

        for (int i = 0; i < lineList.size(); i++) {
            for (int k = 0; k < lineList.size(); k++) {
                if (i != k) {
                    if (calculate(lineList.get(i), lineList.get(k)) == 1) {
                        one = lineList.get(i);
                        two = lineList.get(k);
                        break;
                    }
                }
            }
        }
        System.out.println("one = " + one);
        System.out.println("two = " + two);

        String result = "";
        for (int i = 0; i < one.length(); i++) {
            if (one.charAt(i) != two.charAt(i)) {
                StringBuilder sb = new StringBuilder(one);
                sb.deleteCharAt(i);
                result = sb.toString();
            }
        }
        System.out.println("result = " + result);
    }

    static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
