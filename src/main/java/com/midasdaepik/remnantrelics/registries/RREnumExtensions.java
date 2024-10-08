package com.midasdaepik.remnantrelics.registries;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class RREnumExtensions {
    public static final EnumProxy<Rarity> RARITY_ELDER = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:elder", (UnaryOperator<Style>) style -> style.withColor(13550515)
    );

    public static final EnumProxy<Rarity> RARITY_WIND = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:wind", (UnaryOperator<Style>) style -> style.withColor(9801171)
    );

    public static final EnumProxy<Rarity> RARITY_BLAZE = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:blaze", (UnaryOperator<Style>) style -> style.withColor(16494865)
    );

    public static final EnumProxy<Rarity> RARITY_GOLD = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:gold", (UnaryOperator<Style>) style -> style.withColor(16633124)
    );

    public static final EnumProxy<Rarity> RARITY_WARPED_RAPIER = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:warped_rapier", (UnaryOperator<Style>) style -> style.withColor(2609073)
    );

    public static final EnumProxy<Rarity> RARITY_WITHERBLADE = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:witherblade", (UnaryOperator<Style>) style -> style.withColor(5458763)
    );

    public static final EnumProxy<Rarity> RARITY_CREEPING_CRIMSON = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:creeping_crimson", (UnaryOperator<Style>) style -> style.withColor(8000014)
    );

    public static final EnumProxy<Rarity> RARITY_SOULEATING_SLASHER = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:souleating_slasher", (UnaryOperator<Style>) style -> style.withColor(435369)
    );

    public static final EnumProxy<Rarity> RARITY_WARPING_WITHER = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:warping_wither", (UnaryOperator<Style>) style -> style.withColor(2526073)
    );

    public static final EnumProxy<Rarity> RARITY_SCULK = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:sculk", (UnaryOperator<Style>) style -> style.withColor(356730)
    );

    public static final EnumProxy<Rarity> RARITY_DRAGON = new EnumProxy<>(
            Rarity.class, -1, "remnantrelics:dragon", (UnaryOperator<Style>) style -> style.withColor(13719531)
    );
}
