import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

class Day3part2Test {

    @Test
    void claimToPatches() {
        final Day3part2.Claim claim = new Day3part2.Claim(1, 0, 0, 1, 1);
        final Optional<Map<Integer, Map<Integer, Day3part2.Inch>>> collect = Stream.of(claim)
                .map(Day3part2.claimToPatches())
                .findFirst();
        final Map<Integer, Map<Integer, Day3part2.Inch>> mapHashMap = new HashMap<>();
        Day3part2.Inch inch = new Day3part2.Inch();
        inch.add(claim);
        final HashMap<Integer, Day3part2.Inch> map = new HashMap<>();
        map.put(0, inch);
        mapHashMap.put(0, map);
        Assertions.assertEquals(collect.get(), mapHashMap);
    }

    @Test
    void applyAllPatches() {
    }

    @Test
    void applyPatchRow() {
    }
}