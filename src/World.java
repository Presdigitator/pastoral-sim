import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class World {
    // Which columns of the CSV have which information:
    private static final int CSV_X_COLUMN = 1;
    private static final int CSV_Y_COLUMN = 2;
    private static final int CSV_TYPE_COLUMN = 0;
    private static final int TOTAL_COLUMNS = 3;

    /* a hashMap giving the appropriate actor subclass for each possible
    actor type input in the world csv.
    Referred to
    https://stackoverflow.com/questions/35762823/static-hashmap-initialization
    for how to initialise a static map
     */
    private static final HashMap<String, Class<? extends Actor>> actorNames =
            new HashMap<>();

    static {
        actorNames.put("Tree", Tree.class);
        actorNames.put("GoldenTree", Tree.class);
        actorNames.put("Stockpile", Pile.class);
        actorNames.put("Hoard", Pile.class);
        actorNames.put("Pad", BasicActor.class);
        actorNames.put("Fence", BasicActor.class);
        actorNames.put("SignUp", Sign.class);
        actorNames.put("SignDown", Sign.class);
        actorNames.put("SignLeft", Sign.class);
        actorNames.put("SignRight", Sign.class);
        actorNames.put("Pool", BasicActor.class);
        actorNames.put("Gatherer", Gatherer.class);
        actorNames.put("Thief", Thief.class);
    }

    private final ShadowLife game;
    // Array of all the actors:
    private final ArrayList<Actor> allActors = new ArrayList<>();

    // HashMap from each occupied tile to the actors on that tile, for quick 'collision' detection
    private final HashMap<TileCoordinates, ArrayList<Actor>> OccupiedTiles = new HashMap<>();

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

    /**
     * Returns an unmodifiable reference to the map of actor names
     *
     * @return Map<String, Class < ? extends Actor>> An unmodifiable reference to actorNames
     */
    public static Map<String, Class<? extends Actor>> getActorNames() {
        return (Map<String, Class<? extends Actor>>) Collections.unmodifiableMap(actorNames);
    }

    public ArrayList<Actor> getActors() {
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
            // Create actor of appropriate class by referring to actorNames map
            Actor actor;
            try {
                actor = actorFromCSVName(tileCoordinates, actorName);
            } catch (NullPointerException e) {
                throw new InvalidWorldLineException(worldFile, lineNumber);
            }
            // Add actor to the array of all actors
            this.allActors.add(actor);
        }
    }

    /* Takes the name of an actor (as written in world file) and creates an actor
     * of appropriate class and 'variety' (if more than one actor shares the class,
     * e.g. tree and golden tree)
     */
    private Actor actorFromCSVName(TileCoordinates tile, String actorName) throws NullPointerException {
        /* Array of arguments for constructor */
        Object[] argsList = {tile, actorName};
        /* Retrieve approprite subclass */
        Class<? extends Actor> subClass = getActorNames().get(actorName);
        Actor actor = null;
        // Create actor of appropriate subclass and 'variety'
        try {
            actor = subClass.getDeclaredConstructor(Actor.argClasses).newInstance(argsList);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return actor;

    }

    public void addToWorld(Actor actor) {

    }
}
