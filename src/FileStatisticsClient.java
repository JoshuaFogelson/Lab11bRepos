/** FileStatisticsClient
  *   
  * @author Anna Bieszczad
  * @version 11/28/16
  */
import java.util.*;
import java.text.*;
import java.io.*;

public class FileStatisticsClient
{
  public static void main( String [] args )
  {
    String answer;
    String fileName;
    Scanner scan = new Scanner(System.in);
    DecimalFormat gradeFormat = new DecimalFormat("#0.00");
    DecimalFormat percentFormat = new DecimalFormat("#0%");
    do
    {
      do
      {
        // prompt the user for the name of the file
        System.out.println("Enter the name of the file containing grades (i.e. grades1.txt)");
        fileName = scan.nextLine();
      }while (!fileName.matches("[a-zA-Z0-9]+.txt"));
      
      // create a FileStatistics object passing the Scanner object.
      // The constructor will read from the file and populate
      // the ArrayList with the grades read from the file.
      try
      {                
        File file = new File(fileName);
        System.out.println("Looking for " + file.getAbsolutePath()); 
        Scanner scanFromFile = new Scanner(file);
        Date lastModified = new Date(file.lastModified());
        System.out.println( "Found the file, it was last modified on " +  lastModified);
        FileStatistics stats = new FileStatistics( scanFromFile);
        
        System.out.println("\n***** Grade statistics: *****");
        // call toString - it displays the numbers of grades
        System.out.println(stats);
        
        // calculate and print the grade average
        System.out.println( "\nThe grade average is "
                             + gradeFormat.format(stats.gradeAverage( )) );
        
        // calculate and print the highest grade
        System.out.println( "\nThe highest grade is "
                             + gradeFormat.format(stats.highestGrade( )) );
        
        // calculate and print the pass rate
        System.out.println( "\nThe pass rate is "
                             + percentFormat.format(stats.passRate( )) );
        
        // calculate and print the grades as letter grades
        ArrayList<Integer> listOfGrades = stats.getGrades();
        char arrayOfLetters[] = stats.getLetterGrades();
        System.out.println("\nThe letter grades are: ");
        for (int i = 0; i < arrayOfLetters.length; i++)
        {
          System.out.println(listOfGrades.get(i) + " --> " + arrayOfLetters[i]);
        }
        System.out.println();
        stats.saveGradesInFile();
        stats.displaySavedGrades();
      }
      catch( FileNotFoundException fnfe )
      {
        System.out.println( "Unable to find input file " + fileName);
      }
      System.out.println("\nWould you like to analyze data from another file? (yes/no)");
      answer = scan.nextLine();
    }while(!answer.equalsIgnoreCase("no"));
  }
}