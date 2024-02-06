import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public String ipAddress;
    public String port;


    TextField textFieldIp = new TextField();
    TextField textFieldPort = new TextField();

    JButton startButton = new JButton("Start server");

    JLabel label = new JLabel("Answer from client: ");
    JLabel label2 = new JLabel("Errors Connection");

    eHandler handler = new eHandler();
    public void Start()
    {
        ipAddress = "lockalhost";
        port = "5000";

        JFrame mainFrame = new JFrame("MainServer");
        mainFrame.setVisible(true);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(400,200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textFieldIp.setText(ipAddress);
        textFieldPort.setText(port);
        label2.setForeground(Color.RED);

        mainFrame.add(textFieldIp);
        mainFrame.add(textFieldPort);
        mainFrame.add(startButton);
        startButton.addActionListener(handler);
        mainFrame.add(label);
        mainFrame.add(label2);
    }

    public class eHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == startButton)
            {
                startButton.setBackground(Color.GREEN);
                try {
                    CreateConnection();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void CreateConnection() throws IOException
    {
        try(
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(textFieldPort.getText()));
                Socket socket = serverSocket.accept();
                Scanner scanner = new Scanner(socket.getInputStream())
        )
        {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            //writer.println("Hello!");
            //while(scanner.hasNextLine())
            //{
                String str = scanner.nextLine();
                writer.println("Client message: "+str);

                label.setText(label.getText()+str);

                System.out.println(str);

                //if(str.equals("exit"))
                //{
                    //break;
                //}
            //}
        }
        catch (IOException ex)
        {
            label2.setText("Error: "+ex);
        }
    }
}
