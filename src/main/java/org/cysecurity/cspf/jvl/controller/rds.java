class Cl {
	private static Connection getRemoteConnection() {
		if (System.getenv("RDS_HOSTNAME") != null) {
		  try {
			  Class.forName("org.postgresql.Driver");
			  String dbName = "myRDSdb";
			  String userName = "foo";
			  String password = "foobarbaz";
			  String hostname = System.getenv("RDS_HOSTNAME");
			  String port = System.getenv("RDS_PORT");
			  String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
			  logger.trace("Getting remote connection with connection string from environment variables.");
			  Connection con = DriverManager.getConnection(jdbcUrl);
			  logger.info("Remote connection successful.");
			  return con;
			}
			catch (ClassNotFoundException e) { logger.warn(e.toString());}
			catch (SQLException e) { logger.warn(e.toString());}
		}
		return null;
	  }
	  
	String saveInvoiceData(string data, int id){
			try{
				Connection con = getRemoteConnection();
				Statement stmt = con.createStatement();
				String sql = "UPDATE INVOICE SET data = " + data + " WHERE ID = " + id;
				rs = stmt.executeQuery(sql);
				return rs.getString("Id");
			} catch (Exception exc){
				//
			}
			return null;
		}
}
