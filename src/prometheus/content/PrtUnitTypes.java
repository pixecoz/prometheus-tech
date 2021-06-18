package prometheus.content;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.Fx;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.BasicBulletType;

import static mindustry.Vars.*;

import static prometheus.PrtFuncs.print;

public class PrtUnitTypes implements ContentList {

    public static  UnitType aboba;

    public void load(){
        print("load prtunits");

        aboba = new UnitType("aboltus"){{

            constructor = UnitEntity::create;

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

        Events.run(EventType.Trigger.update,() ->{
            if(player.unit().type == aboba){
                if(mobile){

                    int i = 1+1;
                } else {
                    if(Core.input.keyTap(KeyCode.h)){
                        Fx.bigShockwave.at(player.x,player.y);
                    }
                }
            }

        });

        print("prtunits loaded");

    }
}
