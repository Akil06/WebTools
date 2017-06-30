package com.ordermanage.exception;

public class QuoteException extends Exception {

	public QuoteException(String message)
	{
		super("QuoteException-"+message);
	}
	
	public QuoteException(String message, Throwable cause)
	{
		super("QuoteException-"+message,cause);
	}
	
}