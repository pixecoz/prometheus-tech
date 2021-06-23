package prometheus.content;

import arc.func.Prov;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.ObjectMap.Entry;
import arc.util.Time;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.Vars;
import mindustry.content.Bullets;
import mindustry.entities.Damage;
import mindustry.ctype.ContentList;

import prometheus.entities.units.*;
import prometheus.type.PrtUnitType;

public class PrtUnitTypes implements ContentList{

    /**
    Мануал для будущих поколений.
     Вы хотите добавить нового юнита (? extends Entityc)? Тогда...
     1. В unitList добавьте новый
        entry(Юнит.class, Юнит::new);

     2. Перед типом юнита (? extends UnitType или же просто UnitType) пишите:
        EntityMapping.nameMap.put("название-юнита", Юнит::new);
     */

    //naval
    public static UnitType castor, vega, nembus, arcturus, betelgeuse;

    //ground + passive ability
    public static UnitType berserk;
    //Meep of faith and BetaMindy, thanks
    public static Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] unitList = new Entry[]{
        entry(ArmorRechargeEntity.class, ArmorRechargeEntity::new)
    };
    public static ObjectMap<Class<? extends Entityc>, Integer> idMap = new ObjectMap<>();
    public static int nextId = -1;

     public static void putIDS(){
        for(int i = 0; i < EntityMapping.idMap.length; i++){
            if(EntityMapping.idMap[i] == null){
                //found free id
                nextId++;
                EntityMapping.idMap[i] = unitList[nextId].value;
                idMap.put(unitList[nextId].key, i);
                if (nextId >= unitList.length-1)
                    break;
            }
        }
    }

    public static Entry entry(Class<? extends Entityc> clazz, Prov<? extends Entityc> prov){
        Entry<Class<? extends Entityc>, Prov<? extends Entityc>> entry = new Entry<>();
        entry.key = clazz;
        entry.value = prov;
        return entry;
    }

    public void load(){
        putIDS();

        EntityMapping.nameMap.put("berserk", ArmorRechargeEntity::new);
        berserk = new ArmorRechargeUnitType("berserk"){{

            health = 100;
            speed = 0.5f;
            rotateShooting = true;
            rotateSpeed = 2f;

            flying = false;
            canBoost = false;
        }};

        castor = new PrtUnitType("castor"){{

            constructor = UnitWaterMove::create;

            health = 90;

            speed = 1.1f;
            drag = 0.13f;
            hitSize = 9f;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            trailLength = 20;
            rotateShooting = false;
            armor = 2f;

            hasSpecialAbility = true;
            specialAbility = (Unit unit) -> {
//                PrtFx.destroyCount.at(unit.x,unit.y,0,unit);
//                PrtFx.destroyLights.at(unit.x,unit.y,0,unit);

                Time.run(120f, () -> {
                    Damage.damage(unit.team, unit.x,unit.y,5f * Vars.tilesize, 340f);
                    for(int i = 0;i<0;i++){
                        Call.createBullet(Bullets.fireball, unit.team, unit.x, unit.y, Mathf.random(360f), Bullets.fireball.damage, 1, 0.8f);
                    }
                    Damage.createIncend(unit.x,unit.y,5*Vars.tilesize,10);
                    unit.kill();
                });
            };

        }};

        vega = new PrtUnitType("vega"){{

            constructor = UnitWaterMove::create;

            health = 340;

            speed = 0.9f;
            drag = 0.15f;
            hitSize = 11f;
            armor = 4f;
            accel = 0.3f;
            rotateSpeed = 2.6f;
            rotateShooting = false;

            trailLength = 20;
            trailX = 5.5f;
            trailY = -4f;
            trailScl = 1.9f;

        }};

        nembus = new PrtUnitType("nembus"){{

            constructor = UnitWaterMove::create;

            health = 690;

            speed = 0.85f;
            accel = 0.2f;
            rotateSpeed = 1.8f;
            drag = 0.17f;
            hitSize = 16f;
            armor = 7f;
            rotateShooting = false;

            trailLength = 22;
            trailX = 7f;
            trailY = -9f;
            trailScl = 1.5f;

        }};

        arcturus = new PrtUnitType("arcturus"){{

            constructor = UnitWaterMove::create;

            health = 8600;

            armor = 12f;

            speed = 0.73f;
            drag = 0.17f;
            hitSize = 39f;
            accel = 0.2f;
            rotateSpeed = 1.3f;
            rotateShooting = false;

            trailLength = 50;
            trailX = 18f;
            trailY = -21f;
            trailScl = 3f;

        }};

        betelgeuse = new PrtUnitType("betelgeuse"){{

            constructor = UnitWaterMove::create;

            health = 17900;

            speed = 0.62f;
            drag = 0.18f;
            hitSize = 50f;
            armor = 16f;
            accel = 0.19f;
            rotateSpeed = 0.9f;
            rotateShooting = false;

            trailLength = 70;
            trailX = 23f;
            trailY = -32f;
            trailScl = 3.5f;

        }};

    }

}
