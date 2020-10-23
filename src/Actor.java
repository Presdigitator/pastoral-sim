//Actor.java: Represents actors within the game, who may or may not move
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;
import bagel.Window;
import bagel.util.Point;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Actor {

    public static final String IMAGE_DIRECTORY = "res/images/";
    public static final Class<?>[] argClasses = {TileCoordinates.class, World.class, ActorType.class};
    private static final Image THIEF_IMAGE =
            new Image(IMAGE_DIRECTORY + "thief.png");
    private static final Image GATHERER_IMAGE =
            new Image(IMAGE_DIRECTORY + "gatherer.png");
    private static final Image STOCKPILE_IMAGE =
            new Image(IMAGE_DIRECTORY + "cherries.png");
    private static final Image HOARD_IMAGE =
            new Image(IMAGE_DIRECTORY + "hoard.png");
    private static final Image UP_IMAGE =
            new Image(IMAGE_DIRECTORY + "up.png");
    private static final Image DOWN_IMAGE =
            new Image(IMAGE_DIRECTORY + "down.png");
    private static final Image LEFT_IMAGE =
            new Image(IMAGE_DIRECTORY + "left.png");
    private static final Image RIGHT_IMAGE =
            new Image(IMAGE_DIRECTORY + "right.png");
    private static final Image TREE_IMAGE =
            new Image(IMAGE_DIRECTORY + "tree.png");
    private static final Image GOLDEN_TREE_IMAGE =
            new Image(IMAGE_DIRECTORY + "gold-tree.png");
    private static final Image PAD_IMAGE =
            new Image(IMAGE_DIRECTORY + "pad.png");
    private static final Image FENCE_IMAGE =
            new Image(IMAGE_DIRECTORY + "fence.png");
    private static final Image POOL_IMAGE =
            new Image(IMAGE_DIRECTORY + "pool.png");

    /* How far offscreen will we continue
     *calling render method for an actor (in number of tiles):
     */
    private static final int screenZoneBuffer = 1;
    private TileCoordinates tile = null;
    private World world;
    private ActorType type;
    private Image image;

    public Actor(TileCoordinates tile, World world, ActorType type) {
        this.world = world;
        this.type = type;
        setTile(tile);
        this.image = type.image;
    }

    /**
     * Takes an actorType and creates an Actor
     * of appropriate subClass and type
     *  Referred to https://stackoverflow.com/questions/34395589/selecting-subclass-based-on-user-input
     *  and https://stackoverflow.com/questions/34395589/selecting-subclass-based-on-user-input
     *   for techniques for declaring differing subclasses based on a parameter.
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

    public TileCoordinates getTile() {
        return tile;
    }

    public void setTile(TileCoordinates tile) {
        /*
         * If actor currently has a tile (i.e. is not
         * just being initialised), removed the actor from
         * its tile in OccupiedTiles.
         */
        if (this.tile != null) {
            world.removeFromTile(this);
        }
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

    /**This method checks if given tile is within visible window
     * (with buffer to be safe)
     *@param tile The tile to check
     * @return boolean True if within window+buffer
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
     * Returns the image of the actor.
     *
     * @return Image The image of the actor
     */
    public Image getImage() {
        return image;
    }

    public ActorType getType() {
        return type;
    }

    public void setType(ActorType type) {
        this.type = type;
    }

    /**
     * Called when simulation halts,
     * prints information for this actor
     * is relevant (only stockpiles and hoards
     * actually print)
     */
    public void haltPrint() {
    }

    public World getWorld() {
        return world;
    }


    /**
     * Responds to Agent standing on same tile
     * (Part of Visitor Pattern)
     *
     * @param agent The agent that is standing on this actor
     */
    public abstract void stoodOnBy(Agent agent);


    /**
     * Enum of all the actor types, with associated information
     * for each.
     */
    public enum ActorType {
        FENCE(BasicActor.class, FENCE_IMAGE),
        POOL(BasicActor.class, POOL_IMAGE),
        SIGNUP(BasicActor.class, UP_IMAGE, Direction.UP),
        SIGNDOWN(BasicActor.class, DOWN_IMAGE, Direction.DOWN),
        SIGNLEFT(BasicActor.class, LEFT_IMAGE, Direction.LEFT),
        SIGNRIGHT(BasicActor.class, RIGHT_IMAGE, Direction.RIGHT),
        PAD(BasicActor.class, PAD_IMAGE),
        GATHERER(Gatherer.class, GATHERER_IMAGE),
        TREE(Tree.class, TREE_IMAGE),
        GOLDENTREE(Tree.class, GOLDEN_TREE_IMAGE),
        HOARD(Pile.class, HOARD_IMAGE),
        STOCKPILE(Pile.class, STOCKPILE_IMAGE),
        THIEF(Thief.class, THIEF_IMAGE);

        Class<? extends Actor> subClass;
        Image image;
        Direction direction = null;

        ActorType(Class<? extends Actor> subClass, Image image) {
            this.subClass = subClass;
            this.image = image;
        }

        ActorType(Class<? extends Actor> subClass, Image image, Direction direction) {
            this.subClass = subClass;
            this.image = image;
            this.direction = direction;
        }
    }
}
