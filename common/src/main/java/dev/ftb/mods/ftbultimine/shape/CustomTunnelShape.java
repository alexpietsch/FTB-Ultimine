package dev.ftb.mods.ftbultimine.shape;

import dev.ftb.mods.ftbultimine.config.FTBUltimineServerConfig;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CustomTunnelShape implements Shape {
    @Override
    public String getName() {
        return "custom_tunnel";
    }

    @Override
    public List<BlockPos> getBlocks(ShapeContext context) {
        List<BlockPos> list = new ArrayList<>(9);

        BlockPos basePos = context.pos();
        list.add(basePos);
        int depth = 0;

        while (depth < maxDepth() && list.size() < context.maxBlocks()) {
            int size = list.size();
            int halfWidth = FTBUltimineServerConfig.CUSTOM_RECTANGLE_WIDTH.get() / 2;
            int halfHeight = FTBUltimineServerConfig.CUSTOM_RECTANGLE_HEIGHT.get() / 2;
            LAYER: for (int a = -halfWidth; a <= halfWidth; a++) {
                for (int b = -halfHeight; b <= halfHeight; b++) {
                    if (depth > 0 || a != 0 || b != 0) {
                        BlockPos pos = switch (context.face().getAxis()) {
                            case X -> basePos.offset(0, b, a);
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
                break; // none of the blocks could be mined: stop
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
