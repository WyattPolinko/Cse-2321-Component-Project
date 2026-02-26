import java.util.Iterator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

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

    /**
     * Adds a new empty door (trailer) to the terminal.
     *
     * @param location
     *            the door number of the new trailer
     * @requires location >= 0
     * @ensures doors.hasKey(location) && doors.value(location).isEmpty()
     */
    public final void addDoor(int location) {
        if (!this.doors.hasKey(location)) {
            this.doors.add(location, new Set1L<>());
        }

    }

    /**
     * Adds a new pro to the terminal at the specified door location.
     *
     * @param proNumber
     *            the 8-digit pro number of the shipment
     * @param address
     *            the shipping address of the pro
     * @param doorLocation
     *            the door number at which to place the pro
     * @requires 0 <= doorLocation && proNumber.length() == 8
     * @ensures containsPro(proNumber) &&
     *          doors.value(doorLocation).contains(result)
     */
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

    /**
     * Checks whether the terminal contains a pro with the given pro number.
     *
     * @param proNum
     *            the 8-digit pro number to search for
     * @return true if a pro with the given number exists in any door; false
     *         otherwise
     * @ensures containsPro(proNumber) <==> result == true
     */
    public final boolean containsPro(String proNum) {
        boolean found = false;

        Iterator<Map.Pair<Integer, Set<Pro>>> doorIterator = this.doors
                .iterator();

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
        SimpleReader in = new SimpleReader1L();

        ShipperSystem terminal = new ShipperSystem();

        out.println("enter pro");
        String proNum = in.nextLine();

        out.println("enter address");
        String address = in.nextLine();

        out.println("enter location");
        int location = in.nextInteger();

        terminal.addDoor(1);
        terminal.addPro("12345678", "123 Main St", 1, out);

        out.close();

    }
}
