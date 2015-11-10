/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobsschedule;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author mazenbadr
 */
public class JobsSchedule {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InputStream stream = ClassLoader.getSystemResourceAsStream("jobs.txt");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        ArrayList<Job> jobs = new ArrayList<>();
        String line;
        try {
            line = buffer.readLine();
            int jobsSize = Integer.valueOf(line);
            while ((line = buffer.readLine()) != null) {
                String[] values = line.split("\\s+");
                Job tempJob = new Job();
                tempJob.weight = Integer.valueOf(values[0]);
                tempJob.length = Integer.valueOf(values[1]);
                tempJob.diffScore = tempJob.weight - tempJob.length;
                tempJob.ratioScore = tempJob.weight * 1.0 / tempJob.length * 1.0;
                jobs.add(tempJob);
            }
        } catch (Exception e) {
        }

        Collections.sort(jobs, new Comparator<Job>() {

            @Override
            public int compare(Job o1, Job o2) {
                if (o1.diffScore == o2.diffScore) {
                    return Integer.valueOf(o1.weight).compareTo(o2.weight) * -1;
                } else {
                    return Integer.valueOf(o1.diffScore).compareTo(o2.diffScore) * - 1;
                }
            }
        });

        int lengths = 0;
        Long total = Long.valueOf(0);
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).c = (lengths + jobs.get(i).length);
            lengths += jobs.get(i).length;
            total += jobs.get(i).weight * jobs.get(i).c;
        }
        System.out.println(total);
        Collections.sort(jobs, new Comparator<Job>() {

            @Override
            public int compare(Job o1, Job o2) {

                return Double.valueOf(o1.ratioScore).compareTo(o2.ratioScore) * - 1;

            }
        });
        lengths = 0;
        total = Long.valueOf(0);
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).c = (lengths + jobs.get(i).length);
            lengths += jobs.get(i).length;
            total += jobs.get(i).weight * jobs.get(i).c;
        }
        System.out.println(total);

    }

}
