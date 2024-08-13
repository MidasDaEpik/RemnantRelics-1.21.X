package com.midasdaepik.remnantrelics.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddMultipleLootModifier extends LootModifier {
    private final Item item;
    private final Integer min_count;
    private final Integer max_count;

    public static final Supplier<MapCodec<AddMultipleLootModifier>> MAP_CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(
                    inst -> LootModifier.codecStart(inst).and(
                            inst.group(
                                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item),
                                    Codec.INT.fieldOf("min_count").forGetter(m -> m.min_count),
                                    Codec.INT.fieldOf("max_count").forGetter(m -> m.max_count)
                            )
                    ).apply(inst, AddMultipleLootModifier::new)
            )
    );

    public AddMultipleLootModifier(LootItemCondition[] conditionsIn, Item item, Integer min_count, Integer max_count) {
        super(conditionsIn);
        this.item = item;
        this.min_count = min_count;
        this.max_count = max_count;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack itemstack = new ItemStack(this.item);
        itemstack.setCount(Mth.nextInt(RandomSource.create(), this.min_count, this.max_count));

        generatedLoot.add(new ItemStack(this.item));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return MAP_CODEC.get();
    }
}
