package prometheus.world.blocks.experimental;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.ImageButton;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.scene.utils.Elem;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.ctype.ContentType;
import mindustry.gen.*;
import mindustry.graphics.FloorRenderer;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.LExecutor;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import mindustry.Vars.*;
import mindustry.graphics.FloorRenderer.*;

public class UnitTeleport extends Block {
    public Seq<Unit> units = new Seq<>();
    public float pullRadius = 75f;
    public float chargeTime = 240f;
    public float fractureRadius = pullRadius;
    public UnitTeleport(String name) {
        super(name);
        update = true;
        solid = true;
        configurable = true;
    }

    public class UnitTeleportBuild extends Building{
        float countdown = 0f;
        Label amount = new Label("");
        ImageButton send = Elem.newImageButton(Icon.up, ()->{
            if(countdown <= 0) {
                countdown = chargeTime / 60f;
                Time.run(chargeTime, () -> {
                    Groups.unit.each(unit -> unit.team == this.team && unit.type != UnitTypes.alpha
                                    && unit.type != UnitTypes.beta
                                    && unit.type != UnitTypes.gamma,
                            unit -> {
                                if (Tmp.v1.set(x, y).dst(unit.x, unit.y) < pullRadius) {
                                    units.add(unit);
                                    unit.destroy();
                                    Vars.world.tiles.eachTile(t -> {
                                        if(Tmp.v1.set(t.worldx(), t.worldy()).dst(x, y) < fractureRadius && t.build != this
                                            && Mathf.random() < 0.01f){
                                            Call.setFloor(t, Blocks.space, Blocks.air);
                                            t.setBlock(Blocks.air);
                                        }
                                    });
                                    //Vars.renderer.blocks.floor.clearTiles();
                                    try {
                                        Method refresh = Vars.renderer.blocks.floor.getClass().getDeclaredMethod("cacheChunk", int.class, int.class);
                                        refresh.setAccessible(true);
                                        Log.info("X: " + x / 32 + "\nY: " + y / 32);
                                        //32 * 8
                                        refresh.invoke(Vars.renderer.blocks.floor, (int) (x / 256), (int) (y / 256));
                                    }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
                                        Log.err(e);
                                    }
                                }
                            });
                });
            }
        });
        ImageButton revert = Elem.newImageButton(Icon.down, ()->{
            units.each(unit -> {
                unit.set(x + 10, y + 10);
                unit.add();
            });
            units.clear();
        });
        @Override
        public void draw(){
            super.draw();
            Draw.z(Layer.effect);
            Draw.color(Pal.lighterOrange);
            Lines.stroke(3f);
            Lines.circle(x, y, pullRadius);
        }
        @Override
        public void update(){
            super.update();
            if(!consValid()){
                send.setDisabled(true);
                revert.setDisabled(true);
            }
            else{
                send.setDisabled(false);
                revert.setDisabled(false);
            }
            if(countdown > 0){
                countdown -= 0.016;
                amount.setText("Countdown: " + Mathf.round(countdown * 100, 1f) / 100);
            }
            else{
                amount.setText("Total units: " + units.size);
            }
        }
        @Override
        public void buildConfiguration(Table t){
            super.buildConfiguration(t);
            t.background(Tex.buttonDisabled);
            t.table(table -> {
                table.defaults().left();
                table.add(amount);
            }).row();
            t.defaults().center();
            t.add(send).row();
            t.add(revert);
        }
        //fuck id's
        //all my homies use 'name'
        @Override
        public void write(Writes w){
            super.write(w);
            w.i(units.size);
            units.each(unit -> {
                //w.i(unit.classId());
                w.str(unit.type.name);
            });
        }
        @Override
        public void read(Reads r, byte revision){
            super.read(r, revision);
            int size = r.i();
            units = new Seq<>();
            for (int i = 0; i < size; i++){
                //int id = r.i();
                //Log.info(id);
                //units.add(((UnitType)Vars.content.getByID(ContentType.unit, id)).create(team));
                //units.add((Unit) EntityMapping.idMap[id].get());
                String name = r.str();
                units.add(((UnitType) (Vars.content.getByName(ContentType.unit, name))).create(team));
            }
        }
    }
}
