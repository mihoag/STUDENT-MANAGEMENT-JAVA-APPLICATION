package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import control.studentController;
import model.student;
import service.DAOstudent;

import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

public class studentUI extends JFrame {

	public JTextArea textIDSt;
	public JTextArea textFullname;
	public JTextArea textAddress;
	public JTextArea textNote;
	public JTextArea textMark;
	private  JPanel panelImage;
	private JButton btnUpload;
	private JButton btnInsert;
	private JButton btnDelete;
	private  JButton btnUpdate;
	private List<student> arrStd;
    public JTable table;
    private DAOstudent daostd;
    public JTree tree;
    public DefaultTableModel tableModel;
	private  DefaultMutableTreeNode root ;
	private JPanel leftPanel;
    private JLabel lbImage = new JLabel();
    private JPopupMenu popUpmenuTable;
    private JMenuItem menuItemDelete;
	
	// Set image
	public static String strImage = "user.jpg";
	
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new studentUI().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void setDataforTable(List<student> arrStd)
	{
		 for(student st : arrStd)
		 {
			 Object[] rowData = new Object[] {st.getMHS(), st.getTenHS(), st.getDiachi(), st.getGhichu(), st.getDiem()};
			 tableModel.addRow(rowData);
		 }
	}
	
	public void initTable()
	{
		 this.table = new JTable();
		 arrStd = daostd.selectAll();
		 tableModel = new DefaultTableModel();
		 tableModel.addColumn("ID Student");
		 tableModel.addColumn("Fullname");
		 tableModel.addColumn("Address");
		 tableModel.addColumn("Note");
		 tableModel.addColumn("Point");
		 
		 setDataforTable(arrStd);
	     table = new JTable(tableModel);
	}
	
	public void setTextInformation(student st)
	{
		textIDSt.setText(st.getMHS());
		textFullname.setText(st.getTenHS());
		textAddress.setText(st.getDiachi());
		textNote.setText(st.getGhichu());
		textMark.setText(st.getDiem()+"");	
		System.out.println(st.getHinhanh());
		strImage = st.getHinhanh();
		updatePanelImage();
	}
	
