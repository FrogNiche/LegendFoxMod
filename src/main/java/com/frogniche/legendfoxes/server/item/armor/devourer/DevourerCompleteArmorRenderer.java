package com.frogniche.legendfoxes.server.item.armor.devourer;

import com.frogniche.legendfoxes.LegendFoxes;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DevourerCompleteArmorRenderer extends GeoArmorRenderer<DevourerCompleteArmor> {
    public DevourerCompleteArmorRenderer() {
        super(new DefaultedItemGeoModel<>(LegendFoxes.modLoc("devourer_complete_armour")));
    }
}
