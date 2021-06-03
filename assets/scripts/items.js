///ITEMS///

function newItem(name, hardness, flammability, radioactivity, explosiveness, color){
    const item = extendContent(Item, name, {});
	item.color = Color.valueOf(color);
	item.hardness = hardness;
	item.flammability = flammability;
	item.radioactivity = radioactivity;
	item.explosiveness = explosiveness;
	return item;
};

const platinum = newItem("platinum", 0.0, 0.0, 0.02, 0.0, "#8276a6");
