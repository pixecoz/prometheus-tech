package prometheus.entities.units;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.util.Log;
import arc.util.Time;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

public class ArmorRechargeUnitType extends UnitType {
    //armor per health
    public float armorPerHit = 100f;
    //armor per second
    public float armorPerSecond = 1f;

    public ArmorRechargeUnitType(String name) {
        super(name);
    }

    @Override
    public void update(Unit u){
        super.update(u);
        /*
        ArmorRechargeEntity entity = ((ArmorRechargeEntity) u);
        if(entity.armor > 0){
            entity.armor -= Math.min(entity.armor, armorPerSecond * Time.delta);
        }
        if(entity.prevHealth > entity.health)
            entity.armor += armorPerHit;
        entity.prevHealth = health;
         */
    }

    @Override
    public Unit create(Team team){
        Unit unit = constructor.get();
        unit.team = team;
        unit.setType(this);
        unit.ammo = ammoCapacity;
        unit.health = unit.maxHealth;
        return unit;
    }

    @Override
    public void draw(Unit u){
        super.draw(u);
        Draw.z(Layer.effect);
        Draw.color(Pal.lightishOrange);
        Lines.circle(u.x, u.y, u.armor + 5f);
    }
}
