package prometheus.world.blocks.turrets;

import arc.util.Log;
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
            Log.info(chargeTime);
            super.draw();
            if(target != null && !effectDone){
                effectDone = true;
                TurretEffects.beamsCharge(x, y, target.x(), target.y(), rotation - 90);
            }
            else if(target == null){
                effectDone = false;
            }
        }
    }
}
