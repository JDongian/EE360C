/*
 * Joshua Dong
 * jid295
 */
import java.util.HashMap;


public class Program3 implements IProgram3 {
    private int numClasses;
    private int maxGrade;
    private HashMap<Integer, int[]> solutions;
    GradeFunction gf;

    public Program3() {
    	 this.numClasses = 0;
         this.maxGrade = 0;
         this.solutions = new HashMap<Integer, int[]>();
         this.gf = null;
    }

    public void initialize(int n, int g, GradeFunction gf) {
    	 this.numClasses = n;
         this.maxGrade = g;
         this.gf = gf;
    }
    
    public int[] computeHours(int totalHours) {
        int[][] solution = maximizeGradeAverage(totalHours);
        int[] hours = new int[numClasses];
        hoursRemaining = totalHours;
        currId = numClasses - 1;

        while (hoursRemaining > 0) {
            int result = solution[currId][hoursRemaining];
    
            hours[currId] = result;
            hoursRemaining -= result;
            --currId;
//            if (result == 0) {
//                //hours[currId] = 0;
//                --currId;
//            } else {
//                hours[currId] = result;
//
//                hoursRemaining -= result;
//                --currId;
//            }
        }

        return hours;
    }

    public int[] computeGrades(int totalHours) {
        int[] hours = computeHours(totalHours);
        int[] grades = new int[numClasses];

        for (int id = 0; id < numClasses; ++id) {
            grades[id] = gf.grade(id, hours[id]);
        }

        return grades;
    }

    private int[] maximizeGradeAverage(int totalHours) {
//      TODO: remember

    	int[][] solutions = new int[numClasses][totalHours+1];
    	int[][] selection = new int[numClasses][totalHours+1];
    	
        for (int id = 0; id < numClasses; ++id) {
            for (int hours = 0; hours <= totalHours; ++hours) {
                if (id == 0) {
                    solutions[id][hours] = gf.grade(id, hours);
                    selection[id][hours] = n;
                } else {
                    int max = 0;
                    int hour = 0;

                    for (int d = 0; d < hours; ++d) {
                        int score = solutions[id-1][hours-d] + gf.grade(id, d);

                        if (score > max) {
                            max = score;
                            hour = d;
                        }
                    }

                    if (max > solution[id-1][hours]) {
                        solutions[id][hours] = max;
                        selection[id][hours] = hour;
                    } else {
                        System.out.println("....wtf");
                        solutions[id][hours] = solution[id-1][hours];
                        //selection[id][hours] = -1; //
                        selection[id][hours] = 0; //
                    }
                }
        }
        return solution;
    }
}
