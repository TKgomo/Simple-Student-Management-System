package studentMangementsystem;
import java.sql.*;
public class student
{
	private static Connection conn;
	private static Statement stmt;
	public static void main(String[] args)
 {

		
		try 
		{
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/eduvos","username","password");
			stmt=conn.createStatement();
			
			
			while(true)
			{
				System.out.println("Eduvos 2024 Simple - Student Management System - ITAPA-B21");
				System.out.println("----------------------");
				System.out.println("1. Create Student");
				System.out.println("2. Read Students");
				System.out.println("3. Update Student");
				System.out.println("4. Delete Student");
				System.out.println("5. Search Students");
				System.out.println("6. Quit");
				System.out.println("----------------------------");
				System.out.print("Enter your choice (1-6): ");
				String choice = System.console().readLine();
				System.out.println();
				switch (choice) 
				{
				case"1":
				createStudent();
				break;
				case "2":
				readStudent();
				break;
				case "3":
				updateStudent();
				break;
				case "4":
				deleteStudent();
				break;
				case "5":
				searchStudents();
				break;
				case"6":
				System.out.println("Thank you for using the bStudent Mnaagement System. Goodbye!!");
				
				conn.close();
				return;
				default:
					System.out.println("Invalid choice. Please try again. \n");
				}
			}
		}
 
			   
			
			
		
			catch(SQLException e)
			{
				System.out.println("Error: " + e.getMessage());
			}	
 }
 

private static void createStudent()
{
	try 
	{
		System.out.println("Enter studet name: ");
		String name = System.console().readLine();
		System.out.println("Enter student age: ");
		int age= Integer.parseInt(System.console().readLine());
		System.out.print("Enter student grade: ");
		String grade = System.console().readLine();
		String query = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, name);
		pstmt.setInt(2, age);
		pstmt.setString(3, grade);
		pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Student created successfully. \n");
	}
	catch (Exception e)
	{
		System.out.println("Error: "+ e.getMessage());
		
	}
}

private static void readStudent()
{
	try
	{
		String query = "SELECT * FROM students";
		ResultSet rs=stmt.executeQuery(query);
		
		if(!rs.isBeforeFirst())
				{
			System.out.println("No stsudent s found. ");
			
				}
		else
		{
			System.out.println("Student records: ");
			while(rs.next())
			{
				int id=rs.getInt("id");
				String name=rs.getString("Name");
				int age=rs.getInt("age");
				String grade=rs.getString("grade");
				
				System.out.println("ID: "+id+", Name: " + name + ", Age: " +age+ "Grade: "+grade);
				
			}
			
			System.out.println();
		}
	}
		catch (SQLException e)
		{
			System.out.println("Error: "+ e.getMessage());
			
		}
	}

private static void updateStudent()
{
	try
	{
	System.out.println("Enter student ID to update: ");
	int studentld = Integer.parseInt(System.console().readLine());
	String query = "SELECT * FROM students WHERE id = ?";
	PreparedStatement pstmt = conn.prepareStatement(query);
	pstmt.setInt(1, studentld);
	ResultSet rs = pstmt.executeQuery();
	if(!rs.isBeforeFirst())
	{
	System.out.println("Student not found.");
	}
	else
	rs.next();
	String currentName = rs.getString("name");
	int currentAge = rs.getInt("age");
	String currentGrade = rs.getString("grade");
	System.out.println("Current Name:" + currentName);
	System.out.print("Enter new name (leave blank to keep current): ");
	String newName = System.console().readLine();
	if (newName.isEmpty())
	{
	newName = currentName;
	System.out.println("Current Age:" + currentAge);
	System.out.print("'Enter new age (leave blank to keep current): ");
	String newAgeStr = System.console().readLine();
	int newAge = newAgeStr.isEmpty() ? currentAge : Integer.parseInt(newAgeStr);
	System.out.println("Current Grade:" + currentGrade);
	System.out.println("Enter new grade (leave blank to keep current): ");
	String newGrade = System.console().readLine();
	if (newGrade.isEmpty()) 
	{
	newGrade = currentGrade;
	}
	query = "UPDATE students SET name = ?, age = ?, grade=? WHERE id = ?";
	pstmt = conn.prepareStatement(query);
	pstmt.setString(1, newName);
	pstmt.setInt(2, newAge);
	pstmt.setString(3, newGrade);
	pstmt.setInt(4, studentld);
	pstmt.executeUpdate();
	pstmt.close();
	System.out.println("Student updated successfully. In");
	}
	rs.close();
	}catch (Exception e) {
	System.err.println("Error:" + e.getMessage());
	}
	}
	


private static void deleteStudent()
{
	try
	{
			System.out.print("'Enter student ID to delete: ");
			int studentld = Integer.parseInt(System.console().readLine());
			String query = "SELECT * FROM students WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, studentld);
			ResultSet rs = pstmt.executeQuery();
	   if (!rs.isBeforeFirst())
		{
			System.out.println("Student not found.");
		}
	
		else
		{
			query = "DELETE FROM students WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, studentld);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("Student deleted successfully.\n");
		}
		rs.close();
	}
	catch (Exception e) 
	{
		System.err.println("Error:" + e.getMessage());
	}
}

private static void searchStudents()
{
try
{
System.out.print("Enter search keyword: ");
String keyword = System.console().readLine();
String query = "SELECT * FROM students WHERE name LIKE ? OR grade LIKE ?";
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, "%" + keyword + "%"); pstmt.setString(2, "%" + keyword + "%");
ResultSet rs = pstmt.executeQuery();
if (!rs.isBeforeFirst()) 
{
System.out.println("No students found.");
}
else 
{
System.out.println("Search results: ");

while (rs.next()) 
{
	int id = rs.getInt("id");
	String name = rs.getString("name");
	int age = rs.getInt("age");
	String grade = rs.getString("grade");
	System.out.println("ID:" + id +", Name:" + name +", Age: " + age +", Grade: " + grade);
}
System.out.println();
}
rs.close();
}
catch (Exception e)
{
System.err.println("Error:" + e.getMessage());
}
}
}