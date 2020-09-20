import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

class Copy {
	public void getfile_copy(Socket socket, String t) throws IOException {

		InputStream in = socket.getInputStream();
		String locate = "E:\\" + t;
		OutputStream fos = new FileOutputStream(locate);
		byte[] data = new byte[1024];
		int count;
		System.out.println("Getting file...");
		while ((count = in.read(data)) >= 0) {
			fos.write(data, 0, count);
		}

		System.out.println("Receving file successfully!");
		in.close();
		fos.close();
		socket.close();

	}

	public void getFileZeroCopy(SocketChannel socket, String name) {

		Path location = Paths.get("E:\\" + name);
		FileChannel destination = null;
		try {
			destination = FileChannel.open(location, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE, StandardOpenOption.READ);
			ByteBuffer buff = ByteBuffer.allocate(1024);
			while (socket.read(buff) > 0) {
				buff.flip();
				destination.write(buff);
				buff.clear();
			}
			destination.close();
		} catch (IOException er) {
			System.out.print(er);
		}

	}
}

public class Client extends JFrame {

	Client obj;
	JPanel panel;
	JTextPane status;
	JTextField input;
	JButton btn;

	Socket socket;
	DataInputStream DaIn;
	DataOutputStream DaOut;
	StringBuilder result;

	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btn) {
				// obj.process();
			}
		}
	}

	public Client() {
		panel = new JPanel();
		status = new JTextPane();
		status.setPreferredSize(new Dimension(700, 300));
		input = new JTextField();
		input.setColumns(20);
		btn = new JButton("sent");
		btn.addActionListener(new ButtonActionListener());

		panel.add(status);
		panel.add(input);
		panel.add(btn);
		add(panel);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setVisible(true);

		result = new StringBuilder();

	}

	public void process() throws Exception {

		Scanner sc = new Scanner(System.in);

		while (true) {
			if (socket == null || socket.isClosed()) {
				socket = new Socket("192.168.8.80", 12345);
				DaIn = new DataInputStream(socket.getInputStream());
				DaOut = new DataOutputStream(socket.getOutputStream());
			}

			String str = DaIn.readUTF();
			System.out.println(str);
			String user = sc.nextLine();
			DaOut.writeUTF(user);
			if (user.equals("list")) {
				System.out.println(DaIn.readUTF());
			} else if (user.equals("download")) {
				System.out.println(DaIn.readUTF());
				user = sc.nextLine();
				DaOut.writeUTF(user);
				boolean avilible = DaIn.readBoolean();
				System.out.println(avilible);
				if (avilible) {
					System.out.println("Enter mode (N : normal , Z : zero copy)");
					String mode = sc.nextLine();
					DaOut.writeUTF(mode);
					if (mode.equalsIgnoreCase("N")) {
						new Copy().getfile_copy(socket, user);
						System.out.println("Finished");
					} else if (mode.equals("Z")) {
						SocketChannel socketChannel = SocketChannel.open();
						socketChannel.configureBlocking(true);
						SocketAddress sockAddr = new InetSocketAddress("192.168.8.80", 2144);
						socketChannel.connect(sockAddr);

						new Copy().getFileZeroCopy(socketChannel, user);
					}else {
						System.out.println("incorrect mode");
					}
				}
			} else if (user.equals("exit")) {
				System.out.println(DaIn.readUTF());
				DaIn.close();
				DaOut.close();
				socket.close();
				break;

			}
			System.gc();
		}
	}

	public static void main(String args[]) throws Exception {
		Client c = new Client();
		c.obj = c;
		c.process();

	}
}