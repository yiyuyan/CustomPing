package cn.ksmcbrigade.cp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Mod("cp")
@Mod.EventBusSubscriber
public class CustomPing {

    public static File file = new File("config/cp-config.json");
    public static boolean custom = false;
    public static long ping = 0L;

    public CustomPing() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        if(!file.exists()){
            save();
        }
        JsonObject json = JsonParser.parseString(Files.readString(file.toPath())).getAsJsonObject();
        custom = json.get("custom").getAsBoolean();
        ping = json.get("ping").getAsLong();
    }

    public static void save() throws IOException{
        JsonObject object = new JsonObject();
        object.addProperty("custom",custom);
        object.addProperty("ping",ping);
        Files.writeString(file.toPath(),object.toString());
    }

    @SubscribeEvent
    public static void command(RegisterClientCommandsEvent event){
        event.getDispatcher().register(Commands.literal("cp").then(Commands.argument("custom", BoolArgumentType.bool()).then(Commands.argument("CustomPing", LongArgumentType.longArg()).executes(context -> {
            try {
                custom = BoolArgumentType.getBool(context,"custom");
                ping = LongArgumentType.getLong(context,"CustomPing");
                save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return 0;
        }))));
    }

}
