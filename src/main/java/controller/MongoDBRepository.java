package controller;

import model.Florist;
import model.Flower;
import model.Ornament;
import model.Product;
import model.RepositoryException;
import model.Ticket;
import model.Tree;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


@SuppressWarnings({"unchecked", "rawtypes"})
public class MongoDBRepository implements RepositoryInterface {
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection floristName;
	private MongoCollection trees;
	private MongoCollection flowers;
	private MongoCollection ornaments;
	private MongoCollection tickets;
	
	public MongoDBRepository() {
		this.mongoClient = MongoClients.create("mongodb://localhost:27017");
		this.database = mongoClient.getDatabase("Florist");
		this.floristName = database.getCollection("floristName");
		this.trees = database.getCollection("trees");
		this.flowers = database.getCollection("flowers");
		this.ornaments = database.getCollection("ornaments");
		this.tickets = database.getCollection("tickets");
	}

	public Florist findFlorist() throws RepositoryException {
		Florist florist = Florist.getInstance();
		MongoCursor<Document> it = this.floristName.find().iterator();
		if (!it.hasNext()) {
			throw new RepositoryException("No hay ninguna floristería creada.");
		} else {
			florist.emptyFlorist();
			
			Document document = it.next();
			florist.setName((String)document.get("name"));
			
			it = this.trees.find().iterator();
			while (it.hasNext()) {
				document = it.next();
				String id = (String)document.get("_id");
				String name = (String)document.get("name");
				double price = (Double)document.get("price");
				int quantity = (Integer)document.get("quantity");
				Double height = (Double)document.get("height");
				Tree tree = new Tree(id, name, price, height, quantity);
				florist.getStock().addTree(tree);
			}
			
			it = this.flowers.find().iterator();
			while (it.hasNext()) {
				document = it.next();
				String id = (String)document.get("_id");
				String name = (String)document.get("name");
				double price = (Double)document.get("price");
				int quantity = (Integer)document.get("quantity");
				String color = (String)document.get("color");
				Flower flower = new Flower(id, name, price, color, quantity);
				florist.getStock().addFlower(flower);
			}
			
			it = this.ornaments.find().iterator();
			while (it.hasNext()) {
				document = it.next();
				String id = (String)document.get("_id");
				String name = (String)document.get("name");
				double price = (Double)document.get("price");
				int quantity = (Integer)document.get("quantity");
				String material = (String)document.get("material");
				Ornament ornament;
				ornament = new Ornament(id, name, price, material, quantity);
				florist.getStock().addOrnament(ornament);
			}
			
			it = this.tickets.find().iterator();
			while (it.hasNext()) {
				Ticket ticket = new Ticket();
				document = it.next();
				for (int i = 0; i < (Integer)document.get("size"); i++) {
					Document productDoc = (Document)((Document) document
						.get("purchasedProducts")).get(Integer.toString(i));
					Product product = null;
					String id = (String)productDoc.get("_id");
					String name = (String)productDoc.get("name");
					double price = (Double)productDoc.get("price");
					int quantity = (Integer)productDoc.get("quantity");
					
					if ((Double)productDoc.get("height") != null) {
						Double height = (Double)productDoc.get("height");
						product = new Tree(id, name, price, height, quantity);
					} else if ((String)productDoc.get("color") != null) {
						String color = (String)productDoc.get("color");
						product = new Flower(id, name, price, color, quantity);
					} else if ((String)productDoc.get("material") != null) {
						String material = (String)productDoc.get("material");
						product =
							new Ornament(id, name, price, material, quantity);
					}
					ticket.addToPurchasedProducts(product);
				}
				florist.getTicketCollection().addToTickets(ticket);	
			}
		}
		return florist;
	}

	
	public void addFlorist(Florist florist) {
		Document floristDoc = new Document();
		floristDoc.put("name", florist.getName());
		this.floristName.insertOne(floristDoc);
	}

	public void addProduct(Product product) {
		Document productDoc = new Document();  
        productDoc.put("_id", product.getId());
        productDoc.put("name", product.getName());
        productDoc.put("price", product.getPrice());
        productDoc.put("quantity", product.getQuantity());
		if (product instanceof Tree) {
	        productDoc.put("height", ((Tree)product).getHeight());
	        this.trees.insertOne(productDoc);
		} else if (product instanceof Flower) {
	        productDoc.put("color", ((Flower)product).getColor());
	        this.flowers.insertOne(productDoc);
		} else if (product instanceof Ornament) {
	        productDoc.put("material", ((Ornament)product).getMaterial());
	        this.ornaments.insertOne(productDoc);
		}
	}

	public void updateProduct(Product product) {
		MongoCollection collection = null;
		if (product instanceof Tree) {
	        collection = this.trees;
		} else if (product instanceof Flower) {
			collection = this.flowers;
		} else if (product instanceof Ornament) {
			collection = this.ornaments;
		}
		
		Document find = new Document();
		find.put("_id", product.getId());
		
		Document updatedProduct = new Document();
		updatedProduct.put("quantity", product.getQuantity());

		collection.updateOne(find, new Document("$set", updatedProduct));

	}

	public void deleteProduct(Product product) {
		MongoCollection collection = null;
		if (product instanceof Tree) {
	        collection = this.trees;
		} else if (product instanceof Flower) {
			collection = this.flowers;
		} else if (product instanceof Ornament) {
			collection = this.ornaments;
		}
		collection.deleteOne(new Document("_id", product.getId()));
	}

	public void addTicket(Ticket ticket) {
		Document list = new Document();
		Document bought = new Document();
		int i = 0;
		for (Product product : ticket.getPurchasedProducts()) {
			Document productDoc = new Document();  
	        productDoc.put("_id", product.getId());
	        productDoc.put("name", product.getName());
	        productDoc.put("price", product.getPrice());
	        productDoc.put("quantity", product.getQuantity());
			if (product instanceof Tree) {
		        productDoc.put("height", ((Tree)product).getHeight());
			} else if (product instanceof Flower) {
		        productDoc.put("color", ((Flower)product).getColor());
			} else if (product instanceof Ornament) {
		        productDoc.put("material", ((Ornament)product).getMaterial());
			}
			bought.append(Integer.toString(i), productDoc);
			i++;
		}
		list.append("purchasedProducts", bought);
		list.append("size", ticket.getPurchasedProducts().size());
		this.tickets.insertOne(list);
	}

	public void deleteFlorist() {
		this.floristName.deleteMany(new Document());
		this.trees.deleteMany(new Document());
		this.flowers.deleteMany(new Document());
		this.ornaments.deleteMany(new Document());
		this.tickets.deleteMany(new Document());
	}

}
