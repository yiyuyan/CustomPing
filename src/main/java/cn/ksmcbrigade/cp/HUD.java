package cn.ksmcbrigade.cp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Objects;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class HUD {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft MC = Minecraft.getInstance();
            long ping = getPing(MC);
            int color = ping<=100?Color.GREEN.getRGB():ping<300?Color.BLUE.getRGB():Color.RED.getRGB();
            MC.font.draw(event.getMatrixStack(),"Ping: "+ping,0,1, color);
        }
    }

    public static long getPing(Minecraft MC){
        if(CustomPing.custom){
            return CustomPing.ping;
        }
        else if (MC.getConnection() != null && MC.player!=null) {
            PlayerInfo info = MC.getConnection().getPlayerInfo(MC.player.getUUID());
            if(info!=null){
                return info.getLatency();
            }
            else{
                return -1;
            }
        }
        else{
            return -1L;
        }
    }
}
