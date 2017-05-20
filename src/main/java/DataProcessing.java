import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Andrei on 14/05/2017.
 */
public class DataProcessing {

    List<MonitoredData> monitoredData;

    public DataProcessing()
    {
        monitoredData=new ArrayList<MonitoredData>();
    }

    void getData()
    {
        LocalDateTime startTime,endTime;

        String activity;
        LocalDate date;
        LocalTime time;
        MonitoredData monitoredDataField;
        try {
            Scanner input=new Scanner(new File("D:\\Downloads Chrome\\TP2017_30224_Bursuc_Andrei_Tema_5\\src\\main\\java\\Activities.txt"));
            while(input.hasNextLine())
            {
                String line=input.nextLine();
                String[] fields=line.split("\t\t");
                DateTimeFormatter inputFormat =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                startTime=LocalDateTime.parse(fields[0],inputFormat);
                endTime=LocalDateTime.parse(fields[1],inputFormat);
                activity=fields[2];
                monitoredDataField=new MonitoredData(startTime,endTime,activity);
                monitoredData.add(monitoredDataField);
                //monitoredData.add(monitoredDataField);

                //System.out.println(fields[0]+"\t\t"+fields[1]+"\t\t"+fields[2]);
            }
            /*for(MonitoredData mmonData:monitoredData)
            {
                for (Map.Entry<LocalDate, ArrayList<LocalTime>> entry : mmonData.getStartTime().entrySet()) {
                    for(LocalTime localTime:entry.getValue())
                    {
                        System.out.println(entry.getKey()+" "+localTime);
                    }
            }
            }*/
           /* for(MonitoredData monitoredDataField1:monitoredData)
            {
                System.out.println(monitoredDataField1.getStartTime()+"      "+monitoredDataField1.getEndTime()+"     "+monitoredDataField1.getActivityLabel());
            }*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    void distinctDays()
    {
        long distinct=monitoredData.stream()
                .map(x->x.getStartTime().getDayOfMonth())
                .distinct()
                .count();
        //dates.forEach(date -> System.out.println("Data "+date));
        System.out.println("Distinct days: "+distinct);
    }

    void distinctActivities()
    {
        //String activity;
        Map<String,Long> distinctAction=monitoredData.stream().collect(
                Collectors.groupingBy(MonitoredData::getActivityLabel,Collectors.counting()));

        distinctAction.forEach((activity,count)->System.out.println("Activitate: "+activity+"\t\tAparitie: "+count));

    }

    void distinctActivitiesEachDay() {
        try {
            PrintStream out = new PrintStream(new FileOutputStream("distinctActivitiesEachDay.txt"));
        Map<Integer, Map<String, Long>> distinctAction = monitoredData.stream()
                .collect(Collectors.groupingBy(MonitoredData::getDayStartingTime, Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting())));

        distinctAction.forEach((activity, count) -> count.forEach((activity2, count2) -> out.println("Zi: " + activity + " Activitate: " + activity2 + "\t\tAparitie: " + count2)));
        }catch (IOException e) {
            e.printStackTrace();
        }
        //distinctAction.forEach((day,activity)->);
    }


    void activityDuration()
    {
        try {
            PrintStream out = new PrintStream(new FileOutputStream("activityDuration.txt"));
            Map<String,Long> activityDuration=monitoredData.stream()
                    //.filter(i->ChronoUnit.MINUTES.between(i.getStartTime(),i.getEndTime())>600)
                    .collect(Collectors.toMap(i->i.getActivityLabel(),i->ChronoUnit.MINUTES.between(i.getStartTime(),i.getEndTime()),(val1,val2)->val1+val2))
                    .entrySet().stream()
                    .filter(i->i.getValue()>=60*10)
                    .collect(Collectors.toMap(i->i.getKey(),i->i.getValue()));
            //Map<String,Long> filteredActivityDuration=activityDuration.entrySet().stream()

            activityDuration.forEach((activity,duration)->out.println("Activitate: "+activity+"\t\tDuratie: "+duration));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void shortActivities()
    {
        try{
            PrintStream out = new PrintStream(new FileOutputStream("shortActivities.txt"));

            Map<String,Long> shortTime=monitoredData.stream()
              //  .map(i->ChronoUnit.MINUTES.between(i.getStartTime(),i.getEndTime()))
                    .filter(i->ChronoUnit.SECONDS.between(i.getStartTime(),i.getEndTime())<5*60)
                    .collect(Collectors.toMap(i->i.getActivityLabel(),i->ChronoUnit.SECONDS.between(i.getStartTime(),i.getEndTime()),(val1,val2)->val1+val2));

            Map<String,Long> totalTime=monitoredData.stream()
                    //.filter(i->ChronoUnit.MINUTES.between(i.getStartTime(),i.getEndTime())>600)
                    .collect(Collectors.toMap(i->i.getActivityLabel(),i->ChronoUnit.SECONDS.between(i.getStartTime(),i.getEndTime()),(val1,val2)->val1+val2));

            List<String> activities=shortTime.entrySet().stream()
                    .filter(i->totalTime.get(i.getKey())-i.getValue()<0.1*totalTime.get(i.getKey()))
                    .map(i->i.getKey())
                    .collect(Collectors.toList());
            //shortActivities.forEach((activity,time)-> System.out.println("Activity: "+activity+" Time: "+time));
            activities.forEach(i-> out.println("Activity: "+i));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void numberSix()
    {
        Map<String,Long> daysMap=monitoredData.stream()
                .filter(i->i.getStartTime().getDayOfMonth()%2==0)
                .collect(Collectors.groupingBy(MonitoredData::getActivityLabel,Collectors.counting()));
        List<String> daysList=daysMap.entrySet().stream()
                .filter(i->i.getValue()>=7)
                .map(i->i.getKey())
                .collect(Collectors.toList());

        //daysList.forEach(i-> System.out.println("Activitate: "+i));
        daysMap.forEach((i,j)-> System.out.println("Activitate: "+i+"Suma zile: "+j));


    }



}
