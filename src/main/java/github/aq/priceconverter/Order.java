package github.aq.priceconverter;

import java.math.BigDecimal;

public class Order {
	
	private AssetPairPrice app;
	private BigDecimal amount;
	private BigDecimal value;
	private Order order;

	public AssetPairPrice getApp() {
		return app;
	}

	public void setApp(AssetPairPrice app) {
		this.app = app;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
		if (app != null) {
			this.value = amount.multiply(new BigDecimal(app.getLast()));
		}
	}
	
	public BigDecimal getValue() {
		return value;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
