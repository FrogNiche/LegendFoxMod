package com.cosmo.dungeonfoxes.datagen;

import com.cosmo.dungeonfoxes.DungeonFoxes;
import com.cosmo.dungeonfoxes.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, DungeonFoxes.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.CROWN.get());
        add(ModItems.TASTY_BONE.get());
    }

    public void add(Item key) {
        add(key.getDescriptionId(), toTitleCase(key.getDescriptionId()));
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextTitleCase = true;
                titleCase.append(" ");
                continue;
            }

            if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

}
