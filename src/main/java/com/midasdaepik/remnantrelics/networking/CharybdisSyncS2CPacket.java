package com.midasdaepik.remnantrelics.networking;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.midasdaepik.remnantrelics.registries.RRAttachmentTypes.CHARYBDIS_CHARGE;

public record CharybdisSyncS2CPacket(int CharybdisCharge) implements CustomPacketPayload {
    public static final Type<CharybdisSyncS2CPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "charybdis_sync_s2c_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CharybdisSyncS2CPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            CharybdisSyncS2CPacket::CharybdisCharge,

            CharybdisSyncS2CPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public boolean handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player pPlayer = context.player();
            pPlayer.setData(CHARYBDIS_CHARGE, CharybdisCharge);
        });
        return true;
    }
}