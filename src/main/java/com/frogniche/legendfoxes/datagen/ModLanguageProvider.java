package com.frogniche.legendfoxes.datagen;

import com.frogniche.legendfoxes.LegendFoxes;
import com.frogniche.legendfoxes.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, LegendFoxes.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.UNBREAKABLE_HEAD.get());
        add(ModItems.UNBREAKABLE_CHESTPLATE.get());
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
