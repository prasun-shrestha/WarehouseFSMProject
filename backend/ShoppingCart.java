package backend;
import java.util.*;
import java.io.*;
public class ShoppingCart implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<ShoppingCartItem> cart;

  public ShoppingCart() {
    cart = new LinkedList<ShoppingCartItem>();
  }

  // insert a product into the shopping cart and the quantity
  public boolean insertProductToCart(Product product, int quantity) {
    ShoppingCartItem item = new ShoppingCartItem(product, quantity);
    cart.add(item);
    return true;
  }

  public boolean removeProductFromCart(Product product) {
    Iterator<ShoppingCartItem> cartIter = getShoppingCartProducts();
    while(cartIter.hasNext()) {
      ShoppingCartItem next = cartIter.next();
      if(next.getProduct().equals( product.getId() )) {
        cart.remove(next);
        return true;
      }
    }
    
    return false;
  }
  
  public Iterator<ShoppingCartItem> getShoppingCartProducts() {
    return cart.iterator();
  }

  public int size() {
    return cart.size();
  }

  public double getTotalPrice() {
    double totalPrice = 0;
    Iterator<ShoppingCartItem> cartIterator = cart.iterator();

    while (cartIterator.hasNext()){
      ShoppingCartItem item = cartIterator.next();
      totalPrice += (item.getProduct().getSalePrice() * item.getQuantity());
    }
    
    return totalPrice;
  }
  
  public String toString() {
    return cart.toString();
  }
}
