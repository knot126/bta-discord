package knot126.discord.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import knot126.discord.DiscordIntegration;
import net.minecraft.server.MinecraftServer;

@Mixin(value=MinecraftServer.class, remap=false)
public class MinecraftServerMixin {
    @Inject(method="initiateShutdown", at=@At("HEAD"), remap=false)
    void discordStopServer(CallbackInfo ci) {
        DiscordIntegration.bot.send(null, ":octagonal_sign: **Server stopped**");
    }
    
    @Inject(method="run", at=@At("HEAD"), remap=false)
    void run(CallbackInfo ci) {
        DiscordIntegration.bot.send(null, ":white_check_mark: **Server started**");
    }
}
