import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ReadTextFromFileSelector extends JFrame {

	JPanel p;
	JButton btn;
	JTextArea textArea;
	JScrollPane textareaScroll;


	public ReadTextFromFileSelector() {
		p = new JPanel();
		p.setBackground(Color.CYAN);
		btn = new JButton("Browse");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					int returnValue = fileChooser.showOpenDialog(null);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						BufferedReader buf = new BufferedReader(new FileReader(selectedFile));
						String str = "";
						String print = "";
						if ((str = buf.readLine()) != null) {
							textArea.append(str);
						}
					}
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}

			}
		});

		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(700, 300));
		textareaScroll = new JScrollPane(textArea);

		p.add(textareaScroll);
		p.add(btn);
		add(p);

		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		ReadTextFromFileSelector g = new ReadTextFromFileSelector();

	}
}
