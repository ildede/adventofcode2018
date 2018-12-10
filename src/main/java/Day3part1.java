import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day3part1 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        final URL input = Day3part1.class.getClassLoader().getResource("day3/input");
        Stream<String> lines = (input != null) ? Files.lines(Paths.get(input.toURI())) : Stream.empty();

        final long count = lines.map(l -> l.split(" "))
                .map(s -> new Claim(getId(s[0]), getMarginLeft(s[2]), getMarginTop(s[2]), getWidth(s[3]), getHigh(s[3])))
                .map(claimToPatches())
                .reduce(applyAllPatches())
                .orElseThrow(RuntimeException::new)
                .values().stream()
                .flatMap(map -> Stream.of(map.values()))
                .flatMap(Collection::stream)
                .filter(i -> i > 1)
                .count();

        System.out.println("count = " + count);
    }

    private static BinaryOperator<Map<Integer, Map<Integer, Integer>>> applyAllPatches() {
        return (accumulator, patch) -> {
            for (Integer patchRowIndex : patch.keySet()) {
                Map<Integer, Integer> patchRow = patch.get(patchRowIndex);
                accumulator.put(patchRowIndex, applyPatchRow(accumulator.getOrDefault(patchRowIndex, new HashMap<>()), patchRow));
            }
            return accumulator;
        };
    }

    private static Map<Integer, Integer> applyPatchRow(Map<Integer, Integer> accumulatorRow, Map<Integer, Integer> patchRow) {
        for (Integer index : patchRow.keySet()) {
            accumulatorRow.put(index, accumulatorRow.getOrDefault(index, 0) + 1);
        }
        return accumulatorRow;
    }

    private static Function<Claim, Map<Integer, Map<Integer, Integer>>> claimToPatches() {
        return claim -> {
            Map<Integer, Map<Integer, Integer>> fabric = new HashMap<>();
            for (int i = 0; i < claim.high; i++) {
                Map<Integer, Integer> row = fabric.getOrDefault(claim.marginTop + i, new HashMap<>());
                for (int j = 0; j < claim.width; j++) {
                    Integer count = row.getOrDefault(claim.marginLeft + j, 0);
                    row.put(claim.marginLeft + j, count + 1);
                }
                fabric.put(claim.marginTop + i, row);
            }
            return fabric;
        };
    }

    private static Integer getId(String s) {
        return Integer.valueOf(s.substring(1));
    }

    private static Integer getMarginLeft(String s) {
        return Integer.valueOf(s.split(",")[0]);
    }

    private static Integer getMarginTop(String s) {
        return Integer.valueOf(s.replace(":", "").split(",")[1]);
    }

    private static Integer getWidth(String s) {
        return Integer.valueOf(s.split("x")[0]);
    }

    private static Integer getHigh(String s) {
        return Integer.valueOf(s.split("x")[1]);
    }

    static class Claim {
        Integer id;
        Integer marginLeft;
        Integer marginTop;
        Integer width;
        Integer high;

        Claim(Integer id, Integer marginLeft, Integer marginTop, Integer width, Integer high) {
            this.id = id;
            this.marginLeft = marginLeft;
            this.marginTop = marginTop;
            this.width = width;
            this.high = high;
        }
    }
}
