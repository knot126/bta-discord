package knot126.discord.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import knot126.discord.DiscordIntegration;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketChat;
import net.minecraft.server.net.PlayerList;

@Mixin(value=PlayerList.class, remap=false)
public class PlayerListMixin {
    @Inject(method="sendPacketToAllPlayers", at=@At("HEAD"), remap=false)
    public void sendPacketToAllPlayersDiscord(Packet packet, CallbackInfo ci) {
        if (packet instanceof PacketChat) {
            PacketChat chat = (PacketChat) packet;
            DiscordIntegration.bot.send(null, chat.message);
        }
    }
}
