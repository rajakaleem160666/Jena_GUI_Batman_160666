import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.log4j.BasicConfigurator;
import javax.swing.JTable;

public class Window1 {
	public JTextField textField;
	public JFrame window;
	public JButton btnSearch;
	private JTable table;
	private JLabel lblNewLabel_1;

	JPanel panel = new JPanel();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window1 window2 = new Window1();
					window2.window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	public Window1() {
		initialize();
	}

	private void initialize() {
			
		BasicConfigurator.configure();
		
//		String t = textField.getText();
		String queryString =
				
				"prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"prefix dbpedia-owl: <http://dbpedia.org/ontology/>\r\n" + 
				"prefix movie: <http://data.linkedmdb.org/resource/movie/>\r\n" + 
				"\r\n" + 
				"select distinct ?film where  {\r\n" + 
				"  { ?film a movie:film       } union \r\n" + 
				"  { ?film a dbpedia-owl:Film }\r\n" + 
				"  ?film rdfs:label ?label .\r\n" + 
				"  filter regex( str(?label), \"Batman\", \"i\")\r\n" + 
				"}\r\n" + 
				"limit 10";		
		org.apache.jena.query.Query query = QueryFactory.create(queryString);
		org.apache.jena.query.QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

		
		window = new JFrame("Movie Search Portal");
		window.setVisible(true);
		window.setSize(500,500);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(null);
		window.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Search Movie By KeyWord");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 27));
		lblNewLabel.setBounds(97, 11, 306, 50);
		panel.add(lblNewLabel);

		try {
			org.apache.jena.query.ResultSet results = qexec.execSelect();

			String x = org.apache.jena.query.ResultSetFormatter.asText(results, query);
			String y = String.format(x);
			System.out.println(y);
			
			 JTable tbl = new JTable();
			 DefaultTableModel dtm = new DefaultTableModel(0, 0);
			 String header[] = new String[] { "Film List" };
			// add header in table model     
			 dtm.setColumnIdentifiers(header);
			       tbl.setModel(dtm);
	
			       for (int count = 1; count <=1; count++) {

			    }

			       
			      
					btnSearch = new JButton("Search");
					btnSearch.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							  String textFieldValue = textField.getText();
							  JOptionPane.showMessageDialog(null, "Entered Value : " + y);
						}
					});
					btnSearch.setFont(new Font("Tahoma", Font.BOLD, 17));
					btnSearch.setBounds(364,72,120,45);
					panel.add(btnSearch);

			       
			       
//			lblNewLabel_1 = new JLabel(y);
//			lblNewLabel_1.setBounds(10, 145, 474, 315);
//			panel.add(lblNewLabel_1);

		    //while (results.hasNext()) {
		       // QuerySolution solution = results.next();
		    //}
		    			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		} finally {
			qexec.close();
		}

		
		
		textField = new JTextField();
		textField.setBounds(10, 72, 344, 45);
		panel.add(textField);
		textField.setColumns(10);
		
		 
		org.apache.jena.query.ResultSet results = qexec.execSelect();
		org.apache.jena.query.ResultSetFormatter.out(System.out, results, query);
		
		 while(results.hasNext())
		    {
		        QuerySolution sol = results.nextSolution();
		        
		        JOptionPane.showMessageDialog(null, "Entered Value : " + sol);		        
		        table = new JTable();
				table.setBounds(10, 137, 474, 323);
				panel.add(table);

		        DefaultTableModel dtm = new DefaultTableModel(0, 0);
				String header[] = new String[] { "Film"};
							 dtm.setColumnIdentifiers(header);
						       table.setModel(dtm);
							        dtm.addRow(new Object[] { sol });
		}
	}
}
