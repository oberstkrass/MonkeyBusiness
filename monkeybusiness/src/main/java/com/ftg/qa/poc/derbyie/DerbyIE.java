package com.ftg.qa.poc.derbyie;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DerbyIE {

	Connection con;
	String fileprefix;
	final boolean enableSQLLog;
	final boolean enableSQLExecute = true;

	/**
	 * 
	 * @param con
	 *            The Connection Object to the Database that tables are supposed to
	 *            be ex- or imported to/from.
	 */
	public DerbyIE(Connection con) {
		this.con = con;
		enableSQLLog = true;
	}

	/**
	 * 
	 * @param con
	 *            The Connection Object to the Database that tables are supposed to
	 *            be ex- or imported to/from.
	 * @param fileprefix
	 *            Methods will only create/import files starting with this prefix.
	 */
	public DerbyIE(Connection con, String fileprefix) {
		this.con = con;
		this.fileprefix = fileprefix;
		enableSQLLog = true;
	}

	/**
	 * 
	 * @param con
	 *            The Connection Object to the Database that tables are supposed to
	 *            be ex- or imported to/from.
	 * @param enableSQLLog
	 *            Every SQL Operation can be logged by setting {@link enableSQLLog}
	 *            to true.
	 */
	public DerbyIE(Connection con, boolean enableSQLLog) {
		this.con = con;
		this.enableSQLLog = enableSQLLog;
	}

	/**
	 * 
	 * @param con
	 *            The Connection Object to the Database that tables are supposed to
	 *            be ex- or imported to/from.
	 * @param fileprefix
	 *            Methods will only create/import files starting with this prefix.
	 * @param enableSQLLog
	 *            Every SQL Operation can be logged by setting {@link enableSQLLog}
	 *            to true.
	 */
	public DerbyIE(Connection con, String fileprefix, boolean enableSQLLog) {
		this.con = con;
		this.fileprefix = fileprefix;
		this.enableSQLLog = enableSQLLog;
	}

	/**
	 * Closes the connection.
	 */
	public final void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con = null;
	}

	/**
	 * 
	 * @param fileprefix
	 *            In case this set of data should have a different filenameprefix
	 *            than what is predefined for this instance of DerbyIE
	 * @throws IOException
	 * @throws SQLException
	 */
	public void export(String fileprefix) throws IOException, SQLException {
		String help = this.fileprefix;
		this.fileprefix = fileprefix;
		export();
		this.fileprefix = help;
	}

	/**
	 * Exports each table contained in the Database into .csv files - one each.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	public void export() throws IOException, SQLException {
		ArrayList<String> tables = getExistingTables();

		for (String table : tables) {
			export_table(table); // Export each table in the list
		}
	}

	/**
	 * Exports on table into a .csv file, seperating columns with "," and rows with
	 * ";"
	 * 
	 * @param tablename
	 *            The name of the table which is meant to be exported
	 * @throws IOException
	 *             File cannot be loaded
	 */
	public void export_table(String tablename) throws IOException {
		log("Exporting table: " + tablename);
		BufferedWriter bw = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			bw = new BufferedWriter(new FileWriter(fileprefix + "_" + tablename + ".csv"));

			s = con.createStatement();
			rs = print_and_execute_query("SELECT * FROM " + tablename + ";", s);
			ArrayList<String> columns = getColumnNamesAndTypes(rs);
			for (String column : columns) { // Write <ColumnName>-<ColumnType> into file and add a ',' as long as it
											// doesn't describe the last column
				bw.write(column);
				if (columns.indexOf(column) != columns.size() - 1)
					bw.write(",");
			}
			bw.write(";");
			bw.newLine();

			while (rs.next()) { // Iterate through the ResultSet (the table that is to export)
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) { // Iterate through the columns of one row
																				// of the ResultSet...
					bw.write(rs.getString(i)); // ... to write each field into the file
					if (i != rs.getMetaData().getColumnCount())
						bw.write(",");
				}
				bw.write(";");
				bw.newLine();
			}
			log("Export of " + tablename + " succesful");

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			bw.close();
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Updates (delete and import) all tables that a .csv file exists for.
	 * 
	 * @param prefix
	 *            Method only uses Files beginning with this prefix
	 */
	public final void delete_and_import(String prefix) {
		ArrayList<String> tables = getExistingTables();

		File folder = new File(".");
		String[] filenames = folder.list(); // Get all filenames from the ./ directory
		ArrayList<String> files = new ArrayList<String>();
		for (String filename : filenames) {
			if (filename.startsWith(prefix))
				if (tables.contains(filename.substring(prefix.length() + 1, filename.length() - 4))) // Only add to list
																										// if the table
																										// exists
					files.add(filename.substring(prefix.length() + 1,
							filename.length() - 4)); /*
														 * +1 because of the underscore in the filename -4 because of
														 * the .csv in the end
														 */
		}
		log("Fitting Tablenames in Files: " + files.toString());
		for (String file : files)
			delete_and_import(prefix, file); // Delete and import each file that is in the List
	}

	/**
	 * Updates (delete and import) one specific table
	 * 
	 * @param prefix
	 *            Make clear which save of {@link tablename} is supposed to be
	 *            imported
	 * @param tablename
	 *            Specify which table will be loaded
	 */
	public final void delete_and_import(String prefix, String tablename) {
		log("Starting to delete data and import table: " + tablename);
		try {
			isValidFile(prefix + "_" + tablename
					+ ".csv"); /*
								 * Checks for the file to be valid by checking if it exists, if each row has the
								 * necessary number of columns and if the headline has at least 2 Specifications
								 * (meant to be Name and Type) each
								 */
		} catch (InvalidFileException e) {
			logerr(tablename + " Failed validity check");
			e.printStackTrace();
			return;
		}
		log(tablename + " Passed validity check");

		try {
			Statement s = con.createStatement();
			print_and_execute("DELETE FROM " + tablename, s);
			log("Deleted all entries from " + tablename);
			Scanner in = new Scanner(new FileReader(prefix + "_" + tablename + ".csv"));
			ArrayList<String> touples = new ArrayList<String>(); // ArrayList for all touples/rows in the file
			while (in.hasNextLine()) {
				String temp = in.nextLine();
				temp = temp.substring(0, temp.length() - 1);
				touples.add(temp); // Adds the row without the ';' in the end to the ArrayList
				temp = null;
			}
			in.close();
			touples.remove(0); // Removes the first row because it contains the Columninformation (Metadata)
								// and not the real Data
			String[] temp;
			for (String touple : touples) { // Execute the following for each row
				temp = touple.split(","); // Split the row into a String array where each field contains the data of one
											// column
				String sql = "INSERT INTO " + tablename + " VALUES("; // Prepare SQL-INSERT Statement
				for (int i = 0; i < temp.length; i++)
					sql += "'" + temp[i] + "',"; // Example: INSERT INTO COMPANY
													// VALUES('5','Nicolas','18','Frankfurt','6000.0',
				sql = sql.substring(0, sql.length() - 1) + ");"; // Removes the last ',' and adds ");"
																	// Example: INSERT INTO COMPANY
																	// VALUES('5','Nicolas','18','Frankfurt','6000.0');
				print_and_execute(sql, s);
			}
			con.commit();
			s.close();
			log(tablename + " successfully imported");			
		} catch (FileNotFoundException | SQLException e) {
			try {
				logerr("Failed to import " + tablename);
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * Creates new Table and enters existing Data from file
	 * 
	 * @param prefix
	 *            Make clear which save of {@link tablename} is supposed to be
	 *            imported
	 * @param tablename
	 *            Specify which table will be created
	 */
	public final void create_and_import(String prefix, String tablename) {
		log("Starting to create and import table: " + tablename);
		try {
			isValidFile(prefix + "_" + tablename
					+ ".csv"); /*
								 * Checks for the file to be valid by checking if it exists, if each row has the
								 * necessary number of columns and if the headline has at least 2 Specifications
								 * (meant to be Name and Type) each
								 */
		} catch (InvalidFileException e) {
			logerr(tablename + " Failed validity check");
			e.printStackTrace();
			return;
		}
		log(tablename + " Passed validity check");
		
		try {
			DatabaseMetaData md = con.getMetaData();
			ResultSet tRS = md.getTables(null, null, "%", null);
			ArrayList<String> tables = new ArrayList<String>();
			while (tRS.next())
				tables.add(tRS.getString(3)); // Get all existing tablenames into one ArrayList

			if (tables.contains(tablename)) {
				logerr("Table already exists"); // Throw an Error if the table already exists
				return;
			}

			Scanner in;
			in = new Scanner(new FileReader(prefix + "_" + tablename + ".csv"));
			ArrayList<String> touples = new ArrayList<String>();
			while (in.hasNextLine()) {
				String temp = in.nextLine();
				temp = temp.substring(0, temp.length() - 1); // Adds the row without the ';' in the end to the ArrayList
				touples.add(temp);
			}
			in.close();
			String[] typesnames = touples.remove(0).split(","); // Seperate specifications of each column
			String[] temp;

			String sql = "CREATE TABLE " + tablename + "("; // Prepare SQL-CREATE-TABLE-Statement

			for (String typename : typesnames) {
				temp = typename.split("-"); // Split up each columns specifications...
				for (String data : temp)
					sql += data + " "; // ...and add them to the Statement...
				sql += ","; // ...with a ',' in the end
			}
			sql = sql.substring(0, sql.length() - 1); // Remove the last ','...
			sql += ");"; // ...and replace it with ");"

			Statement stmt = con.createStatement();
			print_and_execute(sql, stmt);

			delete_and_import(prefix, tablename); // Import the actual data into the new table

			con.commit();
			log("Created and imported table correctly: " + tablename);
		} catch (FileNotFoundException | SQLException e) {
			logerr("Failed to create and import table: " + tablename);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Imports all files in this folder into the database. If the corresponding
	 * table exists in the database its data will be overridden. If it doesn't
	 * already exist it will be created
	 * 
	 * @param prefix
	 */
	public final void import_all(String prefix) {
		File folder = new File(".");
		String[] filenames = folder.list(); // Get all filenames from the ./ directory
		ArrayList<String> files = new ArrayList<String>();
		for (String filename : filenames) {
			if (filename.startsWith(prefix))
				files.add(filename.substring(prefix.length() + 1,
						filename.length() - 4)); /*
													 * +1 because of the underscore in the filename -4 because of the
													 * .csv in the end
													 */
		}
		log("Fitting Tablenames in Files: " + files.toString());

		ArrayList<String> tables = getExistingTables();
		log("Tablesnames in Database: " + tables.toString());

		for (String file : files) {
			if (tables.contains(file))
				delete_and_import(prefix, file);
			else
				create_and_import(prefix, file);
		}

	}

	/**
	 * 
	 * @return the names of all tables in the database in an ArrayList
	 */
	private ArrayList<String> getExistingTables() {
		DatabaseMetaData md;
		ArrayList<String> tables = null;
		try {
			md = con.getMetaData();
			ResultSet tRS = md.getTables(null, null, "%", null);
			tables = new ArrayList<String>();
			while (tRS.next())
				tables.add(tRS.getString(3)); // Get all existing tablenames into one ArrayList
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	/**
	 * Checks for the file to be valid by checking if it exists, if each row has the
	 * necessary number of columns and if the headline has at least 2 Specifications
	 * (meant to be Name and Type) each
	 * 
	 * @param file
	 *            Requests the filename of the file that should be checked
	 * @throws InvalidFileException
	 */
	private void isValidFile(String file) throws InvalidFileException {
		try (Scanner input = new Scanner(new FileReader(file))) {
			ArrayList<String> lines = new ArrayList<String>();
			while (input.hasNextLine())
				lines.add(input.nextLine()); // Read all lines into an ArrayList
			int columncount = lines.get(0).split(",").length;
			for (String columnspecification : lines.get(0).split(","))
				if (columnspecification.split("-").length < 2)
					throw new InvalidFileException("Specifications Missing!"); // Each Column needs to have at least two
																				// specifications (Type and name)
			for (String line : lines)
				if (line.split(",").length != columncount)
					throw new InvalidFileException("Columncount doesn't match count of columns specified in header"); // Each
																														// Row
																														// needs
																														// to
																														// have
																														// as
																														// many
																														// Columns
																														// as
																														// the
																														// top
																														// row
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new InvalidFileException("File Not Found!");
		}
	}

	/**
	 * Equivalent to java.sql.statement.execute(String) but can additionally print
	 * the executed Statement to the console.
	 * 
	 * @param sql
	 *            SQL-Statement that's supposed to be executed
	 * @param stmt
	 *            Statement to execute the {@link sql} on
	 * @return true if the first result is a ResultSet object; false if it is an
	 *         update count or there are no results
	 */
	protected final boolean print_and_execute(String sql, Statement stmt) {
		log("Executing following SQL-statement: " + sql);
		try {
			boolean ret = false;
			if (enableSQLExecute)
				ret = stmt.execute(sql);
			log("Execution successful");
			return ret;
		} catch (SQLException e) {
			if(enableSQLLog) System.err.println("Execution failed");
			return false;
		}
	}

	/**
	 * Equivalent to java.sql.statement.executeUpdate(String) but can additionally
	 * print the executed Statement to the console.
	 * 
	 * @param sql
	 *            SQL-Statement that's supposed to be executed
	 * @param stmt
	 *            Statement to execute the {@link sql} on
	 * @return a ResultSet object that contains the data produced by the given
	 *         query; null on error
	 */
	protected final ResultSet print_and_execute_query(String sql, Statement stmt) {
		log("Executing following SQL-statement: " + sql);
		try {
			ResultSet rs = null;
			if (enableSQLExecute)
				rs = stmt.executeQuery(sql);
			log("Execution successful");
			return rs;
		} catch (SQLException e) {
			if (enableSQLLog)
				System.err.println("Execution failed");
			return null;
		}
	}

	/**
	 * Equivalent to java.sql.statement.executeUpdate(String) but can additionally
	 * print the executed Statement to the console.
	 * 
	 * @param sql
	 *            SQL-Statement that's supposed to be executed
	 * @param stmt
	 *            Statement to execute the {@link sql} on
	 * @return either (1) the row count for SQL Data Manipulation Language (DML)
	 *         statements or (2) 0 for SQL statements that return nothing or (3) -1
	 *         on error
	 */
	protected final int print_and_execute_update(String sql, Statement stmt) {
		log("Executing following SQL-statement: " + sql);
		try {
			int ret = 0;
			if (enableSQLExecute)
				ret = stmt.executeUpdate(sql);
			log("Execution successful");
			return ret;
		} catch (SQLException e) {
			if (enableSQLLog)
				System.err.println("Execution failed");
			return -1;
		}
	}

	/**
	 * 
	 * @param rs
	 * @return all Columnnames and types in an ArrayList in the following form:
	 *         <Label>-<Type>
	 */
	private final ArrayList<String> getColumnNamesAndTypes(ResultSet rs) {
		ArrayList<String> ret = new ArrayList<String>();
		try {
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				ret.add(rs.getMetaData().getColumnLabel(i) + "-" + rs.getMetaData().getColumnTypeName(i));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	private final void log(String log) {
		if(enableSQLLog) System.out.println(log);
	}
	
	private final void logerr(String log) {
		if(enableSQLLog) System.err.println(log);
	}
}
