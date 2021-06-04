///ITEMS///

function newItem(name, hardness, cost, flammability, radioactivity, explosiveness, color){
    const item = extendContent(Item, name, {});
	item.color = Color.valueOf(color);
	item.hardness = hardness;
	item.cost = cost;
	item.flammability = flammability;
	item.radioactivity = radioactivity;
	item.explosiveness = explosiveness;
	return item;
};

const platinum = newItem("platinum", 3, 1, 0.0, 0.0, 0.0, "bfd7d9");
const plutonium = newItem("plutonium", 3, 1, 0.0, 0.9, 0.0, "b1ddc6");
const magnetite = newItem("magnetite", 4, 2, 0.0, 0.9, 0.0, "d38989");
