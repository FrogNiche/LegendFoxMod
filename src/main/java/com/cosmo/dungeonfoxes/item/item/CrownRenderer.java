package com.cosmo.dungeonfoxes.item.item;

import com.cosmo.dungeonfoxes.DungeonFoxes;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CrownRenderer extends GeoArmorRenderer<CrownArmor> {
    public CrownRenderer() {
        super(new DefaultedItemGeoModel<>(DungeonFoxes.modLoc("crown")));
    }
}
