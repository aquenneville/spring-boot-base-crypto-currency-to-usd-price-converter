package github.aq.priceconverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/price")
public class PriceConverterController {

	Map<AssetPair, HistoricalAssetPairPriceList> historicalPrices;
	
	@RequestMapping(path="/{assetPairName}/{amount}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Order convertEthAmountToUsdPrice(@PathVariable String assetPairName, @PathVariable BigDecimal amount, @PathVariable String date) throws IOException {
		AssetPairPrice app = null;
		Order order = null;
		if (historicalPrices == null) {
			historicalPrices = new HashMap<>();
		}
		
		AssetPair assetPair = AssetPair.valueOf(assetPairName.toUpperCase());
		if (!historicalPrices.containsKey(assetPair)) {
			AssetPairPricesLoader appl = new AssetPairPricesLoader();
			Map<LocalDate, AssetPairPrice> historicalAssetPrices = appl.load(assetPair);
			HistoricalAssetPairPriceList historicalAssetPriceList = new HistoricalAssetPairPriceList();
			historicalAssetPriceList.setAssetPairPriceList(historicalAssetPrices);
			historicalAssetPriceList.setAssetPair(assetPair);
			historicalPrices.put(assetPair, historicalAssetPriceList);
			
		}
		if (historicalPrices.get(assetPair).getAssetPairPriceList().containsKey(LocalDate.parse(date))) {
			app = historicalPrices.get(assetPair).getAssetPairPriceList().get(LocalDate.parse(date));
			order = new Order();
			order.setApp(app);
			order.setAmount(amount);
			if (!assetPairName.toUpperCase().endsWith("USD")) {
				Order usdOrder = new PriceToUsdExchange(assetPair).convertPriceToUsd(LocalDate.parse(date), order.getValue());
				order.setOrder(usdOrder);
			}

		} else {
			throw new IllegalStateException("The price does not exist for this date");
		}
		return order;
	}
}
