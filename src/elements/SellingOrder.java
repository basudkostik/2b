package elements;

public class SellingOrder extends Order implements Comparable<SellingOrder> {

    public SellingOrder(int traderID, double amount, double price) {
        super(traderID, amount, price);
    }

    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount=amount;
    }
    public double getPrice() {
        return this.price;
    }

    @Override
    public int compareTo(SellingOrder e) {
        if(this.getPrice() < e.getPrice()) {
            return -1;
        }else if(this.getPrice() > e.getPrice()){
            return 1;
        }else {
            if(this.amount>e.amount) {
                return -1;
            }else if(this.amount<e.amount) {
                return 1;
            }else {
                if (this.traderID < e.traderID) {
                    return -1;
                }else if(this.traderID > e.traderID) {
                    return 1;
                }else {
                    return 0;
                }
            }
        }
    }
}
