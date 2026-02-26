import java.util.*;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;


public class ShipperSystem {
    private Map<Integer, Set<Pro>> doors;

    public ShipperSystem() {
        this.doors = new Map1L<>();
    }

        private class Pro {
            String proNumber;
            String address;
            int doorLocation;
            boolean damaged;

        Pro(String proNumber, String address, int doorLocation) {
            this.proNumber = proNumber;
            this.address = address;
            this.doorLocation = doorLocation;
            this.damaged = false;
        }
    }

    public final void addDoor(int location) {
        if (!this.doors.hasKey(location)) {
            this.doors.add(location, new Set1L<>());
        }

    }

    public final void addPro(String proNumber, String address, int doorLocation,
            SimpleWriter out) {
        if (!this.doors.hasKey(doorLocation)) {
            System.out.println("Door does not exist.");
        } else if (this.containsPro(proNumber)) {
            out.println("Pro already exists.");
        } else {
            Pro newPro = new Pro(proNumber, address, doorLocation);
            this.doors.value(doorLocation).add(newPro);
        }

    }

    public final boolean containsPro(String proNum) {
        boolean found = false;

        Iterator<Map.Pair<Integer, Set<Pro>>> doorIterator = this.doors.iterator();

        while (!found && doorIterator.hasNext()) {
            Map.Pair<Integer, Set<Pro>> pair = doorIterator.next();
            Iterator<Pro> proIterator = pair.value().iterator();

            while (!found && proIterator.hasNext()) {
                Pro tmp = proIterator.next();
                if (tmp.proNumber.equals(proNum)) {
                    found = true;
                }
            }
        }

        return false;
    }




    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        ShipperSystem terminal = new ShipperSystem();

        terminal.addDoor(1);
        terminal.addPro("12345678", "123 Main St", 1, out);

        out.close();

    }
}
