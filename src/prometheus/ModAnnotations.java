package prometheus;

import arc.func.Prov;
import mindustry.gen.Entityc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ModAnnotations {
    //todo: runtime?
    // да да, рантайм
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface UnitDef{
        public Class<? extends Unit> value();
    }
}
