package com.Ashutosh.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	private Logger  logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping(path="/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable(name="from") String from,@PathVariable(name="to") String to,@PathVariable(name="quantity") BigDecimal quantity) {
		//Problem1-openfeign
		Map<String,String> urimap=new HashMap<String,String>();
		urimap.put("from",from);
		urimap.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity=new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,urimap);
		CurrencyConversionBean response=responseEntity.getBody();
		CurrencyConversionBean cb=new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,response.getPort());
		cb.setTotalCalculatedAmount(quantity.multiply(response.getConversionMultiple()));
		return cb;
	}
	
	@GetMapping(path="/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable(name="from") String from,@PathVariable(name="to") String to,@PathVariable(name="quantity") BigDecimal quantity) {
		System.out.println("INVOKED");
		CurrencyConversionBean response=proxy.retrieveExchangeValue(from, to);
		CurrencyConversionBean cb=new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,response.getPort());
		cb.setTotalCalculatedAmount(quantity.multiply(response.getConversionMultiple()));
		logger.info("{}",response);
		return cb;
	}
}
