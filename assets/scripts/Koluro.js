const Koluro = new JavaAdapter(Planet, {}, " Koluro", Planets.sun, 3, 1.6);

Koluro.hasAtmosphere = false;
Koluro.meshLoader = () => new SunMesh( Koluro, 4, 5, 0.3, 1.7, 1.2, 1, 1.5, 
                                      Color.valueOf("fbe0ff"), 
                                      Color.valueOf("f1caf7"), 
                                      Color.valueOf("e4aded"), 
                                      Color.valueOf("de98ea"),
                                      Color.valueOf("ce77dd"),
                                      Color.valueOf("d3a4db"));
Koluro.orbitRadius = 27.3;
Koluro.accessible = false;
Koluro.bloom = true;
