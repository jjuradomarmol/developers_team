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
import model.ProductTypeException;
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
			this.conn = DriverManager.getConnection(this.url,this.user,this.password);
			
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
			this.conn = DriverManager.getConnection(this.url,this.user,this.password);
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
					+ "al intentar recuperar la floristeria de la base de datos.");
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
	}
	
	public void getProducts() throws MaterialTypeException {
		String sqlTrees = "SELECT * FROM trees";
		ResultSet trees = getData(sqlTrees);
		
		try {
			while (trees.next()) {
				String name = trees.getString("name");
				double price = trees.getDouble("price");
				price = (Double) (Math.round(price * 1000.0) / 1000.0);
				double height = trees.getDouble("height");
				height = Math.round(height * 1000.00) / 1000.00;
				int quantity = trees.getInt("quantity");
				
				Florist.getInstance().getStock().addTree(new Tree(name, price, (double) height, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sqlFlowers = "SELECT * FROM flowers";
		ResultSet flowers = getData(sqlFlowers);
		
		try {
			while (flowers.next()) {
				String name = flowers.getString("name");
				double price = flowers.getDouble("price");
				price = (Math.round(price * 1000.00) / 1000.00);
				String color = flowers.getString("color");
				int quantity = flowers.getInt("quantity");
				
				Florist.getInstance().getStock().addFlower(new Flower(name, price, color, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sqlOrnaments = "SELECT * FROM ornaments";
		ResultSet ornaments = getData(sqlOrnaments);
		
		try {
			while (ornaments.next()) {
				String name = ornaments.getString("name");
				double price = ornaments.getDouble("price");
				price = (Double) (Math.round(price * 1000.00) / 1000.00);
				String material = ornaments.getString("material");
				int quantity = ornaments.getInt("quantity");
				
				Florist.getInstance().getStock().addOrnament(new Ornament(name, price, material, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getIdProduct(Product product) {
		int id = 0;
		
		String sql = "";
		
		if (product instanceof Tree) {
			Tree tree = (Tree) product;
			sql = "SELECT id FROM trees WHERE name='" + tree.getName() + 
					"' AND ROUND(price, 2)=ROUND(" + tree.getPrice() + 
					", 2) AND ROUND(height, 2)=ROUND(" + tree.getHeight() + ", 2)";
		} else if (product instanceof Flower) {
			Flower flower = (Flower) product;
			sql = "SELECT id FROM flowers WHERE name='" + flower.getName() + 
					"' AND ROUND(price, 2)=ROUND(" + flower.getPrice() + 
					", 2) AND color='" + flower.getColor() + "'";
		} else if (product instanceof Ornament) {
			Ornament ornament = (Ornament) product;
			sql = "SELECT id FROM ornaments WHERE name='" + ornament.getName() + 
					"' AND ROUND(price, 2)=ROUND(" + ornament.getPrice() + 
					", 2) AND material='" + ornament.getMaterial() + "'";
		}
		
		ResultSet rs = getData(sql);
		
		try {
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}

	public void addProduct(Product product) {
		String sql = "";
		
		if (product instanceof Tree) {
			Tree tree = (Tree) product;
			sql = "INSERT INTO trees (name, price, height, quantity) "
					+ "VALUES ('" + tree.getName() + "', " + tree.getPrice() + ", " + 
					tree.getHeight() + ", " + tree.getQuantity() + ");";
		} else if (product instanceof Flower) {
			Flower flower = (Flower) product;
			sql = "INSERT INTO flowers (name, price, color, quantity) "
					+ "VALUES ('" + flower.getName() + "', " + flower.getPrice() + ", '" + 
					flower.getColor() + "', " + flower.getQuantity() + ");";
		} else if (product instanceof Ornament) {
			Ornament ornament = (Ornament) product;
			sql = "INSERT INTO ornaments (name, price, material, quantity) "
					+ "VALUES ('" + ornament.getName() + "', " + ornament.getPrice() + ", '" + 
					ornament.getMaterial() + "', " + ornament.getQuantity() + ");";
		}
		
		changeTable(sql);

	}

	public void updateProduct(Product product) {
		
		String sql = "";
		
		if (product instanceof Tree) {
			Tree tree = (Tree) product;
			sql = "UPDATE trees SET quantity=" + tree.getQuantity() + 
					" WHERE name='" + tree.getName() + "' AND ROUND(price, 2)=ROUND(" +  
					tree.getPrice() + ", 2) AND ROUND(height, 2)=ROUND(" + tree.getHeight() + ", 2)";
		} else if (product instanceof Flower) {
			Flower flower = (Flower) product;
			sql = "UPDATE flowers SET quantity=" + flower.getQuantity() + 
					" WHERE name='" + flower.getName() + "' AND ROUND(price, 2)=ROUND(" + 
					flower.getPrice() + ", 2) AND color='" + flower.getColor() + "'";
		} else if (product instanceof Ornament) {
			Ornament ornament = (Ornament) product;
			sql = "UPDATE ornaments SET quantity=" + ornament.getQuantity() + 
					" WHERE name='" + ornament.getName() + "' AND ROUND(price, 2)=ROUND(" +
					ornament.getPrice() + ", 2) AND material='" + ornament.getMaterial() + "'";
		}
		
		changeTable(sql);
	}

	public void deleteProduct(Product product) {
		String sql = "";
		
		if (product instanceof Tree) {
			Tree tree = (Tree) product;
			sql = "DELETE FROM trees WHERE name='" + tree.getName() + 
					"' AND ROUND(price, 2)=ROUND(" +  
					tree.getPrice() + ", 2) AND ROUND(height, 2)=ROUND(" + tree.getHeight() + ", 2)";
		} else if (product instanceof Flower) {
			Flower flower = (Flower) product;
			sql = "DELETE FROM flowers WHERE name='" + flower.getName() + 
					"' AND ROUND(price, 2)=ROUND(" + 
					flower.getPrice() + ", 2) AND color='" + flower.getColor() + "'";
		} else if (product instanceof Ornament) {
			Ornament ornament = (Ornament) product;
			sql = "DELETE FROM ornaments WHERE name='" + ornament.getName() + 
					"' AND ROUND(price, 2)=ROUND(" +
					ornament.getPrice() + ", 2) AND material='" + ornament.getMaterial() + "'";
		}
		
		changeTable(sql);
	}

	public void addTicket(Ticket ticket) {
		changeTable("INSERT INTO tickets (id) VALUES (0)");
		
		ResultSet rs = getData("SELECT * FROM tickets");
		
		int id = 0;
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "";
		for (Product product : ticket.getPurchasedProducts()) {
			if (product instanceof Tree) {
				Tree tree = (Tree) product;
				sql = "INSERT INTO tickets_details (ticket_id, category_id, name, price, characteristic, quantity) "
						+ "VALUES (" + id + ", 1, '" + 
						tree.getName() + "', " + tree.getPrice() + ", '" + tree.getHeight() + "', " + tree.getQuantity() + ")";
			} else if (product instanceof Flower) {
				Flower flower = (Flower) product;
				sql = "INSERT INTO tickets_details (ticket_id, category_id, name, price, characteristic, quantity) "
						+ "VALUES (" + id + ", 2, '" + 
						flower.getName() + "', " + flower.getPrice() + ", '" + flower.getColor() + "', " + flower.getQuantity() + ")";
			} else if (product instanceof Ornament) {
				Ornament ornament = (Ornament) product;
				sql = "INSERT INTO tickets_details (ticket_id, category_id, name, price, characteristic, quantity) "
						+ "VALUES (" + id + ", 3, '" + 
						ornament.getName() + "', " + ornament.getPrice() + ", '" + ornament.getMaterial() + "', " + ornament.getQuantity() + ")";
			}
			
			changeTable(sql);
		}
	}
	
	public void getTickets() throws MaterialTypeException, ProductTypeException {
		String sql = "SELECT * FROM tickets_details";
		String name = "", color = "", material = "";
		double price, height;
		
		ResultSet rs = getData(sql);
		int last = -1, 
			current = -1,
			idCategory = -1,
			quantity = -1;
		TicketController ticketController = new TicketController();
		Ticket ticket = null;
		Product product = null;
		
		try {
			while (rs.next()) {
				current = rs.getInt("ticket_id");
				if (current != last) {
					ticketController.addNewTicket();
					ticket = Florist.getInstance().getTicketCollection().getLastTicket();
				}
				idCategory = rs.getInt("category_id");
				name = rs.getString("name");
				price = rs.getDouble("price");
				quantity = rs.getInt("quantity");
				
				if (idCategory == 1) {
					height = Double.parseDouble(rs.getString("characteristic"));
					product = new Tree(name, price, height, quantity);
				} else if (idCategory == 2) {
					color = rs.getString("characteristic");
					product = new Flower(name, price, color, quantity);
				} else if (idCategory == 3) {
					material = rs.getString("characteristic");
					product = new Ornament(name, price, material, quantity);
				}
				
				
				ticket.addToPurchasedProducts(product);
				
				last = current;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
