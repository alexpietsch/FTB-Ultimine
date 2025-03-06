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
import org.joml.Math;

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
    }
}
