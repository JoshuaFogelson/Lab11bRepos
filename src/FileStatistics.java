import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Joshua on 5/2/2017.
 */
public class FileStatistics implements Serializable{
    public ArrayList<Integer> grades;
    public final int MIN_GRADE_FOR_A=90;
    public final int MIN_GRADE_FOR_B=80;
    public final int MIN_GRADE_FOR_C=70;
    public final int MIN_GRADE_FOR_D=60;

    public FileStatistics (Scanner scanFromFile){
        this.grades = new ArrayList<Integer>( );
        System.out.println("---> In the constructor: reading data from the input file ");
        while (scanFromFile.hasNext())
        {
            try
            {
                int grade=scanFromFile.nextInt();
                this.grades.add(grade);
                System.out.println(grade);
            }
            catch (InputMismatchException ime)
            {
                System.out.println("The input line \""+scanFromFile.next()+"\" does not contain integer - ignoring");
            }
        }
        System.out.println("---> In the constructor: "+this.grades.size()+" grades read in.");
    }

    public double gradeAverage(){
        double gradeAverage=0.0;
        int sum=0;
        int counter=0;
        for (Integer current : this.grades)
        {
            sum+=current;
            counter++;
        }
        gradeAverage=((double)sum)/((double)counter);
        return gradeAverage;
    }

    public double passRate(){
        int counter=0;
        for (Integer current : this.grades)
        {
            if (current>=MIN_GRADE_FOR_D) {
                counter++;
            }
        }
        double passRate=((double)counter)/this.grades.size();
        return passRate;
    }

    public int highestGrade(){
        int highestGrade=Integer.MIN_VALUE;
        for (Integer current : this.grades)
        {
            if (current>highestGrade){
                highestGrade=current;
            }
        }
        return highestGrade;
    }

    public char[] getLetterGrades(){
        char [] letterGrades= new char[this.grades.size()];
        for (int i = 0; i <this.grades.size() ; i++)
        {
            switch(this.grades.get(i)/10)
            {
                case 10:
                case MIN_GRADE_FOR_A/10: letterGrades[i] ='A';
                    break;
                case MIN_GRADE_FOR_B/10: letterGrades[i] ='B';
                    break;
                case MIN_GRADE_FOR_C/10: letterGrades[i]='C';
                    break;
                case MIN_GRADE_FOR_D/10: letterGrades[i]='D';
                    break;
                default: letterGrades[i]='F';
            }
        }
        return letterGrades;
    }

    public ArrayList<Integer> getGrades(){
        ArrayList<Integer> getGrades= new ArrayList<Integer>();
        for (Integer current : this.grades)
        {
            getGrades.add(current);
        }
        return getGrades;
    }

    public void saveGradesInFile(){
        try
        {
            FileOutputStream fos = new FileOutputStream("savedGrades.dat",false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(this.grades);
        }
        catch (FileNotFoundException fnf){
            System.out.println("Unable to find file");
        }
        catch (IOException ioe){
            ioe.printStackTrace( );
        }
        System.out.println("Saved grades in \"savedGrades.dat\" file.");
    }

    public void displaySavedGrades(){
        try
        {
            FileInputStream fis = new FileInputStream("savedGrades.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            try
            {
                ArrayList<Integer> temp = (ArrayList<Integer>) ois.readObject();
                System.out.print("The saved grades are: [");
                for (Integer current:temp)
                {
                    System.out.print(current+", ");
                }
                System.out.println("]");
            }
            catch( EOFException eofe )
            {
                System.out.println( "End of the file reached" );
            }

            catch( ClassNotFoundException cnfe )
            {
                System.out.println( cnfe.getMessage( ) );
            }
            catch (IOException ioe){
                ioe.printStackTrace( );
            }

            finally
            {
                System.out.println( "End of the file \"savedGrades.dat\" reached" );
                ois.close( );
            }
        } // end outer try block
        catch (FileNotFoundException fnf){
            System.out.println("Unable to find file");
        }
        catch (IOException ioe){
            ioe.printStackTrace( );
        }
    }

    public String toString(){
        return "There are "+this.grades.size()+" grades.";
    }
}
