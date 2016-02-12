package musician101.controlcreativemode.common;

public interface CCMListener<BlockBreak, BlockInteract, BlockPlace, DamageEntity, DropItem, EntityInteract, GameModeChange, UseItem>
{
    void blockBreak(BlockBreak event);

    void blockInteract(BlockInteract event);

    void blockPlace(BlockPlace event);

    void damageEntity(DamageEntity event);

    void dropItem(DropItem event);

    void entityInteract(EntityInteract event);

    void gameModeChange(GameModeChange event);

    void useItem(UseItem event);
}
