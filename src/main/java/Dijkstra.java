import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static java.awt.Color.*;

public class Dijkstra extends JFrame {

    JPanel Mother, Left, Right, LeftUp, LeftDown, LeftSouth;
    JRadioButton AddNodes, AddLines, GetShortestPath;
    ButtonGroup bg;
    JButton Enter, Random, clear, CodeSim, Calculate, Reset;
    int count = 0;
    JTextField From, To, Weight, Sourcetext, Desttext;
    JLabel FromLabel, ToLabel, WeightLabel, Instruction1, Instruction, SourceLabel, DestLabel, MaxDistance;
    ArrayList <Integer> weightlist;
    ArrayList <Integer> xpos;
    ArrayList <Integer> ypos;
    int click1 =0;
    Algo m = new Algo();
    CodeSimulation cs = new CodeSimulation();
    int adjacency[][] = new int[100][100];

    public Dijkstra(){
        super("Dijkstra");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cs.setLocationRelativeTo(null);
        cs.setVisible(false);
        xpos = new ArrayList<>();
        ypos = new ArrayList<>();
        weightlist = new ArrayList<>();
        Mother = new JPanel(new BorderLayout());

        Left = new JPanel();
        Left.setPreferredSize(new Dimension(400,300));
        Left.setBackground(gray);

        Right = new JPanel();
        Right.setPreferredSize(new Dimension(300,300));

        LeftUp = new JPanel();
        LeftUp.setLayout(new GridBagLayout());
        LeftUp.setPreferredSize(new Dimension(400,200));
        LeftUp.setBackground(gray);

        LeftDown = new JPanel();
        LeftDown.setLayout(new GridBagLayout());
        LeftDown.setPreferredSize(new Dimension(400,150));
        LeftDown.setBackground(gray);

        LeftSouth = new JPanel(new GridBagLayout());
        LeftSouth.setPreferredSize(new Dimension(400,150));
        LeftSouth.setBackground(gray);

        Instruction = new JLabel("Press Add Nodes to Add Nodes");
        Instruction.setFont(new Font("Arial", Font.BOLD, 30));
        Instruction1 = new JLabel("Click Anywhere to Add Nodes");
        Instruction1.setFont(new Font("Arial", Font.BOLD, 30));

        Right.add(Instruction);
        Right.add(Instruction1);

        Reset = new JButton(new AbstractAction("Reset") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=1; i<m.path.size(); i++) {
                    drawLineChangeColor(xpos.get(m.path.get(i-1)-1),ypos.get(m.path.get(i-1)-1),xpos.get(m.path.get(i)-1),ypos.get(m.path.get(i)-1), BLACK);
                }
                Left.remove(MaxDistance);
                m.reset();
                cs.dispose();
                Calculate.setEnabled(true);
                Left.updateUI();
            }
        });

        clear = new JButton(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Right.removeAll();
                Right.revalidate();
                Right.repaint();
                count = 0;
                xpos.clear();
                ypos.clear();
                for(int i=0; i<xpos.size(); i++) {
                    System.out.println("xpos: "+xpos);
                    System.out.println("ypos: "+ypos);
                }
                m.reset();
            }
        });

        Calculate = new JButton(new AbstractAction("Calculate") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer source = Integer.parseInt(Sourcetext.getText());
                Integer destination = Integer.parseInt(Desttext.getText());
                cs.setVisible(true);
                cs.setDefaultCloseOperation(HIDE_ON_CLOSE);
                m.Dijkstra(adjacency, source, destination);
                MaxDistance = new JLabel("Cost: " + m.distance);
                MaxDistance.setFont(new Font("Arial", Font.BOLD, 20));

                Left.add(MaxDistance);

                for (int i=0; i<m.path.size(); i++){
                    System.out.println(m.path.get(i));
                }

                // if there is no path b/w source and destination
                if (m.distance == Integer.MAX_VALUE) {
                    System.out.println("There is no way to go.");
                    MaxDistance.setText("There is no way to go.");
                }
                else{
                    for(int i=1; i<m.path.size(); i++){
                        drawLineChangeColor(xpos.get(m.path.get(i-1)-1),ypos.get(m.path.get(i-1)-1),xpos.get(m.path.get(i)-1),ypos.get(m.path.get(i)-1), RED);
                    }
                    System.out.println("Minimum Distance.");
                }
                Calculate.setEnabled(false);
                Left.updateUI();
            }
        });

        CodeSim = new JButton(new AbstractAction("CODE SIMULATION") {
            @Override
            public void actionPerformed(ActionEvent e) { cs.setVisible(true); cs.setDefaultCloseOperation(HIDE_ON_CLOSE); }});

        Random = new JButton(new AbstractAction("Random") {
            @Override
            public void actionPerformed(ActionEvent e) {
                double rand = Math.random()*(50-1+1)+1;
                int converter = (int) rand;
                Weight.setText(String.valueOf(converter));
            }
        });

        Enter = new JButton(new AbstractAction("Enter") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(count);
                for(int i=0; i<xpos.size(); i++){
                    System.out.println(xpos.get(i));
                    System.out.println(ypos.get(i));
                }
                if(AddLines.isSelected()){
                    int FromVal = Integer.parseInt(From.getText());
                    int ToVal = Integer.parseInt(To.getText());
                    int WeightVal = Integer.parseInt(Weight.getText());
                    drawLine(xpos.get(FromVal-1),ypos.get(FromVal-1),xpos.get(ToVal-1),ypos.get(ToVal-1), WeightVal);
                    weightlist.add(WeightVal);
                    adjacency[FromVal][ToVal] = WeightVal;
                    adjacency[ToVal][FromVal] = WeightVal;
                }
            }
        });

        Right.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(AddNodes.isSelected()){
                    boolean stopper = true;
                    Instruction.setVisible(false);
                    Instruction1.setVisible(false);
                    int x = e.getX() + 400;
                    int y = e.getY() +15;
                    xpos.add(x+20);
                    ypos.add(y+30);
                    for(int i=0; i<xpos.size()-1; i++){
                        if(x+20 == xpos.get(i) && y+30 == ypos.get(i)){
                            JOptionPane.showMessageDialog(rootPane, "Please press on another coordinate!", "WARNING", JOptionPane.ERROR_MESSAGE);
                            xpos.remove(i);
                            ypos.remove(i);
                            stopper = false;
                            return;
                        }
                    }
                    if(stopper) {
                        count++;
                        drawNode(count, x, y);
                    }

                    for(int i=0; i<xpos.size(); i++){
                        System.out.println(xpos.get(i));
                        System.out.println(ypos.get(i));
                    }
                }
            }

            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        cs.next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click1++;
                if(click1 >= m.path.size()){
                    click1 = 0;
                }else {
                    System.out.println(click1);
                    System.out.println(m.path.size());
                    drawLineChangeColor(xpos.get(m.path.get(click1 - 1) - 1), ypos.get(m.path.get(click1 - 1) - 1), xpos.get(m.path.get(click1) - 1), ypos.get(m.path.get(click1) - 1), GREEN);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        AddNodes = new JRadioButton("Add Nodes");
        AddNodes.setOpaque(false);
        AddLines = new JRadioButton("Add Lines");
        AddLines.setOpaque(false);
        GetShortestPath = new JRadioButton("Get Shortest Path");
        GetShortestPath.setOpaque(false);
        bg = new ButtonGroup();
        bg.add(AddNodes);
        bg.add(AddLines);
        bg.add(GetShortestPath);

        FromLabel = new JLabel("From: ");
        ToLabel = new JLabel("To: ");
        WeightLabel = new JLabel("Weight: ");
        SourceLabel = new JLabel("Source: ");
        DestLabel = new JLabel("Destination: ");
        From = new JTextField(5);
        To = new JTextField(5);
        Sourcetext = new JTextField(5);
        Desttext = new JTextField(5);
        Weight = new JTextField(10);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        LeftDown.add(FromLabel,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        LeftDown.add(ToLabel,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx =1;
        LeftDown.add(From,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx =1;
        LeftDown.add(To,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx =0;
        gbc.gridy = 3;
        LeftDown.add(WeightLabel,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx =1;
        gbc.gridy = 3;
        gbc.weightx =1;
        LeftDown.add(Weight,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx =0;
        gbc.gridy = 4;
        gbc.weightx =1.5;
        LeftDown.add(Random,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx =1;
        gbc.gridy = 4;
        gbc.weightx =1.5;
        LeftDown.add(Enter, gbc);

        LeftDown.updateUI();
        LeftDown.setVisible(false);
        LeftSouth.setVisible(false);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy =0;
        gbc.weightx=1;
        LeftUp.add(AddNodes,gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy =1;
        gbc.weightx=1;
        LeftUp.add(AddLines, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy =2;
        gbc.weightx=1;
        LeftUp.add(GetShortestPath, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy =3;
        gbc.weightx=1;
        LeftUp.add(clear, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy =4;
        gbc.weightx=1;
        LeftUp.add(CodeSim, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        LeftSouth.add(SourceLabel, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        LeftSouth.add(Sourcetext, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        LeftSouth.add(DestLabel, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        LeftSouth.add(Desttext, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        LeftSouth.add(Calculate, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        LeftSouth.add(Reset, gbc);

        AddLines.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(AddLines.isSelected()){
                    LeftDown.setVisible(true);
                    LeftSouth.setVisible(false);
                }
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        AddNodes.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(AddNodes.isSelected()){
                    LeftDown.setVisible(false);
                    LeftSouth.setVisible(false);
                }
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        GetShortestPath.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(GetShortestPath.isSelected()){
                    LeftSouth.setVisible(true);
                    LeftDown.setVisible(false);
                }
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        Left.add(LeftUp, BorderLayout.NORTH);
        Left.add(LeftDown, BorderLayout.CENTER);
        Left.add(LeftSouth, BorderLayout.SOUTH);
        Mother.add(Left, BorderLayout.WEST);
        Mother.add(Right, BorderLayout.CENTER);
        add(Mother);
        Mother.updateUI();
        Right.updateUI();
        setSize(1280,720);
        setVisible(true);
    }

    private void drawNode(int count, int x, int y){
        Graphics g = this.getGraphics();
        Graphics2D graphics2d = (Graphics2D) g;
        g.setColor(black);
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.fillOval(x-13, y+10, 45, 45);
        Font font = new Font("Verdana", Font.PLAIN, 25);
        g.setFont(font);
        g.setColor(WHITE);
        String text = count + "";
        if (count > 9)
            g.drawString(text, x-6, y+41);
        else
            g.drawString(text, x+2, y+41);
    }

    public void drawLine(int x1, int y1, int x2, int y2, int weight){
        Graphics g = this.getGraphics();
        Line2D line = new Line2D.Float(x1, y1, x2, y2);
        g.drawLine(x1,y1,x2,y2);
        int midx = (x1+x2)/2;
        int midy = (y1+y2)/2;
        g.drawString(String.valueOf(weight), midx, midy);
    }

    private void drawLineChangeColor(int x1, int y1, int x2, int y2, Color col){
        Graphics g = this.getGraphics();
        Graphics2D graphics2d = (Graphics2D) g;
        g.setColor(col);
        g.drawLine(x1,y1,x2,y2);
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    public static void main(String[] args) {
        new Dijkstra();
    }
}