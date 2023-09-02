package com.frogniche.legendfoxes.item.item.beast_skirt;

import com.frogniche.legendfoxes.LegendFoxes;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BeastArmourRenderer extends GeoArmorRenderer<BeastArmor> {
    public BeastArmourRenderer() {
        super(new DefaultedItemGeoModel<>(LegendFoxes.modLoc("beast_skirt_armour")));
    }
}
