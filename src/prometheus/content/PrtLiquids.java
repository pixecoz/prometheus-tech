package prometheus.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class PrtLiquids implements ContentList {
    public static Liquid liquidPlatinum, purifiedOil;

    @Override
    public void load() {
        liquidPlatinum = new Liquid("liquid-platinum") {
            {
                this.localizedName = "Liquid Platinum";
                this.description = "Slurry with high heat capacity.";
                this.temperature = 0.5f;
                this.viscosity = 0.6f;
                this.color = Color.valueOf("5b5780");
            }
        };

        purifiedOil = new Liquid("purified-oil") {
            {
                this.localizedName = "Purified Oil";
                this.description = "";
                this.temperature = 0.1f;
                this.heatCapacity = 0;
                this.flammability = 0.6f;
                this.explosiveness = 0.2f;
                this.viscosity = 0.2f;
                this.color = Color.valueOf("D04954");
                lightColor = Color.valueOf("FF5C77").a(0.5f);
            }
        };
    }
}
