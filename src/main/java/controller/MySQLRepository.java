package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Florist;
import model.Flower;
import model.MaterialTypeException;
import model.Ornament;
import model.Product;
import model.RepositoryException;
import model.Ticket;
import model.Tree;

public class MySQLRepository implements RepositoryInterface {
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	private String url = "jdbc:mysql://127.0.0.1:3306/florist";
	
	private String user = "root";
	
	private String password = "";
	
	private Connection conn;
	
	// Método auxiliar
	public ResultSet getData(String sql)
	{
		ResultSet rs=null;
		try
		{
			//Crear la conexión a la BD
			Class.forName(this.driver);
			this.conn = DriverManager.getConnection(this.url,this.user,
					this.password);
			
			//Preparar el comando de acceso a la BD (basado en el sql)
			Statement stmt = this.conn.createStatement();
			
			//Recibir el ResultSet de la BD
			rs = stmt.executeQuery(sql);
			
		}
		catch (Exception err)
		{
			System.out.println(err.getMessage());
		}
		
		return rs;
		
	}
	
	// Método auxiliar
	public void changeTable(String sql) {
		try {
			Class.forName(this.driver);
			this.conn = DriverManager.getConnection(this.url,this.user,
					this.password);
			this.conn.setAutoCommit(true);
			
			Statement stmt = this.conn.createStatement();
			
			stmt.executeUpdate(sql);
			
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}

	public Florist findFlorist() throws RepositoryException {
		Florist florist = null;
		
		try {
			String sql = "SELECT name FROM florists";
			ResultSet rs = getData(sql);
			if (rs.next()) {
				String name = rs.getString(1);
				florist = Florist.getInstance();
				florist.setName(name);
				getProducts();
				getTickets();
			}
			
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
					+ "al intentar recuperar la floristeria de la "
					+ "base de datos.");
		}
		
		return florist;
	}

	public void addFlorist(Florist florist) {
		
		deleteFlorist();
		
		String sql = "INSERT INTO florists (name) "
				+ "VALUES ('" + florist.getName() + "');";
		
		changeTable(sql);
	}
	
	public void deleteFlorist() {		
		
		String sql = "DELETE FROM florists";
		changeTable(sql);
		
		sql = "DELETE FROM flowers";
		changeTable(sql);
		
		sql = "DELETE FROM flowers_tickets";
		changeTable(sql);
		
		sql = "DELETE FROM ornaments";
		changeTable(sql);
		
		sql = "DELETE FROM ornaments_tickets";
		changeTable(sql);
		
		sql = "DELETE FROM tickets";
		changeTable(sql);
		
		sql = "DELETE FROM trees";
		changeTable(sql);
		
		sql = "DELETE FROM trees_tickets";
		changeTable(sql);
		
	}
	
