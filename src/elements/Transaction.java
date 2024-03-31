package elements;

class Transaction {
	

    public Transaction(SellingOrder sellingOrder, BuyingOrder buyingOrder) {
        this.sellingOrder = sellingOrder;
        this.buyingOrder = buyingOrder;
    }

	private BuyingOrder buyingOrder;
    public BuyingOrder getBuyingOrder() {
		return buyingOrder;
	}
	public void setBuyingOrder(BuyingOrder buyingOrder) {
		this.buyingOrder = buyingOrder;
	}

	private SellingOrder sellingOrder;
	public SellingOrder getSellingOrder() {
		return sellingOrder;
	}
	public void setSellingOrder(SellingOrder sellingOrder) {
		this.sellingOrder = sellingOrder;
	}
}