package prometheus.entities.units;

import arc.util.Time;
import mindustry.gen.UnitEntity;
import prometheus.content.PrtUnitTypes;

public class ArmorRechargeEntity extends UnitEntity {
    public float prevHealth = health;

    //Im sorry, Good code god...
    //TODO move this to UnitType
    @Override
    public void update(){
        super.update();
        if(armor > 0){
            armor -= Math.min(armor, ((ArmorRechargeUnitType)type).armorPerSecond * Time.delta);
        }
        if (prevHealth > health)
            armor += ((ArmorRechargeUnitType)type).armorPerHit;
        prevHealth = health;
    }

    @Override
    public int classId(){
        return PrtUnitTypes.idMap.get(ArmorRechargeEntity.class);
    }
}
