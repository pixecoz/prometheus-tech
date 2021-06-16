package prometheus.world.blocks.turrets;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.turrets.LaserTurret;

public class SomeTurret extends LaserTurret {

    public float animationSpeed = 0.02f;
    float mouthLength = 5f;
    public TextureRegion mouthRegion;
    public TextureRegion bodyRegion;

    public SomeTurret(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        mouthRegion = Core.atlas.find(name+"-mouth");
        bodyRegion = Core.atlas.find(name+"-body");
    }

    public class SomeTurretBuild extends LaserTurretBuild {

        public float progress;

        @Override
        public void updateTile() {
            super.updateTile();

            if(power.status > 0.01 && (target != null || logicControlled() || isControlled())){
                progress = Mathf.clamp(progress + animationSpeed * edelta(), 0f, 1f);
            } else {
                progress = Mathf.clamp(progress - animationSpeed * Time.delta, 0f, 1f);
            }

        }

        @Override
        public void draw() {

            Draw.rect(baseRegion, x, y);
            Draw.color();

            Draw.z(Layer.turret);
            tr2.trns(rotation, -recoil);

            if(progress == 0){

                Drawf.shadow(region, x + tr2.x - elevation, y + tr2.y - elevation, rotation - 90);
                drawer.get(this);

                if(heatRegion != Core.atlas.find("error")){
                    heatDrawer.get(this);
                }

            } else {

                Tmp.v1.trns(rotation,progress*mouthLength);

//                Drawf.shadow(region, x + tr2.x - elevation, y + tr2.y - elevation, rotation - 90);

                Draw.rect(mouthRegion,x+tr2.x+Tmp.v1.x,y+tr2.y+Tmp.v1.y,rotation-90);
                Draw.rect(bodyRegion,x+tr2.x,y+tr2.y,rotation-90);


//                if(heatRegion != Core.atlas.find("error")){
//                    heatDrawer.get(this);
//                }
            }

        }
    }

}
