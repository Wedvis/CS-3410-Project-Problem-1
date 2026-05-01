package group_project;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Test_Deletion_DBLhash {
    public static void main(String[] args) {
        File data = new File("src/group_project/data.csv");
        File dblTest_delete = new File("src/group_project/dblTest_delete.txt");
        boolean doesExist = data.exists();
        //Note this file is not meant to be used directly. It's simply a reference for how testing should work with
        //the linear probing deletion

        if (doesExist) {
            try {
                int average = 0;
                for(int i = 0; i < 200; i++) {
                    Scanner input = new Scanner(data);
                    input.next();
                    DblHashMap<Integer> test = new DblHashMap<>(3);
                    while (input.hasNext()) {
                        String[] line = input.next().split(",");
                        test.put(0, line[0]);
                        if (test.valCount >= (test.array.length / 2)) {
                            test.resizePrime();
                            //test.array.length * 2
                        }
                    }
                    input.close();
                    Scanner input2 = new Scanner(data);
                    long timeTotal = 0;
                    long t1;
                    long t2;

                    while (input2.hasNext()) {
                        String[] line = input2.next().split(",");
                        t1 = System.nanoTime();
                        test.remove(line[0]);
                        t2 = System.nanoTime();
                        timeTotal += (t2 - t1);
                    }
                    input2.close();
                    System.out.println(timeTotal);
                    System.out.println(timeTotal/test.valCount);
                    average += (int)(timeTotal/test.valCount);
                }

                System.out.println("Average = " + (average/200));

            } catch (IOException e) {
                System.out.println(":C");
            }
        }
    }
}
