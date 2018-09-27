package github.aq.priceconverter;

import java.math.BigDecimal;

public class Order {
	
	private AssetPairPrice app;
	private BigDecimal amount;
	private BigDecimal value;

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
		this.value = amount.multiply(new BigDecimal(app.getLast()));
	}
	
	public BigDecimal getValue() {
		return value;
	}
}
