package application;

import java.io.*;

public class Statistics implements Serializable {
    private int xWins;
    private int oWins;
    private int draws;
    private int gamesPlayed;
    private static File data = new File("statistics.data");

    public Statistics() {
        xWins = 0;
        oWins = 0;
        draws = 0;
        gamesPlayed = 0;
    }

    /**
     * writes the object it is called from to the statistics.data file
     */
    public void serialize() {
        FileOutputStream fileOutput = null;
        ObjectOutputStream outStream = null;
        // attempts to write the object to a file
        try {
            this.clearFile();
            fileOutput = new FileOutputStream(data);
            outStream = new ObjectOutputStream(fileOutput);
            outStream.writeObject(this);
        } catch (Exception e) {
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * deserializes the statistics object from the statistics.data file
     * 
     * @return the deserialized statistics object or a new statistics object
     */
    public static Statistics deserialize() {
        // checks if the file exists at the directory
        if (data.exists()) {
            FileInputStream fileInput = null;
            ObjectInputStream inStream = null;
            // deserializes the object
            try {
                fileInput = new FileInputStream(data);
                inStream = new ObjectInputStream(fileInput);
                Object obj1 = inStream.readObject();
                if (obj1 instanceof Statistics)
                    return (Statistics) obj1;
            } catch (Exception e) {
            } finally {
                try {
                    if (inStream != null)
                        inStream.close();
                } catch (Exception e) {
                }
            }
        }
        // returns a new Statistics object if no file was found
        return new Statistics();
    }
    
    /**
     * clears the data file
     * @param data
     */
    public void clearFile() {
        if (data.delete())
            data = new File("statistics.data");
    }

    /**
     * increments the number of games played
     */
    public void incGamesPlayed() {
        gamesPlayed++;
    }

    /**
     * increments the number of games won by X
     */
    public void incXWins() {
        xWins++;
    }

    /**
     * increments the number of games won by O
     */
    public void incOWins() {
        oWins++;
    }

    /**
     * increments the number of games drawn
     */
    public void incDraws() {
        draws++;
    }

    /**
     * return the number of games played
     * 
     * @return number of games played
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * returns the number of games won by x
     * 
     * @return number of games won by x
     */
    public int getXWins() {
        return xWins;
    }

    /**
     * returns the number of games won by O
     * 
     * @return number of games won by O
     */
    public int getOWins() {
        return oWins;
    }

    /**
     * returns the number of games drawn
     * 
     * @return number of games drawn
     */
    public int getDraws() {
        return draws;
    }
}
