package com.furnitureworld.service;


	

	import java.sql.Connection;

	import java.sql.PreparedStatement;

	import java.sql.SQLException;

	import com.furnitureworld.config.DbConfig;
	import com.furnitureworld.model.FurnitureModel;

	
	public class RegisterService {

		private Connection dbConn;

		/**
		 * Constructor initializes the database connection.
		 */
		public RegisterService() {
			try {
				this.dbConn = DbConfig.getDbConnection();
			} catch (SQLException | ClassNotFoundException ex) {
				System.err.println("Database connection error: " + ex.getMessage());
				ex.printStackTrace();
			}
		}

		public Boolean addFurniture(FurnitureModel user) {
			if (dbConn == null) {
				System.err.println("Database connection is not available.");
				return null;
			}

			
			String insertQuery = "INSERT INTO register(full_name, email, username, password,is_Admin) "
					+ "VALUES (?, ?, ?, ?, ?)";

			try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

				
				insertStmt.setString(1, user.getFull_name());
				insertStmt.setString(2, user.getEmail());
				insertStmt.setString(3, user.getUsername());
				insertStmt.setString(4, user.getPassword());
				insertStmt.setBoolean(5, false);

				return insertStmt.executeUpdate() > 0;
			} catch (SQLException e) {
				System.err.println("Error during registration: " + e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
}
