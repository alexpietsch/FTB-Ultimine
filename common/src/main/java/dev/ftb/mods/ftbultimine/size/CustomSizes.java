package dev.ftb.mods.ftbultimine.size;

import dev.ftb.mods.ftbultimine.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CustomSizes {
    private static final List<Integer> SIZES = new CopyOnWriteArrayList<Integer>(List.of(
            3,5,7,9,11,13,15
    ));

    private static Integer defaultSize = SIZES.size() > 0 ? SIZES.get(0) : 3;

    @NotNull
    public static Integer getSize(int idx) {
        if (idx < 0) {
            idx += SIZES.size();
        } else if (idx >= SIZES.size()) {
            idx -= SIZES.size();
        }
        return idx >= 0 && idx < SIZES.size() ? SIZES.get(idx) : defaultSize;
    }

    public static Integer sizesCount(){
        return SIZES.size();
    }
}
