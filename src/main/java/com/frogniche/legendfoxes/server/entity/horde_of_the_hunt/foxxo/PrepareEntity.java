package com.frogniche.legendfoxes.server.entity.horde_of_the_hunt.foxxo;

public interface PrepareEntity {

    /**
     * must work on server side, not on client side
     */
    public void prepare();

    /**
     *
     * @return whether the entity can roar again or not, only correct on the server side
     */
    public boolean canPrepare();
}
