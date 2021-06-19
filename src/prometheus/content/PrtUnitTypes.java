package prometheus.content;

import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.Damage;
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

            speed = 1.1f;
            drag = 0.13f;
            hitSize = 9f;
            health = 280;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            trailLength = 20;
            rotateShooting = false;
            armor = 2f;

            hasSpecialAbility = true;
            specialAbility = (Unit unit) -> {
                PrtFx.destroyCount.at(unit.x,unit.y,0,unit);
                PrtFx.destroyLights.at(unit.x,unit.y,0,unit);

                Time.run(60f, () -> {
                    Damage.damage(unit.team, unit.x,unit.y,25f * Vars.tilesize, 1500f);
                    unit.kill();
                });
            };

        }};


    }

}
