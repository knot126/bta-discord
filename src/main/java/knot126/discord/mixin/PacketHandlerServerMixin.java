package knot126.discord.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.util.helper.AES;
import net.minecraft.core.net.packet.PacketChat;
import net.minecraft.server.net.handler.PacketHandlerServer;
import net.minecraft.server.entity.player.PlayerServer;

import knot126.discord.DiscordIntegration;

/*@Mixin(PacketHandlerServer.class)
public class PacketHandlerServerMixin {
    @Inject()
}*/

@Mixin(value=PacketHandlerServer.class, remap=false)
public class PacketHandlerServerMixin {
    @Shadow PlayerServer playerEntity;

    //@Inject(method="handleChat", at=@At(value="INVOKE", target="handleChat(Lnet/minecraft/core/net/packet/PacketChat;)V", shift=At.Shift.AFTER), remap=false)
    @Inject(method="handleChat", at=@At("HEAD"), remap=false)
    public void handleChatDiscord(PacketChat packet, CallbackInfo ci) {
        String name = this.playerEntity.username;
        String msg = packet.message;

        if (packet.encrypted) {
            try {
                msg = AES.decrypt(packet.message, AES.keyChain.get(this.playerEntity.username));
            }
            catch (Exception e) {}
        }

        if (!msg.startsWith("/")) {
            DiscordIntegration.bot.send(name, msg);
        }
    }
}