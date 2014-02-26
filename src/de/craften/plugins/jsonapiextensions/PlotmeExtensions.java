package de.craften.plugins.jsonapiextensions;

import com.alecgorge.minecraft.jsonapi.api.APIMethodName;
import com.alecgorge.minecraft.jsonapi.api.JSONAPICallHandler;
import net.minecraft.server.v1_6_R3.Position;
import org.bukkit.Material;
import org.bukkit.Server;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class PlotmeExtensions implements JSONAPICallHandler {
    private Server server;

    public PlotmeExtensions(Server server) {
        this.server = server;
    }

    private Server getServer() {
        return this.server;
    }

    public boolean willHandle(APIMethodName methodName) {
        return methodName.matches("getPlot") || methodName.matches("isPlotEmpty");
    }

    public Object handle(APIMethodName methodName, Object[] args) {
        if (methodName.matches("getPlot") && args.length == 2) {
            try {
                final long x = (Long) args[0];
                final long z = (Long) args[1];
                return getServer()
                        .getScheduler()
                        .callSyncMethod(CraftenJsonApiExtender.instance,
                                new Callable<String>() {
                                    @Override
                                    public String call() throws Exception {
                                        return Arrays.deepToString(getPlotData(
                                                x, z));
                                    }
                                }).get();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (methodName.matches("isPlotEmpty") && args.length == 2) {
            try {
                final long x = (Long) args[0];
                final long z = (Long) args[1];
                return getServer()
                        .getScheduler()
                        .callSyncMethod(CraftenJsonApiExtender.instance,
                                new Callable<Boolean>() {
                                    @Override
                                    public Boolean call() throws Exception {
                                        return isPlotEmpty(x, z);
                                    }
                                }).get();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return false;
    }

    private Material[][][] getPlotData(long plotX, long plotZ) {
        final Position start = getPlotStart(plotX, plotZ);
        final Position end = getPlotEnd(plotX, plotZ);

        Material[][][] result = new Material[(int) (end.getX() - start.getX()) + 1][(int) (end
                .getY() - start.getY()) + 1][(int) (end.getZ() - start.getZ()) + 1];

        for (int x = 0; x < result.length; x++)
            for (int y = 0; y < result[x].length; y++)
                for (int z = 0; z < result[x][y].length; z++) {
                    result[x][y][z] = getServer()
                            .getWorld("Plotme")
                            .getBlockAt((int) start.getX() + x,
                                    (int) start.getY() + y,
                                    (int) start.getZ() + z).getType();
                }

        return result;
    }

    private boolean isPlotEmpty(long plotX, long plotZ) {
        Material[][][] blocks = getPlotData(plotX, plotZ);
        for (int x = 0; x < blocks.length; x++)
            for (int y = 0; y < blocks[x].length; y++)
                for (int z = 0; z < blocks[x][y].length; z++) {
                    if (y >= 1 && y < 64 && blocks[x][y][z] != Material.DIRT) {
                        return false;
                    } else if (y > 65 && blocks[x][y][z] != Material.AIR) {
                        return false;
                    }
                }
        return true;
    }

    private Position getPlotStart(long x, long z) {
        return new Position(39 * x - 35, 0, 39 * z - 35);
    }

    private Position getPlotEnd(long x, long z) {
        return new Position(39 * x - 4, 255, 39 * z - 4);
    }
}
