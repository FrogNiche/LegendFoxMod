package com.cosmo.dungeonfoxes.interfaces;

public interface IShieldUser{

    int getShieldCooldownTime();

    void setShieldCooldownTime(int shieldCooldownTime);

    void disableShield(boolean guaranteeDisable);

    boolean isShieldDisabled();
}
