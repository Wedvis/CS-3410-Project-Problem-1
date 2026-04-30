package group_project;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Test_CollisionResolution {
    public static void main(String[] args) {
        File data = new File("src/group_project/data.csv");
        boolean doesExist = data.exists();

        if (doesExist) {
            try {
                long t1 = System.nanoTime();
                Scanner input = new Scanner(data);
                input.next();
                DblHashMap<Integer> test = new DblHashMap<>(3);
                int value = 0;
                while (input.hasNext()) {
                    String[] line = input.next().split(",");
                    test.put(0, line[0]);
                    if (test.valCount >= (test.array.length / 2)) {
                        test.resizePrime();
                    }
                    
                }
                long t2 = System.nanoTime();
                System.out.println(t2 - t1);
                System.out.println(test.probes_put);
                System.out.println(test.valCount);
                input.close();

            } catch (IOException e) {
                System.out.println(":C");
            }
        }
    }
}
