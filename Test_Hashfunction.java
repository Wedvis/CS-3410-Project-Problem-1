package group_project;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Test_Hashfunction {
    public static void main(String args[]) {
        File data = new File("src/group_project/data.csv");
        //File fnv_size11 = new File("src/group_project/fnv_size11.txt");
        boolean doesExist = data.exists();

        if(doesExist) {
            try {
                long t1 = System.nanoTime();
                Scanner input = new Scanner(data);
                //PrintWriter writer = new PrintWriter(fnv_size11);
                //writer.println("valCount,collisions");
                input.next();
                DblHashMap<Integer> test = new DblHashMap<>(3);
                int value = 0;
                while(input.hasNext()) {
                    String[] line = input.next().split(",");
                    if(Double.parseDouble(line[4]) > -7) {
                        test.put(0, line[0]);
                        //writer.println(test.valCount + "," + test.collisions);
                        if(test.valCount >= (test.array.length/2)) {
                            test.resizePrime();
                        }
                    }
                }
                long t2 = System.nanoTime();
                System.out.println(t2 - t1);
                System.out.println(test.collisions);
                System.out.println(test.valCount);
                input.close();
                //writer.close();

            } catch (IOException e) {
                System.out.println(":C");
            }

        }
    }

}
