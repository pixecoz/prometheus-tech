#define HIGHP

uniform sampler2D u_texture;
uniform float u_time;

varying vec2 v_texCoords;

void main(){
    vec2 coord = v_texCoords.xy;
    float c = step(0.4,sin(coord.x*5.+coord.y*20.0+u_time*10.0+sin(u_time+coord.x*3.0+coord.y*1.0)*4.)+cos(coord.x*20.+coord.y*10.+u_time*4.0)*2.);

    coord.x+=cos(-u_time+coord.y*60.0)*0.01;
    coord.y+=cos(-u_time*4.0+coord.x*45.0)*0.01;

    vec4 color = texture2D(u_texture, coord);
    vec3 mul = vec3(0.7-c,1.0,0.99);
    color.rgb*=mul;

    gl_FragColor = color;
}