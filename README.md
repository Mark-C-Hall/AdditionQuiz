# AdditionQuiz
The final project in my Advanced Java Course.

## Prompt
We are going to make a trivia game for the final project.  The trivia game will have a user interface created by Java FX.  This interface will look like the picture below (Appendix A).  The game will start out asking you to enter your name.  Then the game will produce two objects that are being added together.  The buttons below the objects are the possible answers. 

The objects that are being added together and the possible answers will be read from a file that I give you.  The file will contain integers, doubles and Strings.  So, the class that handles this file will have to be generic to handle every form of datatype.  You have to select one of the answers before 5 seconds.  After 5 seconds the answers lock and the game produce two different objects that are being added together with different possible answers. 

To keep track of time, there will be a countdown clock available.  The game runs for 10 questions.  After that the points are totaled.  Each question is worth 1 point.  You get 0 points for a wrong answer.  When each answer is selected during the game, the timestamp and points for that question should be placed on a stack. 

Once the game ends, then the stack gets popped and shows the timestamp and points for each question.  The user name and total score should be stored on a database.  When clicking “generate report” a file should be produced showing the last 10 players and their score.  The generated list should be sorted from highest score to lowest score. 
