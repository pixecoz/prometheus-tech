package prometheus.content;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.Fx;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.BasicBulletType;

import static mindustry.Vars.*;

import static prometheus.staff.PrtFuncs.print;

public class PrtUnitTypes implements ContentList {

    public static  UnitType aboba;
    public static float pressTime, longPressTime = 10f;


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
                    if (Core.input.isTouched()){
                        pressTime += Time.delta;
                        if(pressTime > longPressTime){
                            action();
                            pressTime = 0f;
                        }
                    } else {
                        pressTime = 0f;
                    }
                } else {
                    if(Core.input.keyTap(KeyCode.h)){
                        action();
                    }
                }
            }

        });

        print("prtunits loaded");

    }

    private static void action(){
        Fx.bigShockwave.at(player.x,player.y);
    }

}
