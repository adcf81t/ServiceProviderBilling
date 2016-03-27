package com.carolinasinternet.billing;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author susama saha
 * InternetServiceProviderBilling is the driver class to calculate & print the monthly bill.
 *
 */
public class InternetServiceProviderBilling 
{
	private SubscriptionPackage packageA=new SubscriptionPackage("A",9.95, 10, 2.00);   // package A, $9.95/month; 10 hours internet included. $2.00 each additional hour or fraction.
	private SubscriptionPackage packageB=new SubscriptionPackage("B",18.95, 25, 1.50);  // package B, $18.95/month; 25 hours internet included. $1.50 each additional hour or fraction.
	private SubscriptionPackage packageC=new SubscriptionPackage("C",23.50, 744, 0.00); // package C, $23.50/month; 744 hours internet included means unlimited Internet access. Total maximum hours in a month is 24*31=744
	
	//driver main method.
	public static void main(String[] args) 
	{
		InternetServiceProviderBilling ispBilling=new InternetServiceProviderBilling();
		Scanner in = new Scanner(System.in);
		String selectedPackage = "";
		double hours = 0.00;
		while(true)
		{
			System.out.println("Please select the internet package : (A/B/C):");
			selectedPackage=in.next();
			if (true==selectedPackage.toUpperCase().equals("A") || true==selectedPackage.toUpperCase().equals("B") || true==selectedPackage.toUpperCase().equals("C"))
				break;
			else
				System.out.println("Invalid package !  Please try again ! Valid internet packages are A,B,C !");
		}

		while(true)
		{
			System.out.println("Please select the internet usage hour(s) for the month : ");
			if (in.hasNextDouble())
			{
				hours = in.nextDouble();
				if (hours<=744.00) // total hours in a month is 24*31=744
					break;
				else
					System.out.println("Invalid input ! Please try again ! Example : Valid internet usage hours value : (0.00-744.00) ");
			}
			else
			{
				in.next();
				System.out.println("Invalid input ! Please try again ! Example : Valid internet usage hours value : (0.00-744.00) !");
			}
		}

		if (selectedPackage.toUpperCase().equals("A"))
		{
			ispBilling.printMonthBill(ispBilling.packageA,hours);
		}
		else if (selectedPackage.toUpperCase().equals("B"))
		{
			ispBilling.printMonthBill(ispBilling.packageB,hours);
		}
		else if (selectedPackage.toUpperCase().equals("C"))
		{
			ispBilling.printMonthBill(ispBilling.packageC,hours);
		}
	}
	
	/**
	 * @param pkg
	 * @param hours
	 * This method prints monthly bill amount in standard output console. 
	 * It also prints subscriber's potential savings amount 
	 */
	public void printMonthBill(SubscriptionPackage pkg, double hours)
	{
		if (null == pkg || hours<0.00)
			return; // this should never happen, but if it does happen due to invalid input then return without printing anything;
		
		double totalBillAmount=0.00;
		if (hours<=pkg.getIncludedHours())
			totalBillAmount=pkg.getCost();
		else
			totalBillAmount=pkg.getCost()+((java.lang.Math.ceil(hours))*pkg.getOverageHourlyRate());
		
		DecimalFormat df = new DecimalFormat("#.00");
		df.format(totalBillAmount);
		System.out.println("Total bill = $"+df.format(totalBillAmount));
		if (totalBillAmount>pkg.getCost())
		{
			if (pkg.getPackageName().equals("A"))
			{
				System.out.println("Switch your subscription to package B and save = $"+df.format(totalBillAmount-(packageB.getCost()+((java.lang.Math.ceil(hours))*packageB.getOverageHourlyRate()))) +" per month.");
				System.out.println("Switch your subscription to package C and save = $"+df.format(totalBillAmount-packageC.getCost()) +" per month.");
			}
			if (pkg.getPackageName().equals("B"))
			{
				System.out.println("Switch your subscription to package C and save = $"+df.format(totalBillAmount-packageC.getCost()) +" per month.");
			}
		}
		return;
	}
	
	/**
	 * 
	 * @author susama saha
	 * SubscriptionPackage is the class to represent a subscription package with all its attributes.
	 *
	 */
	private class SubscriptionPackage
	{
		String _packageName;
		double _cost;
		int _includedHours;
		double _overageHourlyRate;
		public SubscriptionPackage(String packageName, double cost, int includedHours, double overageHourlyRate)
		{
			_packageName=packageName;
			_cost=cost;
			_includedHours=includedHours;
			_overageHourlyRate=overageHourlyRate;
		}
		public double getCost() 
		{
			return _cost;
		}
		public int getIncludedHours() 
		{
			return _includedHours;
		}
		public double getOverageHourlyRate()
		{
			return _overageHourlyRate;
		}
		public String getPackageName() 
		{
			return _packageName;
		}
	}
}
