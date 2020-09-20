import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectFileToPath extends JFrame {
	JPanel panel;
	JTextArea textArea, textArea2;
	JList AList;
	JScrollPane textareaScroll, sp;
	JButton browseBtn, pathBtn, listfileBtn, download;
	ButtonGroup bg;
	JRadioButton r1, r2;
	String path = "E:\\";
	String downloadPath;
	JList<String> list;

	SelectFileToPath() {
		setTitle("Copy manager");
		panel = new JPanel();
		textArea = new JTextArea();
		textArea2 = new JTextArea();
		textArea.setPreferredSize(new Dimension(700, 300));
		textArea.setEditable(false);

		textareaScroll = new JScrollPane(textArea);
		list = new JList<String>();
		sp = new JScrollPane(list);
		sp.setPreferredSize(new Dimension(700, 200));
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedValue().toString() != null)
					downloadPath = path + "\\" + list.getSelectedValue().toString();

			}
		});

		listfileBtn = new JButton("ListFile");
		listfileBtn.setBackground(Color.GREEN);
		listfileBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				textareaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				File locationfile = new File(path);
				String listfile[] = locationfile.list();
				
				DefaultListModel<String> model = new DefaultListModel<String>();
				for (String str : listfile) {
						model.addElement(str);
						textArea.append(str + "\n");
				}
				if (!model.isEmpty())
					list.setModel(model);
			}
		});
		pathBtn = new JButton("Select Directory");
		pathBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("choosertitle");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					path = fileChooser.getSelectedFile().toString();
				}
			}
		});
		bg = new ButtonGroup();
		r1 = new JRadioButton("Normal copy", true);
		r2 = new JRadioButton("Zero copy");
		bg.add(r1);
		bg.add(r2);
		download = new JButton("Download");
		download.setBackground(Color.YELLOW);
		download.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(downloadPath);

			}
		});

		panel.add(sp);
		panel.add(pathBtn);
		panel.add(listfileBtn);
		panel.add(r1);
		panel.add(r2);
		panel.add(download);

		panel.setBackground(new Color(153, 255, 255));
		add(panel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		SelectFileToPath d = new SelectFileToPath();
	}

}
