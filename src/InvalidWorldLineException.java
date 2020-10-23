/**
 * Exception for when a line in a world CSV file is
 * invalid in some way.
 *
 * @author Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au
 */

public class InvalidWorldLineException extends Exception {

    public InvalidWorldLineException(String worldFile, int lineNumber) {
        super("error: in file \"" + worldFile + "\" at line " + lineNumber);
    }
}
