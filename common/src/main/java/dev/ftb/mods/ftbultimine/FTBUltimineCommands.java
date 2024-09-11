package dev.ftb.mods.ftbultimine;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.ftb.mods.ftbultimine.net.EditConfigPacket;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class FTBUltimineCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection selection) {
        dispatcher.register(Commands.literal("ftbultimine")
                .then(Commands.literal("serverconfig")
                        .requires(sourceStack -> sourceStack.isPlayer() && sourceStack.hasPermission(2))
                        .executes(context -> {
                            new EditConfigPacket(false).sendTo(Objects.requireNonNull(context.getSource().getPlayer()));
                            return 1;
                        })
                )
                .then(Commands.literal("clientconfig")
                        .requires(CommandSourceStack::isPlayer)
                        .executes(context -> {
                            new EditConfigPacket(true).sendTo(Objects.requireNonNull(context.getSource().getPlayer()));
                            return 1;
                        })
                )
        );
        dispatcher.register(Commands.literal("debug:reset")
                .executes(FTBUltimineCommands::fillFixedArea));
    }

    private static int fillFixedArea(CommandContext<CommandSourceStack> context) {
        Level world = context.getSource().getLevel();

        // Define the two corners of the area to fill with stone
        BlockPos pos1 = new BlockPos(-35, 70, -131);
        BlockPos pos2 = new BlockPos(2, 53, -101);


        // Calculate the minimum and maximum coordinates
        int minX = Math.min(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int maxY = Math.max(pos1.getY(), pos2.getY());
        int maxZ = Math.max(pos1.getZ(), pos2.getZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    world.setBlock(currentPos, Blocks.STONE.defaultBlockState(), 3);
                }
            }
        }

        // Notify the player that the area was filled with stone
        context.getSource().sendSuccess(() -> Component.nullToEmpty("Filled area from -35, 70, -131 to 2, 53, -101 with stone!"), true);
        return 1; // Command success
    }
}
