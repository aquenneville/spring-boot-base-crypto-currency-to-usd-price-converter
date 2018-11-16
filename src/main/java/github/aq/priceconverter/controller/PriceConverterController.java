package github.aq.priceconverter.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import github.aq.priceconverter.model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import github.aq.priceconverter.service.PriceToUsdExchange;

@RestController
@RequestMapping("/api/v1/")
public class PriceConverterController {

	//Map<AssetPair, HistoricalAssetPairPriceList> historicalPrices;
	private HistoricalAssetPairPriceDictionary dictionary;
	
	@RequestMapping(path="/altcoin/{assetPairName}/{price}/{amount}/{date}/value", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Order convertAssetAmountToUsdValue(@PathVariable String assetPairName, @PathVariable BigDecimal price,
            @PathVariable BigDecimal amount, @PathVariable String date) throws IOException {
	    AssetPairPrice assetPairPrice = null;
        Order order = null;
        if (AssetPair.contains(assetPairName)) {
			AssetPair assetPair = AssetPair.valueOf(assetPairName.toUpperCase());
			if (assetPairName.toUpperCase().endsWith("BTC") || assetPairName.toUpperCase().endsWith("ETH")) {
				if (dictionary == null) {
					dictionary = new HistoricalAssetPairPriceDictionary();
				}
				dictionary.addHistoricalBaseAssetPairPrices(assetPair);

				if (dictionary.hasAssetPairAndPrice(assetPair, date)) {
					order = new Order();
					assetPairPrice = new AssetPairPrice();
					assetPairPrice.setLast(price.toString());
					assetPairPrice.setAssetPair(assetPair);
					order.setApp(assetPairPrice);
					order.setAmount(amount);

					// in the case the asset pair does not end with USD
					if (!assetPairName.toUpperCase().endsWith("USD")) {
						Order usdOrder = new PriceToUsdExchange(assetPair).convertPriceToUsd(LocalDate.parse(date), order.getValue());
						order.setOrder(usdOrder);
					}

				} else {
					throw new IllegalStateException("The price does not exist for this date");
				}
			} else {
				throw new IllegalStateException("The asset pair needs to end with BTC or start with ETH.");
			}
		} else {
			throw new IllegalStateException("The asset pair " + assetPairName + " is unknown");
		}
        return order;
	}
	
	@RequestMapping(path="/{assetPairName}/{amount}/{date}/value", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Order convertAssetAmountToUsdValue(@PathVariable String assetPairName, @PathVariable BigDecimal amount, @PathVariable String date) throws IOException {
		AssetPairPrice app = null;
		Order order = null;
		if (AssetPair.contains(assetPairName)) {
			AssetPair assetPair = AssetPair.valueOf(assetPairName.toUpperCase());
			if (assetPairName.toUpperCase().endsWith("BTC") || assetPairName.toUpperCase().startsWith("ETH")) {
				if (dictionary == null) {
					dictionary = new HistoricalAssetPairPriceDictionary();
				}

				dictionary.addHistoricalBaseAssetPairPrices(assetPair);

				if (dictionary.hasAssetPairAndPrice(assetPair, date)) {
					app = dictionary.getAssetPairPrice(assetPair, date);
					order = new Order();
					order.setApp(app);
					order.setAmount(amount);
					// in the case the asset pair does not end with USD
					if (!assetPairName.toUpperCase().endsWith("USD")) {
						Order usdOrder = new PriceToUsdExchange(assetPair).convertPriceToUsd(LocalDate.parse(date), order.getValue());
						order.setOrder(usdOrder);
					}

				} else {
					throw new IllegalStateException("The price does not exist for this date");
				}
			} else {
				throw new IllegalStateException("The asset pair needs to end with BTC or start with ETH.");
			}
		} else {
			throw new IllegalStateException("The asset pair " + assetPairName + " is unknown");
		}
		return order;
	}
}
