package prometheus.entities.bullet;

import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.game.Team;
import mindustry.game.Teams;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.world.meta.BlockGroup;

import java.lang.reflect.Field;

public class EmpPointBulletType extends BasicBulletType{
    public float range = 500f;
    public float powerDamage = 5000f;
    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y);

        //TODO: 7.0 recode
        //use Vars.indexer.allBuildings instead of Groups.build.each
        if(!b.absorbed){
            Groups.build.each(entity ->{
                if(entity.team != b.team && entity.block.hasPower){
                    //Note: this distance is different from normal, will try to fix
                    if(Math.abs(Tmp.v1.set(entity.x, entity.y).dst(x, y)) < range){
                        entity.timeScale = 0f;
                        entity.timeScaleDuration = 600f;
                        Fx.unitSpawn.at(entity.x, entity.y);
                    }
                }
            });

        }
    }
}
