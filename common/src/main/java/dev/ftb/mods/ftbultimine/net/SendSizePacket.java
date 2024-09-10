package dev.ftb.mods.ftbultimine.net;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class SendSizePacket extends BaseS2CMessage {
//	public static Shape current = null;

	private final int sizeIdx;
//	private final List<BlockPos> blocks;

	public SendSizePacket(int idx, List<BlockPos> b) {
		sizeIdx = idx;
//		blocks = b;
	}

	public SendSizePacket(FriendlyByteBuf buf) {
		sizeIdx = buf.readVarInt();
//		shape = ShapeRegistry.getShape(buf.readUtf(Short.MAX_VALUE));
//		int s = buf.readVarInt();
//		blocks = new ArrayList<>(s);
//
//		for (int i = 0; i < s; i++) {
//			blocks.add(buf.readBlockPos());
//		}
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeVarInt(sizeIdx);
//		buf.writeUtf(shape.getName(), Short.MAX_VALUE);
//		buf.writeVarInt(blocks.size());

//		for (BlockPos pos : blocks) {
//			buf.writeBlockPos(pos);
//		}
	}

	@Override
	public MessageType getType() {
		return FTBUltimineNet.SEND_SIZE;
	}

	@Override
	public void handle(NetworkManager.PacketContext context) {
		context.queue(() -> {
//			current = shape;
			FTBUltimine.instance.proxy.setSize(sizeIdx);
		});
	}
}
