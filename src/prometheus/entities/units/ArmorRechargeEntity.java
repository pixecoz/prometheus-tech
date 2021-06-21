package prometheus.entities.units;

import arc.util.Time;
import mindustry.gen.UnitEntity;
import prometheus.content.PrtUnitTypes;

public class ArmorRechargeEntity extends UnitEntity {
    public float prevHealth = health;
    private final float normalArmor = armor;
    public float addedArmor = 0f;

    //Im sorry, Good code god...
    //TODO move this to UnitType
    @Override
    public void update(){
        super.update();
        if(addedArmor > 0){
            addedArmor -= Math.min(armor, ((ArmorRechargeUnitType)type).armorPerSecond * Time.delta);
        }
        if (prevHealth > health && addedArmor < ((ArmorRechargeUnitType)type).addedArmorLimit)
            addedArmor += ((ArmorRechargeUnitType)type).armorPerHit;
        prevHealth = health;
        armor = normalArmor + addedArmor;
    }

    @Override
    public int classId(){
        return PrtUnitTypes.idMap.get(this.getClass());
    }
}
