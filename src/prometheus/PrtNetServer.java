package prometheus;

import arc.util.io.ReusableByteOutStream;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.core.NetServer;
import mindustry.gen.Building;
import mindustry.gen.Call;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

//in order of communism!
public class PrtNetServer{
    public static ReusableByteOutStream syncStream;
    public static DataOutputStream dataStream;
    public PrtNetServer(){
    }
    public static void writeBlockSnapshot(NetServer net, Building entity) throws IllegalAccessException, NoSuchFieldException, IOException {
        //super hacker
        Field ss = net.getClass().getDeclaredField("syncStream");
        ss.setAccessible(true);
        syncStream = (ReusableByteOutStream) ss.get(net);
        dataStream = new DataOutputStream(syncStream);

        syncStream.reset();
        dataStream.writeInt(entity.pos());
        dataStream.writeShort(entity.block.id);
        entity.writeAll(Writes.get(dataStream));
        dataStream.close();

        byte[] stateBytes = syncStream.toByteArray();
        Call.blockSnapshot((short)1, (short)stateBytes.length, Vars.net.compressSnapshot(stateBytes));
    }
}
