package prometheus.type;

import arc.Core;
import arc.graphics.g2d.*;

import mindustry.graphics.*;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.world.meta.Stat;
import prometheus.entities.units.ArmorRechargeEntity;
import prometheus.type.PrtUnitType;

public class ArmorRechargeUnitType extends PrtUnitType {
    //armor per health
    public float armorPerHit = 100f;
    //armor per second
    public float armorPerSecond = 1f;
    //max armor
    public float addedArmorLimit = 200f;
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
    public void setStats(){
        super.setStats();
        stats.add(Stat.abilities, table -> {
            table.row();
            table.table(bt ->{
                bt.left().defaults().padLeft(20f).left();
                bt.add(Core.bundle.format("unit.armor-recharge")).row();
                bt.defaults().padLeft(40f);
                bt.add(Core.bundle.format("unit.armor-per-hit", armorPerHit)).row();
                bt.add(Core.bundle.format("unit.armor-per-second", armorPerSecond * 60f)).row();
            });
        });
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
        ArmorRechargeEntity unit = ((ArmorRechargeEntity) u);
        Draw.z(Layer.effect);

        Draw.color(Pal.lighterOrange);
        Draw.alpha(unit.addedArmor / 100f);
        Lines.circle(u.x, u.y, 15f);

        Draw.color(Pal.lightishOrange);
        Draw.alpha(unit.addedArmor / 200f);
        Lines.circle(u.x, u.y, 20f);
    }
}
