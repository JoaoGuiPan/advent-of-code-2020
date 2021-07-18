import java.util.HashMap;
import java.util.Map;

public class ConwayCubes {

    private static final int FIRST_CYCLE = 0;
    private static final int FINAL_CYCLE = 6;

    private static final String ACTIVE = "#";

    public static void main(final String []args) {

        // TEST SCENARIO - 112 on 3d and 848 on 4d
//        final String[][] xyCoordinates = {
//                {".","#","."},
//                {".",".","#"},
//                {"#","#","#"}
//        };

        final String[][] xyCoordinates = {
                {"#","#","#","#","#",".",".","#"},
                {"#",".",".","#","#","#",".","#"},
                {"#","#","#",".",".",".",".","."},
                {".","#",".","#",".","#",".","."},
                {"#","#",".","#",".",".","#","."},
                {"#","#","#","#","#","#",".","."},
                {".","#","#",".",".","#","#","#"},
                {"#","#","#",".","#","#","#","#"}
        };

        Map<Coordinates, Boolean> coordinates = initCoordinates(xyCoordinates);

        for (int cycle = FIRST_CYCLE; cycle < FINAL_CYCLE; cycle++) {
            coordinates = executeCycle(coordinates);
        }

        System.out.println("Active cubes = " + countActive(coordinates));
    }

    private static long countActive(final Map<Coordinates, Boolean> coordinates) {
        return coordinates.values().stream().filter(isActive -> isActive).count();
    }

    private static Map<Coordinates, Boolean> initCoordinates(final String[][] yxCoordinates) {
        final Map<Coordinates, Boolean> coordinates = new HashMap<>();
        for (int y = 0; y < yxCoordinates.length; y++) {
            for (int x = 0; x < yxCoordinates[y].length; x++) {
                final String value = yxCoordinates[y][x];
                coordinates.put(new Coordinates(x,y,0,0), ACTIVE.equalsIgnoreCase(value));
            }
        }
        return coordinates;
    }

    /**
     * During a cycle, all cubes simultaneously change their state according to the following rules:
     *  - If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
     *  - If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
     */
    private static Map<Coordinates, Boolean> executeCycle(final Map<Coordinates, Boolean> coordinates) {

        int minX = 0;
        int minY = 0;
        int minZ = 0;
        int minW = 0;

        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        int maxW = 0;

        for (final Coordinates coordinate : coordinates.keySet()) {
            int x = coordinate.getX();
            int y = coordinate.getY();
            int z = coordinate.getZ();
            int w = coordinate.getW();

            minX = Math.min(x, minX);
            minY = Math.min(y, minY);
            minZ = Math.min(z, minZ);
            minW = Math.min(w, minW);

            maxX = Math.max(x, maxX);
            maxY = Math.max(y, maxY);
            maxZ = Math.max(z, maxZ);
            maxW = Math.max(w, maxW);
        }

        minX--;
        minY--;
        minZ--;
        minW--;

        maxX++;
        maxY++;
        maxZ++;
        maxW++;

        final Map<Coordinates, Boolean> newCoordinates = new HashMap<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    for (int w = minW; w <= maxW; w++) {
                        updateCoordinates(coordinates, newCoordinates, x, y, z, w);
                    }
                }
            }
        }

        return newCoordinates;
    }

    private static void updateCoordinates(final Map<Coordinates, Boolean> oldCoordinates,
                                          final Map<Coordinates, Boolean> newCoordinates,
                                          final int x, final int y, final int z, final int w) {

        final Coordinates coordinate = new Coordinates(x, y, z, w);
        final boolean isActive = oldCoordinates.getOrDefault(coordinate, false);
        int activeNeighbours = countActiveNeighbours(coordinate, oldCoordinates);

        if (isActive && activeNeighbours != 2 && activeNeighbours != 3) {
            newCoordinates.put(coordinate, false);
        } else if (!isActive && activeNeighbours == 3) {
            newCoordinates.put(coordinate, true);
        } else {
            newCoordinates.put(coordinate, isActive);
        }
    }

    /**
     * Each cube has 26 neighbours on 3d and 80 neighbours on 4d (ignores itself)
     */
    private static int countActiveNeighbours(final Coordinates coordinate, final Map<Coordinates, Boolean> coordinates) {
        int activeNeighbours = 0;

        int minX = coordinate.getX() - 1;
        int minY = coordinate.getY() - 1;
        int minZ = coordinate.getZ() - 1;
        int minW = coordinate.getW() - 1;

        int maxX = coordinate.getX() + 1;
        int maxY = coordinate.getY() + 1;
        int maxZ = coordinate.getZ() + 1;
        int maxW = coordinate.getW() + 1;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    for (int w = minW; w <= maxW; w++) {
                        final Coordinates neighbour = new Coordinates(x,y,z,w);

                        if (coordinate.isValidNeighbour(neighbour)) { // ignores itself
                            final Boolean isActive = coordinates.get(neighbour);
                            if (isActive != null && isActive) {
                                activeNeighbours++;
                            }
                        }
                    }
                }
            }
        }

        return activeNeighbours;
    }
}




































