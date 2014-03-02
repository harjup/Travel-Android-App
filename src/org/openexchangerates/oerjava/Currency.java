/* Copyright 2012 Demétrio de Castro Menezes Neto
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package org.openexchangerates.oerjava;

/**
 * All available currencies on http://openexchangerates.org/currencies.json
 * 
 * @author Demétrio Menezes Neto
 * 
 */
public enum Currency {
	/**
	 * United Arab Emirates Dirham
	 */
	AED("AED"),
	/**
	 * Afghan Afghani
	 */
	AFN("AFN"),
	/**
	 * Albanian Lek
	 */
	ALL("ALL"),
	/**
	 * Armenian Dram
	 */
	AMD("AMD"),
	/**
	 * Netherlands Antillean Guilder
	 */
	ANG("ANG"),
	/**
	 * Angolan Kwanza
	 */
	AOA("AOA"),
	/**
	 * Argentine Peso
	 */
	ARS("ARS"),
	/**
	 * Australian Dollar
	 */
	AUD("AUD"),
	/**
	 * Aruban Florin
	 */
	AWG("AWG"),
	/**
	 * Azerbaijani Manat
	 */
	AZN("AZN"),
	/**
	 * Bosnia-Herzegovina Convertible Mark
	 */
	BAM("BAM"),
	/**
	 * Barbadian Dollar
	 */
	BBD("BBD"),
	/**
	 * Bangladeshi Taka
	 */
	BDT("BDT"),
	/**
	 * Bulgarian Lev
	 */
	BGN("BGN"),
	/**
	 * Bahraini Dinar
	 */
	BHD("BHD"),
	/**
	 * Burundian Franc
	 */
	BIF("BIF"),
	/**
	 * Bermudan Dollar
	 */
	BMD("BMD"),
	/**
	 * Brunei Dollar
	 */
	BND("BND"),
	/**
	 * Bolivian Boliviano
	 */
	BOB("BOB"),
	/**
	 * Brazilian Real
	 */
	BRL("BRL"),
	/**
	 * Bahamian Dollar
	 */
	BSD("BSD"),
	/**
	 * Bhutanese Ngultrum
	 */
	BTN("BTN"),
	/**
	 * Botswanan Pula
	 */
	BWP("BWP"),
	/**
	 * Belarusian Ruble
	 */
	BYR("BYR"),
	/**
	 * Belize Dollar
	 */
	BZD("BZD"),
	/**
	 * Canadian Dollar
	 */
	CAD("CAD"),
	/**
	 * Congolese Franc
	 */
	CDF("CDF"),
	/**
	 * Swiss Franc
	 */
	CHF("CHF"),
	/**
	 * Chilean Unit of Account (UF)
	 */
	CLF("CLF"),
	/**
	 * Chilean Peso
	 */
	CLP("CLP"),
	/**
	 * Chinese Yuan
	 */
	CNY("CNY"),
	/**
	 * Colombian Peso
	 */
	COP("COP"),
	/**
	 * Costa Rican Colón
	 */
	CRC("CRC"),
	/**
	 * Cuban Peso
	 */
	CUP("CUP"),
	/**
	 * Cape Verdean Escudo
	 */
	CVE("CVE"),
	/**
	 * Czech Republic Koruna
	 */
	CZK("CZK"),
	/**
	 * Djiboutian Franc
	 */
	DJF("DJF"),
	/**
	 * Danish Krone
	 */
	DKK("DKK"),
	/**
	 * Dominican Peso
	 */
	DOP("DOP"),
	/**
	 * Algerian Dinar
	 */
	DZD("DZD"),
	/**
	 * Egyptian Pound
	 */
	EGP("EGP"),
	/**
	 * Ethiopian Birr
	 */
	ETB("ETB"),
	/**
	 * Euro
	 */
	EUR("EUR"),
	/**
	 * Fijian Dollar
	 */
	FJD("FJD"),
	/**
	 * Falkland Islands Pound
	 */
	FKP("FKP"),
	/**
	 * British Pound Sterling
	 */
	GBP("GBP"),
	/**
	 * Georgian Lari
	 */
	GEL("GEL"),
	/**
	 * Ghanaian Cedi
	 */
	GHS("GHS"),
	/**
	 * Gibraltar Pound
	 */
	GIP("GIP"),
	/**
	 * Gambian Dalasi
	 */
	GMD("GMD"),
	/**
	 * Guinean Franc
	 */
	GNF("GNF"),
	/**
	 * Guatemalan Quetzal
	 */
	GTQ("GTQ"),
	/**
	 * Guyanaese Dollar
	 */
	GYD("GYD"),
	/**
	 * Hong Kong Dollar
	 */
	HKD("HKD"),
	/**
	 * Honduran Lempira
	 */
	HNL("HNL"),
	/**
	 * Croatian Kuna
	 */
	HRK("HRK"),
	/**
	 * Haitian Gourde
	 */
	HTG("HTG"),
	/**
	 * Hungarian Forint
	 */
	HUF("HUF"),
	/**
	 * Indonesian Rupiah
	 */
	IDR("IDR"),
	/**
	 * Irish Pound
	 */
	IEP("IEP"),
	/**
	 * Israeli New Sheqel
	 */
	ILS("ILS"),
	/**
	 * Indian Rupee
	 */
	INR("INR"),
	/**
	 * Iraqi Dinar
	 */
	IQD("IQD"),
	/**
	 * Iranian Rial
	 */
	IRR("IRR"),
	/**
	 * Icelandic Króna
	 */
	ISK("ISK"),
	/**
	 * Jamaican Dollar
	 */
	JMD("JMD"),
	/**
	 * Jordanian Dinar
	 */
	JOD("JOD"),
	/**
	 * Japanese Yen
	 */
	JPY("JPY"),
	/**
	 * Kenyan Shilling
	 */
	KES("KES"),
	/**
	 * Kyrgystani Som
	 */
	KGS("KGS"),
	/**
	 * Cambodian Riel
	 */
	KHR("KHR"),
	/**
	 * Comorian Franc
	 */
	KMF("KMF"),
	/**
	 * North Korean Won
	 */
	KPW("KPW"),
	/**
	 * South Korean Won
	 */
	KRW("KRW"),
	/**
	 * Kuwaiti Dinar
	 */
	KWD("KWD"),
	/**
	 * Kazakhstani Tenge
	 */
	KZT("KZT"),
	/**
	 * Laotian Kip
	 */
	LAK("LAK"),
	/**
	 * Lebanese Pound
	 */
	LBP("LBP"),
	/**
	 * Sri Lankan Rupee
	 */
	LKR("LKR"),
	/**
	 * Liberian Dollar
	 */
	LRD("LRD"),
	/**
	 * Lesotho Loti
	 */
	LSL("LSL"),
	/**
	 * Lithuanian Litas
	 */
	LTL("LTL"),
	/**
	 * Latvian Lats
	 */
	LVL("LVL"),
	/**
	 * Libyan Dinar
	 */
	LYD("LYD"),
	/**
	 * Moroccan Dirham
	 */
	MAD("MAD"),
	/**
	 * Moldovan Leu
	 */
	MDL("MDL"),
	/**
	 * Malagasy Ariary
	 */
	MGA("MGA"),
	/**
	 * Macedonian Denar
	 */
	MKD("MKD"),
	/**
	 * Myanma Kyat
	 */
	MMK("MMK"),
	/**
	 * Mongolian Tugrik
	 */
	MNT("MNT"),
	/**
	 * Macanese Pataca
	 */
	MOP("MOP"),
	/**
	 * Mauritanian Ouguiya
	 */
	MRO("MRO"),
	/**
	 * Mauritian Rupee
	 */
	MUR("MUR"),
	/**
	 * Maldivian Rufiyaa
	 */
	MVR("MVR"),
	/**
	 * Malawian Kwacha
	 */
	MWK("MWK"),
	/**
	 * Mexican Peso
	 */
	MXN("MXN"),
	/**
	 * Malaysian Ringgit
	 */
	MYR("MYR"),
	/**
	 * Mozambican Metical
	 */
	MZN("MZN"),
	/**
	 * Namibian Dollar
	 */
	NAD("NAD"),
	/**
	 * Nigerian Naira
	 */
	NGN("NGN"),
	/**
	 * Nicaraguan Córdoba
	 */
	NIO("NIO"),
	/**
	 * Norwegian Krone
	 */
	NOK("NOK"),
	/**
	 * Nepalese Rupee
	 */
	NPR("NPR"),
	/**
	 * New Zealand Dollar
	 */
	NZD("NZD"),
	/**
	 * Omani Rial
	 */
	OMR("OMR"),
	/**
	 * Panamanian Balboa
	 */
	PAB("PAB"),
	/**
	 * Peruvian Nuevo Sol
	 */
	PEN("PEN"),
	/**
	 * Papua New Guinean Kina
	 */
	PGK("PGK"),
	/**
	 * Philippine Peso
	 */
	PHP("PHP"),
	/**
	 * Pakistani Rupee
	 */
	PKR("PKR"),
	/**
	 * Polish Zloty
	 */
	PLN("PLN"),
	/**
	 * Paraguayan Guarani
	 */
	PYG("PYG"),
	/**
	 * Qatari Rial
	 */
	QAR("QAR"),
	/**
	 * Romanian Leu
	 */
	RON("RON"),
	/**
	 * Serbian Dinar
	 */
	RSD("RSD"),
	/**
	 * Russian Ruble
	 */
	RUB("RUB"),
	/**
	 * Rwandan Franc
	 */
	RWF("RWF"),
	/**
	 * Saudi Riyal
	 */
	SAR("SAR"),
	/**
	 * Solomon Islands Dollar
	 */
	SBD("SBD"),
	/**
	 * Seychellois Rupee
	 */
	SCR("SCR"),
	/**
	 * Sudanese Pound
	 */
	SDG("SDG"),
	/**
	 * Swedish Krona
	 */
	SEK("SEK"),
	/**
	 * Singapore Dollar
	 */
	SGD("SGD"),
	/**
	 * Saint Helena Pound
	 */
	SHP("SHP"),
	/**
	 * Sierra Leonean Leone
	 */
	SLL("SLL"),
	/**
	 * Somali Shilling
	 */
	SOS("SOS"),
	/**
	 * Surinamese Dollar
	 */
	SRD("SRD"),
	/**
	 * São Tomé and Príncipe Dobra
	 */
	STD("STD"),
	/**
	 * Salvadoran Colón
	 */
	SVC("SVC"),
	/**
	 * Syrian Pound
	 */
	SYP("SYP"),
	/**
	 * Swazi Lilangeni
	 */
	SZL("SZL"),
	/**
	 * Thai Baht
	 */
	THB("THB"),
	/**
	 * Tajikistani Somoni
	 */
	TJS("TJS"),
	/**
	 * Turkmenistani Manat
	 */
	TMT("TMT"),
	/**
	 * Tunisian Dinar
	 */
	TND("TND"),
	/**
	 * Tongan Paʻanga
	 */
	TOP("TOP"),
	/**
	 * Turkish Lira
	 */
	TRY("TRY"),
	/**
	 * Trinidad and Tobago Dollar
	 */
	TTD("TTD"),
	/**
	 * New Taiwan Dollar
	 */
	TWD("TWD"),
	/**
	 * Tanzanian Shilling
	 */
	TZS("TZS"),
	/**
	 * Ukrainian Hryvnia
	 */
	UAH("UAH"),
	/**
	 * Ugandan Shilling
	 */
	UGX("UGX"),
	/**
	 * United States Dollar
	 */
	USD("USD"),
	/**
	 * Uruguayan Peso
	 */
	UYU("UYU"),
	/**
	 * Uzbekistan Som
	 */
	UZS("UZS"),
	/**
	 * Venezuelan Bolívar
	 */
	VEF("VEF"),
	/**
	 * Vietnamese Dong
	 */
	VND("VND"),
	/**
	 * Vanuatu Vatu
	 */
	VUV("VUV"),
	/**
	 * Samoan Tala
	 */
	WST("WST"),
	/**
	 * CFA Franc BEAC
	 */
	XAF("XAF"),
	/**
	 * East Caribbean Dollar
	 */
	XCD("XCD"),
	/**
	 * Special Drawing Rights
	 */
	XDR("XDR"),
	/**
	 * CFA Franc BCEAO
	 */
	XOF("XOF"),
	/**
	 * CFP Franc
	 */
	XPF("XPF"),
	/**
	 * Yemeni Rial
	 */
	YER("YER"),
	/**
	 * South African Rand
	 */
	ZAR("ZAR"),
	/**
	 * Zambian Kwacha
	 */
	ZMK("ZMK"),
	/**
	 * Zimbabwean Dollar (1980-2008)
	 */
	ZWD("ZWD"),
	/**
	 * Zimbabwean Dollar
	 */
	ZWL("ZWL");

    private String stringValue;
    private Currency(String toString)
    {
        stringValue = toString;
    }

    @Override
    public String toString()
    {
        return stringValue;
    }
}