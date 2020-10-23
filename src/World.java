import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class World {
    // Which columns of the CSV have which information:
    private static final int CSV_X_COLUMN = 1;
    private static final int CSV_Y_COLUMN = 2;
    private static final int CSV_TYPE_COLUMN = 0;
    private static final int TOTAL_COLUMNS = 3;


    private final ShadowLife game;
    // Array of all the non-agent actors:
    private final ArrayList<Actor> allActors = new ArrayList<>();


    // HashMap from each occupied tile to the actors on that tile, for quick 'collision' detection
    private final HashMap<TileCoordinates, PriorityQueue<Actor>> OccupiedTiles = new HashMap<>();

    // Track number of active agents
    private int numActive=0;

    // Constructor
    public World(ShadowLife game, String worldFile) {
        this.game = game;
        try {
            // Try to load csv world file and create specified actors
            loadWorld(worldFile);
        } catch (IOException e) {
            /* Problem with opening csv file */
            System.out.printf("error: file \"%s\" not found\n", worldFile);
            System.exit(-1);
        } catch (InvalidWorldLineException e) {
            // Problem with a line of the CSV file
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public ArrayList<Actor> getAllActors() {
        return allActors;
    }


    /* Load the world info and create actors.
     *Referred to https://stackabuse.com/reading-and-writing-csvs-in-java/ for
     *how to load and read csv files with Java.
     * Referred to https://stackoverflow.com/questions/34395589/selecting-subclass-based-on-user-input
     * and https://stackoverflow.com/questions/34395589/selecting-subclass-based-on-user-input
     * for techniques for declaring differing subclasses based on a parameter. */
    private void loadWorld(String worldFile) throws IOException, InvalidWorldLineException {
        BufferedReader csvReader;
        csvReader = new BufferedReader(new FileReader(worldFile));
        String row;
        int lineNumber = 0;

        // Loop through each line of CSV, creating appropriate actor
        while ((row = csvReader.readLine()) != null) {
            lineNumber++;
            // Split the cells (comma separated)
            String[] data = row.split(",");
            // Check for correct number of cells/columns
            if (data.length != TOTAL_COLUMNS) {
                throw new InvalidWorldLineException(worldFile, lineNumber);
            }
            // Get coordinates
            int pixelX;
            int pixelY;
            try {
                pixelX = Integer.parseInt(data[CSV_X_COLUMN]);
                pixelY = Integer.parseInt(data[CSV_Y_COLUMN]);
            } catch (NumberFormatException e) {
                throw new InvalidWorldLineException(worldFile, lineNumber);
            }
            // Make a TileCoordinates object based on the coordinates
            TileCoordinates tileCoordinates;
            try {
                tileCoordinates =
                        TileCoordinates.fromPixels(pixelX, pixelY);
            } catch (UnalignedPixelException e) {
                throw new InvalidWorldLineException(worldFile, lineNumber);
            }
            // Get the name of the actor
            String actorName = data[CSV_TYPE_COLUMN];
            Actor.ActorType type;
            try {
                type = Actor.ActorType.valueOf(actorName.toUpperCase());
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new InvalidWorldLineException(worldFile, lineNumber);
            }
            // Create actor of appropriate class and type
            Actor actor;
            actor = Actor.actorFactory(tileCoordinates, this, type);
            /*Add actor to allActors list
             */
            allActors.add(actor);
        }
    }


    /**
     * Loops through allActors list and updates all
     * actors of the passed type. Used to update
     * Gatherers and Thieves.
     *
     * @param type The actor type to update.
     */
    public void updateActors(Actor.ActorType type) {
        for (Actor actor : allActors) {
            if (actor.getType().equals(type)) {
                actor.update();
            }
        }
    }


    /**
     * For each relevant actor, prints info.
     * Called when simulation halts (only hoards and stockpile
     * print their info).
     */
    public void haltPrint() {
        for (Actor actor : allActors) {
            actor.haltPrint();
        }
    }

    /**
     * Returns a priority queue of all the actors currently on
     * the given tile.
     *
     * @param tile the tile to get the queue of actors for
     * @return PriorityQueue<Actor> Priority queue of all the actors currently on
     * the given tile.
     */
    public PriorityQueue<Actor> getActorsOntile(TileCoordinates tile) {
        return OccupiedTiles.get(tile);
    }

    /**
     * Adds the actor to the queue of actors for
     * its tile in OccupiedTiles, or create new queue
     * if OccupiedTiles currently has no entry for the tile.
     *
     * @param actor The Actor to add
     */
    public void addToTile(Actor actor) {
        // If tile not already in OccupiedTiles as a key, create new entry.
        if (!(OccupiedTiles.containsKey(actor.getTile()))) {
            // Make comparator that uses the ordering from the actorType enum
            Comparator<Actor> processOrder =
                    (a1, a2) -> a1.getType().ordinal() - a2.getType().ordinal();
            /*
             Make new priority queue using the comparator, to hold the tile's actors in order
             */
            PriorityQueue<Actor> queue = new PriorityQueue<>(1,
                    processOrder);
            // Add tile to OccupiedTiles, with the queue
            OccupiedTiles.put(actor.getTile(), queue);
        }
        // Add the actor to the queue for its tile
        OccupiedTiles.get(actor.getTile()).add(actor);
    }

    /**
     * Removes actor from given tile in OccupiedTiles
     * @param actor The actor to remove
     */
    public void removeFromTile(Actor actor) {
        OccupiedTiles.get(actor.getTile()).remove(actor);
    }

    /**
     * Checks if any active agents
     * @return boolean True if any active
     */
    public boolean anyActive() {
        return numActive > 0;
    }

    /**
     * Add 1 to count of active agents
     */
    public void addActive() {
        numActive++;
    }

    /**
     * Reduce count of active agents by 1.
     */
    public void removeActive() {
        numActive--;
    }
}