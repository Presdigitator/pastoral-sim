/**
 * Stores the world full of actors
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class World {
    // Which columns of the CSV have which information:
    private static final int CSV_X_COLUMN = 1;
    private static final int CSV_Y_COLUMN = 2;
    private static final int CSV_TYPE_COLUMN = 0;
    private static final int TOTAL_COLUMNS = 3;


    private final ShadowLife game;
    // Array of all the non-agent actors:
    private final ArrayList<Actor> allActors = new ArrayList<>();
    // Agents waited to be added to allActors:
    private final ArrayList<Agent> benchedAgents = new ArrayList<>();
    private final ArrayList<Agent> activeAgents = new ArrayList<>();


    // HashMap from each occupied tile to the actors on that tile, for quick 'collision' detection
    private final HashMap<TileCoordinates, PriorityQueue<Actor>> OccupiedTiles = new HashMap<>();


    /**
     * Constructor
     */
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


    /* Load the world info and create actors.
     *Referred to https://stackabuse.com/reading-and-writing-csvs-in-java/ for
     *how to load and read csv files with Java.
     */
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
            if ((type.equals(Actor.ActorType.GATHERER)) |
                    (type.equals(Actor.ActorType.THIEF))) {
                Agent agent = (Agent) actor;
                activeAgents.add(agent);
            }
        }
    }


    /**
     * Loops through allAgents list and updates all
     * agents of the passed type. Used to update
     * Gatherers and Thieves.
     *
     * @param type The actor type to update.
     */
    public void updateAgents(Actor.ActorType type) {
        for (Agent agent : activeAgents) {
            if (agent.getType().equals(type)) {
                agent.update();
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
     *
     * @param actor The actor to remove
     */
    public void removeFromTile(Actor actor) {
        // Remove actor from its tile
        OccupiedTiles.get(actor.getTile()).remove(actor);
        /*
         * If the tile's queue is now empty, remove the tile from
         * OccupiedTiles
         * */
        if (OccupiedTiles.get(actor.getTile()).isEmpty()) {
            OccupiedTiles.remove(actor.getTile());
        }
    }

    /**
     * Checks if any active agents
     *
     * @return boolean True if any active
     */
    public boolean anyActive() {
        return !activeAgents.isEmpty();
    }


    /**
     * Add agent to benchedAgents
     */
    public void addToBench(Agent agent) {
        benchedAgents.add(agent);
    }

    /**
     * move agent from benchedAgents
     * to activeAgents
     */
    public void offTheBench() {
        for (Agent agent : benchedAgents) {
            allActors.add(agent);
            if (agent.isActive()) {
                activeAgents.add(agent);
            }
        }
        benchedAgents.clear();
    }

    /**
     * Remove actor from allActors
     */
    public void removeFromActors(Actor actor) {
        allActors.remove(actor);
    }

    /**
     * Add actor to activeAgents
     */
    public void addToActive(Agent agent) {
        activeAgents.add(agent);
    }

    /**
     * Remove agent from activeAgents
     */
    public void removeFromActive(Agent agent) {
        activeAgents.add(agent);
    }

    /**
     * Removes inactive agents from activeAgents.
     * credit: deleting from arraylist using iterator, adapted from:
     * https://stackoverflow.com/questions/10738634/delete-data-from-arraylist-with-a-for-loop
     */
    public void clearInactive() {
        Iterator<Agent> it = activeAgents.iterator();
        while (it.hasNext()) {
            Agent agent = it.next();
            if (!agent.isActive()) {
                it.remove();
            }
        }

    }

    /**
     * Render all actors
     */
    public void renderAll() {
        for (Actor actor : allActors) {
            actor.render();
        }
    }
}