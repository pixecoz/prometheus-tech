package prometheus.type;

import arc.func.Cons;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import prometheus.entities.abilities.active.ActiveAbility;

public class PrtUnitType extends UnitType {

    public ActiveAbility activeAbility;

    public PrtUnitType(String name) {
        super(name);
    }

    public void useActiveAbility(Unit unit) {
        activeAbility.activate(unit);
        unit.controlling.each(u->{
            if(u.type instanceof PrtUnitType && ((PrtUnitType)u.type).hasActiveAbility()){
                ((PrtUnitType)u.type).useActiveAbility(u);
            }
        });
    }

    public boolean hasActiveAbility(){
        return activeAbility != null;
    }

}
