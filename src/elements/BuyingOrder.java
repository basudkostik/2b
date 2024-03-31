package elements;

public class BuyingOrder extends Order implements Comparable<BuyingOrder>{

    public BuyingOrder(int traderID, double amount, double price) {
        super(traderID, amount, price);
    }

    public double getAmount() {
        return super.getAmount();
    }
    public void setAmount(double amount) {
        this.amount=amount;
    }
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public int compareTo(BuyingOrder e) {
        if(this.price > e.price) {
            return -1;
        }else if(this.price < e.price){
            return 1;
        }else {
            if(this.amount>e.amount) {
                return -1;
            }else if(this.amount<e.amount) {
                return 1;
            }else {
                return Integer.compare(this.traderID, e.traderID);
            }
        }
    }
}
