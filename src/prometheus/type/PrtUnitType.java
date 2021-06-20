package prometheus.type;

import arc.func.Cons;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class PrtUnitType extends UnitType {

    public boolean hasSpecialAbility;
    public Cons<Unit> specialAbility;

    public PrtUnitType(String name) {
        super(name);
    }

    public void specialAbility(Unit unit) {
        if (specialAbility != null) specialAbility.get(unit);
    }

}
