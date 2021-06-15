package prometheus.world.blocks.turrets;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import prometheus.entities.TurretEffects;

public class ChargeTurret extends ItemTurret {
    public ChargeTurret(String name) {
        super(name);
    }
    public class ChargeTurretBuild extends ItemTurretBuild{
        public boolean effectDone = false;
        @Override
        public void draw(){
            super.draw();
            if(target != null && !effectDone && charging){
                effectDone = true;
                TurretEffects.beamsCharge(x, y, target, rotation - 90);
            }
            else if(target == null){
                effectDone = false;
            }
        }
    }
}
