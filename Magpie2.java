/**
 * A program to carry on conversations with a human user.
 * This is the initial version that:  
 * <ul><li>
 *       Uses indexOf to find strings
 * </li><li>
 *          Handles responding to simple words and phrases 
 * </li></ul>
 * This version uses a nested if to handle default responses.
 * @author Laurie White
 * @version April 2012
 */
public class Magpie2
{
    /**
     * Get a default greeting   
     * @return a greeting
     */
    public String getGreeting()
    {
        return "Hello, let's talk.";
    }
    
    /**
     * Gives a response to a user statement
     * 
     * @param statement
     *            the user statement
     * @return a response based on the rules given
     */
    
    // changed all else-if statements to use the findKeyword method
    public String getResponse(String statement)
    {
        String response = "";
        if (findKeyword(statement, "no") >= 0)
        {
            response = "Why so negative?";
        }
        else if (findKeyword(statement, "dog") >= 0
                || findKeyword(statement, "cat") >= 0)
        {
            response = "Tell me more about your pets.";
        }
        else if (findKeyword(statement, "Mr. Kaehms") >= 0)
        {
            response = "He sounds like a good teacher.";
        }
        else if (findKeyword(statement, "hobby") >= 0
                || findKeyword(statement, "hobbies") >= 0)
        {
            response = "What are some of your hobbies?";
        }
        else if (findKeyword(statement, "food") >= 0)
        {
            response = "What is your favorite food?";
        }
        else if (findKeyword(statement, "sport") >= 0
                || findKeyword(statement, "sports") >= 0)
        {
            response = "What sports do you like to play?";
        }
        else if (findKeyword(statement, "mother") >= 0
                || findKeyword(statement, "father") >= 0
                || findKeyword(statement, "sister") >= 0
                || findKeyword(statement, "brother") >= 0)
        {
            response = "Tell me more about your family.";
        }
        else if (statement.trim().length() == 0)
        {
            response = "Say something, please.";
        }
        else if (findKeyword(statement, "I want to", 0) >= 0)
        {
            response = transformIWantToStatement(statement);
        }
        else if (findKeyword(statement, "I want", 0) >= 0)
        {
            response = transformIWantSomethingStatement(statement);
        }
        else
        {
            if (findKeyword(statement, "I", 0) >= 0)
            {
                int psn = findKeyword(statement, "I", 0);
                if (findKeyword(statement, "you", psn) >= 0)
                {
                    response = transformISomethingYouStatement(statement);
                }
            }
            else if (findKeyword(statement, "you", 0) >= 0)
            {
                int psn = findKeyword(statement, "you", 0);
               if (findKeyword(statement, "me", psn) >= 0)
               {
                   response = transformYouMeStatement(statement);
               }
            }
            else
            {
                response = getRandomResponse();
            }
        }
        return response;
    }

    /**
     * Pick a default response to use if nothing else fits.
     * @return a non-committal string
     */
    private String getRandomResponse()
    {
        final int NUMBER_OF_RESPONSES = 6;
        double r = Math.random();
        int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
        String response = "";
        
        if (whichResponse == 0)
        {
            response = "Interesting, tell me more.";
        }
        else if (whichResponse == 1)
        {
            response = "Hmmm.";
        }
        else if (whichResponse == 2)
        {
            response = "Do you really think so?";
        }
        else if (whichResponse == 3)
        {
            response = "You don't say.";
        }
        else if (whichResponse == 4)
        {
            response = "Ahh. Okay.";
        }
        else if (whichResponse == 5)
        {
            response = "That is very cool.";
        }
        return response;
    }
    
    // PLTW 1.1.6 Part C start
    private int findKeyword(String statement, String goal,
    int startPos)
    {
        String phrase = statement.trim();
        int psn = phrase.toLowerCase().indexOf(
                goal.toLowerCase(), startPos);while (psn >= 0)
        {
            String before = " ", after = " ";
            if (psn > 0)
            {
                before = phrase.substring(psn - 1, psn)
                .toLowerCase();
            }
            if (psn + goal.length() < phrase.length())
            {
                after = phrase.substring(
                    psn + goal.length(),
                    psn + goal.length() + 1)
                .toLowerCase();
            }

            if (((before.compareTo("a") < 0) || (before
                    .compareTo("z") > 0))
            && ((after.compareTo("a") < 0) || (after
                    .compareTo("z") > 0)))
            {
                return psn;
            }
            psn = phrase.indexOf(goal.toLowerCase(),
                psn + 1);
        }
        return -1;
    }
                
    private int findKeyword(String statement, String goal)
    {
        return findKeyword(statement, goal, 0);
    }
    // PLTW 1.1.6 Part C end
    
    // PLTW 1.1.6 Part D start
    private String transformIWantToStatement(String statement)
    {
        statement = statement.trim();
        String lastChar = statement.substring(statement.length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement.length() - 1);
        }
        
        int psn = findKeyword (statement, "I want to", 0);
        String restOfStatement = statement.substring(psn + 9).trim();
        return "What would it mean to " + restOfStatement + "?";
    }
    
    private String transformIWantSomethingStatement(String statement)
    {
        statement = statement.trim();
        String lastChar = statement.substring(statement.length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement.length() - 1);
        }
       
        int psn = findKeyword (statement, "I want", 0);
        String restOfStatement = statement.substring(psn + 6).trim();
        return "Would you really be happy if you had " + restOfStatement + "?";
    }
    
    private String transformISomethingYouStatement(String statement)
    {
        statement = statement.trim();
        String lastChar = statement.substring(statement.length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement.length() - 1);
        }
        int psnOfI = findKeyword (statement, "I", 0);
        int psnOfYou = findKeyword (statement, "you", psnOfI + 1);
        String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
        return "Why do you " + restOfStatement + " me?";
    }
    
    private String transformYouMeStatement(String statement)
    {
        statement = statement.trim();
        String lastChar = statement.substring(statement.length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement.length() - 1);
        }
        
        int psnOfYou = findKeyword (statement, "you", 0);
        int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
        String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
        return "What makes you think that I " + restOfStatement + " you?";
    }
    // PLTW 1.1.6 Part D end
}
