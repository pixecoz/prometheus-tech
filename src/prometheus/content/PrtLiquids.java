package prometheus.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class PrtLiquids implements ContentList {
    public static Liquid liquidPlatinum,  electrifiedSlurry;

    @Override
    public void load() {
        liquidPlatinum = new Liquid("liquid-platinum") {
            {
                this.localizedName = "Liquid Platinum";
                this.description = "Slurry with high heat capacity.";
                this.temperature = 0.5f;
                this.viscosity = 0.6f;
                this.color = Color.valueOf("ABC4D0");
            }
        };

        electrifiedSlurry = new Liquid("electrified-slurry") {
            {
                this.localizedName = "Electrified Slurry";
                this.description = "Hot and charged slurry, can be used for reactors";
                this.temperature = 0.8f;
                this.heatCapacity = 0;
                this.flammability = 0.6f;
                this.explosiveness = 0.1f;
                this.viscosity = 0.6f;
                this.color = Color.valueOf("ECF170");
                lightColor = Color.valueOf("F9FF77").a(0.8f);
            }
        };
    }
}
