package prometheus.content;


import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import prometheus.entities.bullet.EmpPointBulletType;

import static mindustry.Vars.*;


public class PrtBullets {
    //Note: after 7.0 release ths will be useless, so
    //TODO: 7.0 remove
    public static BulletType empPointBulletType = new EmpPointBulletType(){{
        shootEffect = PrtFx.shootGreen;
        //hitEffect = Fx.instHit;
        smokeEffect = Fx.smokeCloud;
        //trailEffect = Fx.instTrail;
        despawnEffect = Fx.instBomb;
        //trailSpacing = 20f;
        width = 20f;
        height = 10f;
        damage = 1350;
        buildingDamageMultiplier = 0.25f;
        speed = 500f;
        hitShake = 6f;
    }};
}
