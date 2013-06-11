/**
 * 
 */
package org.richfaces.datatablescroller;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.richfaces.demo.datafilterslider.DemoInventoryItem;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 02.03.2007
 * 
 */
public class DataTableScrollerBean {
	private static int DECIMALS = 1;
	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

	private List <DemoInventoryItem> allCars = null;

	public List <DemoInventoryItem> getAllCars() {
		synchronized (this) {
			if (allCars == null) {
				allCars = new ArrayList<DemoInventoryItem>();
				for (int k = 0; k <= 5; k++) {
					try{
						switch (k) {
						case 0:
							allCars.addAll(createCar("Chevrolet","Corvette", 5));
							allCars.addAll(createCar("Chevrolet","Malibu", 8));
							allCars.addAll(createCar("Chevrolet","S-10", 10));
							allCars.addAll(createCar("Chevrolet","Tahoe", 6));
							break;

						case 1:
							allCars.addAll(createCar("Ford","Taurus", 12));
							allCars.addAll(createCar("Ford","Explorer", 11));
							break;
						case 2:
							allCars.addAll(createCar("Nissan","Maxima", 9));
							break;
						case 3:
							allCars.addAll(createCar("Toyota","4-Runner", 7));
							allCars.addAll(createCar("Toyota","Camry", 15));
							allCars.addAll(createCar("Toyota","Avalon", 13));
							break;
						case 4:
							allCars.addAll(createCar("GMC","Sierra", 8));
							allCars.addAll(createCar("GMC","Yukon", 10));
							break;
						case 5:
							allCars.addAll(createCar("Infiniti","G35", 6));
							break;
						/*case 6:
							allCars.addAll(createCar("UAZ","469", 6));
							break;*/
						default:
							break;
						}
					}catch(Exception e){
						System.out.println("!!!!!!loadAllCars Error: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}

		return allCars;
	}

	public int genRand() {
		return rand(1,10000);
	}

	public List <DemoInventoryItem> createCar(String make, String model, int count){

		ArrayList <DemoInventoryItem> iiList = null;

		try{
			int arrayCount = count;

			DemoInventoryItem[] demoInventoryItemArrays = new DemoInventoryItem[arrayCount];

			for (int j = 0; j < demoInventoryItemArrays.length; j++){
				DemoInventoryItem ii = new DemoInventoryItem();

				ii.setMake(make);
				ii.setModel(model);
				ii.setStock(randomstring(6,7));
				ii.setVin(randomstring(14,15));
				ii.setMileage(new BigDecimal(rand(5000,80000)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setMileageMarket(new BigDecimal(rand(25000,45000)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setPrice(new Integer(rand(15000,55000)));
				ii.setPriceMarket(new BigDecimal(rand(15000,55000)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setDaysLive(rand(1,90));
				ii.setChangeSearches(new BigDecimal(rand(0,5)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setChangePrice(new BigDecimal(rand(0,5)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setExposure(new BigDecimal(rand(0,5)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setActivity(new BigDecimal(rand(0,5)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setPrinted(new BigDecimal(rand(0,5)).setScale(DECIMALS, ROUNDING_MODE));
				ii.setInquiries(new BigDecimal(rand(0,5)).setScale(DECIMALS, ROUNDING_MODE));
				demoInventoryItemArrays[j] = ii;

			}

			iiList = new ArrayList<DemoInventoryItem>(Arrays.asList(demoInventoryItemArrays));

		}catch(Exception e){
			System.out.println("!!!!!!createCategory Error: " + e.getMessage());
			e.printStackTrace();
		}
		return iiList;
	}

	public static int rand(int lo, int hi)
	{
		Random rn2 = new Random();
		//System.out.println("**" + lo);
		//System.out.println("**" + hi);
		int n = hi - lo + 1;
		int i = rn2.nextInt() % n;
		if (i < 0)
			i = -i;
		return lo + i;
	}

	public static String randomstring(int lo, int hi)
	{
		int n = rand(lo, hi);
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
			b[i] = (byte)rand('A', 'Z');
		return new String(b);
	}
}