	public void addMouseEventforJTable()
	{
           table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int cnt = table.getSelectedRow();
				//
				String id = (String) table.getValueAt(cnt, 0);
				// Get student
				student st=  daostd.getStudent(id);
				if(st != null)
				{
					setTextInformation(st);		 
				}   
			}
		}); 
	     
	}
	
	
	
	public void addMouseEventforJtree()
	{
		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			    if(selectedNode != null)
			    {
			    	  String id = selectedNode.getUserObject().toString();
					  student st = daostd.getStudent(id);
					  if(st != null)
					  {
						  setTextInformation(st);  
					  }
			    }
			}
		});
	}
	// 
	
	public void refresh()
	{
		// to refresh 
		this.repaint();
	}
	
	public void deleteOneElementInTable()
	{
		 tableModel.setRowCount(0);
		 arrStd = daostd.selectAll();
		 setDataforTable(arrStd);
		 refresh();
	}
	
	public void DeleteFromJtree(String id)
	{
		////
		 // Traverse children
        Enumeration<TreeNode> children = root.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
            String IDdelete = (String) childNode.getUserObject();
            //System.out.println(str);
            if(IDdelete.equals(id))
            {
            	DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        		DefaultMutableTreeNode selectedNode = childNode;
        		if (selectedNode != null) {
        		    // Get the parent node of the selected node
        		    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();

        		    if (parentNode != null) {
        		        // Remove the selected node from its parent
        		        model.removeNodeFromParent(selectedNode);
        		        // Now, you can update the UI
        		        model.reload(parentNode);
        		    }
        		}	
            	
            	
            }
        }
		
		
		
		
	}
	
	public void resetTextField()
	{
		textIDSt.setText("");
		textAddress.setText("");
		textFullname.setText("");
		textMark.setText("");
		textNote.setText("");
	}
	
	public void setDataForTree(List<student> arrStd)
	{
		 for(int i = 0 ; i < arrStd.size() ; i++)
	        {
	        	DefaultMutableTreeNode nd = new DefaultMutableTreeNode(arrStd.get(i).getMHS());
	        	root.add(nd);
	            nd.add(new DefaultMutableTreeNode("ID: " + arrStd.get(i).getMHS()));
	            nd.add(new DefaultMutableTreeNode("Fullname: " + arrStd.get(i).getTenHS()));
	            nd.add(new DefaultMutableTreeNode("Address: " + arrStd.get(i).getDiachi()));    
	        }
	}
	
	public void initJTree()
	{
		//create the root node
        root = new DefaultMutableTreeNode("List Students");
        //create the child nodes
        //DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Vegetables");
        setDataForTree(arrStd);
        tree = new JTree(root);
        tree.setBackground(new Color(255, 255, 255));
	}
	
	public void insertOneElementIntoTable(student st)
	{
		 tableModel.addRow(new Object[] {st.getMHS(), st.getTenHS(), st.getDiachi(), st.getGhichu(), st.getDiem()});
	}
	public void insertOneElementIntoTree(student st)
	{
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(st.getMHS());
		node.add(new DefaultMutableTreeNode("ID: " + st.getMHS()));
        node.add(new DefaultMutableTreeNode("Fullname: " + st.getTenHS()));
        node.add(new DefaultMutableTreeNode("Address: " + st.getDiachi()));      
        root.add(node);
        model.reload(root);
	}
	
	public void updateTable()
	{
		 tableModel.setRowCount(0);
		 arrStd = daostd.selectAll();
		 setDataforTable(arrStd);
		 refresh();
	}
	
	public void updateTree(student st)
	{
		    Enumeration<TreeNode> children = root.children();
	        while (children.hasMoreElements()) {
	            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
	            String IDUpdate = (String) childNode.getUserObject();
	            //System.out.println(str);
	            if(IDUpdate.equals(st.getMHS()))
	            {
	            	DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	        		DefaultMutableTreeNode selectedNode = childNode;
	        		if (selectedNode != null) {
	        		    // Get the parent node of the selected node
	        		    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();

	        		    if (parentNode != null) {
	        		        selectedNode.removeAllChildren();
	        		        selectedNode.add(new DefaultMutableTreeNode("ID: " + st.getMHS()));
	        		        selectedNode.add(new DefaultMutableTreeNode("Fullname: " + st.getTenHS()));
	        		        selectedNode.add(new DefaultMutableTreeNode("Address: " + st.getDiachi()));  
	        		      
	        		        model.reload(parentNode);
	        		    }
	        		}	
	            }	
	        }
	}
	
	/**
	 * Create the frame.
	 */
	public void updatePanelImage()
	{
	      panelImage.removeAll();  
	      //System.out.println(strImage);
		  ImageIcon img = new ImageIcon(strImage);
	        // Set the desired width and height
	      int width = 130;  // Width in pixels
	      int height = 130; // Height in pixels

	        // Create a scaled version of the ImageIcon
	      Image scaledImage = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
	      ImageIcon scaledIcon = new ImageIcon(scaledImage);
		  
	      lbImage.setIcon(scaledIcon);
		  panelImage.add(lbImage, BorderLayout.EAST);
		  refresh();
	}
	public studentUI() {
		daostd = DAOstudent.getInstance();
		this.setTitle("Student management");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(900, 770);
        this.setResizable(false);
        this.getContentPane().setLayout(null);
        
        
        // Add title 
        JLabel lblTitle = new JLabel("STUDENT MANAGEMENT");
        lblTitle.setForeground(new Color(184, 44, 14));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitle.setBounds(260, 10, 428, 48);
		this.getContentPane().add(lblTitle);
		
		// Create panel to hold both left pannel and right pannel
		JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.setBounds(0, 68, 886, 635);
        
        
        //// create left panel
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 255, 255));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        
        
        /// create right panel
        JPanel rightPanel = new JPanel();
        
        
        /// create spiltpanel 
        JSplitPane slp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightPanel);
        bigPanel.add(slp, BorderLayout.CENTER);
        
        //
        JPanel right_top = new JPanel();
	    right_top.setPreferredSize(new Dimension(0, 320));
	    JPanel right_bottom = new JPanel();
	    right_top.setLayout(new BorderLayout());
	    
	    
	    right_bottom.setLayout(null);
	   
	    rightPanel.setLayout(new BorderLayout());
	    JSplitPane slp1= new JSplitPane(JSplitPane.VERTICAL_SPLIT, right_top, right_bottom);
	    rightPanel.add(slp1, BorderLayout.CENTER);
	    
	    //  create Label ( Information detailed student)
	    JLabel lblIDStudent = new JLabel("ID Student");
	    lblIDStudent.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lblIDStudent.setBounds(10, 10, 100, 40);
	    right_bottom.add(lblIDStudent);
	    
	    JLabel lblFullName = new JLabel("Fullname");
	    lblFullName.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lblFullName.setBounds(10, 61, 100, 40);
	    right_bottom.add(lblFullName);
	    
	    JLabel lblAddress = new JLabel("Address");
	    lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lblAddress.setBounds(10, 111, 75, 40);
	    right_bottom.add(lblAddress);
	    
	    JLabel lblNote = new JLabel("Note");
	    lblNote.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lblNote.setBounds(10, 161, 75, 40);
	    right_bottom.add(lblNote);
	    
	    JLabel lblMark = new JLabel("Point");
	    lblMark.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    lblMark.setBounds(10, 211, 75, 40);
	    right_bottom.add(lblMark);
	    
	    
	    
	    /// create text field
	    
	    textIDSt = new JTextArea();
	    textIDSt.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    textIDSt.setBounds(120, 10, 292, 34);
	    right_bottom.add(textIDSt);
	    
	    textFullname = new JTextArea();
	    textFullname.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    textFullname.setBounds(120, 61, 292, 34);
	    right_bottom.add(textFullname);
	    
	    textAddress = new JTextArea();
	    textAddress.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    textAddress.setBounds(120, 111, 292, 34);
	    right_bottom.add(textAddress);
	    
	    textNote = new JTextArea();
	    textNote.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    textNote.setBounds(120, 161, 292, 34);
	    right_bottom.add(textNote);
	    
	    textMark = new JTextArea();
	    textMark.setFont(new Font("SansSerif", Font.PLAIN, 20));
	    textMark.setBounds(120, 211, 292, 34);
	    right_bottom.add(textMark);
	    
	    panelImage = new JPanel();
	    panelImage.setBounds(413, 48, 188, 179);
	    panelImage.setLayout(new BorderLayout());
	    right_bottom.add(panelImage);
	    
	    // add default image
	     updatePanelImage();
	    
	    
	    
	    
	    
	    //// // Labeling a component
	    lblIDStudent.setLabelFor(textIDSt);
	    lblFullName.setLabelFor(textFullname);
	    lblAddress.setLabelFor(textAddress);
	    lblNote.setLabelFor(textNote);
	    lblMark.setLabelFor(textMark);
	  
	    lblIDStudent.setDisplayedMnemonic('I');
	    lblFullName.setDisplayedMnemonic('F');
	    lblAddress.setDisplayedMnemonic('A');
	    lblNote.setDisplayedMnemonic('N');
	    lblMark.setDisplayedMnemonic('P');
	    
	    //// Create JTable
	    
	     initTable();
	     JScrollPane sc = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	     right_top.add(sc, BorderLayout.CENTER);
	    
	     /// add MouseEvent for Jtable
	     addMouseEventforJTable();
	    
	   
	     //// create JTree
	     initJTree();
	     leftPanel.add(tree, BorderLayout.CENTER);
	     addMouseEventforJtree();
	      
	     /// setting sort table
	     // insert code for sorting here...
	     table.setAutoCreateRowSorter(true);
	     TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
	     sorter.setComparator(4, Comparator.comparingDouble(Double::doubleValue)); 
	     table.setRowSorter(sorter);
	     
	     /// create button Upload image
	    btnUpload = new JButton("UPLOAD");
	    btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    btnUpload.setBounds(485, 239, 100, 29);
	    right_bottom.add(btnUpload);
	    
	    
	    ///// create button insert, update, delete,export, import
	    btnInsert = new JButton("INSERT");
	    btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    btnInsert.setBounds(92, 267, 100, 29);
	    right_bottom.add(btnInsert);
	    
	    btnDelete = new JButton("DELETE");
	    btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    btnDelete.setBounds(202, 267, 100, 29);
	    right_bottom.add(btnDelete);
	    
	    btnUpdate = new JButton("UPDATE");
	    btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    btnUpdate.setBounds(312, 267, 100, 29);
	    right_bottom.add(btnUpdate);
	    
	    /// create menu
	    JMenuBar menuBar = new JMenuBar();
	    JMenu menuFile = new JMenu("File");
	    JMenuItem menuImport = new JMenuItem("Import");
	    JMenuItem menuExport = new JMenuItem("Export");
	    
	    menuFile.add(menuImport);
	    menuFile.addSeparator();
	    menuFile.add(menuExport);
	    menuBar.add(menuFile);
	    this.setJMenuBar(menuBar);
	    
	    
	    // create Jpopup menu
	   popUpmenuTable= new JPopupMenu();
	   menuItemDelete= new JMenuItem("Delete student");
	   popUpmenuTable.add(menuItemDelete);
	   table.setComponentPopupMenu(popUpmenuTable);
	    
	    
	    /// Add Event Listener
	    studentController ac = new studentController(this);
	    btnUpload.addActionListener(ac);
	    btnDelete.addActionListener(ac);
	    btnUpdate.addActionListener(ac);
	    btnInsert.addActionListener(ac);
	    menuImport.addActionListener(ac);
	    menuExport.addActionListener(ac);
	    menuItemDelete.addActionListener(ac);    
	    
	    ///// delete
        this.getContentPane().add(bigPanel);		
	}

}
