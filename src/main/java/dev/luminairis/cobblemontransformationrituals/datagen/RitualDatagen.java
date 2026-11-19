package dev.luminairis.cobblemontransformationrituals.datagen;

import dev.luminairis.cobblemontransformationrituals.CobblemonTransformationRituals;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class RitualDatagen {
    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(CobblemonTransformationRituals.MODID)) return;
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new RitualRecipeDatagen(generator.getPackOutput(), event.getLookupProvider()));
    }
}
