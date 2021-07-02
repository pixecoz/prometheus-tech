package prometheus.tools;

import arc.func.Prov;
import arc.struct.ObjectMap;
import arc.struct.ObjectMap.Entry;
import arc.struct.ObjectSet;
import arc.util.Log;
import mindustry.gen.*;
import mindustry.mod.Mod;
import mindustry.type.UnitType;
import prometheus.ModAnnotations;
import prometheus.content.PrtUnitTypes;
import prometheus.entities.units.ArmorRechargeEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class UnitAnnotationProcessor {
    //Meep of faith and BetaMindy, thanks
    public static Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] unitList; //= new Entry[]{
    //        entry(ArmorRechargeEntity.class, ArmorRechargeEntity::new)
    //};
    public static ObjectMap<Class<? extends Entityc>, Integer> idMap = new ObjectMap<>();
    public static int nextId = -1;

    public static void putIDS(){
        for(int i = 0; i < EntityMapping.idMap.length; i++){
            if(EntityMapping.idMap[i] == null){
                //found free id
                nextId++;
                EntityMapping.idMap[i] = unitList[nextId].value;
                idMap.put(unitList[nextId].key, i);
                if (nextId >= unitList.length-1)
                    break;
            }
        }
    }

    public static ObjectMap.Entry entry(Class<? extends Entityc> clazz, Prov<? extends Entityc> prov){
        ObjectMap.Entry<Class<? extends Entityc>, Prov<? extends Entityc>> entry = new ObjectMap.Entry<>();
        entry.key = clazz;
        entry.value = prov;
        return entry;

    }
    public static void setMapping(Class<? extends PrtUnitTypes> clazz)throws IllegalAccessException{
        Field[] fields = clazz.getFields();
        int i = 0;
        for (Field f : fields)
            if(f.isAnnotationPresent(ModAnnotations.UnitDef.class))
                i++;
        unitList = new Entry[i];
        i = 0;
        for (Field f : fields){
            if(f.isAnnotationPresent(ModAnnotations.UnitDef.class)){
                UnitType ut = (UnitType) f.get(clazz);
                Class<? extends Unit> a = f.getAnnotation(ModAnnotations.UnitDef.class).value();
                Prov<? extends Entityc> prov = ut.constructor;
                unitList[i] = entry(a, prov);
                EntityMapping.nameMap.put(ut.name, prov);
                i++;
            }
        }
        putIDS();
    }
}
