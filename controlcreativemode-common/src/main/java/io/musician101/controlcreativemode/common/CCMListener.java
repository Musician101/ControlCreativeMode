package io.musician101.controlcreativemode.common;

public interface CCMListener<B, I, P, D, T, E, G, U, L>
{
    void blockBreak(B event);

    void blockInteract(I event);

    /*
     * Sponge version not needed (opened/placed).
     * Check if same with Spigot (opened/placed).
     */
    @Deprecated
    void blockPlace(P event);

    void damageEntity(D event);

    void dropItem(T event);

    void entityInteract(E event);

    void gameModeChange(G event);

    void useItem(U event);

    void onProjectileLaunch(L event);
}
