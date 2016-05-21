package io.musician101.controlcreativemode.common;

/**
 * @param <B> Block break event
 * @param <I> Block interact event
 * @param <P> Block place event
 * @param <D> Entity damage event
 * @param <T> Drop item event
 * @param <E> Entity interact event
 * @param <G> Gamemode change event
 * @param <U> Use item event
 */
@SuppressWarnings("unused")
public interface CCMListener<B, I, P, D, T, E, G, U>
{
    void blockBreak(B event);

    void blockInteract(I event);

    void blockPlace(P event);

    void damageEntity(D event);

    void dropItem(T event);

    void entityInteract(E event);

    void gameModeChange(G event);

    void useItem(U event);
}
