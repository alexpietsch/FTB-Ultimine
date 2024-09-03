package dev.ftb.mods.ftbultimine.shape;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class BigTunnelShape implements Shape {
    @Override
    public String getName() {
        return "big_tunnel";
    }

    @Override
    public List<BlockPos> getBlocks(ShapeContext context) {
        List<BlockPos> list = new ArrayList<>(9);

        BlockPos basePos = context.pos();
        list.add(basePos);
        int depth = 0;

        while (depth < maxDepth() && list.size() < context.maxBlocks()) {
            int size = list.size();
            LAYER: for (int a = -2; a <= 2; a++) {
                for (int b = -2; b <= 2; b++) {
                    if (depth > 0 || a != 0 || b != 0) {
                        BlockPos pos = switch (context.face().getAxis()) {
                            case X -> basePos.offset(0, a, b);
                            case Y -> basePos.offset(a, 0, b);
                            case Z -> basePos.offset(a, b, 0);
                        };

                        if (context.check(pos)) {
                            list.add(pos);
                            if (list.size() >= context.maxBlocks()) {
                                break LAYER;
                            }
                        }
                    }
                }
            }
            if (list.size() == size) {
                break; // none of the blocks in the 3x3 could be mined: stop
            }
            basePos = basePos.relative(context.face().getOpposite());
            depth++;
        }

        return list;
    }

    protected int maxDepth() {
        return Integer.MAX_VALUE;
    }
}
