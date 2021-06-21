package prometheus.entities.bullet;

import arc.math.geom.Geometry;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.game.Team;
import mindustry.game.Teams;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.world.meta.BlockGroup;
import prometheus.content.PrtFx;

import java.lang.reflect.Field;

public class EmpPointBulletType extends BulletType {
    public float range = 100f;
    public float powerDamage = 200f;

    private static float cdist = 0f;
    private static Unit result;
    public float trailSpacing = 10f;
    public Effect secondHit = PrtFx.empShoot;
    public EmpPointBulletType(){
        scaleVelocity = true;
        lifetime = 100f;
        collides = false;
        keepVelocity = false;
        backMove = false;
    }
    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y);

        //TODO: 7.0 recode
        //use Vars.indexer.allBuildings instead of Groups.build.each
    }
    @Override
    public void init(Bullet b){
        super.init(b);

        float px = b.x + b.lifetime * b.vel.x,
                py = b.y + b.lifetime * b.vel.y,
                rot = b.rotation();

        Geometry.iterateLine(0f, b.x, b.y, px, py, trailSpacing, (x, y) -> {
            trailEffect.at(x, y, rot);
        });

        b.time = b.lifetime;
        b.set(px, py);

        //calculate hit entity

        cdist = 0f;
        result = null;
        float range = 1f;

        Units.nearbyEnemies(b.team, px - range, py - range, range*2f, range*2f, e -> {
            if(e.dead()) return;

            e.hitbox(Tmp.r1);
            if(!Tmp.r1.contains(px, py)) return;

            float dst = e.dst(px, py) - e.hitSize;
            if((result == null || dst < cdist)){
                result = e;
                cdist = dst;
            }
        });

        if(result != null){
            b.collision(result, px, py);
        }else{
            Building build = Vars.world.buildWorld(px, py);
            if(build != null && build.team != b.team){
                build.collision(b);
                Groups.build.each(entity ->{
                    if(entity.block.hasPower && entity.team != b.team){
                        //Note: this distance is different from normal, will try to fix
                        if(Math.abs(Tmp.v1.set(entity.x, entity.y).dst(px, py)) < this.range){
                            entity.timeScale = 0f;
                            entity.damage(powerDamage);
                            entity.timeScaleDuration = 600f;
                        }
                    }
                });
            }
        }

        secondHit.at(px, py);

        b.remove();

        b.vel.setZero();
    }
}
