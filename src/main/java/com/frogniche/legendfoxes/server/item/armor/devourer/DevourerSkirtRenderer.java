package com.frogniche.legendfoxes.server.item.armor.devourer;

import com.frogniche.legendfoxes.LegendFoxes;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DevourerSkirtRenderer extends GeoArmorRenderer<DevourerSkirtArmor> {
    public DevourerSkirtRenderer() {
        super(new DefaultedItemGeoModel<>(LegendFoxes.modLoc("devourer_armour")));
    }
}
