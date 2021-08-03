import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CodeSimulation extends JFrame {

    JPanel Mother, Up, Down;
    JScrollPane tascroll;
    JTextArea ta;
    JButton next, prev;
    int click = 0;


    public CodeSimulation() {
        super("Code Simulation");
        Mother = new JPanel(new BorderLayout());

        Up = new JPanel(new GridLayout());
        Down = new JPanel(new FlowLayout());

        next = new JButton(new AbstractAction("Next") {
            @Override
            public void actionPerformed(ActionEvent e) {
                click++;
                if(click == 6){
                    click = 0;
                }
                Code(click);
            }
        });

        prev = new JButton(new AbstractAction("Prev") {
            @Override
            public void actionPerformed(ActionEvent e) {
                click--;
                if(click == 0){
                    click = 6;
                }
                Code(click);

            }
        });

        ta = new JTextArea();
        ta.setEditable(false);
        ta.setText("Press next for the code simulation");
        tascroll = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);



        Up.add(tascroll);
        Down.add(prev);
        Down.add(next);
        Mother.add(Up, BorderLayout.CENTER);
        Mother.add(Down, BorderLayout.SOUTH);

        add(Mother);
        setSize(500,300);
        setVisible(true);
    }
    public void Code(int click){
        if(click == 1){
            ta.setText("\n" +
                    "    public void Dijkstra(int graph[][], int source, int destination){\n" +
                    "        int size = graph[0].length;\n" +
                    "\n" +
                    "        path.add(source);\n" +
                    "\n" +
                    "        // Stores prev vertices\n" +
                    "        int prev[] = new int[size];\n" +
                    "\n" +
                    "        // Marks vertices\n" +
                    "        Boolean markedVertex[] = new Boolean[size];\n" +
                    "\n" +
                    "        // Stores all the distances\n" +
                    "        int distances[] = new int[size];\n" +
                    "\n" +
                    "        // Initialize all markedVertex to false and all distances to maximum values\n" +
                    "        Arrays.fill(markedVertex, false);\n" +
                    "        Arrays.fill(distances, Integer.MAX_VALUE);\n" +
                    "        Arrays.fill(prev, -1);\n" +
                    "\n" +
                    "        // Source to source = 0\n" +
                    "        distances[source] = 0;\n" +
                    "\n" +
                    "        for (int i=0; i<size-1; i++) {\n" +
                    "            int mini = getMinimumDistance(distances, markedVertex, size);\n" +
                    "            // Marks vertex with minimum value as true\n" +
                    "            markedVertex[mini] = true;\n" +
                    "            // change distance of adjacent vertices of the selected vertex\n" +
                    "            for (int j=0; j<size; j++){\n" +
                    "                if(!markedVertex[j] && graph[mini][j]!=0 &&\n" +
                    "                        distances[mini]!=Integer.MAX_VALUE &&\n" +
                    "                        distances[mini] + graph[mini][j] < distances[j]) {\n" +
                    "                    prev[j] = mini;\n" +
                    "                    distances[j] = distances[mini] + graph[mini][j];\n" +
                    "                    System.out.println(distances[j]);\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "        getActualDistance(distances, prev, source, destination, size);\n" +
                    "\n" +
                    "    }");

        }
        if(click == 2){
            ta.setText("private int getMinimumDistance(int distances[], Boolean markedVertex[], int size) {\n" +
                    "        int maximum = Integer.MAX_VALUE;\n" +
                    "        int min_index = -1;\n" +
                    "        for (int i=0; i<size; i++)\n" +
                    "            if (markedVertex[i] == false && distances[i] <= maximum) {\n" +
                    "                maximum = distances[i];\n" +
                    "                min_index = i;\n" +
                    "            }\n" +
                    "        return min_index;\n" +
                    "    }");
        }
        if(click ==3){
            ta.setText("private void getActualDistance(int distances[], int prev[], int source, int destination, int size) {\n" +
                    "        for (int k=0; k<size; k++){\n" +
                    "            if (k == destination){\n" +
                    "                //System.out.println(source + \"->\" + k + \" is \" + distances[k]);\n" +
                    "                distance = distances[k];\n" +
                    "                printPath(prev, k, destination);\n" +
                    "            }\n" +
                    "        }\n" +
                    "        printIt(path, destination);\n" +
                    "    }");
        }
        if(click ==4){
            ta.setText("private void printPath(int prev[], int x, int destination) {\n" +
                    "        if(prev[x] == -1)\n" +
                    "            return;\n" +
                    "\n" +
                    "        printPath(prev, prev[x], destination);\n" +
                    "\n" +
                    "        if (x == destination)\n" +
                    "            return;\n" +
                    "\n" +
                    "        path.add(x);\n" +
                    "    }");
        }
        if(click == 5){
            ta.setText("private void printIt(ArrayList<Integer> path, int destination){\n" +
                    "        path.add(destination);\n" +
                    "\n" +
                    "        // print path\n" +
                    "        for (int l=0; l<path.size(); l++)\n" +
                    "            connections += \" \" + path.get(l);\n" +
                    "        //System.out.print(path.get(l) + \", \");\n" +
                    "        //System.out.println(via);\n" +
                    "    }");
        }

    }
}
