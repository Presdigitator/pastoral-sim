/**Represents actors within the game, who may or may not move
*@author Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au
 */

import bagel.Image;
import bagel.Window;
import bagel.util.Point;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Actor {


    /**
     * Directory where images are stored
     */
    public static final String IMAGE_DIRECTORY = "res/images/";
    // Array of classes used by actor constructor, used by getDeclaredConstructor()
    private static final Class<?>[] argClasses = {TileCoordinates.class, World.class, ActorType.class};


    /* How far offscreen will we continue
     *calling render method for an actor (in number of tiles):
     */
    private static final int screenZoneBuffer = 1;
    private TileCoordinates tile = null;
    private final World world;
    private ActorType type;
    private final Image image;

    /**
     * Constructor for Actor
     *
     */
    public Actor(TileCoordinates tile, World world, ActorType type) {
        this.world = world;
        this.type = type;
        setTile(tile);
        this.image = type.image;
    }

    /**
     * Takes an actorType and creates an Actor
     * of appropriate subClass and type
     * Referred to https://stackoverflow.com/questions/34395589/selecting-subclass-based-on-user-input
     * and https://stackoverflow.com/questions/34395589/selecting-subclass-based-on-user-input
     * for techniques for declaring differing subclasses based on a parameter.
     *
     * @param tile The actor's initial tile location
     * @param type The type of the actor
     * @return Actor An actor matching the passed type and tile
     */
    public static Actor actorFactory(TileCoordinates tile, World world, ActorType type) throws NullPointerException {
        /* Array of arguments for constructor */
        Object[] argsList = {tile, world, type};
        /* Retrieve appropriate subclass */
        Class<? extends Actor> subClass = type.subClass;
        Actor actor = null;
        /*
         *Create actor of appropriate subclass and 'type', by referring to subclass
         *attribute for this type in actorTypes enum
         */
        try {
            Constructor<? extends Actor> constructor = subClass.getDeclaredConstructor(Actor.argClasses);
            actor = constructor.newInstance(argsList);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return actor;

    }


    /**
     * Sets tile attribute
     * @param tile New tile value.
     */
    public void setTile(TileCoordinates tile) {
        /*
         * If actor currently has a tile (i.e. is not
         * just being initialised), removed the actor from
         * its tile in OccupiedTiles.
         */
        if (this.tile != null) {
            world.removeFromTile(this);
        }
        // Set new tile and add to this tile in world
        this.tile = tile;
        world.addToTile(this);
    }

    /**
     * This method renders the actor onscreen, if within bounds of screen
     */
    public void render() {
        //checks if within visible window
        if (isInWindow(this.tile)) {
            //Convert tile location to pixel location and draw image
            Point centre = this.tile.toPixels();
            this.getImage().drawFromTopLeft(centre.x, centre.y);
        }
    }

    /*
     * This method checks if given tile is within visible window
     * (with buffer to be safe)
     *
     */
    private boolean isInWindow(TileCoordinates tile) {
        boolean isIn = true;
        if ((tile.getX() < (-screenZoneBuffer)) || (tile.getY() < (-screenZoneBuffer)) ||
                (tile.getX() > (Window.getWidth() / TileCoordinates.TILE_SIZE) +
                        screenZoneBuffer) ||
                (tile.getY() > (Window.getHeight() / TileCoordinates.TILE_SIZE +
                        screenZoneBuffer))) {
            isIn = false;
        }
        return isIn;
    }


    /**
     * Used by ShadowLife.update() to update this actor
     */
    public void update() {
    }



    /**
     * Called when simulation halts,
     * prints information for this actor
     * is relevant (only stockpiles and hoards
     * actually print)
     */
    public void haltPrint() {
    }


    /**
     * Responds to Agent standing on same tile
     * (Part of Visitor Pattern)
     *
     * @param agent The agent that is standing on this actor
     */
    public abstract void stoodOnBy(Agent agent);

    /**
     * Sets type
     *
     * @param type New value for type
     */
    public void setType(ActorType type) {
        this.type = type;
    }

    /**
     * Gets tile
     *
     * @return value of tile
     */
    public TileCoordinates getTile() {
        return tile;
    }

    /**
     * Gets world
     *
     * @return value of world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets type
     *
     * @return value of type
     */
    public ActorType getType() {
        return type;
    }

    /**
     * Gets image
     *
     * @return value of image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Enum of all the actor types, with associated information
     * for each.
     */
    public enum ActorType {
        FENCE(BasicActor.class, "fence.png"),
        POOL(BasicActor.class, "pool.png"),
        SIGNUP(BasicActor.class, "up.png", Direction.UP),
        SIGNDOWN(BasicActor.class, "down.png", Direction.DOWN),
        SIGNLEFT(BasicActor.class, "left.png", Direction.LEFT),
        SIGNRIGHT(BasicActor.class, "right.png", Direction.RIGHT),
        PAD(BasicActor.class, "pad.png"),
        GATHERER(Gatherer.class, "gatherer.png"),
        TREE(Tree.class, "tree.png"),
        GOLDENTREE(Tree.class, "gold-tree.png"),
        HOARD(Pile.class, "hoard.png"),
        STOCKPILE(Pile.class, "cherries.png"),
        THIEF(Thief.class, "thief.png");

        Class<? extends Actor> subClass;
        Image image;
        Direction direction = null;

        /**
         * Default constructor
         */
        ActorType(Class<? extends Actor> subClass, String imageName) {
            this.subClass = subClass;
            this.image = new Image(IMAGE_DIRECTORY + imageName);
        }

        /**
         * Constructor used by signs (for direction)
         */
        ActorType(Class<? extends Actor> subClass, String imageName, Direction direction) {
            this(subClass, imageName);
            this.direction = direction;
        }
    }
}
