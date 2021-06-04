const Koluro = new JavaAdapter(Planet, {}, " Koluro", Planets.sun, 3, 1.6);

Koluro.hasAtmosphere = false;
Koluro.meshLoader = () => new SunMesh( Koluro, 4, 5, 0.3, 1.7, 1.2, 1, 1.5, Color.valueOf("EB00B8"), Color.valueOf("FF80E3"), Color.valueOf("CC80FF"), Color.valueOf("F6A3FF"));
Koluro.orbitRadius = 21.3;
Koluro.accessible = false;
Koluro.bloom = true;
