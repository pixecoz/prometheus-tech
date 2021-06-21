package prometheus.content;

import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import prometheus.entities.bullet.EmpPointBulletType;

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
        damage = 1350;
        buildingDamageMultiplier = 0.25f;
        speed = 500f;
        hitShake = 6f;
    }};
}
