package com.furnitureworld.service;


	

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	import com.furnitureworld.config.DbConfig;
	import com.furnitureworld.model.FurnitureModel;
	import com.furnitureworld.util.PasswordUtil;

	
	public class LoginService {

		private Connection dbConn;
		private boolean isConnectionError = false;

		
		public LoginService() {
			try {
				dbConn = DbConfig.getDbConnection();
			} catch (SQLException | ClassNotFoundException ex) {
				ex.printStackTrace();
				isConnectionError = true;
			}
		}

		
		public Boolean loginUser(FurnitureModel furnitureModel) {
			if (isConnectionError) {
				System.out.println("Connection Error!");
				return null;
			}

			String query = "SELECT username, password, is_Admin FROM register WHERE username = ?";
			try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
				stmt.setString(1, furnitureModel.getUsername());
				ResultSet result = stmt.executeQuery();

				if (result.next()) {
					furnitureModel.setIs_Admin(result.getBoolean("is_Admin"));
					return validatePassword(result, furnitureModel);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

			return false;
		}


		private boolean validatePassword(ResultSet result, FurnitureModel furnitureModel) throws SQLException {
			String dbUsername = result.getString("username");
			String dbPassword = result.getString("password");

			return dbUsername.equals(furnitureModel.getUsername())
					&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(furnitureModel.getPassword());
		}
	}

