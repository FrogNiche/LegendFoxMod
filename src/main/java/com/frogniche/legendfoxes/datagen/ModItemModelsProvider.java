package com.frogniche.legendfoxes.datagen;

import com.frogniche.legendfoxes.LegendFoxes;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelsProvider extends ItemModelProvider {

    public final ModelFile generated = getExistingFile(mcLoc("item/generated"));
    public final ModelFile spawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));

    public ModItemModelsProvider(PackOutput poutput, ExistingFileHelper existingFileHelper) {
        super(poutput, LegendFoxes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    private void simple(RegistryObject<Item>... items) {
        for (RegistryObject<Item> item : items) {
            String name = item.getId().getPath();
            getBuilder(name).parent(generated).texture("layer0", "item/" + name);
        }
    }

    private void spawnEgg(RegistryObject<Item>... items) {
        for (RegistryObject<Item> item : items) {
            String name = item.getId().getPath();
            getBuilder(name).parent(spawnEgg);
        }
    }
}
