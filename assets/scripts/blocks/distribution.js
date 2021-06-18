///DISTRIBUTION///

const platinumRouter = extendContent(Router, "platinum-router", {});
platinumRouter.buildCostMultiplier = 4;
platinumRouter.Category = Category.distribution;
platinumRouter.BuildVisibility = BuildVisibility.shown;

const magnetiteConveyor = extendContent(ItemBridge, "magnetite-conveyor", {});
magnetiteConveyor.range = 16;
magnetiteConveyor.hasPower = true;
//magnetiteConveyor.consumes.power(0.30f);
magnetiteConveyor.Category = Category.distribution;
magnetiteConveyor.BuildVisibility = BuildVisibility.shown;

Events.on(ClientLoadEvent, cons(e=>{
    platinumRouter.requirements = ItemStack.with(Vars.content.getByName(ContentType.item,"prometheus-platinum"), 3);
    magnetiteConveyor.requirements = ItemStack.with(Vars.content.getByName(ContentType.item,"prometheus-magnetite"), 6, Items.silicon, 8, Items.lead, 12, Items.graphite, 12);
}));

const abomus = "aboba" + "amogus" + "anukus" + "beLikus"
