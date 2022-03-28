import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.GridLayout;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class Application {
    private static void renderFlights(WeightedGraphInterface graph, Object departAirport, JTextArea panel1, JTextPane panel2, JTextArea panel3, JTextPane panel4) {
        String bfsOutput = graph.BFS(departAirport);
        graph.clearMarks();
        Object[] airports = bfsOutput.split(",");
        Arrays.sort(airports);
        String departureAirports = "";
        String toCount = "";
        String arrivalAirports = "";
        String distances = "";
        for (Object airport : airports) {
            airport = airport.toString().intern();
            QueueInterface queue = graph.getToVertices(airport);
            while (!queue.isEmpty()) {
                Object arrivalPort = queue.dequeue();
                int dist = graph.weightIs(airport, arrivalPort);
                departureAirports += capitilize(airport) + "\n";
                toCount += "to\n";
                arrivalAirports += capitilize(arrivalPort) + "\n";
                distances += dist + "\n";
            }
        }
        panel1.setText(departureAirports);
        panel2.setText(toCount);
        panel3.setText(arrivalAirports);
        panel4.setText(distances);
    }

    private static String capitilize(Object obj) {
        String str = obj.toString();
        return str.toString().substring(0, 1).toUpperCase() + str.toString().substring(1);
    }

    private static boolean validateInput(JTextField field1, JTextField field2, JTextField field3) {
        String stringField1 = field1.getText().intern();
        String stringField2 = field2.getText().intern();
        String intField = field3.getText().intern();
        boolean validIntField = true;
        if (intField == "") {
            validIntField = false;
        }
        else {
            for (int i = 0; i < intField.length(); i++) {
                if (!Character.isDigit(intField.charAt(i))) {
                    validIntField = false;
                    break;
                }
            }
        }
        if (stringField1 == "" || stringField2 == "" || !validIntField) {
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
        WeightedGraphInterface graph = new WeightedGraph();

        JFrame frame = new JFrame("United States Travel Search");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
    
        JPanel flightPanel = new JPanel();
        flightPanel.setLayout(new GridLayout(1, 2));

        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new GridLayout(1, 4));
        schedulePanel.setBackground(Color.LIGHT_GRAY);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 1));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 4));

        // Lbl => Label
        // TF => Text Field
        // Btn => Button
        JLabel departLbl, arrivalLbl, distanceLbl;
        JTextField departTF, arrivalTF, distanceTF;
        JButton addBtn, seekBtn, deleteBtn, exitBtn;
        JTextArea schedulePanel1, schedulePanel3, flightInfo;
        JTextPane schedulePanel2, schedulePanel4;

        departLbl = new JLabel("Departure Airport");
        departTF = new JTextField(20);

        arrivalLbl = new JLabel("Arrival Airport");
        arrivalTF = new JTextField(20);

        distanceLbl = new JLabel("Distance");
        distanceTF = new JTextField(20);

        schedulePanel1 = new JTextArea();
        schedulePanel1.setOpaque(false);
        schedulePanel1.setEditable(false);

        schedulePanel2 = new JTextPane();
        StyledDocument panel = schedulePanel2.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        panel.setParagraphAttributes(0, panel.getLength(), center, false);
        schedulePanel2.setOpaque(false);
        schedulePanel2.setEditable(false);

        schedulePanel3 = new JTextArea();
        schedulePanel3.setOpaque(false);
        schedulePanel3.setEditable(false);

        schedulePanel4 = new JTextPane();
        panel = schedulePanel4.getStyledDocument();
        SimpleAttributeSet right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        panel.setParagraphAttributes(0, panel.getLength(), right, false);
        schedulePanel4.setOpaque(false);
        schedulePanel4.setEditable(false);

        flightInfo = new JTextArea();
        flightInfo.setEditable(false);

        addBtn = new JButton("Add New Flight");
        seekBtn = new JButton("Seek Flight");
        deleteBtn = new JButton("Delete Flight");
        exitBtn = new JButton("Exit");

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (graph.isFull()) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Please delete flights to start adding more",
                        "Maximum Flights Limit Reached",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    if (!validateInput(departTF, arrivalTF, distanceTF)) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Please enter valid inputs",
                            "No Valid Inputs",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    else {
                        Object departAirport = departTF.getText().toLowerCase().intern();
                        Object arrivalAirport = arrivalTF.getText().toLowerCase().intern();
                        int distance = Integer.parseInt(distanceTF.getText());
                        if (!graph.isExistVertex(departAirport)) {
                            graph.addVertex(departAirport);
                        }
                        if (!graph.isExistVertex(arrivalAirport)) {
                            graph.addVertex(arrivalAirport);
                        }
                        graph.addEdge(departAirport, arrivalAirport, distance);
                        renderFlights(graph, departAirport, schedulePanel1, schedulePanel2, schedulePanel3, schedulePanel4);
                    }
                }
            }
        });

        seekBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (graph.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Please add flights to start searching them",
                        "No Flights",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    if (!validateInput(departTF, arrivalTF, distanceTF)) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Please enter valid inputs",
                            "No Valid Inputs",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    else {
                        Object departAirport = departTF.getText().toLowerCase().intern();
                        Object arrivalAirport = arrivalTF.getText().toLowerCase().intern();
                        int distance = Integer.parseInt(distanceTF.getText());
                        String bfsOutput = graph.BFS(departAirport);
                        graph.clearMarks();
                        Object[] airports = bfsOutput.split(",");
                        Arrays.sort(airports);
                        boolean flightFound = false;
                        for (Object airport : airports) {
                            airport = airport.toString().intern();
                            QueueInterface queue = graph.getToVertices(airport);
                            while (!queue.isEmpty()) {
                                Object arrivalPort = queue.dequeue();
                                int dist = graph.weightIs(airport, arrivalPort);
                                if ((airport == departAirport) && (arrivalPort == arrivalAirport) && (dist == distance)) {
                                    flightInfo.setText(capitilize(airport) + " to " + capitilize(arrivalPort) + " is available");
                                    flightFound = true;
                                    break;
                                }
                            }
                        }
                        if (!flightFound) {
                            flightInfo.setText(capitilize(departAirport) + " to " + capitilize(arrivalAirport) + " is not available");
                        }
                    }
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (graph.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Please add flights to start deleting them",
                        "No Flights",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    if (!validateInput(departTF, arrivalTF, distanceTF)) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Please enter valid inputs",
                            "No Valid Inputs",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    else {
                        Object departAirport = departTF.getText().toLowerCase().intern();
                        Object arrivalAirport = arrivalTF.getText().toLowerCase().intern();
                        int distance = Integer.parseInt(distanceTF.getText());
                        String bfsOutput = graph.BFS(departAirport);
                        graph.clearMarks();
                        Object[] airports = bfsOutput.split(",");
                        Arrays.sort(airports);
                        boolean flightFound = false;
                        for (Object airport : airports) {
                            airport = airport.toString().intern();
                            QueueInterface queue = graph.getToVertices(airport);
                            while (!queue.isEmpty()) {
                                Object arrivalPort = queue.dequeue();
                                int dist = graph.weightIs(airport, arrivalPort);
                                if ((airport == departAirport) && (arrivalPort == arrivalAirport) && (dist == distance)) {
                                    graph.removeEdge(airport, arrivalPort, dist);
                                    flightInfo.setText(capitilize(airport) + " to " + capitilize(arrivalPort) + " is deleted");
                                    renderFlights(graph, departAirport, schedulePanel1, schedulePanel2, schedulePanel3, schedulePanel4);
                                    flightFound = true;
                                    break;
                                }
                            }
                        }
                        if (!flightFound) {
                            flightInfo.setText(capitilize(departAirport) + " to " + capitilize(arrivalAirport) + " is not available");
                        }
                    }
                }
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        inputPanel.add(departLbl);
        inputPanel.add(departTF);
        inputPanel.add(arrivalLbl);
        inputPanel.add(arrivalTF);
        inputPanel.add(distanceLbl);
        inputPanel.add(distanceTF);

        TitledBorder scheduleTitle, infoTitle;
        scheduleTitle = BorderFactory.createTitledBorder("Flight Schedule");
        infoTitle = BorderFactory.createTitledBorder("Flight Information");
        schedulePanel.setBorder(scheduleTitle);
        infoPanel.setBorder(infoTitle);

        schedulePanel.add(schedulePanel1);
        schedulePanel.add(schedulePanel2);
        schedulePanel.add(schedulePanel3);
        schedulePanel.add(schedulePanel4);

        infoPanel.add(flightInfo);

        flightPanel.add(schedulePanel);
        flightPanel.add(infoPanel);
        
        btnPanel.add(addBtn);
        btnPanel.add(seekBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(exitBtn);

        frame.add(inputPanel, "North");
        frame.add(flightPanel, "Center");
        frame.add(btnPanel, "South");
        frame.setVisible(true);
        frame.setSize(700, 500);
    }
}