	public void getProducts() throws MaterialTypeException {
		String sqlTrees = "SELECT * FROM trees";
		ResultSet trees = getData(sqlTrees);
		
		try {
			while (trees.next()) {
				String id = trees.getString("id");
				String name = trees.getString("name");
				double price = trees.getDouble("price");
				price = (Double) (Math.round(price * 1000.0) / 1000.0);
				double height = trees.getDouble("height");
				height = Math.round(height * 1000.00) / 1000.00;
				int quantity = trees.getInt("quantity");
				
				Florist.getInstance().getStock().addTree(new Tree(id, name, 
						price, (double) height, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sqlFlowers = "SELECT * FROM flowers";
		ResultSet flowers = getData(sqlFlowers);
		
		try {
			while (flowers.next()) {
				String id = flowers.getString("id");
				String name = flowers.getString("name");
				double price = flowers.getDouble("price");
				price = (Math.round(price * 1000.00) / 1000.00);
				String color = flowers.getString("color");
				int quantity = flowers.getInt("quantity");
				
				Florist.getInstance().getStock().addFlower(new Flower(id, 
						name, price, color, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sqlOrnaments = "SELECT * FROM ornaments";
		ResultSet ornaments = getData(sqlOrnaments);
		
		try {
			while (ornaments.next()) {
				String id = ornaments.getString("id");
				String name = ornaments.getString("name");
				double price = ornaments.getDouble("price");
				price = (Double) (Math.round(price * 1000.00) / 1000.00);
				String material = ornaments.getString("material");
				int quantity = ornaments.getInt("quantity");
				
				Florist.getInstance().getStock().addOrnament(new Ornament(id, 
						name, price, material, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addProduct(Product product) {
		String sql = "";
		
		if (product instanceof Tree) {
			Tree tree = (Tree) product;
			sql = "INSERT INTO trees (id, name, price, height, quantity) "
					+ "VALUES ('" + tree.getId() + "', '" + tree.getName() + 
					"', " + tree.getPrice() + ", " + tree.getHeight() + ", " + 
					tree.getQuantity() + ");";
		} else if (product instanceof Flower) {
			Flower flower = (Flower) product;
			sql = "INSERT INTO flowers (id, name, price, color, quantity) "
					+ "VALUES ('" + flower.getId() + "', '" + flower.getName() 
					+ "', " + flower.getPrice() + ", '" + flower.getColor() + 
					"', " + flower.getQuantity() + ");";
		} else if (product instanceof Ornament) {
			Ornament ornament = (Ornament) product;
			sql = "INSERT INTO ornaments (id, name, price, material, quantity) "
					+ "VALUES ('" + ornament.getId() + "', '" + 
					ornament.getName() + "', " + ornament.getPrice() + ", '" + 
					ornament.getMaterial() + "', " + ornament.getQuantity() 
					+ ");";
		}
		
		changeTable(sql);

	}

	public void updateProduct(Product product) {
		
		String sql = "";
		
		if (product instanceof Tree) {
			Tree tree = (Tree) product;
			sql = "UPDATE trees SET quantity=" + tree.getQuantity() + 
					" WHERE id='" + tree.getId() + "'";
		} else if (product instanceof Flower) {
			Flower flower = (Flower) product;
			sql = "UPDATE flowers SET quantity=" + flower.getQuantity() + 
					" WHERE id='" + flower.getId() + "'";
		} else if (product instanceof Ornament) {
			Ornament ornament = (Ornament) product;
			sql = "UPDATE ornaments SET quantity=" + ornament.getQuantity() + 
					" WHERE id='" + ornament.getId() + "'";
		}
		
		changeTable(sql);
	}

	public void deleteProduct(Product product) {
		String sql = "";
		
		if (product instanceof Tree) {
			Tree tree = (Tree) product;
			sql = "DELETE FROM trees WHERE id='" + tree.getId() + "'";
		} else if (product instanceof Flower) {
			Flower flower = (Flower) product;
			sql = "DELETE FROM flowers WHERE id='" + flower.getId() + "'";
		} else if (product instanceof Ornament) {
			Ornament ornament = (Ornament) product;
			sql = "DELETE FROM ornaments WHERE id='" + ornament.getId() + "'";
		}
		
		changeTable(sql);
	}

	public void addTicket(Ticket ticket) {
		changeTable("INSERT INTO tickets (id) VALUES (0)");
		
		ResultSet rs = getData("SELECT * FROM tickets");
		
		int ticketId = 0;
		try {
			while (rs.next()) {
				ticketId = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "";
		for (Product product : ticket.getPurchasedProducts()) {
			if (product instanceof Tree) {
				Tree tree = (Tree) product;
				sql = "INSERT INTO trees_tickets (id, ticket_id, name, price, "
						+ "height, quantity) " + "VALUES ('" + tree.getId() + 
						"', " + ticketId + ", '" + tree.getName() + "', " + 
						tree.getPrice() + ", '" + tree.getHeight() + "', " + 
						tree.getQuantity() + ")";
			} else if (product instanceof Flower) {
				Flower flower = (Flower) product;
				sql = "INSERT INTO flowers_tickets (id, ticket_id, name, "
						+ "price, "	+ "color, quantity) " + "VALUES ('" + 
						flower.getId() + "', " + ticketId + ", '" + 
						flower.getName() + "', " + flower.getPrice() + ", '" + 
						flower.getColor() + "', " + flower.getQuantity() + ")";
			} else if (product instanceof Ornament) {
				Ornament ornament = (Ornament) product;
				sql = "INSERT INTO ornaments_tickets (id, ticket_id, name, "
						+ "price, material, quantity) "	+ "VALUES ('" + 
						ornament.getName() + "', " + ticketId + ", '" + 
						ornament.getName() + "', " + ornament.getPrice() + 
						", '" + ornament.getMaterial() + "', " + 
						ornament.getQuantity() + ")";
			}
			
			changeTable(sql);
		}
	}
	
	public void getTickets() throws SQLException {
		TicketController ticketController = new TicketController();
		Ticket ticket = null;
		
		String sql = "SELECT * FROM tickets";
		ResultSet tickets = getData(sql);
		
		ResultSet flowers, ornaments, trees;
		int ticketId;
		String id, name, color, material;
		double price, height;
		int quantity;
		Product product;
		
		while(tickets.next()) {
			ticketController.addNewTicket();
			ticket = Florist.getInstance().getTicketCollection()
					.getLastTicket();
			
			ticketId = tickets.getInt("id");
			
			sql = "SELECT * FROM flowers_tickets WHERE ticket_id=" 
					+ ticketId;
			flowers = getData(sql);
			while(flowers.next()) {
				id = flowers.getString("id");
				name = flowers.getString("name");
				price = flowers.getDouble("price");
				color = flowers.getString("color");
				quantity = flowers.getInt("quantity");
				
				product = new Flower(id, name, price, color, quantity);
				ticket.addToPurchasedProducts(product);
			}
			
			sql = "SELECT * FROM ornaments_tickets WHERE ticket_id=" 
					+ ticketId;
			ornaments = getData(sql);
			while(ornaments.next()) {
				id = ornaments.getString("id");
				name = ornaments.getString("name");
				price = ornaments.getDouble("price");
				material = ornaments.getString("material");
				quantity = ornaments.getInt("quantity");
				
				product = new Ornament(id, name, price, material, quantity);
				ticket.addToPurchasedProducts(product);
			}
			
			sql = "SELECT * FROM trees_tickets WHERE ticket_id=" + ticketId;
			trees = getData(sql);
			while(trees.next()) {
				id = trees.getString("id");
				name = trees.getString("name");
				price = trees.getDouble("price");
				height = trees.getDouble("height");
				quantity = trees.getInt("quantity");
				
				product = new Tree(id, name, price, height, quantity);
				ticket.addToPurchasedProducts(product);
			}
		}
	}

}
