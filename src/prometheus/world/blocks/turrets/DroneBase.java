package prometheus.world.blocks.turrets;

import arc.Core;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;

import mindustry.type.*;
import mindustry.ui.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;

import mindustry.world.Block;
import mindustry.world.consumers.ConsumeItemFilter;

import prometheus.content.PrtFx;
import prometheus.entities.PodEffects;
import prometheus.world.meta.PodStat;

import static mindustry.Vars.*;

public class DroneBase extends Block {
    public static TextureRegion podRegion;
    public float range = 200f;
    public float buildTime = 20f;

    public Effect shootEffect = PrtFx.orbitalLaserCharge;
    public int shootReload = 60;
    public int nextTimer = timers++;
    public int maxShots = 3;
    public ObjectMap<Item, PodStat> ammoTypes;
    //fall settings
    public float fallFireRange = 48f;
    public float fallShakeIntensity = 50f;
    public float fallShakeDuration = 120f;
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
            podRegion = Core.atlas.find(name + "-pod");
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
        bars.add("countdown", (DroneBaseBuild entity) -> new Bar("Build countdown", Pal.lancerLaser, () -> (float) entity.countDown / buildTime));
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.shootRange, range / tilesize, StatUnit.blocks);
        stats.add(Stat.ammo, table -> {
            table.row();
            Seq<Item> keys = ammoTypes.keys().toSeq();
            keys.sort();
            for (Item i : keys){
                PodStat stat = ammoTypes.get(i);

                //TODO: 7.0 recode
                //also if we use everything that is not Cicon.full, we recieve uiIcon
                table.image(i.icon(Cicon.large)).size(3 * 8).padRight(4).right().top();
                table.add(i.localizedName).padRight(10).left().top();

                table.table(bt -> {
                    bt.left().defaults().padRight(3).left();
                    bt.add(Core.bundle.format("bullet.damage", stat.damage));
                    bt.row();
                    bt.add(Core.bundle.format("pod.damageradius", stat.range));
                    bt.row();
                    bt.add(Core.bundle.format("pod.neededitems", stat.itemCap));
                    bt.row();
                    bt.add(Core.bundle.format("pod.maxshots", stat.maxShots));
                    if (stat.speedScale != 1f){
                        bt.row();
                        bt.add(Core.bundle.format("bullet.reload", Strings.autoFixed(stat.speedScale, 2)));
                    }
                    if(stat.effect != StatusEffects.none) {
                        bt.row();
                        bt.add("[stat]" + stat.effect.name + "[]");
                    }
                    bt.row();
                }).padTop(-9).left().get().background(Tex.underline);
                table.row();
            }
        });
    }
    public void ammo(Object... objects){
        ammoTypes = OrderedMap.of(objects);
    }

    @Override
    public void init(){
        consumes.add(new ConsumeItemFilter(i -> ammoTypes.containsKey(i)){
            @Override
            public void build(Building tile, Table table){
                MultiReqImage image = new MultiReqImage();
                //TODO: 7.0 remove
                content.items().each(i -> filter.get(i), item -> image.add(new ReqImage(item.icon(Cicon.tiny),
                        () -> tile instanceof DroneBaseBuild && !tile.items().empty() && tile.items.has(item, 1))));
                table.add(image).size(8 * 4);
            }

            @Override
            public boolean valid(Building entity){
                //valid when there's any ammo in the turret
                return entity instanceof DroneBaseBuild && entity.items.total() == 15;
            }

            @Override
            public void display(Stats stats){
                //don't display
            }
        });

        super.init();
    }

    public class DroneBaseBuild extends Building {
        public float countDown = buildTime;
        public boolean launched = false;
        public boolean buildStatus = false;
        public boolean ready = false;
        public Teamc target;
        public float speedScl = 0;
        public float time = 0f;
        public int shots = maxShots;
        public PodStat current;

        @Override
        public boolean shouldConsume(){
            return true;
        }

        public void launch() {
            launched = true;
            ready = true;
            shots = 0;
            PrtFx.launchPodLaunch.at(x, y);
            Fx.launch.at(this);
            Effect.shake(3f, 3f, this);
        }

        public void reloadLaunch(){
            launched = false;
            countDown = buildTime;
            PodEffects.podFallEffect(x + Mathf.random(-range, range), y + Mathf.random(-range, range), fallFireRange, fallShakeIntensity, fallShakeDuration);
        }

        @Override
        public void updateTile() {
            //TODO: recode this for better look
            if(shots == maxShots && launched)
                reloadLaunch();

            if(countDown != 0){
                if(!buildStatus){
                    for (Item t : ammoTypes.keys().toSeq()) {
                        if (items.has(t, ammoTypes.get(t).itemCap)){
                            consume();
                            items.remove(ItemStack.with(t, ammoTypes.get(t).itemCap));
                            current = ammoTypes.get(t);
                            maxShots = current.maxShots;
                            shots = maxShots;
                            buildStatus = true;
                            break;
                        }
                    }
                }
                else if (timer(0, 60)) {
                    countDown--;
                }
            }
            else{
                buildStatus = false;
                if(!launched)
                    launch();
            }
            if(launched) {
                if(!ready){
                    if(timer(nextTimer, shootReload / current.speedScale)){
                        ready = true;
                    }
                }
                if (ready) {
                    target = Units.closestTarget(team, x, y, range);
                    if (target != null) {
                       shots++;
                       ready = false;
                       if (current.hitEffect == null)
                           shootEffect.at(target.x(), target.y());
                       else {
                           current.hitEffect.at(target.x(), target.y());
                       }
                       //TODO: 7.0 fix
                        PodEffects.podDust(target.x(), target.y());
                       Damage.damage(team, target.x(), target.y(), current.range, current.damage);

                       Damage.status(team, target.x(), target.y(), current.range, current.effect, 60f * 8, true, true);
                    }
                }
            }
            //constructing animation
            if (!launched){
                if(countDown != 0 && buildStatus) {
                    time += edelta() * speedScl;
                    speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
                }
                else{
                    speedScl = Mathf.lerpDelta(speedScl, 0f, 0.05f);
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

        @Override
        public void write(Writes write){
            super.write(write);
            write.bool(launched);
            write.bool(buildStatus);
            write.i(shots);
            write.f(countDown);
            write.i(ammoTypes.findKey(current, false).id);
        }
        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            launched = read.bool();
            buildStatus = read.bool();
            shots = read.i();
            countDown = read.f();
            current = ammoTypes.get(content.item(read.i()));
        }
    }
}
