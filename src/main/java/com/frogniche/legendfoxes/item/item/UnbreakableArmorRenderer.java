package com.frogniche.legendfoxes.item.item;

import com.frogniche.legendfoxes.LegendFoxes;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class UnbreakableArmorRenderer extends GeoArmorRenderer<UnbreakableArmor> {
    public UnbreakableArmorRenderer() {
        super(new DefaultedItemGeoModel<>(LegendFoxes.modLoc("unbreakable_armour")));
    }
}
