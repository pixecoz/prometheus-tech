package prometheus.world.blocks.experimental;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Intersector;
import arc.scene.Group;
import arc.scene.event.ClickListener;
import arc.scene.ui.Button;
import arc.scene.ui.Image;
import arc.scene.ui.ImageButton;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.scene.utils.Elem;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.ctype.ContentType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;

public class UnitTeleport extends Block {
    public Seq<Unit> units = new Seq<>();
    public float pullRadius = 75f;
    public UnitTeleport(String name) {
        super(name);
        update = true;
        solid = true;
        configurable = true;
    }

    public class UnitTeleportBuild extends Building{
        Label amount = new Label("");
        ImageButton send = Elem.newImageButton(Icon.up, ()->{
            Groups.unit.each(unit -> unit.team == this.team && unit.type != UnitTypes.alpha
                            && unit.type != UnitTypes.beta
                            && unit.type != UnitTypes.gamma,
                    unit -> {
                        if(Tmp.v1.set(x, y).dst(unit.x, unit.y) < pullRadius) {
                            units.add(unit);
                            unit.destroy();
                            /*
                            Log.info("Unit ID: " + unit.id);
                            Log.info("Unit class ID: " + unit.classId());
                            Log.info("Class: ID:" + Vars.content.getByID(ContentType.unit, unit.classId()).id
                                     + "UnitType: classID: " + ((UnitType)Vars.content.getByID(ContentType.unit, unit.classId())).id);

                             */
                        }
            });
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
            amount.setText("Total units: " + units.size);
        }
        @Override
        public void buildConfiguration(Table t){
            super.buildConfiguration(t);
            t.table(table -> {
                table.background(Tex.buttonDisabled);
                table.defaults().center();
                table.add(amount).row();
                table.add(send).row();
                table.add(revert);
            });
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
