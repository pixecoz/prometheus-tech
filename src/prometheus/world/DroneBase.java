package prometheus.world;

import arc.Core;
import arc.math.Mathf;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import arc.graphics.g2d.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;

import prometheus.content.PrtFx;
import static mindustry.Vars.*;

public class DroneBase extends Block {
    public TextureRegion podRegion;
    public float range = 200f;
    public float buildTime = 20f;
    public Effect shootEffect = PrtFx.orbitalLaserCharge;
    public int shootReload = 60;
    public int nextTimer = timers++;
    public int maxShots = 3;
    public float damage = 5000f;
    public DroneBase(String name) {
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.turrets;
    }
    @Override
    public void load(){
        super.load();
        if(podRegion == null)
            podRegion = Core.atlas.find("launchpod");
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.accent);
    }
    @Override
    public void setBars(){
        super.setBars();
        bars.add("shots", (DroneBaseBuild entity) -> new Bar("Shots", Pal.accent, ()-> (1f - (float) entity.shots / maxShots) - 0.01f));
    }

    public class DroneBaseBuild extends Building {
        public float countDown = buildTime;
        public boolean launched = false;
        public boolean buildStatus = false;
        public Teamc target;
        public float speedScl = 0;
        public float time = 0f;
        public int shots = 0;

        @Override
        public boolean shouldConsume(){
            return true;
        }

        public void launch() {
            launched = true;
            shots = 0;
            LaunchPayload entity = LaunchPayload.create();
            entity.set(this);
            entity.lifetime(120f);
            entity.team(team);
            entity.add();
            Fx.launch.at(this);
            Effect.shake(3f, 3f, this);
        }

        public void reloadLaunch(){
            launched = false;
            countDown = buildTime;
            PrtFx.launchPodFall.at(x + Mathf.random(-range, range), y + Mathf.random(-range, range));
        }

        @Override
        public void updateTile() {
            //TODO: recode this for better look
            if(shots == maxShots && launched){
                reloadLaunch();
            }
            if(countDown != 0){
                if(!buildStatus){
                    if(consValid()){
                        consume();
                        buildStatus = true;
                    }
                }
                else if (timer(0, 60)) {
                    countDown--;
                }
            }
            else{
                buildStatus = false;
            }

            if(timer(nextTimer, shootReload)) {
                target = Units.closestTarget(team, x, y, range);
                if (target != null) {
                    if (!launched && countDown == 0)
                        launch();
                    if (launched) {
                        shots++;
                        shootEffect.at(target.x(), target.y());
                        Call.createBullet(Bullets.heavyCryoShot, team, target.x(), target.y(), 0, damage, 0, 0.5f);
                    }
                }
            }
            //constructing animation
            if (!launched){
                if(countDown == 0)
                    speedScl = Mathf.lerpDelta(speedScl, 0f, 0.05f);
                else{
                    time += edelta() * speedScl;
                    speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
                }
            }
        }

        @Override
        public void draw() {
            super.draw();
            if(!launched) {
                if (countDown == 0) {
                    Draw.rect(podRegion, x, y);
                }
                else{
                    //idk how this works... but this works
                    Draw.draw(Layer.blockOver, ()->Drawf.construct(this, podRegion, 0, 1f - countDown / buildTime, speedScl, time));
                }
            }
        }
        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, range, team.color);
        }
    }
}
