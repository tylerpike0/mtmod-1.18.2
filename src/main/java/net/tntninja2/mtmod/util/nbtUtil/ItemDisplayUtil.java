package net.tntninja2.mtmod.util.nbtUtil;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;

import java.util.Iterator;
import java.util.List;

public class ItemDisplayUtil {


    public static String NameJson(String name, String color) {
        String json = "{\"text\":\"" + name + "\",\"color\":\"" + color + "\",\"bold\":" + false + ",\"italic\":" + false + "}";
        return NameJson(name, color, false, false);
    }

    public static String NameJson(String name, String color, boolean bold) {
        return NameJson(name, color, bold, false);
    }

    public static String NameJson(String name, String color, boolean bold, boolean italic) {
        String json = "{\"text\":\"" + name + "\",\"color\":\"" + color + "\",\"bold\":" + bold + ",\"italic\":" + italic + "}";
        return json;
    }

    public static NbtElement LoreLineJson(String lore, String color, boolean bold, boolean italic) {
        String json = "{\"text\":\"" + lore + "\",\"color\":\"" + color + "\",\"bold\":" + bold + ",\"italic\":" + italic + "}";
        return NbtString.of(json);
    }

    public static String LoreJson(List<String> lines) {
        String json = "[";
        Iterator<String> iteratorLines = lines.iterator();
        while (iteratorLines.hasNext()) {
            String line = iteratorLines.next();
            json += line;
            if (iteratorLines.hasNext()) {
                json += ",";
            }
        }
        json += "]";
        return json;
    }




}
