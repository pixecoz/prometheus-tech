package prometheus.entities.units;

import arc.util.Interval;
import arc.util.Log;
import arc.util.Time;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import prometheus.tools.UnitAnnotationProcessor;

public class RespawnEntity extends MechUnit {
    public boolean respawned;

    @Override
    public void killed() {
        super.killed();
        if (!respawned) {
            Time.run(180f, () -> {
                Unit newUnit = type().spawn(team, x, y);
                newUnit.health(maxHealth() * 0.3f);
                ((RespawnEntity) newUnit).respawned = true;
            });
        }

    }

    @Override
    public void update() {
        super.update();
        if (Lol.timer.get(120)) {
            Log.info("RespawnEntity " + id);
        }
    }

    @Override
    public int classId(){
        return UnitAnnotationProcessor.idMap.get(this.getClass());
    }
}

class Lol {
    public static Interval timer = new Interval(10);
}