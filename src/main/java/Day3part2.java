import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3part2 {

    public static void main(String[] args) throws IOException, URISyntaxException {

        final URL input = Day3part2.class.getClassLoader().getResource("day3/input");
        Stream<String> lines = (input != null) ? Files.lines(Paths.get(input.toURI())) : Stream.empty();


        final List<List<Claim>> all = lines.map(l -> l.split(" "))
                .map(s -> new Claim(getId(s[0]), getMarginLeft(s[2]), getMarginTop(s[2]), getWidth(s[3]), getHigh(s[3])))
                .map(claimToPatches())
                .reduce(applyAllPatches())
                .orElseThrow(RuntimeException::new)
                .values().stream()

                .flatMap(map -> Stream.of(map.values()))
                .flatMap(Collection::stream)
                .map(i -> i.claims)
                .collect(Collectors.toList());

        final List<Integer> multiple = all.stream()
                .filter(claims -> claims.size() > 1)
                .flatMap(claims -> claims.stream().map(c -> c.id))
                .collect(Collectors.toList());

        all.stream()
                .filter(claims -> claims.size() == 1)
                .flatMap(claims -> claims.stream().map(c -> c.id))
                .filter(id -> !multiple.contains(id))
                .distinct()
                .forEach(System.out::println);
    }

    static BinaryOperator<Map<Integer, Map<Integer, Inch>>> applyAllPatches() {
        return (accumulator, patch) -> {
            for (Integer patchRowIndex : patch.keySet()) {
                Map<Integer, Inch> patchRow = patch.get(patchRowIndex);
                accumulator.put(patchRowIndex, applyPatchRow(accumulator.getOrDefault(patchRowIndex, new HashMap<>()), patchRow));
            }
            return accumulator;
        };
    }

    static Map<Integer, Inch> applyPatchRow(Map<Integer, Inch> accumulatorRow, Map<Integer, Inch> patchRow) {
        for (Integer index : patchRow.keySet()) {
            accumulatorRow.put(index, accumulatorRow.getOrDefault(index, new Inch()).addAll(patchRow.get(index).claims));
        }
        return accumulatorRow;
    }

    static Function<Claim, Map<Integer, Map<Integer, Inch>>> claimToPatches() {
        return claim -> {
            Map<Integer, Map<Integer, Inch>> fabric = new HashMap<>();
            for (int i = 0; i < claim.high; i++) {
                Map<Integer, Inch> row = fabric.getOrDefault(claim.marginTop + i, new HashMap<>());
                for (int j = 0; j < claim.width; j++) {
                    Inch inch = row.getOrDefault(claim.marginLeft + j, new Inch());
                    row.put(claim.marginLeft + j, inch.add(claim));
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

    static class Inch {
        List<Claim> claims;

        public Inch() {
            this.claims = new ArrayList<>();
        }


        public Inch add(Claim claim) {
            claims.add(claim);
            return this;
        }

        public Inch addAll(List<Claim> cl) {
            claims.addAll(cl);
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Inch inch = (Inch) o;
            return Objects.equals(claims, inch.claims);
        }

        @Override
        public int hashCode() {
            return Objects.hash(claims);
        }
    }
}
