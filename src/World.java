import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class World {
    private static final int csvXColumn=1;
    private static final int csvYColumn=2;
    private static final int csvTypeColumn=0;
    private final int tileSize = 64; //size of tiles
    private ShadowLife game;
    private ArrayList<Actor> actors = new ArrayList<>();

    //Constructor
    public World(ShadowLife game, String worldFile ) {
        this.game=game;
        try {
            //Try to load csv world file and create specified actors
            if (!(loadWorld())) {
                /*Name column in CSV contains something other than "Tree"
                or "Gatherer" */
                System.out.println("Actor name in csv not recognised.\n");
                return;
            }
        } catch (
                IOException e) {
            /* problem with csv file */
            System.out.println("Error reading file.\n");
            return;
        }
    }

    public int getTileSize() {
        return tileSize;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    /* Load the world info and create actors.
     *Referred to https://stackabuse.com/reading-and-writing-csvs-in-java/ for
     *how to load and read csv files with Java. */
    private boolean loadWorld() throws IOException {
        BufferedReader csvReader;
        csvReader = new BufferedReader(new FileReader("res/worlds/test.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            int pixelX = Integer.parseInt(data[csvXColumn]);
            int pixelY = Integer.parseInt(data[csvYColumn]);
            int tileX = pixelX / tileSize;
            int tileY = pixelY / tileSize;
            Actor actor;
            if (data[csvTypeColumn].equals("Tree")) {
                actor = new Tree(tileX, tileY);
            } else if (data[csvTypeColumn].equals("Gatherer")) {
                actor = new Gatherer(tileX, tileY);
            } else {
                return false;
            }
            this.actors.add(actor);
        }
        return true;
    }
}
