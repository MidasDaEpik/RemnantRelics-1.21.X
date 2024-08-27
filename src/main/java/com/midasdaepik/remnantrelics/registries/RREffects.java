package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RREffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, RemnantRelics.MOD_ID);

    public static final Holder<MobEffect> PLUNGING = EFFECTS.register("plunging",
            () -> new Plunging(MobEffectCategory.HARMFUL,9715365)
    );

    public static final Holder<MobEffect> FROSBITTEN = EFFECTS.register("frostbitten",
            () -> new Frostbitten(MobEffectCategory.HARMFUL,10877181)
    );

    public static final Holder<MobEffect> UNWIELDY = EFFECTS.register("unwieldy",
            () -> new Unwieldy(MobEffectCategory.HARMFUL,11384036)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"unwieldy"), (double)-0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"unwieldy"), (double)-0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );

    public static final Holder<MobEffect> ECHO = EFFECTS.register("echo",
            () -> new Echo(MobEffectCategory.HARMFUL,-16563888)
    );

    public static final Holder<MobEffect> BULWARK = EFFECTS.register("bulwark",
            () -> new Bulwark(MobEffectCategory.BENEFICIAL,2445989)
                    .addAttributeModifier(Attributes.MAX_ABSORPTION, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"bulwark"), (double)1F, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> HEXED_DICE_ONE = EFFECTS.register("hexed_dice_one",
            () -> new HexedDice(MobEffectCategory.BENEFICIAL,14253864)
                    .addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_one"), (double)4F, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> HEXED_DICE_TWO = EFFECTS.register("hexed_dice_two",
            () -> new HexedDice(MobEffectCategory.BENEFICIAL,14253864)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_two"), (double)0.15F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );

    public static final Holder<MobEffect> HEXED_DICE_THREE = EFFECTS.register("hexed_dice_three",
            () -> new HexedDice(MobEffectCategory.BENEFICIAL,14253864)
                    .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_three"), (double)1F, AttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_three"), (double)1F, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> HEXED_DICE_FOUR = EFFECTS.register("hexed_dice_four",
            () -> new HexedDice(MobEffectCategory.HARMFUL,14253864)
                    .addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_four"), (double)-4F, AttributeModifier.Operation.ADD_VALUE)
    );

    public static final Holder<MobEffect> HEXED_DICE_FIVE = EFFECTS.register("hexed_dice_five",
            () -> new HexedDice(MobEffectCategory.HARMFUL,14253864)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_five"), (double)-0.15F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );

    public static final Holder<MobEffect> HEXED_DICE_SIX = EFFECTS.register("hexed_dice_six",
            () -> new HexedDice(MobEffectCategory.HARMFUL,14253864)
                    .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_six"), (double)-1F, AttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID,"hexed_dice_six"), (double)-1F, AttributeModifier.Operation.ADD_VALUE)
    );

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
