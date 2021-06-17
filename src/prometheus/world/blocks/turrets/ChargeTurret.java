package prometheus.world.blocks.turrets;

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
