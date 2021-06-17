package prometheus.entities.units;

import mindustry.gen.UnitEntity;
import prometheus.entities.ExtendedUnitType;

import static prometheus.PrtFuncs.print;

public class Aboltus extends UnitEntity {
    @Override
    public int classId() {
        return ((ExtendedUnitType)type).classId;
    }

    @Override
    public void update() {
        super.update();
        print("abobus");
    }

    @Override
    public String toString() {
        return "Aboltus#"+id;
    }
}
