package control;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;import javax.swing.table.TableModel;

import model.student;
import service.DAOstudent;
import view.studentUI;

public class studentController implements ActionListener {

	private studentUI view;
	private DAOstudent daoStd;
	public studentController(studentUI view)
	{
		this.view = view;
		daoStd = DAOstudent.getInstance();
	}
	
	private void exportToCSV()  {
		JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this.view);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileToSave), "UTF-8"))) {
               
                    StringBuilder sb = new StringBuilder();
                    sb.append("ID Student"); sb.append(",");
                    sb.append("Fullname");  sb.append(",");
                    sb.append("Address");  sb.append(",");
                    sb.append("Note");  sb.append(",");
                    sb.append("Point");  sb.append(",");
                    sb.append("Image");  sb.append(",");
                    sb.append("\n");
                    writer.write(sb.toString());
                    
                    List<student> arr=  daoStd.selectAll();
                    for(student st : arr)
                    {
                    	 StringBuilder sb1 = new StringBuilder();
                    	sb1.append(st.getMHS()); sb1.append(",");
                        sb1.append(st.getTenHS());  sb1.append(",");
                        sb1.append(st.getDiachi());  sb1.append(",");
                        sb1.append(st.getGhichu());  sb1.append(",");
                        sb1.append(st.getDiem());  sb1.append(",");
                        sb1.append(st.getHinhanh());  sb1.append(",");
                        sb1.append("\n");
                        writer.write(sb1.toString());
                    } 
                } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            JOptionPane.showMessageDialog(this.view, "Export Successfully!");
            }
                  
	}
	private void readFileCSV(File file)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // read Header
            line = br.readLine();
            // read data
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
            	String[] data = line.split(",");
            	student st = new student(data[0], data[1], Double.parseDouble(data[4]), data[5], data[2], data[3]);
            	boolean check = daoStd.insert(st);
            	if(check)
            	{
            		this.view.insertOneElementIntoTable(st);
            		this.view.insertOneElementIntoTree(st);
            	}
            	
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.view, "Error reading CSV file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
	}
	
	private void importCSV()
	{
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));

        int userSelection = fileChooser.showOpenDialog(this.view);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            readFileCSV(selectedFile);
        }
	}
	
	
	
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// TODO Auto-generated method stub
		String str = e.getActionCommand();
		switch (str) {
		case "DELETE":
		{
			 String id = this.view.textIDSt.getText();
			    student st = daoStd.getStudent(id);
			    if(st == null)
			    {
			    	JOptionPane.showMessageDialog(this.view, "Student's ID is not valid");
			    	return;
			    }
			    else
			    {
			       	int rs = JOptionPane.showConfirmDialog(this.view, "Do you want to delete this student?", "Delete Student", JOptionPane.YES_NO_OPTION);
			       	if(rs == JOptionPane.YES_OPTION)
			       	{
			       		daoStd.delete(st);
			       		this.view.deleteOneElementInTable();
			       		this.view.DeleteFromJtree(id);
			       		this.view.resetTextField();
			       	}
			    }
			break;
		}
		
		case "UPLOAD":
		{
			JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
	        int result = fileChooser.showOpenDialog(this.view);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            studentUI.strImage = selectedFile.getAbsolutePath();
	            this.view.updatePanelImage();
	        }
			break;
		}
		case "INSERT":
		{
		    ///// check 
			if(this.view.textMark.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(view, "Point must not be empty");
		    	return;
			}
					student st = new student(this.view.textIDSt.getText(), this.view.textFullname.getText(), Double.parseDouble(this.view.textMark.getText()), this.view.strImage, this.view.textAddress.getText(), this.view.textNote.getText());
				    if(daoStd.isExist(st))
				    {
				       JOptionPane.showMessageDialog(this.view, "The student's ID is exist");	
				       return;
				    }
				    if(st.getMHS().isEmpty() || st.getTenHS().isEmpty() || st.getHinhanh().isEmpty() || st.getGhichu().isEmpty() || st.getDiachi().isEmpty() )
				    {
				    	JOptionPane.showMessageDialog(view, "Textfield must not be empty");
				    	return;
				    }
				    if(st.getDiem() < 0  || st.getDiem() > 10)
				    {
				    	JOptionPane.showMessageDialog(view, "Point is not valid");
				    	return;	
				    }
					
				    daoStd.insert(st);
				    JOptionPane.showMessageDialog(view, "Insert successfully!!!");
				    this.view.insertOneElementIntoTable(st);
				    this.view.insertOneElementIntoTree(st);
			    	break;
		}
		case "UPDATE":
		{
			if(this.view.textMark.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(view, "Point must not be empty");
		    	return;
			}
			//System.out.println(Double.parseDouble(this.view.textMark.getText()));
			System.out.println(this.view.strImage);
			student st = new student(this.view.textIDSt.getText(), this.view.textFullname.getText(), Double.parseDouble(this.view.textMark.getText()), this.view.strImage, this.view.textAddress.getText(), this.view.textNote.getText());
			 if(!daoStd.isExist(st))
			    {
			       JOptionPane.showMessageDialog(this.view, "The student is not exist!");	
			       return;
			    }
			    if(st.getMHS().isEmpty() || st.getTenHS().isEmpty() || st.getHinhanh().isEmpty() || st.getGhichu().isEmpty() || st.getDiachi().isEmpty() )
			    {
			    	JOptionPane.showMessageDialog(view, "Textfield must not be empty");
			    	return;
			    }
			    if(st.getDiem() < 0  || st.getDiem() > 10)
			    {
			    	JOptionPane.showMessageDialog(view, "Point is not valid");
			    	return;	
			    }
				
			    daoStd.edit(st);
			    JOptionPane.showMessageDialog(view, "Update successfully successfully!!!");
			    this.view.updateTable();
			    this.view.updateTree(st);
		    	break;
		}
		case "Import":
		{
			importCSV();
			break;
		}
		case "Export":
		{
		    exportToCSV();      	
			break;
		}
		case "Delete student":
		{
			int row = this.view.table.getSelectedRow();
			
			String id = (String)this.view.tableModel.getValueAt(row, 0);
			System.out.println(id);
			student st = daoStd.getStudent(id);
			
			daoStd.delete(st);
			this.view.deleteOneElementInTable();
       		this.view.DeleteFromJtree(id);
       		this.view.resetTextField();
	        
			break;
		}
		
		default:
			break;
		}
	}
}
