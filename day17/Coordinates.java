import java.util.Arrays;
import java.util.Objects;

public class Coordinates {

    private final int x;
    private final int y;
    private final int z;
    private final int w;

    public Coordinates(final int x, final int y, final int z, final int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getW() {
        return w;
    }

    public boolean isValidNeighbour(final Coordinates neighbour) {
        return this.x != neighbour.getX()
                || this.y != neighbour.getY()
                || this.z != neighbour.getZ()
                || this.w != neighbour.getW();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ() && getW() == that.getW();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ(), getW());
    }

    @Override
    public String toString() {
        return Arrays.toString(new int[]{x,y,z,w});
    }
}
