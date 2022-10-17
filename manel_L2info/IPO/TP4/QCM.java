import java.util.*; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.BreakIterator;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
class QCM {
    private HashMap<String, HashMap<String, Integer>> questions_answers;

    public QCM()
    {
        this.questions_answers = new HashMap<String, HashMap<String, Integer>>();

    }
    public void printQ_As ()
    {
        int index;
        for (Map.Entry <String ,HashMap<String, Integer>> m : this.questions_answers.entrySet()) {
            System.out.println("la question :\n "+m.getKey()+"\n" +"les reponses possible :\n");
            index = 1;
            HashMap<String, Integer> temp =  m.getValue();
            for (Map.Entry mm : ((HashMap<String, Integer>) temp).entrySet()){
                System.out.println(index +") "+mm.getKey()+"\n");
                index++;
            }
        }

    }
    // public void printQ_As ()
    // {
    //     for (Entry<String, HashMap<String, Integer>> m : this.questions_answers) {
    //         System.out.println("la question \n: "+m.getKey());
    //         HashMap<String, Integer> temp = (HashMap<String, Integer>) m.getValue();
    //         for (Entry mm : temp.entrySet()){
    //             System.out.println("ces reponses :\n "+mm.getKey());
    //         }
    //     }

    // }
    public boolean answerCheck(int n , HashMap<String, Integer> resps)
    {
        int index = 1;
        Integer value = 0;
        if( n < 0 || n > resps.size())
        {
            System.out.println("reponse invalide ");
            return (false);
        }
        ArrayList <String> rep  = new ArrayList<>();
        while (index <= n)
        {
            for (Map.Entry <String, Integer> m : resps.entrySet()) {
                rep.add(m.getKey());
            if (index == n){
                value = m.getValue();
                return ( value == 1 ? true: false );
            }
            index++;
        }
        }
        return false;

    }

     public void insertQuestion (String question, HashMap<String, Integer> answers){
        this.questions_answers.put (question, answers);
     }
     public boolean isvalide ()
     {
        boolean validity;
        for (Map.Entry <String ,HashMap<String, Integer>> m : this.questions_answers.entrySet()) {
            HashMap<String, Integer> temp =  m.getValue();
            validity = false;
            for (Map.Entry<String, Integer> mm : temp.entrySet()){
                if (mm.getValue() == 1)
                    validity = true;
            }
            if (!validity)
            {
                System.out.println("QCM invalide!!");
                return (false);
            }
        }
        return (true);
     }
     //public Integer correctanswers()

     public static void main(String[] args){
        int numberQuest = 0;
        QCM qsm = new QCM();
        //QCM preparation 
        String q1 = "quel est les resultats de 1+1 = ?";
        HashMap<String, Integer> r1 = new HashMap<>(){{put ("2",1); put ("0",0); put ("90",0);}};
        qsm.insertQuestion(q1, r1);
        //printing the QCM
        if ( !qsm.isvalide())
            return ;
        
            qsm.printQ_As();
            //user input 
            System.out.println("entrez une reponse :\n");
            Scanner sc = new Scanner(System.in);
            int a = sc.nextInt();
            while (!qsm.answerCheck(a , r1))
            {
                System.out.println("Sorry!! entrez une nouvelle  reponse :\n");
                a = sc.nextInt();
            }
            System.out.println("Bravoo!!!!\nbonne reponse champion\n");
        
        // System.out.println(qsm.answerCheck(9 , r1));
        // System.out.println( qsm.isvalide());
        
       
    }
}


