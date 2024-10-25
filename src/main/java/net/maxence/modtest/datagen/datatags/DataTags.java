package net.maxence.modtest.datagen.datatags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class DataTags {
    public static CompoundTag getDataTag(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("mekData", 10)) {
            return tag.getCompound("mekData");
        } else {
            CompoundTag dataMap = new CompoundTag();
            tag.put("mekData", dataMap);
            return dataMap;
        }
    }
    @Nullable
    public static CompoundTag getDataTagIfPresent(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("mekData", 10) ? tag.getCompound("mekData") : null;
    }

    public static boolean hasData(ItemStack stack, String key, int type) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        return dataMap != null && dataMap.contains(key, type);
    }

    public static void removeData(ItemStack stack, String key) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        if (dataMap != null) {
            dataMap.remove(key);
            if (dataMap.isEmpty()) {
                CompoundTag tag = stack.getTag();
                tag.remove("mekData");
                if (tag.isEmpty()) {
                    stack.setTag((CompoundTag)null);
                }
            }
        }

    }

    public static <T> T getDataValue(ItemStack stack, Function<CompoundTag, T> getter, T fallback) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        return dataMap == null ? fallback : getter.apply(dataMap);
    }

    public static int getInt(ItemStack stack, String key) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        return dataMap == null ? 0 : dataMap.getInt(key);
    }

    public static long getLong(ItemStack stack, String key) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        return dataMap == null ? 0L : dataMap.getLong(key);
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        return dataMap != null && dataMap.getBoolean(key);
    }

    public static double getDouble(ItemStack stack, String key) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        return dataMap == null ? 0.0 : dataMap.getDouble(key);
    }

    public static String getString(ItemStack stack, String key) {
        return (String)getDataValue(stack, (dataMap) -> {
            return dataMap.getString(key);
        }, "");
    }

    public static CompoundTag getCompound(ItemStack stack, String key) {
        return (CompoundTag)getDataValue(stack, (dataMap) -> {
            return dataMap.getCompound(key);
        }, new CompoundTag());
    }

    public static CompoundTag getOrAddCompound(ItemStack stack, String key) {
        CompoundTag dataMap = getDataTag(stack);
        if (dataMap.contains(key, 10)) {
            return dataMap.getCompound(key);
        } else {
            CompoundTag compound = new CompoundTag();
            dataMap.put(key, compound);
            return compound;
        }
    }

    public static void setCompoundIfPresent(ItemStack stack, String key, Consumer<CompoundTag> setter) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        if (dataMap != null && dataMap.contains(key, 10)) {
            setter.accept(dataMap.getCompound(key));
        }

    }

    @Nullable
    public static UUID getUniqueID(ItemStack stack, String key) {
        CompoundTag dataMap = getDataTagIfPresent(stack);
        return dataMap != null && dataMap.hasUUID(key) ? dataMap.getUUID(key) : null;
    }

    public static ListTag getList(ItemStack stack, String key) {
        return (ListTag)getDataValue(stack, (dataMap) -> {
            return dataMap.getList(key, 10);
        }, new ListTag());
    }

    public static void setInt(ItemStack stack, String key, int i) {
        getDataTag(stack).putInt(key, i);
    }

    public static void setIntOrRemove(ItemStack stack, String key, int i) {
        if (i == 0) {
            removeData(stack, key);
        } else {
            setInt(stack, key, i);
        }

    }

    public static void setLong(ItemStack stack, String key, long l) {
        getDataTag(stack).putLong(key, l);
    }

    public static void setLongOrRemove(ItemStack stack, String key, long l) {
        if (l == 0L) {
            removeData(stack, key);
        } else {
            setLong(stack, key, l);
        }

    }

    public static void setBoolean(ItemStack stack, String key, boolean b) {
        getDataTag(stack).putBoolean(key, b);
    }

    public static void setDouble(ItemStack stack, String key, double d) {
        getDataTag(stack).putDouble(key, d);
    }

    public static void setString(ItemStack stack, String key, String s) {
        getDataTag(stack).putString(key, s);
    }

    public static void setCompound(ItemStack stack, String key, CompoundTag tag) {
        getDataTag(stack).put(key, tag);
    }

    public static void setUUID(ItemStack stack, String key, @Nullable UUID uuid) {
        if (uuid == null) {
            removeData(stack, key);
        } else {
            getDataTag(stack).putUUID(key, uuid);
        }

    }

    public static void setList(ItemStack stack, String key, ListTag tag) {
        getDataTag(stack).put(key, tag);
    }

    public static void setListOrRemove(ItemStack stack, String key, ListTag tag) {
        if (tag.isEmpty()) {
            removeData(stack, key);
        } else {
            setList(stack, key, tag);
        }

    }

    /*public static void readContainers(ItemStack stack, String containerKey, List<? extends INBTSerializable<CompoundTag>> containers) {
        if (!stack.isEmpty()) {
            DataHandlerUtils.readContainers(containers, getList(stack, containerKey));
        }

    }

    public static void writeContainers(ItemStack stack, String containerKey, List<? extends INBTSerializable<CompoundTag>> containers) {
        if (!stack.isEmpty()) {
            setListOrRemove(stack, containerKey, DataHandlerUtils.writeContainers(containers));
        }

    }*/
}