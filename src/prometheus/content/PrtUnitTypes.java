package prometheus.content;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Bullets;
import mindustry.entities.Damage;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ctype.ContentList;
import prometheus.type.PrtUnitType;

public class PrtUnitTypes implements ContentList {

    //naval
    public static UnitType castor, vega, nembus, arcturus, betelgeuse;


    public void load(){

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


    }

}
