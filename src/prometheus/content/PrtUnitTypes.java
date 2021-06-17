package prometheus.content;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.Fx;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.BasicBulletType;

import prometheus.entities.ExtendedUnitType;
import prometheus.entities.units.Aboltus;

import static prometheus.PrtFuncs.print;

public class PrtUnitTypes implements ContentList {

    public static  UnitType aboba;

    public void load(){
        print("load prtunits");

        aboba = new ExtendedUnitType("aboltus"){{

            constructor = Aboltus::new;
            EntityMapping.nameMap.put(this.name, this.constructor);

            for(int i = 0; i<EntityMapping.idMap.length; i++){
                if(EntityMapping.idMap[i] == null){
                    EntityMapping.idMap[i] = constructor;
                    classId = i;
                    break;
                }
            }

            speed = 3f;
            accel = 0.08f;
            drag = 0.01f;
            flying = true;
            health = 75;
            engineOffset = 5.5f;
            range = 140f;
            targetAir = false;
            commandLimit = 4;
            circleTarget = true;

            weapons.add(new Weapon(){{
                y = 0f;
                x = 2f;
                reload = 13f;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.5f, 9){{
                    width = 7f;
                    height = 9f;
                    lifetime = 45f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    ammoMultiplier = 2;
                }};
                shootSound = Sounds.pew;
            }});

        }

            @Override
            public void update(Unit unit) {
                super.update(unit);
                print(unit);
            }
        };

        print("prtunits loaded");
        print(aboba);

    }
}
